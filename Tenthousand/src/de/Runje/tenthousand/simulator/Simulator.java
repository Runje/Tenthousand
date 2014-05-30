package de.Runje.tenthousand.simulator;

import de.Runje.tenthousand.model.Dice;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.Dices;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.observer.IObserver;

public class Simulator implements IObserver{

	private GameModel model;
	
	private int dice;
	
	private int number;
	
	private DiceState state;
	
	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @param diceState the state to set
	 */
	public void setState(DiceState diceState) {
		this.state = diceState;
	}

	/**
	 * @return the dice
	 */
	public int getDice() {
		return dice;
	}

	/**
	 * @param dice the dice to set
	 */
	public void setDice(int dice) {
		this.dice = dice;
	}

	public Simulator(GameModel model) {
		this.model = model;
		this.dice = 0;
		this.number = 1;
		this.state = DiceState.NO_POINTS;
	}

	public static int simulate() {
		Dices dices = new Dices();
		dices.roll();
		dices.roll();
		dices.roll();
		return 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		//Do i need this? Let's see...
	}

	public void changeDice() {
		Dice d = model.dices.getDices().get(dice);
		d.setNumber(number);
		d.setState(state);
		model.diceHandler.update();
		model.notifyObservers();
	}

}
