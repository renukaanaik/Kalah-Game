package com.game.kalah.service;

import com.game.kalah.repository.KalahRepository;
import com.game.kalah.exception.Errors;
import com.game.kalah.exception.KalahServiceException;
import com.game.kalah.model.GameStatus;
import com.game.kalah.model.Kalah;
import com.game.kalah.service.rules.KalahGameRule;
import com.game.kalah.utils.KalahGameHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KalahGameService {

Logger log = LoggerFactory.getLogger(KalahGameService.class);

  @Autowired
  List<KalahGameRule> kalahRuleEngine;

  @Autowired
  KalahRepository repository;

  KalahGameHelper kalahServiceHelper = new KalahGameHelper();


  /**
   * This method initialize the kalah Object with assigning 6 stones to all the non home pits. -
   * generates gameId - set game status INPROGRESS - stores kalah object in cache
   *
   * @return kalah {@link Kalah}
   */
  public Kalah startGame() throws KalahServiceException{

    List<Kalah> gamesInRepository = new ArrayList<Kalah>();
    repository.findAll().forEach(game -> gamesInRepository.add(game));

    if(!gamesInRepository.isEmpty())  {
      throw new KalahServiceException(Errors.GAME_ALREADY_IN_PROGRESS.getErrorCode(),
            Errors.GAME_ALREADY_IN_PROGRESS.getErrorMessage());
    }

    Kalah kalah = new Kalah();
    kalah.setGameId(new Random().nextInt(Integer.MAX_VALUE));
    kalah.setStatus(GameStatus.INPROGRESS);
    kalahServiceHelper.populateStonesInPits(kalah);
    repository.save(kalah);
    return kalah;
  }

  /**
   * This method 1. distribute stones of the selected pit as per the Kalah Rules 2. identify the
   * next user 3. verify the game status 4. When game is over, identifies the winner
   *
   * @param gameId id of the game
   * @param pitId  id of the selected pit
   * @return kalah {@link Kalah}
   * @throws KalahServiceException {@link KalahServiceException}
   */
  public Kalah playTurn(int gameId, int pitId) throws KalahServiceException {

    Kalah kalah = repository.findById(gameId).get(); //kalahCache.get(Integer.valueOf(gameId));

    if (null != kalah) {

      kalah.setCurrentPlayer(null);

      if (GameStatus.INPROGRESS.equals(kalah.getStatus())) {

        kalahServiceHelper.identifyPlayer(kalah, pitId);
        kalah.setSelectedPit(pitId);

        log.debug("Current player is {} taking turn for pit ", kalah.getCurrentPlayer().name(),
            kalah.getSelectedPit());

        kalahRuleEngine.forEach(kalahRule -> kalahRule.applyRule(kalah));

        log.info("Kalah state is " + kalah);

        log.debug("Saving Kalah object to Cache");
        repository.save(kalah);//kalahCache.save(kalah);

      } else {
        throw new KalahServiceException(Errors.INVALID_STATE.getErrorCode(),
            Errors.INVALID_STATE.getErrorMessage());
      }
    } else {
      throw new KalahServiceException(Errors.GAME_DOES_NOT_EXIST.getErrorCode(),
          Errors.GAME_DOES_NOT_EXIST.getErrorMessage());
    }

    return kalah;

  }

}
