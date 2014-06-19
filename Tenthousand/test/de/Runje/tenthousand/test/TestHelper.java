package de.Runje.tenthousand.test;

import de.Runje.tenthousand.model.Dice;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.GameModel;

public class TestHelper {

	private GameModel model;
	public TestHelper(GameModel model) {
		this.model = model;
	}
	public void changeDice(int index, int number) {
		changeDice(index, number, DiceState.NO_POINTS);
		model.diceHandler.update();
	}
	public void changeDice(int index, int number, DiceState state) {
		Dice d = model.dices.getDices().get(index);
		d.setNumber(number);
		d.setState(state);
		model.diceHandler.update();
	}

	public void changeRolls(int i) {
		model.getPlayingPlayer().setRolls(3);
		model.diceHandler.update();
	}
	
	public DiceState getState(int i) {
		return model.dices.getDices().get(i).getState();
	}
	
	public int getNumber(int i) {
		return model.dices.getDices().get(i).getNumber();
	}
}
