package de.runje.tenthousand.androidView;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import de.runje.tenthousand.R;
import de.runje.tenthousand.model.AIPlayer;
import de.runje.tenthousand.model.GameModel;
import de.runje.tenthousand.model.MyStrategy;
import de.runje.tenthousand.model.Player;
import de.runje.tenthousand.model.Rules;
import de.runje.tenthousand.observer.IObserver;
import de.runje.tenthousand.statistics.DBHandler;
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
		
		Intent intent = getIntent();
		String[] players = new String[4];
		boolean[] ai= new boolean[4];
		for (int i = 0; i < 4; i++) {
			if (intent.getStringExtra(MainActivity.Player[i]) != null)
			{
				players[i] = intent.getStringExtra(MainActivity.Player[i]);
				ai[i] = intent.getBooleanExtra(MainActivity.IsAi[i], ai[i]);
			}
		}
		int points = intent.getIntExtra(MainActivity.Points, 10000);
		GameModel model = initGame(players, ai, points);
		setContentView(new GameLayout(this, model));
		GameUIElement.init(this);
		update();
		Context context = getApplicationContext();
		CharSequence text = "Goal of the game is to get " + points + " points.";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
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

	private GameModel initGame(String[] player, boolean[] ai, int points) {
		ArrayList<Player> players = new ArrayList<Player>();
		for (int i = 0; i < 4; i++) {
			if (player[i] != null) {
				if (ai[i]) {
					players.add(new AIPlayer(player[i], new MyStrategy()));
				}
				else
				{
					players.add(new Player(player[i]));
				}
			}
			
		}

		
		this.model = new GameModel(players, new Rules());
		model.addObserver(this);
		model.rules.WinPoints = points;
		this.diceViewer = new DiceViewer(model, this);
		this.tenthousandViewer = new TenthousandViewer(model);
		return model;
	}




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
				GameUIElement.points.setText("Points: " + model.getPoints());
				if (model.isGameFinished())
				{
					DBHandler db = new DBHandler(getApplicationContext());
					try {
						db.updatePlayerFromGame(model);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Show popup dialog and save result in DB
					FinishedDialog d = new FinishedDialog();
					d.name = model.getWinner();
					d.show(getFragmentManager(), "Game is finished");
				}
			}
		});
	}
}
