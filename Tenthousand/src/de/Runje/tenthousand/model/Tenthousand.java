package de.Runje.tenthousand.model;

import java.util.ArrayList;


public class Tenthousand {
    
	private static Rules rules;
	public static void main(String[] args) {
        Dices dices = new Dices();
        dices.roll();
        System.out.println(dices);
        ArrayList<ValuePair> result = dices.determineValuePairs();
        for (int i = 0; i < result.size(); ++i) {
        	System.out.println(result.get(i));
        }
        System.out.println(getRules().calcPoints(result));
    }
    
	public static boolean isStraightActive() {
		return getRules().isStraightActive();
	}
	
    public static Rules getRules() {
    	if (rules == null) {
    		rules = new Rules();
    	}
    	return rules;
    }

}

