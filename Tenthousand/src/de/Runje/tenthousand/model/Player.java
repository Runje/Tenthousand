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
	
	/**
	 * Counts zero points in a row
	 */
	private int strikes;
	
	public Player(String name) {
		this.name = name;
		this.allPoints = 0;
		this.rolls = 0;
		this.finished = false;
		this.willTakeOver = false;
		this.strikes = 0;
	}

	/**
	 * @return the allPoints
	 */
	public int getAllPoints() {
		return allPoints;
	}

	/**
	 * @param allPoints the allPoints to set
	 */
	public void setAllPoints(int allPoints) {
		this.allPoints = allPoints;
	}

	/**
	 * @return the strikes
	 */
	public int getStrikes() {
		return strikes;
	}

	/**
	 * @param strikes the strikes to set
	 */
	public void setStrikes(int strikes) {
		this.strikes = strikes;
	}

	/**
	 * @return the willTakeOver
	 */
	public boolean isWillTakeOver() {
		return willTakeOver;
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

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
		
}
