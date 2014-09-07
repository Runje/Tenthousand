package de.runje.tenthousand.androidView;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import de.runje.tenthousand.R;
import de.runje.tenthousand.model.GameModel;
import de.runje.tenthousand.model.Player;
import de.runje.tenthousand.model.Rules;
import de.runje.tenthousand.observer.IObserver;
import de.runje.tenthousand.statistics.DBHandler;

public class Tutorial extends Activity implements IObserver {
	private DiceViewer diceViewer;
	private TenthousandViewer tenthousandViewer;
	
	private int step = 0;

	private GameModel model;
	private TutorialLayout tutorial;
	
	public Tutorial() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_game);
		hideActionBar();
		
		GameModel model = initTutorialGame();
		tutorial = new TutorialLayout(this, model);
		setContentView(tutorial);
		GameUIElement.init(this);
		update();
		Context context = getApplicationContext();
		CharSequence text = "Goal of the game is to get " + 1000 + " points.";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		//showWelcomeDialog();
		tenthousandViewer.updateButtons();
	}
	

	private void showWelcomeDialog() {
		showOKDialog("Goal of the game", "Welcome to the Tutorial! \n Bla bla");
		step++;
	}
	
	private void showOKDialog(String title, String message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// Add the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User clicked OK button
		           }
		       });
		builder.setMessage(message);
		builder.setTitle(title);
		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		dialog.show();
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
				if (tutorial.step > tutorial.maxSteps)
				{
					tenthousandViewer.updateButtons();
				}
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
	private GameModel initTutorialGame() {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("Milena"));
		players.add(new Player("Robin"));
		players.add(new Player("Wolfgang"));
		players.add(new Player("Gisela"));
		
		this.model = new GameModel(players, new Rules());
		model.addObserver(this);
		Rules.WinPoints = 1000;
		this.diceViewer = new DiceViewer(model, this);
		this.tenthousandViewer = new TenthousandViewer(model);
		return model;
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

}
