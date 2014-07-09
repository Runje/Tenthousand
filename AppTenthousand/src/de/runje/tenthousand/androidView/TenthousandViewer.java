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
			UIElement.players[i].setText(model.getPlayers().get(i).toString());
			if (model.getPlayingPlayer() == model.getPlayers().get(i)) {
				// highlight player which has the turn
				UIElement.players[i].setBackgroundColor(Color.RED);
			} else {
				UIElement.players[i].setBackgroundColor(Color.BLUE);
			}
		}
		
	}

	public void next() {
		new ActionHandler().executeAction(Action.Next, model);
	}
	
	public void updateButtons() {
		if (model.nextIsPossible()) {
			UIElement.next.setEnabled(true);
		} else {
			UIElement.next.setEnabled(false);
		}
		if (model.isPossibleToTakeOver()) {
			UIElement.takeover.setEnabled(true);
		} else {
			UIElement.takeover.setEnabled(false);
		}
		if (model.isPossibleToMerge()) {
			UIElement.merge.setEnabled(true);
		} else {
			UIElement.merge.setEnabled(false);
		}
		if (model.isPossibleToRoll()) {
			UIElement.roll.setEnabled(true);
		} else {
			UIElement.roll.setEnabled(false);
		}
	}
}
