package de.runje.tenthousand.androidView;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import de.runje.tenthousand.statistics.DBHandler;
import de.runje.tenthousand.statistics.DBPlayer;

public class StatisticsLayout extends TableLayout {

	private Context context;

	public StatisticsLayout(Context context) {
		super(context);
		this.context = context;
		addView(createHead(), new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
		
		DBHandler db = new DBHandler(context);
		ArrayList<DBPlayer> players = db.getAllPlayers();
		
		Log.d("Statistics", "There are that many players: " + players.size());
		int i = 0;
		for (DBPlayer dbPlayer : players) {
			this.addView(playerToRow(dbPlayer, i), new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
		i++;
			
		}
	}

	public TableRow playerToRow(DBPlayer player, int id)
	{
		TableRow row = new TableRow(context);
		// TODO: change colors
		row.setBackgroundColor(Color.GRAY);
		row.setId(id);
		row.setLayoutParams(new LayoutParams(
		LayoutParams.FILL_PARENT,
		LayoutParams.WRAP_CONTENT));

		//Create two columns to add as table data
		 // Create a TextView to add date
		TextView name = new TextView(context);
		name.setId(200 + id); 
		name.setText(player.getName());
		name.setPadding(2, 0, 5, 0);
		name.setTextColor(Color.WHITE);
		row.addView(name);
		
		TextView games = new TextView(context);
		games.setId(300 + id);
		games.setText(Integer.toString(player.getGames()));
		games.setTextColor(Color.WHITE);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT); 
        params.gravity = Gravity.CENTER_HORIZONTAL;

		row.addView(games, params);

		TextView wins = new TextView(context);
		wins.setId(300 + id);
		wins.setText(Integer.toString(player.getWins()));
		wins.setTextColor(Color.WHITE);
		
		row.addView(wins, params);

		return row;
	}
	
	public TableRow createHead()
	{
		TableRow tr_head = new TableRow(context);
		tr_head.setId(10);
		tr_head.setBackgroundColor(Color.GRAY);
		tr_head.setLayoutParams(new LayoutParams(
		LayoutParams.FILL_PARENT,
		LayoutParams.WRAP_CONTENT));
		TextView label_date = new TextView(context);
        label_date.setId(20);
        label_date.setText("NAME");
        label_date.setTextColor(Color.WHITE);
        label_date.setPadding(5, 5, 5, 5);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT); 
        params.gravity = Gravity.CENTER_HORIZONTAL;
        tr_head.addView(label_date, params);// add the column to the table row here

        TextView label_weight_kg = new TextView(context);
        label_weight_kg.setId(21);// define id that must be unique
        label_weight_kg.setText("Games"); // set the text for the header 
        label_weight_kg.setTextColor(Color.WHITE); // set the color
        label_weight_kg.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_weight_kg, params); // add the column to the table row here

        TextView wins = new TextView(context);
        wins.setId(22);// define id that must be unique
        wins.setText("Wins"); // set the text for the header 
        wins.setTextColor(Color.WHITE); // set the color
        wins.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(wins, params); // add the column to the table row here

        
		return tr_head;
	}
}
