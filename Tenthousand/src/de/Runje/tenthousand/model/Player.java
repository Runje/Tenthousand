package de.Runje.tenthousand.model;

public class Player {

	/**
	 * Points of the Player
	 */
	private int points;
	
	private Rules rules;
	
	/**
	 * Name of the Player
	 */
	private String name;
	
	/**
	 * Flag to determine if players turn is finished
	 */
	private boolean finished;
	
	//TODO: Add rule as parameter
	Player(String name) {
		this.name = name;
		this.points = 0;
		this.rules = new Rules();
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
		return points;
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
		dices.roll();
		dices.updateValuePairs();
		points += rules.calcPoints(dices.getNewValuePairs());
	}


		
}
