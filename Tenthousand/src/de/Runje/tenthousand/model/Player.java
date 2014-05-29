package de.Runje.tenthousand.model;

import de.Runje.tenthousand.logger.LogLevel;
import de.Runje.tenthousand.logger.Logger;

public class Player {

	/**
	 * Points of the Player
	 */
	private int allPoints;
	
	private boolean willTakeOver;
	/**
	 * Name of the Player
	 */
	private String name;
	
	private int rolls;
	
	/**
	 * Flag to determine if players turn is finished
	 */
	private boolean finished;
	
	Player(String name) {
		this.name = name;
		this.allPoints = 0;
		this.rolls = 0;
		this.finished = false;
		this.willTakeOver = false;
	}

	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}
	/**
	 * @return the points
	 */
	public int getPoints() {
		return allPoints;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public void rollDices(DiceHandler diceHandler) {
		//TODO: assert funktioniert nicht!?
		assert(!isFinished());
		if (rolls == 0 && !willTakeOver) {
			Logger.log(LogLevel.INFO, "Player", name + " will not take over the dices.");
			diceHandler.resetAll();
		} else {
			Logger.log(LogLevel.INFO, "Player", name + " will take over the dices.");
			willTakeOver = false;
		}
		if (diceHandler.dices.areAllFixed()) {
			rolls = 0;
			diceHandler.dices.reset();
		}
		diceHandler.rollDices();
		rolls++;
		Logger.log(LogLevel.INFO, "Player", name + " rolls the dices for the " + rolls + ". time.");
		checkIfFinished(diceHandler);
	}

	/**
	 * @param rolls the rolls to set
	 */
	public void setRolls(int rolls) {
		this.rolls = rolls;
	}

	public void checkIfFinished(DiceHandler diceHandler) {
		if (diceHandler.getNewPoints() == 0) {
			rolls = 0;
			diceHandler.resetPoints();
			finished = true;
			Logger.log(LogLevel.INFO, "Player", name + " is finished ( 0 points).");
		} else if (rolls == 3 && !diceHandler.getDices().areAllFixed()) {
			rolls = 0;
			finished = true;
			Logger.log(LogLevel.INFO, "Player", name + " is finished ( 3 rolls).");
		}
	}
	
	public void startNewMove() {
		finished = false;
	}
	
	public void fixDice(int index, Dices dices) {
		dices.fixDice(index);
	}

	public void freeDice(int index, Dices dices) {
		dices.freeDice(index);
	}

	public void addPoints(int actualPoints) {
		this.allPoints += actualPoints;
		
	}

	public void takeOver() {
		willTakeOver = true;
	}

	public boolean hasNotRolled() {
		return rolls == 0 && !isFinished();
	}
		
}
