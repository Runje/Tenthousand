package de.Runje.tenthousand.controller;

import de.Runje.tenthousand.logger.LogLevel;
import de.Runje.tenthousand.logger.Logger;
import de.Runje.tenthousand.model.Dice;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.model.Player;
import de.Runje.tenthousand.model.PlayerHandler;
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
		model.playerHandler.rollDices(model.getPlayingPlayer());
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
		model.diceHandler.update();
		model.notifyObservers();
	}
	
	private void executeNext(GameModel model) {
		int points = model.diceHandler.getAllPoints() + model.diceHandler.getNewPoints();
		if (points < Rules.MinPoints) {
			model.diceHandler.resetAll();
			model.takeover = false;
			model.playerHandler.addStrike(model.getPlayingPlayer());
			if (model.getPlayingPlayer().getStrikes() == Rules.MaxStrikes) {
				Logger.log(LogLevel.INFO, "ActionHandler", Rules.MaxStrikes + ". Strike, resetting points for " + model.getPlayingPlayer().getName());
				model.getPlayingPlayer().setAllPoints(0);
			};
		} else {
			//add points
			model.getPlayingPlayer().addPoints(points);
			model.dices.fix();
			model.takeover = true;
			model.playerHandler.resetStrikes(model.getPlayingPlayer());
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
