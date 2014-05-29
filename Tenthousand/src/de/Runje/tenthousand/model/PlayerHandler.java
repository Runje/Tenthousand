package de.Runje.tenthousand.model;

import de.Runje.tenthousand.logger.LogLevel;
import de.Runje.tenthousand.logger.Logger;

public class PlayerHandler {
	
	private DiceHandler diceHandler;

	public PlayerHandler(DiceHandler diceHandler) {
		this.diceHandler = diceHandler;
	}
	
	public void rollDices(Player player) {
		assert(!player.isFinished());
		if (player.getRolls() == 0 && !player.willTakeOver()) {
			Logger.log(LogLevel.INFO, "Player", player.getName() + " will not take over the dices.");
			diceHandler.resetAll();
		} else {
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
	
	private void checkIfFinished(Player player) {
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

}
