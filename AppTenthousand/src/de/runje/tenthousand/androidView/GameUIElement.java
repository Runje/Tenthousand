package de.runje.tenthousand.androidView;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import de.runje.tenthousand.R;

public class GameUIElement {

	public static TextView[] players;
	
	public static ImageView[] dices;
	
	public static TextView points;
	
	public static Button next;

	public static Button takeover;

	public static Button merge;

	public static Button roll;
	
	public static ColorDrawable backgroundColor;

	public static void init(Activity activity) {
		players = new TextView[4];
		players[0] = (TextView) activity.findViewById(R.id.textViewPlayer1);
		players[1] = (TextView) activity.findViewById(R.id.textViewPlayer2);
		players[2] = (TextView) activity.findViewById(R.id.textViewPlayer3);
		players[3] = (TextView) activity.findViewById(R.id.textViewPlayer4);
		
		dices = new ImageView[5];
		dices[0] = (ImageView) activity.findViewById(R.id.dice1);
		dices[1] = (ImageView) activity.findViewById(R.id.dice2);
		dices[2] = (ImageView) activity.findViewById(R.id.dice3);
		dices[3] = (ImageView) activity.findViewById(R.id.dice4);
		dices[4] = (ImageView) activity.findViewById(R.id.dice5);
		
		points = (TextView) activity.findViewById(R.id.textViewPoints);
		
		next = (Button) activity.findViewById(R.id.buttonNext);
		takeover = (Button) activity.findViewById(R.id.buttonTakeover);
		roll = (Button) activity.findViewById(R.id.buttonRoll);
		merge = (Button) activity.findViewById(R.id.buttonMerge);
		
		TextView tv = (TextView) activity.findViewById(R.id.fullscreen_content);
		backgroundColor = (ColorDrawable)tv.getBackground();
		
	}

	public static void disableButtons() {
		next.setEnabled(false);
		takeover.setEnabled(false);
		roll.setEnabled(false);
		merge.setEnabled(false);
	}
}
