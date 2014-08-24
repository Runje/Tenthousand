package de.runje.tenthousand.androidView;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerView {

	private TextView NameView;
	private TextView StrikesView;
	private ImageView[] rolls;
	private String name;
	private int points;
	
	public PlayerView(TextView name, TextView tvStrikes, ImageView rolls,
			ImageView rolls2, ImageView rolls3) {
		this.NameView = name;
		this.StrikesView = tvStrikes;
		this.rolls = new ImageView[] { rolls, rolls2, rolls3 };
	}
	
	public void setRolls(int i)
	{
		for (int j = 0; j < 3 - i; j++) {
			rolls[j].setVisibility(View.VISIBLE);
		}
		
		for (int j = 3 - i; j < 3; j++) {
			rolls[j].setVisibility(View.INVISIBLE);
		}
	}
	
	public void setName(String name) 
	{
		this.name = name; 
		updatePoints();
	}
	
	private void updatePoints() {
		this.NameView.setText(this.name + "\n" + this.points);
	}

	public void setPoints(int points)
	{
		this.points = points;
		updatePoints();
	}
	
	public void setStrikes(int i)
	{
		if (i == 0)
		{
			StrikesView.setText("");
		}
		if (i == 1)
		{
			StrikesView.setText("X");
		}
		if (i == 2)
		{
			StrikesView.setText("X X");
		}
		if (i == 3)
		{
			StrikesView.setText("X X X");
		}
	}

	public void setHighlighted(boolean b) {
		if (b)
		{
			this.NameView.setTextColor(Color.RED);
		}
		else
		{
			this.NameView.setTextColor(Color.GRAY);
			setRolls(3);
		}
	}

}
