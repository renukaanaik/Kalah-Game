package com.game.kalah.utils;

import com.game.kalah.exception.Errors;
import com.game.kalah.exception.KalahServiceException;
import com.game.kalah.model.Kalah;
import com.game.kalah.model.Player;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Helper class for Kalah service
 *
 * @author Renuka Naik
 */
public class KalahGameHelper {

  /**
   * This method populates initial number of stones in non home pits of the Kalah
   *
   * @param kalah {@link Kalah}
   * @return kalah {@link Kalah}
   */
  public Kalah populateStonesInPits(Kalah kalah) {

    int[] pits = kalah.getPits();
    IntStream.range(0, KalahConstants.TOTAL_NUMBER_OF_PITS).forEach(i -> {
      setPitValues(pits, i);
    });

    return kalah;

  }

  /**
   * This method set pit values
   *
   * @param pits pits array
   * @param i    index of pit to be set
   */
  private void setPitValues(int[] pits, int i) {
    if (!isHomePit(i)) {
      pits[i] = KalahConstants.NO_OF_STONES_IN_PIT;
    }
  }

  /**
   * This method validate if the position of the pit, home position of any player
   *
   * @param i position of the pit
   * @return isHomePit, is the position home pit of the kalah
   */
  public boolean isHomePit(int i) {

    return Arrays.stream(Player.values())
        .anyMatch(player -> i == player.getHomePit());

  }

  /**
   * This method identifies the {@linkplain Player} who has made the move
   *
   * @param kalah {@linkplain Kalah }
   * @param pitId select Pit Id of the move
   * @throws {@linkplain KalahServiceException }
   */
  public void identifyPlayer(Kalah kalah, int pitId) {

    Arrays.stream(Player.values()).forEach(player -> {
      if (pitId >= player.getPitStartPosition() && pitId <= player.getPitEndPosition()) {
        kalah.setCurrentPlayer(player);
      }
    });

    if (null == kalah.getCurrentPlayer()) {
      throw new KalahServiceException(Errors.INVALID_PIT_SELECTED.getErrorCode(),
          Errors.INVALID_PIT_SELECTED.getErrorMessage());
    }

  }

  /**
   * This method checks if the position of the last distributed stone belongs to the current
   * player.
   *
   * @param pitId  position of the last stone distributed
   * @param player current player
   * @return if the last pit position belongs to the current player
   */
  public boolean isPlayersPit(int pitId, Player player) {

    return (pitId >= player.getPitStartPosition() && pitId <= player.getPitEndPosition());

  }

}
