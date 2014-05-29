package de.Runje.tenthousand.controller;

import de.Runje.tenthousand.model.Dice;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.Dices;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.model.Player;

public class ActionHandler {

	public void executeAction(Action action, GameModel model) {
		switch (action) {
		case Merge:
			executeMerge(model);
			break;
		case Next:
			executeNext(model);
			break;
		case Roll:
			executeRoll(model);
			break;
		case Switch:
			executeSwitch(model, action.index);
			break;
		case Takeover:
			break;
		default:
			break;
		
		}
	}

	private void executeRoll(GameModel model) {
		Player player = model.getPlayingPlayer();
		player.rollDices(model.dices);
		model.takeover = false;
		model.notifyObservers();
	}
	
	private void executeSwitch(GameModel model, int index) {
		Dice dice = model.dices.getDices().get(index);
		DiceState state = dice.getState();
		if (state == DiceState.POINTS) {
			dice.setState(DiceState.FORCE_NO_POINTS);
		} else if (state == DiceState.FORCE_NO_POINTS) {
			dice.setState(DiceState.POINTS);
		}
		model.dices.determineValuePairs();
		model.notifyObservers();
	}
	
	private void executeNext(GameModel model) {
		int points = model.dices.getAllPoints() + model.dices.getNewPoints();
		//TODO: Make 300 constant
		if (points < 300) {
			model.dices.resetAll();
			model.takeover = false;
		} else {
			//add points
			model.getPlayingPlayer().addPoints(points);
			model.dices.fix();
			model.takeover = true;
		}
		
		model.nextPlayer();
		model.getPlayingPlayer().startNewMove();
		model.notifyObservers();
	}
	
	private void executeMerge(GameModel model) {
		model.dices.mergeTwoFives();
		model.notifyObservers();
	}


}
