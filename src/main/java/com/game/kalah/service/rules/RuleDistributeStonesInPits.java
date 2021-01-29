package com.game.kalah.service.rules;

import com.game.kalah.model.Kalah;
import com.game.kalah.model.Player;
import com.game.kalah.utils.KalahConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for distributing stones from selected pit to other pits skipping the
 * kalah of the opponent in anti-clockwise direction.
 *
 * @author Renuka Naik
 */
@Component
@Order(2)
public class RuleDistributeStonesInPits implements KalahGameRule {

  Logger log = LoggerFactory.getLogger(RuleDistributeStonesInPits.class);

  /**
   * This method distributes stones from selected pit to other pits skipping opponent's kalah in
   * anti-clockwise direction Also, resets pit position when calculated pit postion is greater than
   * total number of pits set last pit position for the rest of the rules of the game.
   */
  @Override
  public void applyRule(Kalah kalah) {

    int selectedPit = kalah.getSelectedPit();
    int[] pits = kalah.getPits();

    log.debug("Distributing stones from the selected pit {}", selectedPit);
    // if there are no stones in the selected pit, then last pit will be same as
    // selected pit
    int nextPit = selectedPit;
    // total number of stones to be distributed
    int noOfStonesInPit = pits[selectedPit];
    // remove stones from selected pit
    pits[selectedPit] = 0;

    for (int stones = 0; stones < noOfStonesInPit; stones++) {

      nextPit++;
      // verify pit position and skip home of opposite player
      nextPit = skipOppositePlayerHome(kalah, nextPit);
      // verify pit position and reset if greater than total number of Pit Positions
      nextPit = resetPitPosition(nextPit);
      pits[nextPit] += 1;

    }

    // set last pit position
    kalah.setLastPitPosition(nextPit);
    log.debug("Last seeded pit is {}", kalah.getLastPitPosition());

  }

	/**
   * This method resets the pit position , if next pit position calculated is greater than total
   * number of pits in Kalah
   *
   * @param nextPit calculated next pit position
   * @return next pit position, reseting to 1, calculated position is greater than total number of
   * pits
   */
  private int resetPitPosition(int nextPit) {
    if (nextPit > KalahConstants.TOTAL_NUMBER_OF_PITS - 1) {
      nextPit = 0;
    }
    return nextPit;
  }

  /**
   * @param kalah   {@link Kalah}
   * @param nextPit calculated next pit position
   * @return next pit position, skipping the kalah of opposite player
   */
  private int skipOppositePlayerHome(Kalah kalah, int nextPit) {

    if (Player.NORTH.equals(kalah.getCurrentPlayer())) {
      if (nextPit == Player.SOUTH.getHomePit()) {
        nextPit++;
      }
    } else {
      if (nextPit == Player.NORTH.getHomePit()) {
        nextPit++;
      }
    }
    return nextPit;
  }

}
