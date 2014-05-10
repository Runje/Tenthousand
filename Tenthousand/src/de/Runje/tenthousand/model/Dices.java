package de.Runje.tenthousand.model;

import java.util.ArrayList;
import java.util.Collections;

public class Dices {
	
	public static final int NUMBER_OF_DICES = 5;

	private ArrayList<Dice> dices;
	/**
	 * ValuePairs from dices with status "FIX"
	 */
	private ArrayList<ValuePair> oldValuePairs;
	
	/**
	 * ValuePairs from dices with status "FREE"
	 */
	private ArrayList<ValuePair> newValuePairs;
	
	/**
	 * @return the valuePairs
	 */
	public ArrayList<ValuePair> getOldValuePairs() {
		return oldValuePairs;
	}

	public ArrayList<ValuePair> getNewValuePairs() {
		return newValuePairs;
	}
	
	private int rollCount = 0;
	public Dices() {
		this.dices = new ArrayList<Dice>();
		for(int i=0; i< NUMBER_OF_DICES; ++i ) {
			dices.add(new Dice(DiceState.FREE));
		}
	}
	
	public Dices(int n1, int n2, int n3, int n4, int n5) {
		this.dices = new ArrayList<Dice>();
		dices.add(new Dice(n1));
		dices.add(new Dice(n2));
		dices.add(new Dice(n3));
		dices.add(new Dice(n4));
		dices.add(new Dice(n5));
		Collections.sort(dices);
	}
	
	public Dices(ArrayList<Dice> dices) {
		this.dices = dices;
		Collections.sort(dices);
	}
	
	/**
	 * roll all FREE dices
	 */
	public void roll() {
		//fix dices with points before roll
		fixDices();
		for(int i=0; i< NUMBER_OF_DICES; ++i ) {
			if (dices.get(i).getState() == DiceState.FREE) {
				dices.get(i).roll();
			}
		}
		Collections.sort(dices);
		rollCount++;
		updateValuePairs();
	}

	/**
	 * Updates the member valuePairs
	 */
	public void updateValuePairs() {
		newValuePairs = determineValuePairs();
		if (oldValuePairs == null) {
			oldValuePairs = newValuePairs;
		} else {
			//add new ValuePairs to old ones
			oldValuePairs.addAll(newValuePairs);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dices [dices=" + dices + "]";
	}
	
	private void fixDices() {
		
	}
	/**
	 * Only calc valuePairs from FREE dices
	 * @return List of new Value Pairs
	 */
	public ArrayList<ValuePair> determineValuePairs() {
		assert(dices.size() <= 5);
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
		return result;
	}
	
	/**
	 * Counts the amount of one number in all dices.
	 * @return array of integer. Value n in 0 means that n ones are thrown.
	 */
	private ArrayList<Integer> countDices() {
		ArrayList<Integer> counts = new ArrayList<Integer>();
		//Initialize all counts with 0
		for (int i = 0; i < 6; ++i) {
			counts.add(0);
		}
		for(int i=0; i< NUMBER_OF_DICES; ++i) {
			if (dices.get(i).getState() == DiceState.FREE) {
				int old = counts.get(dices.get(i).getValue() - 1);
				//increment value
				counts.set(dices.get(i).getValue() - 1, old + 1);
			}
		}
		
		return counts;
	}
	
	/**
	 * Determines if there is a straight (only for FREE dices)
	 * @param dices
	 * @return returns 0 if there is no straight, else it returns the first dice of the straight
	 */
	private int straight() {
		if (dices.size() < 5) {
			return 0;
		}
		
		//straight has to begin with 1 or 2
		if (dices.get(0).getValue() > 2)
		{
			return 0;
		}
		int last = dices.get(0).getValue();
		if (!dices.get(0).isFree()) {
			return 0;
		}
		for(int i = 1; i < 5; ++i){
			int current = dices.get(i).getValue();
			if (last != current - 1 || !dices.get(i).isFree() )
			{
				return 0;
			}
			last = current;
		}
		return dices.get(0).getValue();
	}
}
