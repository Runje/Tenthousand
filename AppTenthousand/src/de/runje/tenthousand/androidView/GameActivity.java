package de.runje.tenthousand.androidView;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import de.runje.tenthousand.R;
import de.runje.tenthousand.model.AIPlayer;
import de.runje.tenthousand.model.GameModel;
import de.runje.tenthousand.model.MyStrategy;
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
		GameUIElement.init(this);
		
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
		initGame(players, ai);
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

	private void initGame(String[] player, boolean[] ai) {
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
		for (int i = 0; i < 4 - players.size() ; i++) {
			GameUIElement.players[3 - i].setVisibility(TextView.INVISIBLE);
		}
		
		this.model = new GameModel(players, new Rules());
		model.addObserver(this);

		this.diceViewer = new DiceViewer(model);
		this.tenthousandViewer = new TenthousandViewer(model);
		update();
	}


	public void clickRoll(View v) {
		diceViewer.roll();
	}
	
	public void clickNext(View v) {
		tenthousandViewer.next();
	}
	
	public void clickTakeover(View v) {
		diceViewer.takeover();
	}
	
	public void clickMerge(View v)  {
		diceViewer.merge();
	}
	
	public void switchDice1(View v)  {
		diceViewer.switchDice(0);
	}
	
	public void switchDice2(View v)  {
		diceViewer.switchDice(1);
	}
	
	public void switchDice3(View v)  {
		diceViewer.switchDice(2);
	}
	
	public void switchDice4(View v)  {
		diceViewer.switchDice(3);
	}
	
	public void switchDice5(View v)  {
		diceViewer.switchDice(4);
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
			}
		});
	}

}
