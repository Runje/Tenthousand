package de.runje.tenthousand.model;


public class Player {

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + ", points: " + allPoints + ", rolls="
				+ rolls + ", strikes=" + strikes;
	}

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
	
	private int totalRolls = 0;
	
	private int totalTurns = 0;
	
	private int maxPointsRoll = 0;
	
	private int maxPointsTurn = 0;
	
	/**
	 * @return the totalRolls
	 */
	public int getTotalRolls() {
		return totalRolls;
	}

	/**
	 * @param totalRolls the totalRolls to set
	 */
	public void increaseTotalRolls() {
		this.totalRolls++;
	}

	/**
	 * @return the totalTurns
	 */
	public int getTotalTurns() {
		return totalTurns;
	}

	/**
	 * @param totalTurns the totalTurns to set
	 */
	public void increaseTotalTurns() {
		this.totalTurns++;;
	}

	/**
	 * @return the maxPointsRoll
	 */
	public int getMaxPointsRoll() {
		return maxPointsRoll;
	}

	/**
	 * @param maxPointsRoll the maxPointsRoll to set
	 */
	public void setMaxPointsRoll(int maxPointsRoll) {
		this.maxPointsRoll = maxPointsRoll;
	}

	/**
	 * @return the maxPointsTurn
	 */
	public int getMaxPointsTurn() {
		return maxPointsTurn;
	}

	/**
	 * @param maxPointsTurn the maxPointsTurn to set
	 */
	public void setMaxPointsTurn(int maxPointsTurn) {
		this.maxPointsTurn = maxPointsTurn;
	}

	/**
	 * Flag to determine if players turn is finished
	 */
	private boolean finished;
	
	/**
	 * Counts zero points in a row
	 */
	private int strikes;
	
	/**
	 * AI Player
	 */
	protected boolean human;
	
	/**
	 * @return the ai
	 */
	public boolean isHuman() {
		return human;
	}

	public Player(String name) {
		this.name = name;
		this.allPoints = 0;
		this.rolls = 0;
		this.finished = false;
		this.willTakeOver = false;
		this.strikes = 0;
		this.human = true;
	}

	public Player(Player player) {
		this.name = player.getName();
		this.allPoints = player.getAllPoints();
		this.rolls = player.getRolls();
		this.finished = player.isFinished();
		this.willTakeOver = player.willTakeOver();
		this.strikes = player.getStrikes();
		this.human = player.isHuman();
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
