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

	/**
	 * @return the willTakeOver
	 */
	public boolean willTakeOver() {
		return willTakeOver;
	}

	/**
	 * @param willTakeOver the willTakeOver to set
	 */
	public void setWillTakeOver(boolean willTakeOver) {
		this.willTakeOver = willTakeOver;
	}

	/**
	 * @return the rolls
	 */
	public int getRolls() {
		return rolls;
	}

	/**
	 * @param rolls the rolls to set
	 */
	public void setRolls(int rolls) {
		this.rolls = rolls;
	}

	
	/**
	 * @param finished the finished to set
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
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
