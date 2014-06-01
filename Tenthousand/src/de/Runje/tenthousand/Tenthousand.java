package de.Runje.tenthousand;

import java.util.ArrayList;

import de.Runje.tenthousand.controller.Controller;
import de.Runje.tenthousand.model.AIPlayer;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.model.HumanPlayer;
import de.Runje.tenthousand.model.MyStrategy;
import de.Runje.tenthousand.model.Player;
import de.Runje.tenthousand.model.Rules;
import de.Runje.tenthousand.simulator.Simulator;
import de.Runje.tenthousand.simulatorView.SimulatorView;
import de.Runje.tenthousand.view.GameView;


public class Tenthousand {
    
	private static Rules rules;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer("Thomas"));
		players.add(new AIPlayer("Milena", new MyStrategy()));
		GameModel model = new GameModel(players, new Rules());
		
		Controller controller = new Controller(model);
		GameView view = new GameView(controller);
		Simulator simulator = new Simulator(model);
		SimulatorView sView = new SimulatorView(simulator);
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

