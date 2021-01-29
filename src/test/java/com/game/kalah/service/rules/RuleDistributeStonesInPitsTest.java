package com.game.kalah.service.rules;

import com.game.kalah.model.Kalah;
import com.game.kalah.model.Player;
import com.game.kalah.utils.KalahGameHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RuleDistributeStonesInPitsTest {
	
	KalahGameHelper kalahHelper = new KalahGameHelper();
	Kalah kalah;
	
	@Before
	public void setUp() {
		
		kalah = new Kalah();
		kalahHelper.populateStonesInPits(kalah);
		kalah.setCurrentPlayer(Player.NORTH);
		kalah.setNextPlayer(Player.SOUTH);
		
	}
	
	@Test
	public void distributeStonesInPitsTest() {
		
		RuleDistributeStonesInPits distributeStonesInPits = new RuleDistributeStonesInPits();
		kalah.setSelectedPit(0);
		
		distributeStonesInPits.applyRule(kalah);
		
		Assert.assertArrayEquals(new int[] {0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 6, 6, 0}, kalah.getPits());
		
		
	}
	
	@Test
	public void distributeStonesInPitsSkipOpponentKalah() {
		// also testing the house looping condition
		RuleDistributeStonesInPits distributeStonesInPits = new RuleDistributeStonesInPits();
		kalah.getPits()[0] = 15;
		kalah.setSelectedPit(0);
		
		distributeStonesInPits.applyRule(kalah);
		
		Assert.assertArrayEquals(new int[] {1, 8, 8, 7, 7, 7, 1, 7, 7, 7, 7, 7, 7, 0}, kalah.getPits());
		
		
	}

}
