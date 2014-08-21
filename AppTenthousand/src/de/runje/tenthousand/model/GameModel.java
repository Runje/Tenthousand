package de.runje.tenthousand.model;

import java.util.ArrayList;

import de.runje.tenthousand.controller.Action;
import de.runje.tenthousand.controller.ActionHandler;
import de.runje.tenthousand.logger.LogLevel;
import de.runje.tenthousand.logger.Logger;
import de.runje.tenthousand.observer.Observable;

public class GameModel extends Observable{

	private ArrayList<Player> players;
	int indexPlayingPlayer = 0;
	public boolean takeover;
	public Dices dices;
	public Rules rules;
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
		this.playerHandler = new PlayerHandler(this);
		this.gameFinished = false;
	}
	
	public GameModel(GameModel model) {
		//copy players
		this.players = new ArrayList<Player>(model.players.size());
		for (Player player : model.players) {
			this.players.add(new Player(player));
		}
		this.indexPlayingPlayer = model.indexPlayingPlayer;
		this.finished = model.finished;
		this.rules = model.rules;
		this.dices = new Dices(model.dices);
		this.takeover = model.takeover;
		this.diceHandler = new DiceHandler(this.dices);
		this.diceHandler.setAllPoints(model.diceHandler.getAllPoints());
		this.diceHandler.setNewPoints(model.diceHandler.getNewPoints());
		this.playerHandler = new PlayerHandler(this);
		this.gameFinished = model.gameFinished;
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

	public void handleAIPlayer() {
		AIPlayer player = (AIPlayer) getPlayingPlayer();
		playerHandler.makeTurnFor(player);
		new ActionHandler().executeAction(Action.Next, this);
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

	public boolean isPossibleToTakeOver() {
		return takeover && !isGameFinished();
	}

	public boolean isPossibleToRoll() {
		return !isPlayerFinished() && !isGameFinished();
	}
	
	public boolean nextIsPossible() {
		return !getPlayingPlayer().hasNotRolled()  && !isGameFinished() && (getPoints() >= 300 || getPlayingPlayer().isFinished());
	}

	public boolean isPossibleToMerge() {
		return diceHandler.isPossibleToMergeTwoFives()  && !isGameFinished();
	}

	public void handleNextPlayer() {
		getPlayingPlayer().startNewMove();
		// AIPlayer?
		if (!getPlayingPlayer().isHuman()) {
			handleAIPlayer();
		}
	}

	public int getPoints() {
		return diceHandler.getAllPoints() + diceHandler.getNewPoints();
	}
	public int getFreeDices() {
		return dices.getFreeDices();
	}

	public boolean resetDices() {
		if (dices.areAllFixed() || (getPlayingPlayer().hasNotRolled() && !getPlayingPlayer().willTakeOver())) {
			return true;
		}
		
		else return false;
	}

	public String getWinner() {
		int max = 0;
		String winner = "No winner";
		for (Player player : players) {
			if (player.getPoints() > max)
			{
				winner = player.getName();
				max = player.getPoints();
			}
		}
		// TODO what if two players have the same points?
		return winner;
	}
	

}
