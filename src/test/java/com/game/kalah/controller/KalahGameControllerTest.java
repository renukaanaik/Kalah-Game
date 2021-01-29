package com.game.kalah.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.game.kalah.controller.KalahGameController;
import com.game.kalah.exception.KalahServiceException;
import com.game.kalah.model.Kalah;
import com.game.kalah.model.GameStatus;
import com.game.kalah.service.KalahGameService;
import com.game.kalah.utils.KalahGameHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class KalahGameControllerTest {
	
	@InjectMocks
	KalahGameController kalahController;
	
	@Mock
	KalahGameService kalahService;
	
	@Test
	public void startGame() {
		
		when(kalahService.startGame()).thenReturn(getKalah());
		
		ResponseEntity<Object> response = kalahController.startGame();
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
	}

	@Test
	public void startGame_alreadyExistsException() {

		when(kalahService.startGame()).thenThrow(KalahServiceException.class);

		ResponseEntity<Object> response = kalahController.startGame();
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}
	
	@Test
	public void playTurn() {
		
		int gameId = 1;
		int pitId = 0;

		when(kalahService.playTurn(any(Integer.class),any(Integer.class))).thenReturn(getKalah());		
		ResponseEntity<Object> response = kalahController.playTurn(gameId,pitId);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	
	}
	
	@Test
	public void playTurn_BadRequset_400() {
		
		int gameId = 1;
		int pitId = 0;

		when(kalahService.playTurn(any(Integer.class),any(Integer.class))).thenThrow(
        KalahServiceException.class);
		ResponseEntity<Object> response = kalahController.playTurn(gameId, pitId);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	
	}
	
	private Kalah getKalah() {
		
		KalahGameHelper kalahHelper = new KalahGameHelper();
		
		Kalah kalah = new Kalah();
		kalah.setStatus(GameStatus.INPROGRESS);
		kalahHelper.populateStonesInPits(kalah);
		
		return kalah;
		
	}

}
