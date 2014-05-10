package de.Runje.tenthousand.model;

import java.util.ArrayList;

import de.Runje.tenthousand.controller.Controller;
import de.Runje.tenthousand.view.GameView;


public class Tenthousand {
    
	private static Rules rules;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer("Thomas"));
		GameModel model = new GameModel(players, new Rules());
		
		Controller controller = new Controller(model);
		GameView view = new GameView(controller);
	}

//	public static void main(String[] args) {
//        Dices dices = new Dices();
//        dices.roll();
//        System.out.println(dices);
//        ArrayList<ValuePair> result = dices.determineValuePairs();
//        for (int i = 0; i < result.size(); ++i) {
//        	System.out.println(result.get(i));
//        }
//        System.out.println(getRules().calcPoints(result));
//        dices.roll();
//        System.out.println(dices);
//        result = dices.determineValuePairs();
//        for (int i = 0; i < result.size(); ++i) {
//        	System.out.println(result.get(i));
//        }
//        System.out.println(getRules().calcPoints(result));
//        dices.roll();
//        System.out.println(dices);
//        result = dices.determineValuePairs();
//        for (int i = 0; i < result.size(); ++i) {
//        	System.out.println(result.get(i));
//        }
//        System.out.println(getRules().calcPoints(result));
//
//    }
    
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

