package de.Runje.tenthousand.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.Runje.tenthousand.controller.Action;
import de.Runje.tenthousand.controller.ActionHandler;
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
		players.add(new AIPlayer("Milena", new MyStrategy()));
		model = new GameModel(players, new Rules());
	}

	@Test
	public void mergeTwoFives() {
		changeDice(0, 5);
		changeDice(1, 5);
		model.diceHandler.update();
		
		assertEquals(100, model.getPoints());
		assertTrue(model.isPossibleToMerge());
		
		actionHandler.executeAction(Action.Merge, model);

		assertEquals(100, model.getPoints());
		assertFalse(model.isPossibleToMerge());
	}
	
	@Test
	public void notMergeTwoFives() {
		changeDice(0,5, DiceState.FIX);
		changeDice(1,5, DiceState.FIX);
		changeDice(2,5);
		assertFalse(model.isPossibleToMerge());
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
	
	private void changeDice(int index, int number) {
		changeDice(index, number, DiceState.NO_POINTS);
	}
	private void changeDice(int index, int number, DiceState state) {
		Dice d = model.dices.getDices().get(index);
		d.setNumber(number);
		d.setState(state);
	}

}
