package com.game.kalah.service.rules;

import com.game.kalah.repository.KalahRepository;
import com.game.kalah.model.GameStatus;
import com.game.kalah.model.Kalah;
import com.game.kalah.model.Player;
import com.game.kalah.utils.KalahGameHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RuleCheckGameStatusTest {
	
	@InjectMocks
	RuleCheckGameStatus checkGameStatus = new RuleCheckGameStatus();

	KalahGameHelper kalahHelper = new KalahGameHelper();

	Kalah kalah;

	@Mock
	KalahRepository repository;

	@Before
	public void setUp() {

		kalah = new Kalah();
		kalahHelper.populateStonesInPits(kalah);
		kalah.setCurrentPlayer(Player.NORTH);

	}
	
	@Test
	public void checkGameStatus() {
		
		kalah.setSelectedPit(0);
		kalah.setNextPlayer(Player.SOUTH);

		// test set to replicate the scenario
		kalah.getPits()[0] = 0; kalah.getPits()[1] = 0; kalah.getPits()[2] = 0; kalah.getPits()[3] = 0; kalah.getPits()[4] = 0; kalah.getPits()[5] = 0;
		
		checkGameStatus.applyRule(kalah);
		
		Assert.assertEquals(GameStatus.FINISHED, kalah.getStatus());
		
	}
	
	@Test
	public void checkGameStatusNotFinished() {
		
		kalah = new Kalah();
		kalahHelper.populateStonesInPits(kalah);
		
		kalah.setSelectedPit(0);
		kalah.setNextPlayer(Player.SOUTH);
		kalah.setStatus(GameStatus.INPROGRESS);
		
		checkGameStatus.applyRule(kalah);
		
		Assert.assertEquals(GameStatus.INPROGRESS, kalah.getStatus());
		
	}
	
	@Test
	public void checkGameWinnerAndFinalPitPositionsDraw() {
		
		kalah = new Kalah();
		kalahHelper.populateStonesInPits(kalah);
		
		kalah.setSelectedPit(0);
		kalah.setNextPlayer(Player.SOUTH);
		kalah.setStatus(GameStatus.FINISHED);
		
		checkGameStatus.applyRule(kalah);
		
		Assert.assertEquals("DRAW",kalah.getWinner());
		
		Assert.assertArrayEquals(new int[] {0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 36}, kalah.getPits());
		
	}
	
	@Test
	public void checkGameWinnerAndFinalPitPositionsSouth() {
		
		kalah = new Kalah();
		kalahHelper.populateStonesInPits(kalah);
		
		kalah.getPits()[7] = 7;
		
		kalah.setSelectedPit(0);
		kalah.setNextPlayer(Player.SOUTH);
		kalah.setStatus(GameStatus.FINISHED);
		
		checkGameStatus.applyRule(kalah);
		
		Assert.assertEquals("SOUTH",kalah.getWinner());
		
		Assert.assertArrayEquals(new int[] {0, 0, 0, 0, 0, 0, 36, 0, 0, 0, 0, 0, 0, 37}, kalah.getPits());
		
	}
	
	@Test
	public void checkGameWinnerAndFinalPitPositionsNorth() {
		
		kalah = new Kalah();
		kalahHelper.populateStonesInPits(kalah);
		
		kalah.getPits()[0] = 7;
		
		kalah.setSelectedPit(0);
		kalah.setNextPlayer(Player.SOUTH);
		kalah.setStatus(GameStatus.FINISHED);
		
		checkGameStatus.applyRule(kalah);
		
		Assert.assertEquals("NORTH",kalah.getWinner());
		
		Assert.assertArrayEquals(new int[] {0, 0, 0, 0, 0, 0, 37, 0, 0, 0, 0, 0, 0, 36}, kalah.getPits());
		
	}
	


}
