package de.Runje.tenthousand.model;

import java.util.ArrayList;

public class Dices {

	public static final int NUMBER_OF_DICES = 5;

	private ArrayList<Dice> dices;

	/**
	 * @return the dices
	 */
	public ArrayList<Dice> getDices() {
		return dices;
	}

	public Dices() {
		this.dices = new ArrayList<Dice>();
		for (int i = 0; i < NUMBER_OF_DICES; ++i) {
			dices.add(new Dice(DiceState.NO_POINTS));
		}
	}

	public Dices(int n1, int n2, int n3, int n4, int n5) {
		this.dices = new ArrayList<Dice>();
		dices.add(new Dice(n1));
		dices.add(new Dice(n2));
		dices.add(new Dice(n3));
		dices.add(new Dice(n4));
		dices.add(new Dice(n5));
	}

	public Dices(ArrayList<Dice> dices) {
		this.dices = dices;
	}
	
	/**
	 * Copy constructor
	 * @param d Dices to copy
	 */
	public Dices(Dices d) {
		this.dices = new ArrayList<Dice>(d.getDices().size());
		for (Dice dice : d.getDices()) {
			this.dices.add(new Dice(dice));
		}
	}

	/**
	 * roll all rollable dices
	 */
	public void roll() {
		for (Dice dice : dices) {
			if (dice.isRollable()) {
				dice.roll();
			}
		}
	}

	public void reset() {
		for (Dice dice : dices) {
			dice.reset();
		}
	}

	public void setToStatePoints(int value) {
		boolean test = false;
		for (Dice dice : dices) {
			if (dice.getState() == DiceState.NO_POINTS
					&& dice.getNumber() == value) {
				test = true;
				dice.setState(DiceState.POINTS);
				break;
			}
		}
		// we must be once in the if
		assert (test);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dices [dices=" + dices + "]";
	}

	/**
	 * Fix all dices with state "POINTS" and change FORCE_NO_POINTS to NO_POINTS
	 */
	public void fix() {
		for (Dice dice : dices) {
			if (dice.isPoints()) {
				dice.setState(DiceState.FIX);
			} else if (dice.getState() == DiceState.FORCE_NO_POINTS) {
				dice.setState(DiceState.NO_POINTS);
			}
		}
	}

	/**
	 * @param dices
	 *            the dices to set
	 */
	public void setDices(ArrayList<Dice> dices) {
		this.dices = dices;
	}

	public void fixDice(int index) {
		this.dices.get(index).setState(DiceState.FIX);
		// TODO: fix all dices which are "connected"
	}

	public void freeDice(int index) {
		this.dices.get(index).setState(DiceState.NO_POINTS);
		// TODO: free all dices which are "connected"

	}

	public boolean areAllFixed() {
		boolean allFixed = true;
		for (Dice dice : dices) {
			if (dice.hasNoPoints()) {
				allFixed = false;
			}
		}
		return allFixed;
	}
	
}
