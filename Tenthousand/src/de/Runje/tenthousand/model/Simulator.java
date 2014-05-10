package de.Runje.tenthousand.model;

public class Simulator {

	public Simulator() {
	}

	public static int simulate() {
		Dices dices = new Dices();
		dices.roll();
		dices.roll();
		dices.roll();
		return 0;
	}

}
