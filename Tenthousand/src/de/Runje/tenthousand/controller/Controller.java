package de.Runje.tenthousand.controller;

import java.util.ArrayList;

import de.Runje.tenthousand.model.Dice;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.Dices;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.model.Player;
import de.Runje.tenthousand.observer.Observable;

public class Controller extends Observable {


	//Model of the game
	private GameModel model;
	private boolean takeOver;

	public Controller(GameModel model) {
		this.model = model;
		this.takeOver = false;
	}

	public void rollDices() {
		Player player = model.getPlayingPlayer();
		player.rollDices(model.dices);
		takeOver = false;
		notifyObservers();
	}
	
	public Dices getDices() {
		return model.dices;
	}

	public int getPoints() {
		return model.getPlayingPlayer().getPoints();
	}

	public void switchDiceState(int index) {
		Dice dice = model.dices.getDices().get(index);
		DiceState state = dice.getState();
		if (state == DiceState.POINTS) {
			dice.setState(DiceState.FORCE_NO_POINTS);
		} else if (state == DiceState.FORCE_NO_POINTS) {
			dice.setState(DiceState.POINTS);
		}
		model.dices.determineValuePairs();
		notifyObservers();
	}

	public int getActualPoints() {
		return model.dices.getAllPoints() + model.dices.getNewPoints();
	}

	public void endMove() {
		//TODO: Make 300 constant
		if (getActualPoints() < 300) {
			model.dices.resetAll();
			takeOver = false;
		} else {
			//add points
			addPoints();
			model.dices.fix();
			takeOver = true;
		}
		
		model.nextPlayer();
		model.getPlayingPlayer().startNewMove();
		notifyObservers();
	}

	private void addPoints() {
		model.getPlayingPlayer().addPoints(getActualPoints());
	}
	
	public String getNameOfPlayingPlayer() {
		return model.getPlayingPlayer().getName();
	}
	
	public ArrayList<Player> getPlayers() {
		return model.getPlayers();
	}

	public boolean isPossibleToRoll() {
		return !model.isPlayerFinished();
	}

	public void takeOver() {
		model.getPlayingPlayer().takeOver();
	}

	public boolean isPossibleToTakeOver() {
		return takeOver;
	}

	public boolean nextIsPossible() {
		return !model.getPlayingPlayer().hasNotRolled();
	}

	public void mergeTwoFives() {
		model.dices.mergeTwoFives();
		notifyObservers();
	}
	
	public boolean isPossibleToMerge() {
		return model.dices.isPossibleToMergeTwoFives();
	}
	
}
