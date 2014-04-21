package de.Runje.tenthousand.model;

import java.util.ArrayList;
import java.util.Collections;

public class Dices {
	
	public static final int NUMBER_OF_DICES = 5;

	private ArrayList<Dice> dices;
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
		for(int i=0; i< NUMBER_OF_DICES; ++i ) {
			if (dices.get(i).getState() == DiceState.FREE) {
				dices.get(i).roll();
			}
		}
		Collections.sort(dices);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dices [dices=" + dices + "]";
	}
	
	/**
	 * Only calc points from FREE dices
	 * @param dices
	 * @return
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
		};
		return result;
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
