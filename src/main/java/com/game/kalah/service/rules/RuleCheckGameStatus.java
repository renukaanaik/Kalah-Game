package com.game.kalah.service.rules;

import com.game.kalah.repository.KalahRepository;
import com.game.kalah.model.GameStatus;
import com.game.kalah.model.Kalah;
import com.game.kalah.model.Player;
import java.util.Arrays;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * This class verifies the final status of the game, sets final pits position and winner
 *
 * @author Renuka Naik
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class RuleCheckGameStatus implements KalahGameRule {

  Logger log = LoggerFactory.getLogger(RuleCheckGameStatus.class);

  @Autowired
  KalahRepository repository;

  /**
   * This method verifies the final status of the game based on sum of stones in each players pits.
   * If sum of stones in any player's pits is zero, then game is finished. Decides the winner based
   * on final scores and set final pit positions.
   *
   * @param kalah {@link Kalah}
   */
  @Override
  public void applyRule(Kalah kalah) {

    checkGameStatus(kalah);

    if (GameStatus.FINISHED.equals(kalah.getStatus())) {

      log.info("Game has finished, calculating final scores and winner");
      // sum of pits and home
      int sumOfStonesNorthPlayer = calculateFinalScore(kalah, Player.NORTH);
      log.debug("Final Sum of North Player is {} ", sumOfStonesNorthPlayer);
      // sum of pits and home
      int sumOfStonesSouthPlayer = calculateFinalScore(kalah, Player.SOUTH);
      log.debug("Final Sum of North Player is {} ", sumOfStonesSouthPlayer);

      setFinalPitPositions(kalah, sumOfStonesNorthPlayer, sumOfStonesSouthPlayer);

      compareScoresAndSetWinner(kalah);

      // remove game from cache
      repository.deleteById(kalah.getGameId());
      //kalahCache.remove(kalah.getGameId());
    }

  }

  /**
   * This method calculates total score i.e. sum of stones in the pits + stones in home pit
   *
   * @param kalah  {@link Kalah}
   * @param player {@link Player}
   * @return final score of the player
   */
  private int calculateFinalScore(Kalah kalah, Player player) {

    log.debug("calculating final scores");
    // sum of pits and home
    return getPitsSum(kalah.getPits(), player) + kalah.getPits()[player.getHomePit()];

  }

  /**
   * This method set the final position of the pits i.e. when game is finished set the home pits
   * with total scores and rest of the pits with 0.
   *
   * @param kalah                  {@link Kalah}
   * @param sumOfStonesNorthPlayer total sum of stones for North Player
   * @param sumOfStonesSouthPlayer total sum of stones for South Player
   */
  private void setFinalPitPositions(Kalah kalah, int sumOfStonesNorthPlayer,
      int sumOfStonesSouthPlayer) {

    log.debug("setting final pit positions");

    Arrays.fill(kalah.getPits(), 0);
    kalah.getPits()[Player.NORTH.getHomePit()] = sumOfStonesNorthPlayer;
    kalah.getPits()[Player.SOUTH.getHomePit()] = sumOfStonesSouthPlayer;

  }

  /**
   * This method compares final scores and set winner
   *
   * @param kalah {@link Kalah}
   */
  private void compareScoresAndSetWinner(Kalah kalah) {

    int compare = Integer.compare(kalah.getPits()[Player.NORTH.getHomePit()],
        kalah.getPits()[Player.SOUTH.getHomePit()]);

    if (compare == 0) {
      log.info("Both players have the same score, hence a DRAW");
      kalah.setWinner("DRAW");
    } else if (compare > 0) {
      log.info("Winner is NORTH Player. Game Finished Successfully");
      kalah.setWinner(Player.NORTH.name());
    } else {
      log.info("Winner is SOUTH Player. Game Finished Successfully");
      kalah.setWinner(Player.SOUTH.name());
    }
  }

  /**
   * This method calculates the sum of all the pits for each player
   *
   * @param pits   Kalah pits
   * @param player {@link Player}
   * @return sum of all the pits
   */
  private int getPitsSum(int[] pits, Player player) {

    log.debug("Calculating sum of pits for player {}", player.name());

    return IntStream.range(0, pits.length)
        .filter(
            pitId -> pitId >= player.getPitStartPosition() && pitId <= player.getPitEndPosition())
        .map(pitId -> pits[pitId]).sum();
  }

  /**
   * This method checks if the sum of all the pits is 0 for any player set game status to FINISHED
   *
   * @param kalah {@link Kalah}
   */
  private void checkGameStatus(Kalah kalah) {

    log.debug("Checking game status");

    Arrays.stream(Player.values()).forEach(player -> {
      int sum = getPitsSum(kalah.getPits(), player);
      if (sum == 0) {
        kalah.setStatus(GameStatus.FINISHED);
      }
    });
  }

}
