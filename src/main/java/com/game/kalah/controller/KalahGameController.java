package com.game.kalah.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.game.kalah.exception.KalahServiceException;
import com.game.kalah.model.Kalah;
import com.game.kalah.service.KalahGameService;
import com.game.kalah.utils.KalahConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KalahGameController {

  @Autowired
  private KalahGameService kalahGameService;

  Logger log = LoggerFactory.getLogger(KalahGameController.class);

  @PostMapping(value = "/games")
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseEntity<Object> startGame() throws KalahServiceException {
    Map<String, Object> response = new HashMap<>();
    try {
      log.debug("Start method startKalah");
      Kalah kalah = kalahGameService.startGame();

      Link uri = linkTo(methodOn(KalahGameController.class).startGame()).slash(kalah.getGameId())
          .withSelfRel();

      log.info("Game successfully initiated");

      response.put(KalahConstants.ID, kalah.getGameId());
      response.put(KalahConstants.URI, uri.toUri());

    } catch (KalahServiceException kalahServiceException) {
      log.error("Exception while starting Game", kalahServiceException);
      return new ResponseEntity<Object>(kalahServiceException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<Object>(response, HttpStatus.CREATED);
  }

  /**
   * Method to handle request for the playing turn by the players.
   *
   * @param gameId id of the game created while start of the game
   * @param pitId  id of pit to be played by the player
   * @return ResponseEntity {@link ResponseEntity}
   */
  @PutMapping(value = "/games/{gameId}/pits/{pitId}")
  @Produces(MediaType.APPLICATION_JSON)
  public ResponseEntity<Object> playTurn(@PathVariable int gameId,
      @PathVariable int pitId)
      throws KalahServiceException {

    Map<String, Object> response = new HashMap<>();
    // subtract 1 to match with array position
    pitId = pitId - 1;

    try {

      Kalah kalah = kalahGameService.playTurn(gameId, pitId);
      Map<Integer, Integer> pitStatus = populatePitStatus(kalah.getPits());

      Link uri = linkTo(methodOn(KalahGameController.class).startGame()).slash(kalah.getGameId())
          .withSelfRel();

      response.put(KalahConstants.ID, kalah.getGameId());
      response.put(KalahConstants.URI, uri.toUri());
      response.put(KalahConstants.STATUS, pitStatus);

    } catch (KalahServiceException kalahServiceException) {
      log.error("Exception while executing Turn", kalahServiceException);
      return new ResponseEntity<Object>(kalahServiceException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<Object>(response, HttpStatus.OK);

  }

  /**
   * This method generates the response from kalah pits array
   *
   * @param pits pits of the kalah
   * @return response populated from pits
   */
  private Map<Integer, Integer> populatePitStatus(int[] pits) {
    Map<Integer, Integer> pitStatus = new HashMap<>();
    IntStream.range(0, pits.length).forEachOrdered(pitId -> pitStatus.put(pitId + 1, pits[pitId]));
    return pitStatus;
  }

}
