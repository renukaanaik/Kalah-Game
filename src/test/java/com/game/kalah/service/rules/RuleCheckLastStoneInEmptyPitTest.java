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
public class RuleCheckLastStoneInEmptyPitTest {

	KalahGameHelper kalahHelper = new KalahGameHelper();
	Kalah kalah;
	RuleDistributeStonesInPits distributeStonesInPits = new RuleDistributeStonesInPits();
	RuleCheckLastStoneInEmptyPit checkLastStoneInEmptyPit = new RuleCheckLastStoneInEmptyPit();

	@Before
	public void setUp() {

		kalah = new Kalah();
		kalahHelper.populateStonesInPits(kalah);
		kalah.setCurrentPlayer(Player.NORTH);

	}

	@Test
	public void checkLastStoneInEmptyPit() {

		kalah.setSelectedPit(0);
		kalah.setNextPlayer(Player.SOUTH);

		// test set to replicate the scenario
		kalah.getPits()[0] = 5;
		kalah.getPits()[5] = 0;

		distributeStonesInPits.applyRule(kalah);

		checkLastStoneInEmptyPit.applyRule(kalah);

		Assert.assertEquals(7, kalah.getPits()[kalah.getCurrentPlayer().getHomePit()]);

	}

	@Test
	public void checkLastStoneNotInEmptyPit() {

		kalah.setSelectedPit(0);
		kalah.setNextPlayer(Player.SOUTH);

		// test set to replicate the scenario
		kalah.getPits()[0] = 5;

		distributeStonesInPits.applyRule(kalah);

		checkLastStoneInEmptyPit.applyRule(kalah);

		Assert.assertEquals(0, kalah.getPits()[kalah.getCurrentPlayer().getHomePit()]);

	}

	@Test
	public void checkLastStoneInOpponentsEmptyPit() {

		kalah.setSelectedPit(0);
		kalah.setNextPlayer(Player.SOUTH);

		// test set to replicate the scenario
		kalah.getPits()[0] = 7;
		kalah.getPits()[7] = 0;

		distributeStonesInPits.applyRule(kalah);

		checkLastStoneInEmptyPit.applyRule(kalah);

		Assert.assertEquals(1, kalah.getPits()[kalah.getCurrentPlayer().getHomePit()]);

	}

}
