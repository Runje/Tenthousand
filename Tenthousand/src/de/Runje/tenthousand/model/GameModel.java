package de.Runje.tenthousand.model;

import java.util.ArrayList;

import de.Runje.tenthousand.observer.Observable;

public class GameModel extends Observable{

	private ArrayList<Player> players;
	int indexPlayingPlayer = 0;
	public boolean takeover;
	public Dices dices;
	private Rules rules;
	private boolean finished;
	public DiceHandler diceHandler;
	public PlayerHandler playerHandler;
	private boolean gameFinished;
	
	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}

	public GameModel(ArrayList<Player> players, Rules rules) {
		this.players = players;
		this.finished = false;
		this.rules = rules;
		this.dices = new Dices();
		this.takeover = false;
		this.diceHandler = new DiceHandler(dices);
		this.playerHandler = new PlayerHandler(diceHandler);
	}
	

	/**
	 * @return the rules
	 */
	public Rules getRules() {
		return rules;
	}

	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	private void determineOrder() {
		//random or let decide rules
		//change order of players
	}

	/**
	 * Returns player which turn it is
	 * @return
	 */
	public Player getPlayingPlayer() {
		return players.get(indexPlayingPlayer);
	}

	public void nextPlayer() {
		indexPlayingPlayer = (indexPlayingPlayer + 1) % players.size();
		if (indexPlayingPlayer == 0) {
			for (Player player : players) {
				if (player.getPoints() >= Rules.WinPoints) {
					endGame();
				}
			}
		}
	}

	private void endGame() {
		gameFinished = true;
		
	}

	/**
	 * @return the gameFinished
	 */
	public boolean isGameFinished() {
		return gameFinished;
	}

	public boolean isPlayerFinished() {
		return getPlayingPlayer().isFinished();
	}

	public Player getPlayerByName(String name) {
		for (Player player : players) {
			if (player.getName().equals(name)) {
				return player;
			}
		}
		return null;
	}
	

}
