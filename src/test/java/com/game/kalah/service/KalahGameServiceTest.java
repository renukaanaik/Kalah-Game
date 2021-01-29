package com.game.kalah.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.game.kalah.repository.KalahRepository;
import com.game.kalah.exception.KalahServiceException;
import com.game.kalah.model.GameStatus;
import com.game.kalah.model.Kalah;
import com.game.kalah.service.rules.KalahGameRule;
import com.game.kalah.utils.KalahGameHelper;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class KalahGameServiceTest {

	@InjectMocks
	KalahGameService kalahService = new KalahGameService();
	
	@Mock
	List<KalahGameRule> kalahRuleEngine;

	@Mock
	KalahRepository repository;


	@Test
	public void startGameTest() {
		
		Kalah kalah = kalahService.startGame();

		Assert.assertArrayEquals(new int[] { 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0 }, kalah.getPits());

	}

	@Test
	public void playTurnTest() {

		int gameId = 1;
		int pitId = 0;

		
		when(repository.findById(gameId)).thenReturn(java.util.Optional.of(getKalah()));

		Kalah kalah = kalahService.playTurn(gameId, pitId);
		Assert.assertNotNull(kalah);

	}
	
	@Test(expected= RuntimeException.class)
	public void playTurnGameNotPresent() {

		int gameId = 1;
		int pitId = 0;
		
		when(repository.findById(any(Integer.class))).thenReturn(null);
		kalahService.playTurn(gameId, pitId);	

	}
	
	@Test(expected= KalahServiceException.class)
	public void playTurnGameFinished() {

		int gameId = 1;
		int pitId = 0;
		Kalah kalah = getKalah();
		kalah.setStatus(GameStatus.FINISHED);
		
		when(repository.findById(any(Integer.class))).thenReturn(java.util.Optional.of(kalah));
		kalahService.playTurn(gameId, pitId);	

	}
	
	private Kalah getKalah() {
		
		KalahGameHelper kalahHelper = new KalahGameHelper();
		
		Kalah kalah = new Kalah();
		kalah.setStatus(GameStatus.INPROGRESS);
		kalahHelper.populateStonesInPits(kalah);
		
		return kalah;
		
	}



}
