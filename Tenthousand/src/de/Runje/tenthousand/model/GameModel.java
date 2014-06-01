package de.Runje.tenthousand.model;

import java.util.ArrayList;

import de.Runje.tenthousand.controller.Action;
import de.Runje.tenthousand.controller.ActionHandler;
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
		this.playerHandler = new PlayerHandler(diceHandler, this);
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
		this.diceHandler = new DiceHandler(dices);
		this.playerHandler = new PlayerHandler(diceHandler, this);
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

	private void handleAIPlayer() {
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
		return !getPlayingPlayer().hasNotRolled()  && !isGameFinished();
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
	

}
