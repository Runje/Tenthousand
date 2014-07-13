package de.runje.tenthousand.androidView;

import android.graphics.Color;
import de.runje.tenthousand.controller.Action;
import de.runje.tenthousand.controller.ActionHandler;
import de.runje.tenthousand.model.GameModel;

public class TenthousandViewer {
	
	private GameModel model;

	public TenthousandViewer(GameModel model) {
		this.model = model;
	}
	
	public void updatePlayers() {
		for (int i = 0; i < model.getPlayers().size(); i++) {
			GameUIElement.players[i].setText(model.getPlayers().get(i).toString());
			if (model.getPlayingPlayer() == model.getPlayers().get(i)) {
				// highlight player which has the turn
				GameUIElement.players[i].setBackgroundColor(Color.RED);
			} else {
				GameUIElement.players[i].setBackground(GameUIElement.backgroundColor);
			}
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
