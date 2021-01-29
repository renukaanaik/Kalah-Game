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
public class RuleCheckLastStoneInHomePitTest {
	
	KalahGameHelper kalahHelper = new KalahGameHelper();
	Kalah kalah;
	RuleDistributeStonesInPits distributeStonesInPits = new RuleDistributeStonesInPits();
	
	@Before
	public void setUp() {		
		
		kalah = new Kalah();
		kalahHelper.populateStonesInPits(kalah);
		kalah.setCurrentPlayer(Player.NORTH);
		
	}
	
	@Test
	public void checkNextPlayerWhenLastStoneInHomePit() {
		
		
		RuleCheckLastStoneInHomePit checkLastStoneInHomePit = new RuleCheckLastStoneInHomePit();
		kalah.setSelectedPit(0);
		
		distributeStonesInPits.applyRule(kalah);
		
		checkLastStoneInHomePit.applyRule(kalah);
		
		Assert.assertEquals(Player.NORTH, kalah.getNextPlayer());
		
	}
	
	@Test
	public void checkNextPlayerWhenLastStoneNotInHomePit() {
		
		RuleCheckLastStoneInHomePit checkLastStoneInHomePit = new RuleCheckLastStoneInHomePit();
		kalah.setSelectedPit(1);
		
		distributeStonesInPits.applyRule(kalah);
		
		checkLastStoneInHomePit.applyRule(kalah);
		
		Assert.assertEquals(Player.SOUTH, kalah.getNextPlayer());
		
	}	

}
