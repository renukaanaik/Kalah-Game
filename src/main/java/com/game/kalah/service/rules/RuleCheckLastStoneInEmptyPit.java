package com.game.kalah.service.rules;

import com.game.kalah.model.Kalah;
import com.game.kalah.model.Player;
import com.game.kalah.utils.KalahConstants;
import com.game.kalah.utils.KalahGameHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * This class checks if the last stone of the player lands in his own empty pit, then move all the
 * stones from his last pit and all stones from opposite pit to his Kalah
 *
 * @author Renuka Naik
 */
@Component
@Order(4)
public class RuleCheckLastStoneInEmptyPit implements KalahGameRule {

  KalahGameHelper kalahHelper = new KalahGameHelper();
  Logger log = LoggerFactory.getLogger(RuleCheckLastStoneInEmptyPit.class);

  /**
   * This method checks if the last stone of the player lands in his own empty pit, then move all
   * the stones from his last pit and all stones from opposite pit to his Kalah
   *
   * @param kalah {@link Kalah}
   */
  @Override
  public void applyRule(Kalah kalah) {

    log.debug("Checking if last stone has landed in empty pit");

    // if next player is same as current player then, this Rule could be skipped as
    // last stone has landed in player's home pit
    if (null != kalah.getNextPlayer() && !kalah.getNextPlayer().equals(kalah.getCurrentPlayer())) {

      int[] pits = kalah.getPits();
      Player currentPlayer = kalah.getCurrentPlayer();

      // if last stone has landed in player's own pit
      if (pits[kalah.getLastPitPosition()] == 1
          && kalahHelper.isPlayersPit(kalah.getLastPitPosition(), currentPlayer)) {

        log.debug("Last stone has landed in players own empty pit, updating scores");
        // add last pit and opposite pit stones to Kalah
        addLastAndOppositePitStonesToKalah(kalah, pits, currentPlayer);
        // reset last and opposite pit to Zero
        setLastAndOppositePitToZero(kalah, pits);
      }
    }
  }

  /**
   * This method reset value of last pit and opposite pit to Zero
   *
   * @param kalah {@link Kalah}
   * @param pits  pits
   */
  private void setLastAndOppositePitToZero(Kalah kalah, int[] pits) {

    int oppositePitPosition = getOppositePitId(kalah.getLastPitPosition());
    // set opposite pit value to 0
    pits[oppositePitPosition] = 0;
    // set last pit position to 0
    pits[kalah.getLastPitPosition()] = 0;
  }

  /**
   * This method move last pit and opposite pits stones to Kalah
   *
   * @param kalah         {@link Kalah}
   * @param pits          pits
   * @param currentPlayer {@link Player}
   */
  private void addLastAndOppositePitStonesToKalah(Kalah kalah, int[] pits, Player currentPlayer) {

    int oppositePitPosition = getOppositePitId(kalah.getLastPitPosition());
    log.debug("Opposite pit id identified {} ", oppositePitPosition);
    // add last pit and opponent's stones to current player's Kalah
    pits[currentPlayer.getHomePit()] +=
        pits[oppositePitPosition] + pits[kalah.getLastPitPosition()];
    log.debug("Updated home pit score {}", pits[currentPlayer.getHomePit()]);

  }

  /**
   * This method identifies the opposite pit position
   *
   * @param lastPitPosition
   * @return opposite pit position
   */
  private int getOppositePitId(int lastPitPosition) {
    return KalahConstants.TOTAL_NUMBER_OF_PITS - 1 - lastPitPosition - 1;
  }

}
