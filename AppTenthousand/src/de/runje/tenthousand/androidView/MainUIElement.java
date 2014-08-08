package de.runje.tenthousand.androidView;

import java.util.ArrayList;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import de.runje.tenthousand.R;
import de.runje.tenthousand.statistics.DBPlayer;

public class MainUIElement {
	
	public static TextView[] players;
	
	public static CheckBox[] playing;

	public static CheckBox[] ai;

	public static void init(Activity activity) {
		players = new TextView[4];
		players[0] = (TextView) activity.findViewById(R.id.textViewPlayer1);
		players[1] = (TextView) activity.findViewById(R.id.textViewPlayer2);
		players[2] = (TextView) activity.findViewById(R.id.textViewPlayer3);
		players[3] = (TextView) activity.findViewById(R.id.textViewPlayer4);
		
		playing = new CheckBox[4];
		playing[0] = (CheckBox) activity.findViewById(R.id.checkBoxPlaying1);
		playing[1] = (CheckBox) activity.findViewById(R.id.checkBoxPlaying2);
		playing[2] = (CheckBox) activity.findViewById(R.id.checkBoxPlaying3);
		playing[3] = (CheckBox) activity.findViewById(R.id.checkBoxPlaying4);
		
		ai = new CheckBox[4];
		ai[0] = (CheckBox) activity.findViewById(R.id.checkBoxAI1);
		ai[1] = (CheckBox) activity.findViewById(R.id.checkBoxAI2);
		ai[2] = (CheckBox) activity.findViewById(R.id.checkBoxAI3);
		ai[3] = (CheckBox) activity.findViewById(R.id.checkBoxAI4);
		
		
	}

	public static int getHumanPlayers() {
		int count = 0;
		for (int i = 0; i < 4; i++) {
			if (!ai[i].isChecked() && playing[i].isChecked()) {
				count++;
			}
		}
		return count;
	}

	public static ArrayList<String> getPlayingPlayers() {
		ArrayList<String> playingPlayers = new ArrayList<String>();
		
		for (TextView tv : players) {
			playingPlayers.add(tv.getText().toString());
		}
		
		return playingPlayers;
	}
}
