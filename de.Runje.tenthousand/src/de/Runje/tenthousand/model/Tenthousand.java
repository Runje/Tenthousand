package de.Runje.tenthousand.model;


public class Tenthousand {
    
	private static Rules rules;
	public static void main(String[] args) {
        
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

