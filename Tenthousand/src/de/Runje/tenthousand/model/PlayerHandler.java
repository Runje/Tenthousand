package de.Runje.tenthousand.model;

import de.Runje.tenthousand.controller.Action;
import de.Runje.tenthousand.controller.ActionHandler;
import de.Runje.tenthousand.logger.LogLevel;
import de.Runje.tenthousand.logger.Logger;

public class PlayerHandler {
	
	public DiceHandler diceHandler;
	public GameModel model;

	public PlayerHandler(DiceHandler diceHandler, GameModel model) {
		this.diceHandler = diceHandler;
		this.model = model;
	}
	
	public void rollDices(Player player) {
		assert(!player.isFinished());
		if (player.getRolls() == 0 && !player.willTakeOver()) {
			Logger.log(LogLevel.INFO, "Player", player.getName() + " will not take over the dices.");
			diceHandler.resetAll();
		} else if (player.getRolls() == 0){
			Logger.log(LogLevel.INFO, "Player", player.getName() + " will take over the dices.");
			player.setWillTakeOver(false);
		}
		if (diceHandler.dices.areAllFixed()) {
			player.setRolls(0);
			diceHandler.dices.reset();
		}
		diceHandler.rollDices();
		player.setRolls(player.getRolls() + 1);
		Logger.log(LogLevel.INFO, "Player", player.getName() + " rolls the dices for the " + player.getRolls() + ". time.");
		checkIfFinished(player);
	}
	
	public void checkIfFinished(Player player) {
		if (diceHandler.getNewPoints() == 0) {
			player.setRolls(0);
			diceHandler.resetPoints();
			player.setFinished(true);
			Logger.log(LogLevel.INFO, "Player", player.getName() + " is finished ( 0 points).");
		} else if (player.getRolls() == 3 && !diceHandler.getDices().areAllFixed()) {
			player.setRolls(0);
			player.setFinished(true);
			Logger.log(LogLevel.INFO, "Player", player.getName() + " is finished ( 3 rolls).");
		}
	}

	public void addStrike(Player player) {
		player.setStrikes(player.getStrikes() + 1);
	}

	public void resetStrikes(Player player) {
		player.setStrikes(0);
	}
	
	public void makeTurnFor(Player player, IStrategy strategy) {
		Logger.log(LogLevel.INFO, "Player", "Make turn for " + player.getName() + "Rolls: " + player.getRolls() + " Strikes: " + player.getStrikes());
		Logger.log(LogLevel.INFO, "Player", "Points: " + model.getPoints() + " Dices: " + model.dices);
		ActionHandler actionHandler = new ActionHandler();
		// take over
		if (model.isPossibleToTakeOver()) {
			if (strategy.takeover(model)) {
				player.willTakeOver();
			}
		}
		while (!player.isFinished()) {
			// release dices
			for (int i : strategy.releaseDices(model)) {
				Action a = Action.Switch;
				a.index = i;
				actionHandler.executeAction(a, model);
			}
			// roll
			actionHandler.executeRoll(model, player);
			// next
			Logger.log(LogLevel.INFO, "Player", "Points " + model.getPoints());
			if (strategy.endMove(model) && model.nextIsPossible()) {
				Logger.log(LogLevel.INFO, "Player", "End move for " + player.getName());
				break;
			}
			// merge
			if (model.isPossibleToMerge() && strategy.merge(model)) {
				actionHandler.executeAction(Action.Merge, model);
			}
		}
	}
	public void makeTurnFor(AIPlayer player) {
		makeTurnFor(player, player.getStrategy());
	}

}
