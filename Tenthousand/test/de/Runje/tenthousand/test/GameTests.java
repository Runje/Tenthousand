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
import de.Runje.tenthousand.model.Rules;

public class GameTests {

	private GameModel model;
	private ActionHandler actionHandler = new ActionHandler();

	@Before
	public void setUp() throws Exception {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer("Thomas"));
		players.add(new HumanPlayer("Milena"));
		model = new GameModel(players, new Rules());
	}

	@Test
	public void mergeTwoFives() {
		changeDice(0, 5);
		changeDice(1, 5);
		
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
		changeDice(0,5, DiceState.FIX);
		changeDice(1,5, DiceState.FIX);
		changeDice(2,5);
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
		
		changeDice(0,1);
		changeDice(1,1);
		changeRolls(3);
		actionHandler.executeAction(Action.Next, model);
		//not possible after 200 points
		assertFalse(model.isPossibleToTakeOver());
		assertEquals(model.getPoints(), 0);
	}
	
	@Test
	public void takeoverPossible() {
		changeDice(0,1);
		changeDice(1,1);
		changeDice(2,1);
		// Possible after more than 300 points
		actionHandler.executeAction(Action.Next, model);
		assertTrue(model.isPossibleToTakeOver());
		actionHandler.executeAction(Action.Takeover, model);
		simulateRoll();
		changeRolls(1);
		changeDice(3,1);
		changeDice(4,2);
		// first three dices must be fixed
		assertEquals(DiceState.FIX, model.dices.getDices().get(0).getState());
		assertEquals(DiceState.FIX, model.dices.getDices().get(1).getState());
		assertEquals(DiceState.FIX, model.dices.getDices().get(2).getState());
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
	public void releaseDices() {
		changeDice(0,1);
		changeDice(1,1);
		changeDice(2,5);
		changeDice(3,1);
		
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

	private void changeDice(int index, int number) {
		changeDice(index, number, DiceState.NO_POINTS);
		model.diceHandler.update();
	}
	private void changeDice(int index, int number, DiceState state) {
		Dice d = model.dices.getDices().get(index);
		d.setNumber(number);
		d.setState(state);
		model.diceHandler.update();
	}

	private void changeRolls(int i) {
		model.getPlayingPlayer().setRolls(3);
		model.diceHandler.update();
	}
}
