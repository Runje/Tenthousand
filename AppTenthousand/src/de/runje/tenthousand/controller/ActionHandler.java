package de.runje.tenthousand.controller;

import de.runje.tenthousand.logger.LogLevel;
import de.runje.tenthousand.logger.Logger;
import de.runje.tenthousand.model.Dice;
import de.runje.tenthousand.model.DiceState;
import de.runje.tenthousand.model.GameModel;
import de.runje.tenthousand.model.Player;
import de.runje.tenthousand.model.PlayerHandler;
import de.runje.tenthousand.model.Rules;

public class ActionHandler {

	public void executeAction(Action action, GameModel model) {
		Logger.log(LogLevel.DEBUG, "ActionHandler", action.toString());
		switch (action) {
		case Merge:
			executeMerge(model);
			break;
		case Next:
			executeNext(model);
			break;
		case Roll:
			executeRoll(model, model.getPlayingPlayer());
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
		executeRoll(model, model.getPlayingPlayer());
	}

	public void executeRoll(GameModel model, Player player) {
		model.playerHandler.rollDices(player);
		model.takeover = false;
		Logger.log(LogLevel.INFO, "ActionHandler", model.dices.getDices().toString());
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
		model.getPlayingPlayer().setFinished(true);
		int points = model.diceHandler.getAllPoints() + model.diceHandler.getNewPoints();
		if (points < Rules.MinPoints) {
			model.diceHandler.resetAll();
			model.takeover = false;
			model.playerHandler.addStrike(model.getPlayingPlayer());
			if (model.getPlayingPlayer().getStrikes() == Rules.MaxStrikes) {
				Logger.log(LogLevel.INFO, "ActionHandler", Rules.MaxStrikes + ". Strike, resetting points for " + model.getPlayingPlayer().getName());
				model.getPlayingPlayer().setAllPoints(0);
				model.playerHandler.resetStrikes(model.getPlayingPlayer());
			}
		} else {
			//add points
			model.getPlayingPlayer().addPoints(points);
			model.dices.fix();
			model.takeover = true;
			model.playerHandler.resetStrikes(model.getPlayingPlayer());
		}
		
		model.getPlayingPlayer().setRolls(0);
		model.nextPlayer();
		model.notifyObservers();
		model.handleNextPlayer();
		model.notifyObservers();
	}
	
	private void executeMerge(GameModel model) {
		model.diceHandler.mergeTwoFives();
		model.notifyObservers();
	}


}
