package com.game.kalah.service.rules;

import com.game.kalah.model.Kalah;
import com.game.kalah.model.Player;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * This class verifies if the last pit position is player's home pit then same player gets the next
 * turn
 *
 * @author Renuka Naik
 */
@Component
@Order(3)
public class RuleCheckLastStoneInHomePit implements KalahGameRule {

  Logger log = LoggerFactory.getLogger(RuleCheckLastStoneInHomePit.class);

  /**
   * This method verifies if the last pit position is player's home pit then same player gets the
   * next turn
   *
   * @param kalah {@link Kalah}
   */
  @Override
  public void applyRule(Kalah kalah) {

    Arrays.stream(Player.values()).forEach(player -> {
      if (player.equals(kalah.getCurrentPlayer()) && player.getHomePit() == kalah
          .getLastPitPosition()) {

        log.debug(
            "Last stone has landed in Players home pit, hence same player {} take turn again ",
            player.name());

        kalah.setNextPlayer(player);
      }
    });

    if (null == kalah.getNextPlayer()) {
      if (Player.NORTH.equals(kalah.getCurrentPlayer())) {
        kalah.setNextPlayer(Player.SOUTH);
      } else {
        kalah.setNextPlayer(Player.NORTH);
      }
    }

  }

}
