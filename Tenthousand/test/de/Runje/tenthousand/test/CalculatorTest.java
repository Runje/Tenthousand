package de.Runje.tenthousand.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.Runje.tenthousand.model.AIPlayer;
import de.Runje.tenthousand.model.DefaultStrategy;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.model.MyStrategy;
import de.Runje.tenthousand.model.Player;
import de.Runje.tenthousand.model.Rules;

public class CalculatorTest {
	
	private final double epsilon = 1e-2;
	private TestHelper helper;
	private GameModel model;

	@Before
	public void setUp() throws Exception {
		createGame();
	}

	private void createGame() {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new AIPlayer("Thomas", new DefaultStrategy()));
		players.add(new AIPlayer("Milena", new DefaultStrategy()));
		model = new GameModel(players, new Rules());
		helper = new TestHelper(model);
		helper.setIterations(10000);
	}

	@Test
	public void probability() {
		assertEquals(1.0 / 3.0, helper.calcProbability(1,1), epsilon);
		createGame();
		assertEquals(5.0 / 9.0, helper.calcProbability(2,1), epsilon);
		createGame();
		assertEquals(1.0 / 6.0, helper.calcProbability(1,100), epsilon);
		createGame();
		assertEquals(1.0 / 36.0, helper.calcProbability(2,200), epsilon);
	}
	@Test
	public void probabilityTurn() {
		// TODO:
	}
	@Test
	public void ev() {
		assertEquals(25, helper.calcEv(1), 100*epsilon);
		createGame();
		assertEquals(50, helper.calcEv(2), 100*epsilon);
	}
	@Test
	public void evTurn() {
		// TODO:
		model.diceHandler.setAllPoints(300);
		helper.setStrategy(new MyStrategy());
		model.getPlayingPlayer().setRolls(2);
		assertTrue(model.nextIsPossible());
		assertEquals(0, helper.calcProbabilityTurn(1, 301), epsilon);
		assertEquals(300, helper.calcEvTurn(1), epsilon);
	}

}
