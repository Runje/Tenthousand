package de.runje.tenthousand.androidView;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import de.runje.tenthousand.R;
import de.runje.tenthousand.statistics.DBHandler;
import de.runje.tenthousand.statistics.DBPlayer;

public class EditPlayer extends Activity {

	ListView lv;
	ArrayAdapter<String> adapter;
	ArrayList<String> players = new ArrayList<String>();
	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_player);

		Intent intent = getIntent();
		index = intent.getIntExtra(MainActivity.PlayerToEdit, -1);

		updateList();
		for (String player : players) {
			Log.d("EditPlayer", "Player: " + player);
		}
		lv = (ListView) findViewById(R.id.listViewPlayers);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, players);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				DBHandler db = new DBHandler(EditPlayer.this);
				// reset old player
				try {
					DBPlayer p = db.getPlaying(index);
					p.setPlaying(-1);
					db.updatePlayer(p);
					DBPlayer player = db.getPlayer(item);
					player.setPlaying(index);
					db.updatePlayer(player);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(EditPlayer.this, MainActivity.class);
				startActivity(intent);
			}
		});

	}

	public void clickCancel(View v) {
		// TODO: save players
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public void clickCreateNewPlayer(View v) {
		EditText tv = (EditText) findViewById(R.id.editTextNewPlayer);

		DBHandler db = new DBHandler(this);
		if (!tv.getText().toString().isEmpty()) {
			db.addPlayer(new DBPlayer(tv.getText().toString().trim(), -1, 0));
		}

		updateList();
		adapter.notifyDataSetChanged();
	}

	public void updateList() {
		DBHandler db = new DBHandler(this);
		players.clear();
		for (DBPlayer player : db.getAllPlayers()) {
			players.add(player.getName());
			Log.d("EditPlayer", player.getName());
		}

	}
}