package de.runje.tenthousand.androidView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import de.runje.tenthousand.R;
import de.runje.tenthousand.controller.Action;
import de.runje.tenthousand.controller.ActionHandler;
import de.runje.tenthousand.model.Dice;
import de.runje.tenthousand.model.GameModel;

public class DiceViewer {
	Handler handler;
	private Callback callback;
	private Timer timer = new Timer();
	private GameModel model;

	public DiceViewer(GameModel model) {
		this.model = model;
		callback = createCallback();
		handler = new Handler(callback);
	}

	private Callback createCallback() {
		Callback c = new Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				int dice = msg.what;
				Log.d("Callback", "dice = " + dice);
				Dice d = new Dice(0);
				int n = d.roll();
				showDice(dice, n);
				return false;
			}
		};
		return c;
	}

	public void roll() {
		// animate roll
		animateRollFreeDices();
		// roll
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new ActionHandler().executeAction(Action.Roll, model);
			}
		}, 10 * 100);
	}

	private void showDice(int dice, int n) {
		switch (n) {
		case 0:
			GameUIElement.dices[dice].setImageResource(R.drawable.dice3droll);
			break;
		case 1:
			GameUIElement.dices[dice].setImageResource(R.drawable.one);
			break;
		case 2:
			GameUIElement.dices[dice].setImageResource(R.drawable.two);
			break;
		case 3:
			GameUIElement.dices[dice].setImageResource(R.drawable.three);
			break;
		case 4:
			GameUIElement.dices[dice].setImageResource(R.drawable.four);
			break;
		case 5:
			GameUIElement.dices[dice].setImageResource(R.drawable.five);
			break;
		case 6:
			GameUIElement.dices[dice].setImageResource(R.drawable.six);
			break;
		default:
		}

		if (model.dices.getDices().get(dice).isRollable()) {
			setBlackAndWhite(GameUIElement.dices[dice]);
		} else {
			setNormalColor(GameUIElement.dices[dice]);
		}
	}

	public void updateDices() {
		ArrayList<Dice> dices = model.dices.getDices();
		for (int i = 0; i < dices.size(); i++) {
			showDice(i, dices.get(i).getNumber());
		}
	}

	public void animateRollDice(final int dice) {
		Log.d("D", "dice = " + dice);
		for (int i = 0; i < 10; i++) {

			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					handler.obtainMessage(dice).sendToTarget();
				}
			}, i * 100);

		}
	}

	private void animateRollFreeDices() {
		GameUIElement.disableButtons();
		boolean rollAll = false;
		if (model.resetDices()) {
			rollAll = true;
		}
		ArrayList<Dice> dices = model.dices.getDices();

		for (int i = 0; i < dices.size(); i++) {
			if (dices.get(i).isRollable() || rollAll) {
				animateRollDice(i);
			}
		}
	}


	public void takeover() {
		model.getPlayingPlayer().setWillTakeOver(true);
		// animate roll
		animateRollFreeDices();
		// roll
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new ActionHandler().executeAction(Action.Takeover, model);
			}
		}, 10 * 100);

	}

	public void merge() {
		new ActionHandler().executeAction(Action.Merge, model);
	}

	private void setBlackAndWhite(ImageView iv) {

		float brightness = 100;
		float[] colorMatrix = { 0.33f, 0.33f, 0.33f, 0, brightness, // red
				0.33f, 0.33f, 0.33f, 0, brightness, // green
				0.33f, 0.33f, 0.33f, 0, brightness, // blue
				0, 0, 0, 1, 0 // alpha
		};

		ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
		iv.setColorFilter(colorFilter);

	}
	
	private void setNormalColor(ImageView iv) {
		ColorMatrix cm = new ColorMatrix();
		ColorFilter colorFilter = new ColorMatrixColorFilter(cm);
		iv.setColorFilter(colorFilter);
	}

	public void switchDice(int i) {
		Action action = Action.Switch;
		action.index = i;
		new ActionHandler().executeAction(action, model);
	}
}
