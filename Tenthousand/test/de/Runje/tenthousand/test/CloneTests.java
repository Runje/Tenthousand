package de.Runje.tenthousand.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import de.Runje.tenthousand.model.Dices;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.model.HumanPlayer;
import de.Runje.tenthousand.model.Player;
import de.Runje.tenthousand.model.Rules;
import de.Runje.tenthousand.simulator.Simulator;

public class CloneTests {

	private GameModel model;
	private TestHelper helper;

	@Before
	public void setUp() throws Exception {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer("Thomas"));
		players.add(new HumanPlayer("Milena"));
		model = new GameModel(players, new Rules());
		helper = new TestHelper(model);

	}

	@Test
	public void cloneDices() {
		Dices dices = new Dices(1,2,3,4,5);
		Dices clones = new Dices(dices);
		dices.getDices().get(0).setNumber(5);
		dices.getDices().get(1).setNumber(4);
		dices.getDices().get(2).setNumber(3);
		dices.getDices().get(3).setNumber(2);
		dices.getDices().get(4).setNumber(1);
		
		assertEquals(1, clones.getDices().get(0).getNumber());
		assertEquals(2, clones.getDices().get(1).getNumber());
		assertEquals(3, clones.getDices().get(2).getNumber());
		assertEquals(4, clones.getDices().get(3).getNumber());
		assertEquals(5, clones.getDices().get(4).getNumber());
	}
	
	@Test
	public void cloneModel() {
		helper.changeDice(0,1);
		helper.changeDice(1,2);
		helper.changeDice(2,3);
		helper.changeDice(3,4);
		helper.changeDice(4,5);
		Simulator simulator = new Simulator(model);
		GameModel clone = new GameModel(model);
		helper.changeDice(0,5);
		helper.changeDice(1,4);
		helper.changeDice(2,3);
		helper.changeDice(3,2);
		helper.changeDice(4,1);
		
		assertEquals(1, clone.dices.getDices().get(0).getNumber());
		assertEquals(2, clone.dices.getDices().get(1).getNumber());
		assertEquals(3, clone.dices.getDices().get(2).getNumber());
		assertEquals(4, clone.dices.getDices().get(3).getNumber());
		assertEquals(5, clone.dices.getDices().get(4).getNumber());

		assertEquals(1, clone.playerHandler.model.dices.getDices().get(0).getNumber());
		assertEquals(2, clone.playerHandler.model.dices.getDices().get(1).getNumber());
		assertEquals(3, clone.playerHandler.model.dices.getDices().get(2).getNumber());
		assertEquals(4, clone.playerHandler.model.dices.getDices().get(3).getNumber());
		assertEquals(5, clone.playerHandler.model.dices.getDices().get(4).getNumber());

		simulator.calcProbability(1);
	}
	

}
