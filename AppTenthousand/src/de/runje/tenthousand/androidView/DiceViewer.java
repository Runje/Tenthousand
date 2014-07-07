package de.runje.tenthousand.androidView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
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
		rollFreeDices();
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
				case 1:
					UIElement.dices[dice].setImageResource(R.drawable.one);
					break;
				case 2:
					UIElement.dices[dice].setImageResource(R.drawable.two);
					break;
				case 3:
					UIElement.dices[dice].setImageResource(R.drawable.three);
					break;
				case 4:
					UIElement.dices[dice].setImageResource(R.drawable.four);
					break;
				case 5:
					UIElement.dices[dice].setImageResource(R.drawable.five);
					break;
				case 6:
					UIElement.dices[dice].setImageResource(R.drawable.six);
					break;
				default:
				}
	}

	public void updateDices() {
		ArrayList<Dice> dices = model.dices.getDices();
		for (int i = 0; i < dices.size(); i++) {
			showDice(i, dices.get(i).getNumber());
		}
	}

	public void rollDice(final int dice) {
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

	private void rollFreeDices() {
		ArrayList<Dice> dices = model.dices.getDices();

		for (int i = 0; i < dices.size(); i++) {
			if (dices.get(i).isRollable()) {
				rollDice(i);
			}
		}
	}

	public void test() {
		Log.d("Test", Boolean.toString(UIElement.dices[0] == null));
		Log.d("Test", Boolean.toString(UIElement.dices[1] == null));
		Log.d("Test", Boolean.toString(UIElement.dices[2] == null));
		Log.d("Test", Boolean.toString(UIElement.dices[3] == null));
		Log.d("Test", Boolean.toString(UIElement.dices[4] == null));
		
	}
}
