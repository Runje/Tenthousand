package de.runje.tenthousand.androidView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import de.runje.tenthousand.R;

public class MainActivity extends Activity {
	
	public final static String Players = "Players";
	
	public final static String Player[] = new String[] { "Player1", "Player2", "Player3", "Player4" };

	public final static String IsAi[] = new String[] { "IsAi1", "IsAi2", "IsAi3", "IsAi4" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		hideActionBar();
		MainUIElement.init(this);
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

	
	public void clickEditPlayer(View v) {
		Log.d("Click", "EditField");
		new EditPlayer().show(getFragmentManager(), "Tag");
	}

	public void clickStart(View v) {
		int players = 0;
		Intent intent = new Intent(this, GameActivity.class);
		for (int i = 0; i < 4; i++) {
			if (MainUIElement.playing[i].isChecked()) {
				players++;
				intent.putExtra(Player[i], MainUIElement.players[i].getText());
				intent.putExtra(IsAi[i], MainUIElement.ai[i].isChecked());
			}
		}
		
		if (players == 0) {
			// TODO: show dialog
			return;
		}
		intent.putExtra(Players, players);
		startActivity(intent);
	}
}
