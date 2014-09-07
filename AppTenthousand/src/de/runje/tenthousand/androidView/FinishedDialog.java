package de.runje.tenthousand.androidView;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import de.runje.tenthousand.statistics.DBHandler;
import de.runje.tenthousand.statistics.DBPlayer;

public class FinishedDialog extends DialogFragment {

	public String name = "Unknown";
	
	public FinishedDialog() {
	}

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        DBHandler db = new DBHandler(getActivity());
        ArrayList<DBPlayer> players = db.getAllPlayers();
        String p = "";
        for (DBPlayer dbPlayer : players) {
			
        	p += dbPlayer.toString() + "\n";
		}
        builder.setMessage(name + " wins the game." + p)
               .setPositiveButton("Show Statistics", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // TODO: Show statistics
                   }
               })
               .setNegativeButton("Back to Menu", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   Intent intent = new Intent(((Dialog) dialog).getContext(), MainActivity.class);
                	   startActivity(intent);
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
