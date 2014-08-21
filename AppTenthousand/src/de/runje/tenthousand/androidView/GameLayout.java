package de.runje.tenthousand.androidView;

import de.runje.tenthousand.R;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameLayout extends RelativeLayout {

	private Context context;

	public GameLayout(Context context, int players) {
		super(context);
		this.context = context;
		this.setBackgroundColor(Color.parseColor("#33b5e5"));
		createDices();
		createPlayers(players);
	}

	private void createDices() {
		ImageView[] dices = new ImageView[5];
		LayoutParams[] params = new LayoutParams[5];
		for (int i = 0; i < dices.length; i++) {
			dices[i] = new ImageView(context);
			dices[i].setImageResource(R.drawable.dice3droll);
			
			params[i] = createLayoutParam();
			params[i].addRule(RelativeLayout.CENTER_HORIZONTAL);
		}
		
		dices[0].setId(R.id.dice1);
		dices[1].setId(R.id.dice2);
		dices[2].setId(R.id.dice3);
		dices[3].setId(R.id.dice4);
		dices[4].setId(R.id.dice5);
		params[1].addRule(RelativeLayout.BELOW, R.id.dice1);
		params[2].addRule(RelativeLayout.BELOW, R.id.dice2);
		params[3].addRule(RelativeLayout.BELOW, R.id.dice3);
		params[4].addRule(RelativeLayout.BELOW, R.id.dice4);
		
		for (int i = 0; i < params.length; i++) {
			this.addView(dices[i], params[i]);
		}
	}

	private LayoutParams createLayoutParam()
	{
		return new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	}
	
	private void createPlayers(int players) {
		assert(players > 0 && players < 5);
		
		switch (players) {
		case 4:
			createPlayer4();
		case 3:
			createPlayer3();
		case 2:
			createPlayer2();
		case 1:
			createPlayer1();
			break;
		default:
			break;
		}
	}

	private void createPlayer4() {
		LayoutParams params = createLayoutParam();
		params.addRule(BELOW,3);
		params.addRule(ALIGN_PARENT_RIGHT);
		RelativeLayout l = createPlayer(R.id.player4text, R.id.player4Rolls1, R.id.player4Rolls2, R.id.player4Rolls3, "Player 4");
		l.setId(3);
		this.addView(l, params);
	}

	private void createPlayer3() {
		LayoutParams params = createLayoutParam();
		params.addRule(ALIGN_PARENT_RIGHT);
		RelativeLayout l = createPlayer(R.id.player3text, R.id.player3Rolls1, R.id.player3Rolls2, R.id.player3Rolls3, "Player 3");
		l.setId(3);
		this.addView(l, params);
	}

	private void createPlayer2() {
		LayoutParams params = createLayoutParam();
		params.addRule(BELOW,1);
		RelativeLayout l = createPlayer(R.id.player2text, R.id.player2Rolls1, R.id.player2Rolls2, R.id.player2Rolls3, "Player 2");
		l.setId(1);
		this.addView(l, params);
	}
	
	private void createPlayer1() {
		RelativeLayout l = createPlayer(R.id.player1text, R.id.player1Rolls1, R.id.player1Rolls2, R.id.player1Rolls3, "Player 1");
		l.setId(1);
		this.addView(l);
		
	}
	
	RelativeLayout createPlayer(int playerId, int rollsId1, int rollsId2, int rollsId3, String name)
	{
		RelativeLayout layout = new RelativeLayout(context);
		Log.d("IDS", "Player: " + playerId + ", rolls1: " + rollsId1 + ", rolls2: " + rollsId2 + ", rolls3: " + rollsId3);
		TextView tv = new TextView(context);
		tv.setId(playerId);
		tv.setText(name);
		tv.setTextSize(20);
		
		TextView tvStrikes = new TextView(context);
		// TODO
		tvStrikes.setId(1);
		tvStrikes.setText("X X X");
		tvStrikes.setTextColor(Color.RED);
		tvStrikes.setTextSize(20);
		
		ImageView rolls = createRollsDice(context, rollsId1);
		ImageView rolls2 = createRollsDice(context, rollsId2);
		ImageView rolls3 = createRollsDice(context, rollsId3);
		
		layout.addView(tv);
		LayoutParams params = createLayoutParam();
		params.width = 50;
		params.addRule(BELOW, playerId);

		LayoutParams params2 = createLayoutParam();
		params2.addRule(RIGHT_OF, rollsId1);
		params2.addRule(BELOW, playerId);
		params2.width = 50;
		
		LayoutParams params3 = createLayoutParam();
		params3.addRule(RIGHT_OF, rollsId2);
		params3.addRule(BELOW, playerId);
		params3.width = 50;
		
		LayoutParams paramsStrikes = createLayoutParam();
		paramsStrikes.addRule(BELOW, rollsId1);
		
		layout.addView(rolls, params);
		layout.addView(rolls2, params2);
		layout.addView(rolls3, params3);
		layout.addView(tvStrikes, paramsStrikes);
		return layout;
	}
	
	ImageView createRollsDice(Context context, int rollsId)
	{
		ImageView rolls = new ImageView(context);
		rolls.setImageResource(R.drawable.dice3d);
		rolls.setId(rollsId);
		return rolls;
	}



}
