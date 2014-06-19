package de.Runje.tenthousand.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.Runje.tenthousand.controller.ActionHandler;
import de.Runje.tenthousand.model.AIPlayer;
import de.Runje.tenthousand.model.DefaultStrategy;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.model.IStrategy;
import de.Runje.tenthousand.model.MyStrategy;
import de.Runje.tenthousand.model.Player;
import de.Runje.tenthousand.model.PlayerHandler;
import de.Runje.tenthousand.model.Rules;

public class StrategyTest {

	private GameModel model;
	private PlayerHandler ph;
	private TestHelper helper;
	ArrayList<IStrategy> allStrategys = new ArrayList<IStrategy>();
	private ActionHandler actionHandler = new ActionHandler();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	@Before
	public void setUp() throws Exception {
		createGame();
		allStrategys = new ArrayList<IStrategy>();
		allStrategys.add(new DefaultStrategy());
		allStrategys.add(new MyStrategy());
	}
	private void createGame() {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new AIPlayer("Thomas", new DefaultStrategy()));
		players.add(new AIPlayer("Milena", new DefaultStrategy()));
		model = new GameModel(players, new Rules());
		ph = new PlayerHandler(model);
		helper = new TestHelper(model);
	}

	@Test
	public void simple() {
		for (IStrategy s : allStrategys) {
			createGame();
			simple(s);
		}
	}
	
	private void simple(IStrategy strategy) {
		AIPlayer player = (AIPlayer) model.getPlayingPlayer();
		player.setStrategy(strategy);
		ph.makeTurnFor(player);
		assertTrue(player.isFinished());
	}
	
	@Test
	public void defaultReleaseDices() {
		// Nothing to do
	}
	
	@Test
	public void defaultMerge() {
		merge(new DefaultStrategy());
	}
	
	private void merge(IStrategy strategy) {
		AIPlayer player = (AIPlayer) model.getPlayingPlayer();
		player.setStrategy(strategy);
		player.setRolls(1);
		helper.changeDice(0, 5);
		helper.changeDice(1, 5);
		ph.makeOneMove(player);
		
		//merge two fives to one one
		assertEquals(DiceState.FIX, helper.getState(0));
		assertEquals(1, helper.getNumber(0));
		assertThat(DiceState.FIX, not(helper.getState(1)));
	}
	
//	@Test
//	public void repeatTest() {
//		for(int i = 0; i < 100; i++) {
//			createGame();
//			defaultEndMove();
//		}
//	}
	@Test
	public void defaultEndMove() {
		// test endMove
		AIPlayer player = (AIPlayer) model.getPlayingPlayer();
		player.setStrategy(new DefaultStrategy());
		player.setRolls(1);
		helper.changeDice(0, 1);
		helper.changeDice(1, 1);
		helper.changeDice(2, 1);
		helper.changeDice(3, 1);
		assertTrue(model.nextIsPossible());
		ph.makeOneMove(player);
		if (model.getPoints() == 0) {
			assertTrue(player.isFinished());
		} else {
			assertFalse(player.isFinished());
		}
	}
	@Test
	public void defaultTakeover() {
		for (int i = 0; i < 100; i++) {
			createGame();
			defaultTakeoverTest();
		}
	}

	private void defaultTakeoverTest() {
		helper.changeDice(0, 2);
		helper.changeDice(1, 2);
		helper.changeDice(2, 2);
		helper.changeDice(3, 1);
		assertEquals(model.getPoints(), 300);
		AIPlayer player = (AIPlayer) model.getPlayingPlayer();
		player.setStrategy(new DefaultStrategy());
		//possible to takeover
		model.takeover = true;
		//takeover
		ph.makeTakeover(player);
		assertTrue(player.willTakeOver());
		ph.makeOneMove(player);
		int lastDice = model.dices.getDices().get(4).getNumber();
		if (lastDice == 1)
		{
			assertEquals(model.getPoints(), 400);
		} else if (lastDice == 5)
		{
			assertEquals(model.getPoints(), 350);
		} else
		{
			assertEquals(model.getPoints(), 0);
		}
	}
	
	@Test
	public void myMerge() {
		// Merge
		merge(new MyStrategy());
		
		// No Merge
		createGame();
		AIPlayer player = (AIPlayer) model.getPlayingPlayer();
		player.setStrategy(new MyStrategy());
		player.setRolls(1);
		helper.changeDice(0, 5);
		helper.changeDice(1, 5);
		helper.changeDice(2, 1);
		helper.changeDice(3, 1);
		helper.changeDice(4, 1);
		ph.makeOneMove(player);
		
		//merge two fives to one one
		assertThat(DiceState.FIX, not(helper.getState(0)));
		assertThat(DiceState.FIX, not(helper.getState(1)));
		assertThat(DiceState.FIX, not(helper.getState(2)));
		assertThat(DiceState.FIX, not(helper.getState(3)));
		assertThat(DiceState.FIX, not(helper.getState(4)));

	}
	@Test
	public void myEndMove() {
		//TODO
		// 3 free dices && > 1000
		AIPlayer player = (AIPlayer) model.getPlayingPlayer();
		player.setStrategy(new MyStrategy());
		player.setRolls(1);
		model.diceHandler.setAllPoints(1000);
		helper.changeDice(0, 5);
		helper.changeDice(1, 5);
		
		assertTrue(model.nextIsPossible());
		
		ph.makeOneMove(player);
		
		assertTrue(player.isFinished());
		
		// < 3 free dices && > 300
		createGame();
		player = (AIPlayer) model.getPlayingPlayer();
		player.setStrategy(new MyStrategy());
		player.setRolls(1);
		model.diceHandler.setAllPoints(100);
		helper.changeDice(0, 5);
		helper.changeDice(1, 5);
		helper.changeDice(2, 1);
		
		assertTrue(model.nextIsPossible());
		
		ph.makeOneMove(player);
		
		assertTrue(player.isFinished());

		// !0 free dices && > 300
		createGame();
		player = (AIPlayer) model.getPlayingPlayer();
		player.setStrategy(new MyStrategy());
		player.setRolls(1);
		model.diceHandler.setAllPoints(100);
		helper.changeDice(0, 1);
		helper.changeDice(1, 1);
		helper.changeDice(2, 1);
		helper.changeDice(3, 1);
		helper.changeDice(4, 1);
		
		assertTrue(model.nextIsPossible());
		
		ph.makeOneMove(player);
		
		assertFalse(player.isFinished());
		if (model.getPoints() == 0) {
			assertTrue(player.isFinished());
			myEndMove();
		} else {
			assertFalse(player.isFinished());
		}
	}
	@Test
	public void myTakeover() {
		//free Dices > 2
		helper.changeDice(0, 1);
		helper.changeDice(1, 1);
		assertEquals(model.getPoints(), 200);
		AIPlayer player = (AIPlayer) model.getPlayingPlayer();
		player.setStrategy(new MyStrategy());
		//possible to takeover
		model.takeover = true;
		//takeover
		ph.makeTakeover(player);
		assertTrue(player.willTakeOver());

		// Points > 800
		createGame();
		helper.changeDice(0, 1);
		helper.changeDice(1, 1);
		helper.changeDice(2, 1);
		helper.changeDice(3, 1);
		assertEquals(model.getPoints(), 2000);
		player = (AIPlayer) model.getPlayingPlayer();
		player.setStrategy(new MyStrategy());
		//possible to takeover
		model.takeover = true;
		//takeover
		ph.makeTakeover(player);
		assertTrue(player.willTakeOver());
		
		//  800
		createGame();
		model.diceHandler.setAllPoints(500);
		helper.changeDice(0, 1);
		helper.changeDice(1, 2);
		helper.changeDice(2, 2);
		helper.changeDice(3, 2);
		assertEquals(model.getPoints(), 800);
		player = (AIPlayer) model.getPlayingPlayer();
		player.setStrategy(new MyStrategy());
		//possible to takeover
		model.takeover = true;
		//takeover
		ph.makeTakeover(player);
		assertFalse(player.willTakeOver());
	}
}
