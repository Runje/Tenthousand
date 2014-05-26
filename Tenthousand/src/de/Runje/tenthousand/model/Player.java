package de.Runje.tenthousand.model;

import de.Runje.tenthousand.logger.LogLevel;
import de.Runje.tenthousand.logger.Logger;

public class Player {

	/**
	 * Points of the Player
	 */
	private int allPoints;
	
	private Rules rules;

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
	
	//TODO: Add rule as parameter
	Player(String name) {
		this.name = name;
		this.allPoints = 0;
		this.rules = new Rules();
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
	public void makeTurn(Dices dices) {
		while(!isFinished()) {
			rollDices(dices);
		}

	}
	public void rollDices(Dices dices) {
		//TODO: assert funktioniert nicht!?
		assert(!isFinished());
		if (rolls == 0 && !willTakeOver) {
			Logger.log(LogLevel.INFO, "Player", name + " will not take over the dices.");
			dices.resetAll();
		} else {
			Logger.log(LogLevel.INFO, "Player", name + " will take over the dices.");
			willTakeOver = false;
		}
		if (dices.areAllFixed()) {
			rolls = 0;
			dices.resetJustDices();
		}
		dices.roll();
		rolls++;
		Logger.log(LogLevel.INFO, "Player", name + " rolls the dices for the " + rolls + ". time.");
		checkIfFinished(dices);
	}

	public void checkIfFinished(Dices dices) {
		if (dices.getNewPoints() == 0) {
			rolls = 0;
			dices.resetPoints();
			finished = true;
			Logger.log(LogLevel.INFO, "Player", name + " is finished ( 0 points).");
		} else if (rolls == 3 && !dices.areAllFixed()) {
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
