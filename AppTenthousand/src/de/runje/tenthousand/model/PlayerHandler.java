package de.runje.tenthousand.model;

import de.runje.tenthousand.controller.Action;
import de.runje.tenthousand.controller.ActionHandler;
import de.runje.tenthousand.logger.LogLevel;
import de.runje.tenthousand.logger.Logger;

public class PlayerHandler {
	
	public DiceHandler diceHandler;
	public GameModel model;

	public PlayerHandler(GameModel model) {
		this.diceHandler = model.diceHandler;
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
		makeTakeover(player, strategy);
		while (!player.isFinished()) {
			makeOneMove(player, strategy);
		}
	}
	
	public void makeTakeover(Player player, IStrategy strategy) {
		// take over
		if (model.isPossibleToTakeOver()) {
			if (strategy.takeover(model)) {
				player.setWillTakeOver(true);
			}
		}
	}
	
	public void makeOneMove(AIPlayer p) {
		makeOneMove(p, p.getStrategy());
	}
	public void makeOneMove(Player player, IStrategy strategy) {
		ActionHandler actionHandler = new ActionHandler();
		// release dices
		if (strategy.endMove(model) && model.nextIsPossible()) {
			Logger.log(LogLevel.INFO, "Player", "End move for " + player.getName());
			player.setFinished(true);
			return;
		} else if (model.isPossibleToMerge() && strategy.merge(model)) {
			// merge
			actionHandler.executeAction(Action.Merge, model);
		}
		for (int i : strategy.releaseDices(model)) {
			Action a = Action.Switch;
			a.index = i;
			actionHandler.executeAction(a, model);
		}
		// roll
		actionHandler.executeRoll(model, player);
		// next
		Logger.log(LogLevel.INFO, "Player", "Points " + model.getPoints());
	}
	public void makeTurnFor(AIPlayer player) {
		makeTurnFor(player, player.getStrategy());
	}

	public void makeTakeover(AIPlayer player) {
		makeTakeover(player, player.getStrategy());
	}

}
