package de.Runje.tenthousand.controller;

import java.util.ArrayList;

import de.Runje.tenthousand.model.Dice;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.Dices;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.model.Player;
import de.Runje.tenthousand.observer.Observable;

public class Controller {


	//Model of the game
	public GameModel model;
	public ActionHandler actionHandler;

	public Controller(GameModel model) {
		this.model = model;
		this.actionHandler = new ActionHandler();
	}

	public Dices getDices() {
		return model.dices;
	}

	public int getPoints() {
		return model.getPlayingPlayer().getPoints();
	}

	public int getActualPoints() {
		return model.diceHandler.getAllPoints() + model.diceHandler.getNewPoints();
	}

	public String getNameOfPlayingPlayer() {
		return model.getPlayingPlayer().getName();
	}
	
	public ArrayList<Player> getPlayers() {
		return model.getPlayers();
	}

	public boolean isPossibleToRoll() {
		return model.isPossibleToRoll();
	}

	public boolean isPossibleToTakeOver() {
		return model.isPossibleToTakeOver();
	}

	public boolean nextIsPossible() {
		return model.nextIsPossible();
	}

	public boolean isPossibleToMerge() {
		return model.isPossibleToMerge();
	}

	public void handleAction(Action action) {
		actionHandler.executeAction(action, model);
	}
	
}
