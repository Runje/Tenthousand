package de.Runje.tenthousand.controller;

import de.Runje.tenthousand.model.Dices;
import de.Runje.tenthousand.model.GameModel;
import de.Runje.tenthousand.model.Player;
import de.Runje.tenthousand.observer.Observable;

public class Controller extends Observable {

	//Model of the game
	private GameModel model;
	public Controller(GameModel model) {
		this.model = model;
		
	}

	public void rollDices() {
		//is this a reference or copy???
		Player player = model.getPlayingPlayer();
		player.rollDices(model.dices);
		notifyObservers();
		System.out.println("rollDices Controller");
	}
	
	public Dices getDices() {
		return model.dices;
	}

	public int getPoints() {
		return model.getPlayingPlayer().getPoints();
	}

	public void updateView() {
		//update view with the model
		
		//Points
		
		//Which turn is it
		
		//Dices
	}
}
