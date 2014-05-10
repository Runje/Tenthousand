package de.Runje.tenthousand.model;

import java.util.ArrayList;

public class GameModel {

	private ArrayList<Player> players;
	private Rules rules;
	public Dices dices;
	
	private boolean finished;
	
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
	}
	
	public void start() {
		
		determineOrder();
		Dices dices = new Dices();
		while (!isFinished()) {
			for (Player player : players) {
				player.makeTurn(dices);
			}
		}
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
		// TODO
		return players.get(0);
	}

}
