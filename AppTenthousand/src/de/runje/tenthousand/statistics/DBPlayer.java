package de.runje.tenthousand.statistics;

import de.runje.tenthousand.model.GameModel;
import de.runje.tenthousand.model.Player;


public class DBPlayer {
	private String name;
	private int games;
	private int playing;
	private int wins;
	private int minTurns;
	private int minRolls;
	private int maxPointsGame;
	private int maxPointsRoll;
	private int maxPointsTurn;
	public DBPlayer(String name, int playing, int games, int wins, int maxPointsTurn, int maxPointsRoll, int maxPointsGame, int minRolls, int minTurns) {
		this.name = name;
		this.playing = playing;
		this.games = games;
		this.wins = wins;
		this.maxPointsTurn = maxPointsTurn;
		this.maxPointsRoll = maxPointsRoll;
		this.maxPointsGame = maxPointsGame;
		this.minRolls = minRolls;
		this.minTurns = minTurns;
	}
	
	public DBPlayer(String name, int playing)
	{
		this(name, playing, 0, 0, 0, 0, 0, -1, -1);
	}
	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DBPlayer [name=" + name + ", games=" + games + ", playing="
				+ playing + ", wins=" + wins + ", minTurns=" + minTurns
				+ ", minRolls=" + minRolls + ", maxPointsGame=" + maxPointsGame
				+ ", maxPointsRoll=" + maxPointsRoll + ", maxPointsTurn="
				+ maxPointsTurn + "]";
	}

	/**
	 * @return the wins
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * @param wins the wins to set
	 */
	public void setWins(int wins) {
		this.wins = wins;
	}

	/**
	 * @return the minTurns
	 */
	public int getMinTurns() {
		return minTurns;
	}

	/**
	 * @param minTurns the minTurns to set
	 */
	public void setMinTurns(int minTurns) {
		this.minTurns = minTurns;
	}

	/**
	 * @return the minRolls
	 */
	public int getMinRolls() {
		return minRolls;
	}

	/**
	 * @param minRolls the minRolls to set
	 */
	public void setMinRolls(int minRolls) {
		this.minRolls = minRolls;
	}

	/**
	 * @return the maxPointsGame
	 */
	public int getMaxPointsGame() {
		return maxPointsGame;
	}

	/**
	 * @param maxPointsGame the maxPointsGame to set
	 */
	public void setMaxPointsGame(int maxPointsGame) {
		this.maxPointsGame = maxPointsGame;
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
	 * @return the playing
	 */
	public int getPlaying() {
		return playing;
	}

	/**
	 * @param playing the playing to set
	 */
	public void setPlaying(int playing) {
		this.playing = playing;
	}

	public DBPlayer() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the games
	 */
	public int getGames() {
		return games;
	}
	/**
	 * @param games the games to set
	 */
	public void setGames(int games) {
		this.games = games;
	}
	
}
