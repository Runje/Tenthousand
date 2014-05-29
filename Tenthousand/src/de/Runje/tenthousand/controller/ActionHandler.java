package de.Runje.tenthousand.controller;

import de.Runje.tenthousand.model.Dice;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.model.Player;
import de.Runje.tenthousand.model.Rules;

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
			executeTakeover(model);
			break;
		default:
			break;
		
		}
	}

	private void executeTakeover(GameModel model) {
		model.getPlayingPlayer().takeOver();
	}

	private void executeRoll(GameModel model) {
		Player player = model.getPlayingPlayer();
		player.rollDices(model.diceHandler);
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
		model.diceHandler.updateValuePairs();
		model.diceHandler.updatePoints();
		model.diceHandler.updateStates();
		model.notifyObservers();
	}
	
	private void executeNext(GameModel model) {
		int points = model.diceHandler.getAllPoints() + model.diceHandler.getNewPoints();
		if (points < Rules.MinPoints) {
			model.diceHandler.resetAll();
			model.takeover = false;
		} else {
			//add points
			model.getPlayingPlayer().addPoints(points);
			model.dices.fix();
			model.takeover = true;
		}
		
		model.getPlayingPlayer().setRolls(0);
		model.nextPlayer();
		model.getPlayingPlayer().startNewMove();
		model.notifyObservers();
	}
	
	private void executeMerge(GameModel model) {
		model.diceHandler.mergeTwoFives();
		model.notifyObservers();
	}


}
