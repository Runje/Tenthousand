package de.Runje.tenthousand.simulator;

import java.util.ArrayList;

import de.Runje.tenthousand.model.Dice;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.Dices;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.model.Player;
import de.Runje.tenthousand.observer.IObserver;

public class Simulator implements IObserver{

	private GameModel model;
	
	/**
	 * The player to change
	 */
	private Player player;
	
	/**
	 * Index of the dice to change
	 */
	private int index;
	
	/**
	 * Number of the changed dice
	 */
	private int number;
	
	/**
	 * State of the changed dice
	 */
	private DiceState state;

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @param diceState the state to set
	 */
	public void setState(DiceState diceState) {
		this.state = diceState;
	}

	/**
	 * @return the dice
	 */
	public int getDice() {
		return index;
	}

	/**
	 * @param dice the dice to set
	 */
	public void setDice(int dice) {
		this.index = dice;
	}

	public Simulator(GameModel model) {
		this.model = model;
		this.index = 0;
		this.number = 1;
		this.state = DiceState.NO_POINTS;
		this.player = new Player(model.getPlayingPlayer().getName());
	}

	public static int simulate() {
		Dices dices = new Dices();
		dices.roll();
		dices.roll();
		dices.roll();
		return 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		//Do i need this? Let's see...
	}

	public void changeDice() {
		Dice d = model.dices.getDices().get(index);
		d.setNumber(number);
		d.setState(state);
		model.diceHandler.update();
		model.notifyObservers();
	}

	public String[] getPlayersList() {
		ArrayList<Player> players = model.getPlayers();
		String[] list = new String[players.size()];
		
		int i = 0;
		for (Player player : players) {
			list[i] = player.getName();
			i++;
		}
		return list;
	}

	public void changePlayer(int points) {
		Player changePlayer = model.getPlayerByName(player.getName());
		changePlayer.setAllPoints(points);
		changePlayer.setStrikes(player.getStrikes());
		changePlayer.setRolls(player.getRolls());
		model.notifyObservers();
	}

	
	public void setName(String name) {
		player.setName(name);
	}
	
	public void setPoints(int points) {
		player.setAllPoints(points);
	}
	
	public void setRolls(int rolls) {
		player.setRolls(rolls);
	}
	
	public void setStrikes(int strikes) {
		player.setStrikes(strikes);
	}
}
