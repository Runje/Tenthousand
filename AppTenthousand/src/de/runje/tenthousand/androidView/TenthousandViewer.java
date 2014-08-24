package de.runje.tenthousand.androidView;

import android.graphics.Color;
import de.runje.tenthousand.controller.Action;
import de.runje.tenthousand.controller.ActionHandler;
import de.runje.tenthousand.model.GameModel;
import de.runje.tenthousand.model.Player;

public class TenthousandViewer {
	
	private GameModel model;

	public TenthousandViewer(GameModel model) {
		this.model = model;
	}
	
	public void updatePlayers() {
		for (int i = 0; i < model.getPlayers().size(); i++) {
			Player p = model.getPlayers().get(i);
			GameUIElement.players[i].setPoints(p.getPoints());
			GameUIElement.players[i].setStrikes(p.getStrikes());
			GameUIElement.players[i].setName(p.getName());
			GameUIElement.players[i].setHighlighted(model.getPlayingPlayer() == model.getPlayers().get(i));
			
			int rolls = 0;
			if (p.isFinished())
			{
				// Show no dices
				rolls = 3;
			}
			else
			{
				rolls = p.getRolls();
			}
			
			GameUIElement.players[i].setRolls(rolls);
		}
		
	}

	public void next() {
		new ActionHandler().executeAction(Action.Next, model);
	}
	
	public void updateButtons() {
		if (model.nextIsPossible()) {
			GameUIElement.next.setEnabled(true);
		} else {
			GameUIElement.next.setEnabled(false);
		}
		if (model.isPossibleToTakeOver()) {
			GameUIElement.takeover.setEnabled(true);
		} else {
			GameUIElement.takeover.setEnabled(false);
		}
		if (model.isPossibleToMerge()) {
			GameUIElement.merge.setEnabled(true);
		} else {
			GameUIElement.merge.setEnabled(false);
		}
		if (model.isPossibleToRoll()) {
			GameUIElement.roll.setEnabled(true);
		} else {
			GameUIElement.roll.setEnabled(false);
		}
	}
}
