package de.runje.tenthousand.model;

import java.util.ArrayList;
import java.util.Collections;

import de.runje.tenthousand.logger.LogLevel;
import de.runje.tenthousand.logger.Logger;

public class DiceHandler {
	public Dices dices;
	public ArrayList<ValuePair> newValuePairs;
	private int newPoints = 0;
	private int allPoints = 0;
	private Rules rules;

	public DiceHandler(Dices dices) {
		this.dices = dices;
		this.rules = new Rules();
	}
	
	/**
	 * @return the dices
	 */
	public Dices getDices() {
		return dices;
	}

	/**
	 * @param dices
	 *            the dices to set
	 */
	public void setDices(Dices dices) {
		this.dices = dices;
	}

	/**
	 * @return the newValuePairs
	 */
	public ArrayList<ValuePair> getNewValuePairs() {
		return newValuePairs;
	}

	/**
	 * @param newValuePairs
	 *            the newValuePairs to set
	 */
	public void setNewValuePairs(ArrayList<ValuePair> newValuePairs) {
		this.newValuePairs = newValuePairs;
	}

	/**
	 * @return the newPoints
	 */
	public int getNewPoints() {
		return newPoints;
	}

	/**
	 * @param newPoints
	 *            the newPoints to set
	 */
	public void setNewPoints(int newPoints) {
		this.newPoints = newPoints;
	}

	/**
	 * @return the allPoints
	 */
	public int getAllPoints() {
		return allPoints;
	}

	/**
	 * @param allPoints
	 *            the allPoints to set
	 */
	public void setAllPoints(int allPoints) {
		this.allPoints = allPoints;
	}

	/**
	 * @return the rules
	 */
	public Rules getRules() {
		return rules;
	}

	/**
	 * @param rules
	 *            the rules to set
	 */
	public void setRules(Rules rules) {
		this.rules = rules;
	}

	/**
	 * Fixes all dices with state POINTS, resets the Dices if all dices are
	 * fixed, rolls the dices, updates the Points
	 */
	public void rollDices() {
		// fix dices with points before roll
		dices.fix();
		allPoints += newPoints;
		newPoints = 0;
		if (dices.areAllFixed()) {
			Logger.log(LogLevel.INFO, "Dices",
					"All Dices are fixed. Resetting them...");
			dices.reset();
		}
		dices.roll();
		Logger.log(LogLevel.DEBUG, "DiceHandler", "Rolled dices: " + dices);
		update();
	}

	/**
	 * Updates the member valuePairs and the states of the dices
	 */
	private void updateValuePairs() {
		newValuePairs = determineValuePairs();
		updateStates();
	}

	private void updateStates() {
		// Set all countable dices to NO_POINTS
		for (Dice dice : dices.getDices()) {
			if (dice.isCountable()) {
				dice.setState(DiceState.NO_POINTS);
			}
		}
		for (ValuePair valuePair : newValuePairs) {
			switch (valuePair.value) {
			case FIVE:
				dices.setToStatePoints(5);
				break;
			case FIVE_OF_A_KIND:
				dices.setToStatePoints(valuePair.dice);
				dices.setToStatePoints(valuePair.dice);
				dices.setToStatePoints(valuePair.dice);
				dices.setToStatePoints(valuePair.dice);
				dices.setToStatePoints(valuePair.dice);
				break;
			case FOUR_OF_A_KIND:
				dices.setToStatePoints(valuePair.dice);
				dices.setToStatePoints(valuePair.dice);
				dices.setToStatePoints(valuePair.dice);
				dices.setToStatePoints(valuePair.dice);
				break;
			case ONE:
				dices.setToStatePoints(1);
				break;
			case STRAIGHT:
				dices.setToStatePoints(valuePair.dice);
				dices.setToStatePoints(valuePair.dice + 1);
				dices.setToStatePoints(valuePair.dice + 2);
				dices.setToStatePoints(valuePair.dice + 3);
				dices.setToStatePoints(valuePair.dice + 4);
				break;
			case THREE_OF_A_KIND:
				dices.setToStatePoints(valuePair.dice);
				dices.setToStatePoints(valuePair.dice);
				dices.setToStatePoints(valuePair.dice);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Only calc valuePairs from FREE dices
	 * 
	 * @return List of new Value Pairs
	 */
	public ArrayList<ValuePair> determineValuePairs() {
		assert (dices.getDices().size() <= 5);
		// copy original list, because it shouldn't be sorted (There should be a
		// better way?)
		ArrayList<Dice> tempDices = new ArrayList<Dice>(dices.getDices());
		Collections.sort(dices.getDices());
		ArrayList<ValuePair> result = new ArrayList<ValuePair>();
		// only calc if straight is "activated"
		// look if straight is activated for the active game
		if (rules.isStraightActive()) {
			int straight = straight();
			if (straight != 0) {
				result.add(new ValuePair(Value.STRAIGHT, straight));
				return result;
			}
		}

		// look if there are other points
		ArrayList<Integer> countDices = countDices();
		for (int i = 0; i < 6; ++i) {
			int diceValue = i + 1;
			int numberOfSameValue = countDices.get(i);
			if (numberOfSameValue == 0) {
				continue;
			}
			if (numberOfSameValue > 2) {
				if (numberOfSameValue == 3) {
					result.add(new ValuePair(Value.THREE_OF_A_KIND, diceValue));
				} else if (numberOfSameValue == 4) {
					result.add(new ValuePair(Value.FOUR_OF_A_KIND, diceValue));
				} else if (numberOfSameValue == 5) {
					result.add(new ValuePair(Value.FIVE_OF_A_KIND, diceValue));
				} else {
					// can't be
					assert (false);
				}
			} else if (diceValue == 1) {
				if (numberOfSameValue == 1) {
					result.add(new ValuePair(Value.ONE));
				} else {
					// must be two
					result.add(new ValuePair(Value.ONE));
					result.add(new ValuePair(Value.ONE));
				}
			} else if (diceValue == 5) {
				if (numberOfSameValue == 1) {
					result.add(new ValuePair(Value.FIVE));
				} else {
					// must be two
					result.add(new ValuePair(Value.FIVE));
					result.add(new ValuePair(Value.FIVE));
				}
			}
		}
		dices.setDices(tempDices);
		Logger.log(LogLevel.DEBUG, "Dices", "The dices are " + dices);
		Logger.log(LogLevel.DEBUG, "Dices", "The value pairs are " + result);
		return result;
	}

	/**
	 * Counts the amount of one number in all dices.(with state POINTS or
	 * NO_POINTS)
	 * 
	 * @return array of integer. Value n in 0 means that n ones are thrown.
	 */
	private ArrayList<Integer> countDices() {
		ArrayList<Integer> counts = new ArrayList<Integer>();
		// Initialize all counts with 0
		for (int i = 0; i < 6; ++i) {
			counts.add(0);
		}
		for (Dice dice : dices.getDices()) {
			if (dice.isCountable() && dice.hasNumber()) {
				int old = counts.get(dice.getNumber() - 1);
				// increment value
				counts.set(dice.getNumber() - 1, old + 1);
			}
		}
		return counts;
	}

	/**
	 * Determines if there is a straight (only for POINTS or NO_POINTS dices)
	 * 
	 * @param dices
	 * @return returns 0 if there is no straight, else it returns the first dice
	 *         of the straight
	 */
	private int straight() {
		if (dices.getDices().size() < 5) {
			return 0;
		}

		// straight has to begin with 1 or 2
		if (dices.getDices().get(0).getNumber() > 2) {
			return 0;
		}
		int last = dices.getDices().get(0).getNumber();
		if (!dices.getDices().get(0).isCountable()) {
			return 0;
		}
		for (int i = 1; i < 5; ++i) {
			int current = dices.getDices().get(i).getNumber();
			if (last != current - 1 || !dices.getDices().get(i).isCountable()) {
				return 0;
			}
			last = current;
		}
		return dices.getDices().get(0).getNumber();
	}

	public void mergeTwoFives() {
		assert (isPossibleToMergeTwoFives());
		boolean first = true;
		for (Dice dice : dices.getDices()) {
			if (dice.getState() != DiceState.FIX && dice.getNumber() == 5) {
				if (first) {
					dice.setNumber(1);
					first = false;
				} else {
					dice.reset();
					dice.setState(DiceState.FORCE_NO_POINTS);
				}
			}
		}
		updateValuePairs();
	}

	public boolean isPossibleToMergeTwoFives() {
		int i = 0;
		for (Dice dice : dices.getDices()) {
			if (dice.getState() != DiceState.FIX && dice.getNumber() == 5) {
				i++;
			}
		}
		// only possible if there are exactly two not fixed fives
		return i == 2;
	}

	public void update() {
		updateValuePairs();
		this.newPoints = rules.calcPoints(newValuePairs);
	}

	public void resetAll() {
		dices.reset();
		resetPoints();
	}



	public void resetPoints() {
		allPoints = 0;
		newPoints = 0;
	}

}
