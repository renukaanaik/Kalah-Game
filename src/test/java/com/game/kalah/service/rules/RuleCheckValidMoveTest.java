package com.game.kalah.service.rules;

import com.game.kalah.exception.KalahServiceException;
import com.game.kalah.model.Kalah;
import com.game.kalah.model.Player;
import com.game.kalah.utils.KalahGameHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RuleCheckValidMoveTest {
	
	KalahGameHelper kalahHelper = new KalahGameHelper();
	Kalah kalah;
	
	@Before
	public void setUp() {
		
		kalah = new Kalah();
		kalahHelper.populateStonesInPits(kalah);		
		kalah.setNextPlayer(Player.SOUTH);
		
	}
	
	@Test(expected = KalahServiceException.class)
	public void checkInvalidMove() {
		
		kalah.setCurrentPlayer(Player.NORTH);
		
		RuleCheckValidMove checkIsMoveValid = new RuleCheckValidMove();
		checkIsMoveValid.applyRule(kalah);		
		
	}
	
	@Test
	public void checkValidMove() {
		
		kalah.setCurrentPlayer(Player.SOUTH);
		
		RuleCheckValidMove checkIsMoveValid = new RuleCheckValidMove();
		checkIsMoveValid.applyRule(kalah);
		
		Assert.assertNull(kalah.getNextPlayer());
		
	}

}
