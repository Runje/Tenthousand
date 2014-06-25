package de.Runje.tenthousand.test;

import de.Runje.tenthousand.model.DefaultStrategy;
import de.Runje.tenthousand.model.Dice;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.model.IStrategy;
import de.Runje.tenthousand.simulator.Simulator;

public class TestHelper {

	private GameModel model;
	private int n;
	private IStrategy strategy = new DefaultStrategy();
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
	public double calcProbability(int dicesLeft, int minPoints) {
		for (int i = 0; i < 5 - dicesLeft; i++) {
			changeDice(i, 1, DiceState.FIX);
		}
		Simulator simulator = new Simulator(model);
		simulator.setIterations(dicesLeft*n);
		return simulator.calcProbability(minPoints);
	}
	
	public double calcProbabilityTurn(int dicesLeft, int minPoints) {
		for (int i = 0; i < 5 - dicesLeft; i++) {
			changeDice(i, 1, DiceState.FIX);
		}
		Simulator simulator = new Simulator(model);
		simulator.setIterations(dicesLeft*n);
		simulator.setStrategy(strategy);
		return simulator.calcProbabilityTillEnd(minPoints);
	}
	
	public double calcEv(int dicesLeft) {
		for (int i = 0; i < 5 - dicesLeft; i++) {
			changeDice(i, 1, DiceState.FIX);
		}
		Simulator simulator = new Simulator(model);
		simulator.setIterations(dicesLeft*n);
		return simulator.calcExpectedValue();
	}
	
	public double calcEvTurn(int dicesLeft) {
		for (int i = 0; i < 5 - dicesLeft; i++) {
			changeDice(i, 1, DiceState.FIX);
		}
		Simulator simulator = new Simulator(model);
		simulator.setStrategy(strategy);
		simulator.setIterations(dicesLeft*n);
		return simulator.calcExpectedValueTillEnd();
	}
	
	public void setIterations(int n) {
		this.n = n;
	}
	public void setStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}
}
