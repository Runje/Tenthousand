package de.runje.tenthousand.androidView;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import de.runje.tenthousand.R;
import de.runje.tenthousand.statistics.DBHandler;
import de.runje.tenthousand.statistics.DBPlayer;

public class MainActivity extends Activity {
	
	public final static String Players = "Players";
	
	public final static String Player[] = new String[] { "Player1", "Player2", "Player3", "Player4" };

	public final static String IsAi[] = new String[] { "IsAi1", "IsAi2", "IsAi3", "IsAi4" };

	public final static String PlayerToEdit = "PlayerToEdit";
	
	public final static String PlayerNameToEdit = "PlayerNameToEdit";

	public final static String Points = "Points";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		hideActionBar();
		MainUIElement.init(this);
		
		DBHandler db = new DBHandler(this);
//		db.onUpgrade(db.getWritableDatabase(), 0, 1);
//		db.addPlayer(new DBPlayer("Mario", 0));
//		db.addPlayer(new DBPlayer("Patrick", 1));
//		db.addPlayer(new DBPlayer("Gisela", 2));
//		db.addPlayer(new DBPlayer("Wolfgang", 3));
		updatePlayers();
	
		db.logAllPlayers();
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

	public void clickNewPlayer(View v) {
		Log.d("Test", "New Player");
		View layout = getLayoutInflater().inflate(R.layout.edit_player, null);
		EditText e = (EditText) layout.findViewById(R.id.editTextNewPlayer);
		e.setText("Hallo");
		Dialog dialog = new Dialog(MainActivity.this, R.layout.edit_player );
		dialog.setContentView(R.layout.edit_player);
		dialog.cancel();
	}
	
	public void clickEditPlayer(View v) {
		Intent intent = new Intent(this, EditPlayer.class);
		int j = -1;
		for (int k = 0; k < MainUIElement.players.length; k++) {
			
			if (v.getId() == MainUIElement.players[k].getId())
			{
				j = k;
			}
		}
		
		intent.putExtra(PlayerToEdit , j);
		startActivity(intent);
	}


	private void updatePlayers()
	{
		DBHandler db = new DBHandler(this);
		//Get all player
		ArrayList<DBPlayer> players = db.getAllPlayers();
		for (DBPlayer dbPlayer : players) {
			if (dbPlayer.getPlaying() != -1) 
			{
				MainUIElement.players[dbPlayer.getPlaying()].setText(dbPlayer.getName());
			}
		}
	}
	
	public void clickTutorial(View v)
	{
		Intent intent = new Intent(this, Tutorial.class);
		startActivity(intent);
	}

	public void clickShort(View v) {
		startGame(5000);
	}
	
	public void clickNormal(View v) {
		startGame(10000);
	}
	
	public void clickLong(View v) {
		startGame(200);
	}


	private void startGame(int points) {
		int players = 0;
		Intent intent = new Intent(this, GameActivity.class);
		for (int i = 0; i < 4; i++) {
			if (MainUIElement.playing[i].isChecked()) {
				players++;
				intent.putExtra(Player[i], MainUIElement.players[i].getText());
				intent.putExtra(IsAi[i], MainUIElement.ai[i].isChecked());
			}
		}
		intent.putExtra(Points , points);
		
		if (MainUIElement.getHumanPlayers() == 0) {
			// TODO: show dialog
			Log.d("Main", "No Human player");
			return;
		}
		intent.putExtra(Players, players);
		startActivity(intent);
	}
}
