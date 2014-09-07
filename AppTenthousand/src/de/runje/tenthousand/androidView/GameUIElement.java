package de.runje.tenthousand.androidView;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import de.runje.tenthousand.R;

public class GameUIElement {

	public static PlayerView[] players = new PlayerView[4];
	
	public static ImageView[] dices;
	
	public static TextView points;
	
	public static Button next;

	public static Button takeover;

	public static Button merge;

	public static Button roll;
	
	//public static ColorDrawable backgroundColor;

	public static void init(Activity activity) {
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
		
		//TextView tv = (TextView) activity.findViewById(R.id.fullscreen_content);
		//backgroundColor = (ColorDrawable)tv.getBackground();
		
	}

	public static void disableButtons() {
		next.setEnabled(false);
		takeover.setEnabled(false);
		roll.setEnabled(false);
		merge.setEnabled(false);
		
		for (ImageView image : dices) {
			image.setEnabled(false);
		}
	}

	public static void enableMerge() {
		merge.setEnabled(true);
	}
	public static void enableNext() {
		next.setEnabled(true);
	}
	public static void enableTakeover() {
		takeover.setEnabled(true);
	}
	public static void enableRoll() {
		roll.setEnabled(true);
	}

	public static void enableSwitch() {
		for (ImageView image : dices) {
			image.setEnabled(true);
		}
	}
}
