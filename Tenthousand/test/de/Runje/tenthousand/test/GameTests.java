package de.Runje.tenthousand.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.Runje.tenthousand.controller.Action;
import de.Runje.tenthousand.controller.ActionHandler;
import de.Runje.tenthousand.logger.LogLevel;
import de.Runje.tenthousand.logger.Logger;
import de.Runje.tenthousand.model.AIPlayer;
import de.Runje.tenthousand.model.Dice;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.model.HumanPlayer;
import de.Runje.tenthousand.model.MyStrategy;
import de.Runje.tenthousand.model.Player;
import de.Runje.tenthousand.model.PlayerHandler;
import de.Runje.tenthousand.model.Rules;

public class GameTests {

	private GameModel model;
	private TestHelper helper;
	private ActionHandler actionHandler = new ActionHandler();
	private int i = 0;

	@Before
	public void setUp() throws Exception {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer("Thomas"));
		players.add(new HumanPlayer("Milena"));
		model = new GameModel(players, new Rules());
		helper = new TestHelper(model);
	}

	@Test
	public void mergeTwoFives() {
		helper.changeDice(0, 5);
		helper.changeDice(1, 5);
		
		assertEquals(100, model.getPoints());
		assertTrue(model.isPossibleToMerge());
		assertEquals(model.getFreeDices(), 3);
		
		actionHandler.executeAction(Action.Merge, model);

		assertEquals(100, model.getPoints());
		assertFalse(model.isPossibleToMerge());
		assertEquals(model.getFreeDices(), 4);
	}
	
	@Test
	public void notMergeTwoFives() {
		helper.changeDice(0,5, DiceState.FIX);
		helper.changeDice(1,5, DiceState.FIX);
		helper.changeDice(2,5);
		assertFalse(model.isPossibleToMerge());
		assertEquals(model.getFreeDices(), 2);
	}
	
	@Test
	public void next() {
		for (int i = 0; i < 10; i++) {
			//Not possible at begin of turn
			assertFalse(model.nextIsPossible());
			while (!model.getPlayingPlayer().isFinished()) {
				//True after first roll
				actionHandler.executeAction(Action.Roll, model);
				assertTrue(model.nextIsPossible());
			}
			actionHandler.executeAction(Action.Next, model);
			//Not possible at begin of turn
			assertFalse(model.nextIsPossible());
		}
	}
	
	@Test
	public void takeoverNotPossible() {
		// Not possible at the beginning
		assertFalse(model.isPossibleToTakeOver());
		
		helper.changeDice(0,1);
		helper.changeDice(1,1);
		changeRolls(3);
		actionHandler.executeAction(Action.Next, model);
		//not possible after 200 points
		assertFalse(model.isPossibleToTakeOver());
		assertEquals(model.getPoints(), 0);
	}
	
	@Test
	public void takeoverPossible() {
		helper.changeDice(0,1);
		helper.changeDice(1,1);
		helper.changeDice(2,1);
		// Possible after more than 300 points
		actionHandler.executeAction(Action.Next, model);
		assertTrue(model.isPossibleToTakeOver());
		actionHandler.executeAction(Action.Takeover, model);
		simulateRoll();
		changeRolls(1);
		helper.changeDice(3,1);
		helper.changeDice(4,2);
		// first three dices must be fixed
		assertEquals(DiceState.FIX, helper.getState(0));
		assertEquals(DiceState.FIX, helper.getState(1));
		assertEquals(DiceState.FIX, helper.getState(0));
		assertEquals(model.getPoints(), 1100);
	}
	

	
	@Test
	public void gameEnd() {
		assertFalse(model.isGameFinished());
		model.getPlayingPlayer().setAllPoints(Rules.WinPoints);
		changeRolls(2);
		actionHandler.executeAction(Action.Next, model);
		assertFalse(model.isGameFinished());
		actionHandler.executeAction(Action.Roll, model);
		assertFalse(model.isGameFinished());
		actionHandler.executeAction(Action.Next, model);
		actionHandler.executeAction(Action.Next, model);
		//Game ends if one round is finished and one player has more than the given points
		assertTrue(model.isGameFinished());
		
	}
	
	@Test
	public void turnEndNoPoints() {
		helper.changeDice(0,3);
		helper.changeDice(1,2);
		helper.changeDice(2,4);
		helper.changeDice(3,3);
		helper.changeDice(4,2);
		PlayerHandler playerHandler = new PlayerHandler(model);
		playerHandler.checkIfFinished(model.getPlayingPlayer());
		assertTrue(model.getPlayingPlayer().isFinished());
		
	}
	@Test
	public void turnEndMaxRolls() {
		helper.changeDice(0,3);
		helper.changeDice(1,1);
		helper.changeDice(2,4);
		helper.changeDice(3,3);
		helper.changeDice(4,2);
		PlayerHandler playerHandler = new PlayerHandler(model);
		playerHandler.checkIfFinished(model.getPlayingPlayer());
		// Not finished
		assertFalse(model.getPlayingPlayer().isFinished());
		assertTrue(model.isPossibleToRoll());
		changeRolls(Rules.MaxRolls);
		playerHandler.checkIfFinished(model.getPlayingPlayer());
		// finished
		assertTrue(model.getPlayingPlayer().isFinished());
		assertFalse(model.isPossibleToRoll());
	}
	
	@Test
	public void threeStrikes() {
		helper.changeDice(0,3);
		helper.changeDice(1,2);
		helper.changeDice(2,4);
		helper.changeDice(3,3);
		helper.changeDice(4,2);
		Player player = model.getPlayingPlayer();
		player.setAllPoints(5000);
		player.setStrikes(Rules.MaxStrikes - 1);
		//gets a strike
		assertEquals(0, model.getPoints());
		actionHandler.executeAction(Action.Next, model);
		assertEquals(0, player.getPoints());
		assertEquals(0, player.getStrikes());
	}
	
	@Test
	public void allPointsRollAgain() {
		helper.changeDice(0,1);
		helper.changeDice(1,1);
		helper.changeDice(2,5);
		helper.changeDice(3,1);
		helper.changeDice(4,1);
		//Even if it was the last roll
		changeRolls(Rules.MaxRolls);
		assertTrue(model.isPossibleToRoll());
		Player player = model.getPlayingPlayer();
		actionHandler.executeAction(Action.Roll, model);
		// No dice is allowed to be fixed
		for (int i = 0; i < 5; i++) {
			assertNotEquals(DiceState.FIX, helper.getState(i));
		}
		if (player.isFinished()) {
			// zero points
			assertEquals(player.getRolls(), 0);
		} else {
			// first roll again
			assertEquals(player.getRolls(), 1);
		}
	}
	@Test
	public void releaseDices() {
		helper.changeDice(0,1);
		helper.changeDice(1,1);
		helper.changeDice(2,5);
		helper.changeDice(3,1);
		
		//free Dices
		assertEquals(1, model.getFreeDices());
		//Points
		assertEquals(1050, model.getPoints());
		Action a = Action.Switch;
		a.index = 1;
		actionHandler.executeAction(a, model);
		//free Dices
		assertEquals(2, model.getFreeDices());
		//Points
		assertEquals(250, model.getPoints());

		//Switch back
		actionHandler.executeAction(a, model);
		//free Dices
		assertEquals(1, model.getFreeDices());
		//Points
		assertEquals(1050, model.getPoints());
	}

	private void simulateRoll() {
		model.diceHandler.setAllPoints(model.diceHandler.getAllPoints() + model.diceHandler.getNewPoints());
		model.diceHandler.setNewPoints(0);
		model.takeover = false;
		model.getPlayingPlayer().setRolls(model.getPlayingPlayer().getRolls() + 1);
	}

	private void changeRolls(int i) {
		model.getPlayingPlayer().setRolls(3);
		model.diceHandler.update();
	}
}
