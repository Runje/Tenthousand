package de.runje.tenthousand.androidView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.runje.tenthousand.R;
import de.runje.tenthousand.model.GameModel;

public class GameLayout extends RelativeLayout implements View.OnClickListener {

	private Context context;
	private GameModel model;

	public GameLayout(Context context, GameModel model) {
		super(context);
		this.model = model;
		this.context = context;
		this.setBackgroundColor(Color.parseColor("#33b5e5"));
		LayoutParams paramsDices = createLayoutParam();
		paramsDices.addRule(RelativeLayout.CENTER_HORIZONTAL);
		paramsDices.addRule(RelativeLayout.CENTER_VERTICAL);
		this.addView(createDices(), paramsDices);
		createPlayers(model.getPlayers().size());
		LayoutParams params = createLayoutParam();
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		this.addView(createButtons(), params);
		
		TextView points = new TextView(context);
		points.setId(R.id.textViewPoints);
		points.setTextSize(20);
		points.setText("Points: 0");
		points.setTypeface(null, Typeface.BOLD);
		LayoutParams paramsPoints = createLayoutParam();
		paramsPoints.addRule(RelativeLayout.CENTER_HORIZONTAL);
		this.addView(points, paramsPoints);
	}

	private RelativeLayout createDices() {
		ImageView[] dices = new ImageView[5];
		LayoutParams[] params = new LayoutParams[5];
		for (int i = 0; i < dices.length; i++) {
			dices[i] = new ImageView(context);
			dices[i].setImageResource(R.drawable.dice3droll);
			dices[i].setOnClickListener(this);
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
		
		RelativeLayout l = new RelativeLayout(context);
		for (int i = 0; i < params.length; i++) {
			l.addView(dices[i], params[i]);
		}
		
		return l;
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
		params.addRule(CENTER_VERTICAL);
		params.addRule(ALIGN_PARENT_RIGHT);
		RelativeLayout l = createPlayer(R.id.player4text, R.id.player4Rolls1, R.id.player4Rolls2, R.id.player4Rolls3, R.id.player4Strike, "Player 4", 3);
		l.setId(4);
		this.addView(l, params);
	}

	private void createPlayer3() {
		LayoutParams params = createLayoutParam();
		params.addRule(ALIGN_PARENT_RIGHT);
		params.addRule(ABOVE,2);		
		RelativeLayout l = createPlayer(R.id.player3text, R.id.player3Rolls1, R.id.player3Rolls2, R.id.player3Rolls3, R.id.player3Strike, "Player 3", 2);
		l.setId(3);
		this.addView(l, params);
	}

	private void createPlayer2() {
		LayoutParams params = createLayoutParam();
		params.addRule(CENTER_VERTICAL);
		RelativeLayout l = createPlayer(R.id.player2text, R.id.player2Rolls1, R.id.player2Rolls2, R.id.player2Rolls3, R.id.player2Strike, "Player 2", 1);
		l.setId(2);
		this.addView(l, params);
	}
	
	private void createPlayer1() {
		RelativeLayout l = createPlayer(R.id.player1text, R.id.player1Rolls1, R.id.player1Rolls2, R.id.player1Rolls3, R.id.player1Strike, "Player 1", 0);
		l.setId(1);
		LayoutParams params = createLayoutParam();
		params.addRule(ABOVE,2);
		this.addView(l, params);
		
	}
	
	RelativeLayout createPlayer(int playerId, int rollsId1, int rollsId2, int rollsId3, int strikeId,  String name, int player)
	{
		RelativeLayout layout = new RelativeLayout(context);
		Log.d("IDS", "Player: " + playerId + ", rolls1: " + rollsId1 + ", rolls2: " + rollsId2 + ", rolls3: " + rollsId3);
		TextView tv = new TextView(context);
		tv.setId(playerId);
		tv.setText(name);
		tv.setTextSize(20);
		
		TextView tvStrikes = new TextView(context);
		tvStrikes.setId(strikeId);
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
		GameUIElement.players[player] = new PlayerView(tv, tvStrikes, rolls, rolls2, rolls3);
		return layout;
	}
	
	ImageView createRollsDice(Context context, int rollsId)
	{
		ImageView rolls = new ImageView(context);
		rolls.setImageResource(R.drawable.dice3d);
		rolls.setId(rollsId);
		return rolls;
	}
	
	RelativeLayout createButtons()
	{
		RelativeLayout layout = new RelativeLayout(context);
		Button roll = new Button(context);
		roll.setId(R.id.buttonRoll);
		roll.setText("Roll");
		roll.setOnClickListener(this);
		
		Button merge = new Button(context);
		merge.setId(R.id.buttonMerge);
		merge.setText("Merge");
		merge.setOnClickListener(this);
		
		Button next = new Button(context);
		next.setId(R.id.buttonNext);
		next.setText("Next");
		next.setOnClickListener(this);
		
		Button takeover = new Button(context);
		takeover.setId(R.id.buttonTakeover);
		takeover.setText("Takeover");
		takeover.setOnClickListener(this);
		
		LayoutParams paramsMerge = createLayoutParam();
		paramsMerge.addRule(RIGHT_OF, R.id.buttonNext);
		
		LayoutParams paramsTakeover = createLayoutParam();
		paramsTakeover.addRule(RIGHT_OF, R.id.buttonMerge);
		
		LayoutParams paramsRoll = createLayoutParam();
		paramsRoll.addRule(RIGHT_OF, R.id.buttonTakeover);
		
		layout.addView(next);
		layout.addView(merge, paramsMerge);
		layout.addView(takeover, paramsTakeover);
		layout.addView(roll, paramsRoll);
		return layout;
	}

	@Override
	public void onClick(View v) {
		DiceViewer diceViewer = new DiceViewer(model);
		TenthousandViewer tenthousandViewer = new TenthousandViewer(model);
		switch (v.getId()) {
		case R.id.buttonRoll:
			diceViewer.roll();
			break;
		case R.id.buttonNext:
			tenthousandViewer.next();
			break;
		case R.id.buttonMerge:
			diceViewer.merge();
			break;
		case R.id.buttonTakeover:
			diceViewer.takeover();
			break;
		case R.id.dice1:
			diceViewer.switchDice(0);
			break;
		case R.id.dice2:
			diceViewer.switchDice(1);
			break;
		case R.id.dice3:
			diceViewer.switchDice(2);
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


}
