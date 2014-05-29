package de.Runje.tenthousand.model;

import java.util.ArrayList;
import java.util.Collections;

import de.Runje.tenthousand.logger.LogLevel;
import de.Runje.tenthousand.logger.Logger;

public class Dices {
	
	public static final int NUMBER_OF_DICES = 5;

	//TODO: What to do with the Rules?
	private Rules rules = new Rules();
	private ArrayList<Dice> dices;
	/**
	 * @return the dices
	 */
	public ArrayList<Dice> getDices() {
		return dices;
	}

	/**
	 * ValuePairs from dices with status "FREE"
	 */
	private ArrayList<ValuePair> newValuePairs;
	
	public ArrayList<ValuePair> getNewValuePairs() {
		return newValuePairs;
	}
	
	private int newPoints = 0;
	private int allPoints = 0;
	public Dices() {
		this.dices = new ArrayList<Dice>();
		for(int i=0; i< NUMBER_OF_DICES; ++i ) {
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
	 * roll all FREE dices
	 */
	public void roll() {
		//fix dices with points before roll
		fix();
		if (areAllFixed()) {
			Logger.log(LogLevel.INFO, "Dices", "All Dices are fixed. Resetting them...");
			resetJustDices();
		}
		
		for(int i=0; i< NUMBER_OF_DICES; ++i ) {
			if (dices.get(i).getState() == DiceState.NO_POINTS) {
				dices.get(i).roll();
			}
		}
		updateValuePairs();
		updatePoints();
	}

	
	public void resetJustDices() {
		for (Dice dice : dices) {
			dice.reset();
		}	
	}

	private void updateStates() {
		//Set all countable dices to NO_POINTS
		for (Dice dice : dices) {
			if (dice.isCountable()) {
				dice.setState(DiceState.NO_POINTS);
			}
		}
		for (ValuePair valuePair : newValuePairs) {
			switch (valuePair.value) {
			case FIVE:
				setNoPoints(5);
				break;
			case FIVE_OF_A_KIND:
				setNoPoints(valuePair.dice);
				setNoPoints(valuePair.dice);
				setNoPoints(valuePair.dice);
				setNoPoints(valuePair.dice);
				setNoPoints(valuePair.dice);
				break;
			case FOUR_OF_A_KIND:
				setNoPoints(valuePair.dice);
				setNoPoints(valuePair.dice);
				setNoPoints(valuePair.dice);
				setNoPoints(valuePair.dice);
				break;
			case ONE:
				setNoPoints(1);
				break;
			case STRAIGHT:
				setNoPoints(valuePair.dice);
				setNoPoints(valuePair.dice + 1);
				setNoPoints(valuePair.dice + 2);
				setNoPoints(valuePair.dice + 3);
				setNoPoints(valuePair.dice + 4);
				break;
			case THREE_OF_A_KIND:
				setNoPoints(valuePair.dice);
				setNoPoints(valuePair.dice);
				setNoPoints(valuePair.dice);
				break;
			default:
				break;
			}
		}
	}

	private void setNoPoints(int value) {
		boolean test = false;
		for (Dice dice : dices) {
			if (dice.getState() == DiceState.NO_POINTS && dice.getNumber() == value) {
				test = true;
				dice.setState(DiceState.POINTS);
				break;
			}
		}
		//we must be once in the if
		assert(test);
	}

	private void updatePoints() {
		this.allPoints += this.newPoints;
		this.newPoints = rules.calcPoints(newValuePairs);		
	}
	

	/**
	 * Updates the member valuePairs and the states of the dices
	 */
	public void updateValuePairs() {
		newValuePairs = determineValuePairs();
		updateStates();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dices [dices=" + dices + "]";
	}
	
	/**
	 * Fix all dices with state "POINTS"
	 */
	public void fix() {
		for (Dice dice : dices) {
			if (dice.isPoints()) {
				dice.setState(DiceState.FIX);
			} 
		}
	}
	/**
	 * Only calc valuePairs from FREE dices
	 * @return List of new Value Pairs
	 */
	public ArrayList<ValuePair> determineValuePairs() {
		assert(dices.size() <= 5);
		//copy original list, because it shouldn't be sorted (There should be a better way?)
		ArrayList<Dice> tempDices = new ArrayList<Dice>(dices);
		Collections.sort(dices);
		ArrayList<ValuePair> result = new ArrayList<ValuePair>();
		//only calc if straight is "activated"
		//look if straight is activated for the active game
		if (Tenthousand.isStraightActive()) {
			int straight = straight();
			if (straight != 0) {
				result.add(new ValuePair(Value.STRAIGHT, straight));
				return result;
			}
		}
		
		//look if there are other points
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
					//can't be
					assert(false);
				}
			} else if (diceValue == 1) {
				if (numberOfSameValue == 1) {
					result.add(new ValuePair(Value.ONE));
				} else {
					//must be two
					result.add(new ValuePair(Value.ONE));
					result.add(new ValuePair(Value.ONE));
				}
			} else if (diceValue == 5) {
				if (numberOfSameValue == 1) {
					result.add(new ValuePair(Value.FIVE));
				} else {
					//must be two
					result.add(new ValuePair(Value.FIVE));
					result.add(new ValuePair(Value.FIVE));
				}
			}
		}
		dices = tempDices;
		
		Logger.log(LogLevel.DEBUG, "Dices", "The dices are " + dices);
		Logger.log(LogLevel.DEBUG, "Dices", "The value pairs are " + result);
		return result;
	}
	
	/**
	 * Counts the amount of one number in all dices.(with state POINTS or NO_POINTS)
	 * @return array of integer. Value n in 0 means that n ones are thrown.
	 */
	private ArrayList<Integer> countDices() {
		ArrayList<Integer> counts = new ArrayList<Integer>();
		//Initialize all counts with 0
		for (int i = 0; i < 6; ++i) {
			counts.add(0);
		}
		for(int i=0; i< NUMBER_OF_DICES; ++i) {
			if (dices.get(i).isCountable() && dices.get(i).hasNumber()) {
				int old = counts.get(dices.get(i).getNumber() - 1);
				//increment value
				counts.set(dices.get(i).getNumber() - 1, old + 1);
			}
		}
		
		return counts;
	}
	
	/**
	 * Determines if there is a straight (only for POINTS or NO_POINTS dices)
	 * @param dices
	 * @return returns 0 if there is no straight, else it returns the first dice of the straight
	 */
	private int straight() {
		if (dices.size() < 5) {
			return 0;
		}
		
		//straight has to begin with 1 or 2
		if (dices.get(0).getNumber() > 2)
		{
			return 0;
		}
		int last = dices.get(0).getNumber();
		if (!dices.get(0).isCountable()) {
			return 0;
		}
		for(int i = 1; i < 5; ++i){
			int current = dices.get(i).getNumber();
			if (last != current - 1 || !dices.get(i).isCountable() )
			{
				return 0;
			}
			last = current;
		}
		return dices.get(0).getNumber();
	}

	public void fixDice(int index) {
		this.dices.get(index).setState(DiceState.FIX);
		//TODO: fix all dices which are "connected"
	}
	
	public void freeDice(int index) {
		this.dices.get(index).setState(DiceState.NO_POINTS);
		//TODO: free all dices which are "connected"
		
	}

	public int getAllPoints() {

		return allPoints;
	}

	public int getNewPoints() {
		return newPoints;
	}

	public void resetAll() {
		resetJustDices();
		resetPoints();
	}

	public void resetPoints() {
		allPoints = 0;
		newPoints = 0;		
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

	public void mergeTwoFives() {
		assert(isPossibleToMergeTwoFives());
		boolean first = true;
		for (Dice dice : dices) {
			if (dice.getState() != DiceState.FIX && dice.getNumber() == 5 ) {
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
		for (Dice dice : dices) {
			if (dice.getState() != DiceState.FIX && dice.getNumber() == 5 ) {
				i++;
			}
		}
		//only possible if there are exactly two not fixed fives
		return i == 2;
	}

}
