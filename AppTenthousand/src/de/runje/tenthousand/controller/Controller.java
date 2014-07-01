package de.runje.tenthousand.controller;

import java.util.ArrayList;

import de.runje.tenthousand.model.Dice;
import de.runje.tenthousand.model.DiceState;
import de.runje.tenthousand.model.Dices;
import de.runje.tenthousand.model.GameModel;
import de.runje.tenthousand.model.Player;
import de.runje.tenthousand.observer.Observable;

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
