package de.Runje.tenthousand.model;

import java.util.ArrayList;

import de.Runje.tenthousand.observer.Observable;

public class GameModel extends Observable{

	private ArrayList<Player> players;
	int indexPlayingPlayer = 0;
	private Rules rules;
	public boolean takeover;
	public Dices dices;
	
	private boolean finished;
	public DiceHandler diceHandler;
	
	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}

	public GameModel(ArrayList<Player> players, Rules rules) {
		this.rules = rules;
		this.players = players;
		this.finished = false;
		this.dices = new Dices();
		this.takeover = false;
		this.diceHandler = new DiceHandler(dices);
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
	}

	public boolean isPlayerFinished() {
		return getPlayingPlayer().isFinished();
	}
	

}
