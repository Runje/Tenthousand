package de.runje.tenthousand.androidView;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.runje.tenthousand.R;
import de.runje.tenthousand.model.GameModel;
import de.runje.tenthousand.model.Player;

public class TutorialLayout extends GameLayout {

	public int step = 0;
	public int maxSteps = 5;
	private Timer timer = new Timer();
	public TutorialLayout(final Context context, GameModel model) {
		super(context, model);
		showTextAfterRoll("Welcome to the Tutorial! \n"
				+ "Goal of this game is to achieve 10000 Points \n"
				+ "Press Roll to roll the dices");
	}
	
	@Override
	public void onClick(View v) {
		DiceViewer diceViewer = new DiceViewer(model, context);
		TenthousandViewer tenthousandViewer = new TenthousandViewer(model);
		switch (v.getId()) {
		case R.id.buttonRoll:
			if (step == 0)
			{
				diceViewer.showRoll(1,5,5,2,3);
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						Activity a = (Activity) context;
						a.runOnUiThread(new Runnable() {
						     @Override
						     public void run() {
						    	 showText("A one gives 100 Points, A five gives 50 Points. \n"
						    			 + "The current points are shown at the top of the screen \n"
						    			 + "Press Merge to merge two fives to roll with one more dice \n"
						    			 , LEFT_OF, R.id.textViewPoints);
						    }
						});
					}
				}, 10 * 100);
				OnlyMergePossible();
			}
			else if (step == 1)
			{
				diceViewer.showRoll(1,1,5,5,6);
				showTextAfterRoll("Press Next to end your turn and save your points");
				OnlyNextPossible();
			}
			else if (step == 2)
			{
				diceViewer.showRoll(2,3,4,6,6);
				showTextAfterRoll("If you score no points you loose all your points so far in this turn and get one strike. If you have three strikes in a row, you loose all your points in the entire game.");
				model.diceHandler.setAllPoints(0);
				model.diceHandler.setNewPoints(0);
				OnlyNextPossible();
				step++;
			}
			else if (step == 3)
			{
				diceViewer.showRoll(2,2,2,6,6);
				showTextAfterRoll("2,2,2 = 200 P \n"
						+ "6,6,6 = 600 P \n"
						+ "but 1,1,1 = 1000 P \n"
						+ "You can also decide to rethrow dices with points \n"
						+ "click on one of the twos to rethrow them");
				OnlySwitchPossible();
				step++;
			}
			else if (step == 4)
			{
				diceViewer.showRoll(1,1,1,1,1);
				showTextAfterRoll("Four of a kind gives two times and five of a kind gives four times the points from three of a kind.");
				OnlyNextPossible();
				step++;
			}
			else
			{
				diceViewer.roll();
			}
			break;
		case R.id.buttonNext:
			tenthousandViewer.next();
			if (step == 1)
			{
				showTextAfterRoll("Press Takeover to continue Milenas turn.");
				OnlyTakeoverPossible();
			}

			if (step == 3)
			{
				Player robin = model.getPlayerByName("Robin");
//				robin.setStrikes(1);
//				robin.setAllPoints(0);
				showTextAfterRoll("It is now Wolfangs turn. But he can't takeover the dices, because Robin didn't score.");
				OnlyRollPossible();
			}
			if (step == 5)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				// Add the buttons
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   Intent intent = new Intent(((Dialog) dialog).getContext(), MainActivity.class);
		                	   context.startActivity(intent);
				           }
				       });
				builder.setMessage("You can now start your own game");
				builder.setTitle("Tutorial is finished");
				// Create the AlertDialog
				AlertDialog dialog = builder.create();
				dialog.show();
			}
			
			break;
		case R.id.buttonMerge:
			diceViewer.merge();
			if (step == 0)
			{
				showTextAfterRoll("You need at least 300 Points to save the points to your account.\n"
						+ "Roll again to get more points");
				OnlyRollPossible();
				step++;
			}
			break;
		case R.id.buttonTakeover:
			
			if (step == 1)
			{
				model.getPlayingPlayer().setWillTakeOver(true);
				model.dices.fix();
				diceViewer.showRoll(0,0,0,0,1);
				showTextAfterRoll("If you scored points with all dices, you have again three rolls with five new dices.");
				OnlyRollPossible();
				step++;
			}
			else
			{
				diceViewer.takeover();
			}
			break;
		case R.id.dice1:
			diceViewer.switchDice(0);
			afterSwitch();
			break;
		case R.id.dice2:
			diceViewer.switchDice(1);
			afterSwitch();
			break;
		case R.id.dice3:
			diceViewer.switchDice(2);
			afterSwitch();
			break;
		case R.id.dice4:
			diceViewer.switchDice(3);
			break;
		case R.id.dice5:
			diceViewer.switchDice(4);
			break;
		default:
			break;
		}
		
	}
	
	private void afterSwitch()
	{
		showTextAfterRoll("The rolls left for the turn are shown below the players name \n"
					+ "Below that you can see the strikes.");
		OnlyRollPossible();
	}
	

	private void OnlySwitchPossible() {
		GameUIElement.disableButtons();
		GameUIElement.enableSwitch();
		
	}

	private void OnlyTakeoverPossible() {
		GameUIElement.disableButtons();
		GameUIElement.enableTakeover();
		
	}

	private void OnlyNextPossible() {
		GameUIElement.disableButtons();
		GameUIElement.enableNext();
		
	}

	private void showTextAfterRoll(final String string) {
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				Activity a = (Activity) context;
				a.runOnUiThread(new Runnable() {
				     @Override
				     public void run() {
				    	 showText(string, 0, 0);
				    }
				});
			}
		}, 10 * 100);
	}

	private void OnlyRollPossible() {
		GameUIElement.disableButtons();
		GameUIElement.enableRoll();
	}

	private void OnlyMergePossible() {
		GameUIElement.disableButtons();
		GameUIElement.enableMerge();
	}

	private void showText(String message, int verb, int id) {
		TextView tv = new TextView(context);
		tv.setText(message);
		tv.setBackgroundColor(Color.GRAY);
		tv.setAlpha((float) 0.4);
		tv.setTextColor(Color.BLACK);
		tv.setTextSize(30);
		tv.setId(12456);
		
		LayoutParams params = createLayoutParam();
		params.addRule(CENTER_VERTICAL);
		params.addRule(CENTER_HORIZONTAL);
		this.addView(tv, params);
		
		if (id == 0)
		{
			removeViewOnClick(new int[] {12456});
			return;
		}
		
		ImageView arrow = new ImageView(context);
		arrow.setImageResource(R.drawable.arrow2);
		arrow.setId(745745);
		arrow.setBackgroundColor(Color.TRANSPARENT);
		
		LayoutParams paramsArrow = new LayoutParams(50, 50);
		paramsArrow.addRule(verb, id);
		if (verb == ABOVE)
		{
			arrow.setRotation(90f);
			paramsArrow.addRule(ALIGN_RIGHT, id);
		}
		
		if (verb == RIGHT_OF)
		{
			arrow.setRotation(180f);
			paramsArrow.addRule(ALIGN_BOTTOM, id);
		}
		if (verb == LEFT_OF)
		{
			paramsArrow.addRule(ALIGN_BOTTOM, id);
		}
		
		this.addView(arrow, paramsArrow);
		removeViewOnClick(new int[] {12456, 745745});
		
	}
	
	private void removeViewOnClick(final int[] id)
	{
		RelativeLayout l = new RelativeLayout(context);
		l.setAlpha(0f);
		LayoutParams lParams = new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		l.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
				((ViewManager) v.getParent()).removeView(v);
				for (int i : id) {
					View tv = ((Activity) context).findViewById(i);
					((ViewManager) tv.getParent()).removeView(tv);
				}
		    }

		 });
		this.addView(l, lParams);
	}

}
