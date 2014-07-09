package de.runje.tenthousand.androidView;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import de.runje.tenthousand.R;
import de.runje.tenthousand.model.GameModel;
import de.runje.tenthousand.model.HumanPlayer;
import de.runje.tenthousand.model.Player;
import de.runje.tenthousand.model.Rules;
import de.runje.tenthousand.observer.IObserver;
import de.runje.tenthousand.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GameActivity extends Activity implements IObserver {

	private DiceViewer diceViewer;
	private TenthousandViewer tenthousandViewer;

	private GameModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_game);
		hideActionBar();
		initGame();
	}

	private void hideActionBar() {
		View decorView = getWindow().getDecorView();
		// Hide the status bar.
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);
		// Remember that you should never show the action bar if the
		// status bar is hidden, so hide that too if necessary.
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
	}

	private void initGame() {
		UIElement.init(this);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer("Thomas"));
		players.add(new Player("Milena"));
		this.model = new GameModel(players, new Rules());
		model.addObserver(this);

		this.diceViewer = new DiceViewer(model);
		this.tenthousandViewer = new TenthousandViewer(model);
		update();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

	}


	public void clickRoll(View v) throws InterruptedException {
		diceViewer.roll();
	}
	
	public void clickNext(View v) throws InterruptedException {
		tenthousandViewer.next();
	}
	
	public void clickTakeover(View v) throws InterruptedException {
		diceViewer.takeover();
	}
	
	public void clickMerge(View v) throws InterruptedException {
		diceViewer.merge();
	}

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
//	private void delayedHide(int delayMillis) {
//		mHideHandler.removeCallbacks(mHideRunnable);
//		mHideHandler.postDelayed(mHideRunnable, delayMillis);
//	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			

			@Override
			public void run() {
				// TODO Auto-generated method stub
				diceViewer.updateDices();
				tenthousandViewer.updatePlayers();
				tenthousandViewer.updateButtons();
				UIElement.points.setText("Points: " + model.getPoints());
			}
		});
	}

}
