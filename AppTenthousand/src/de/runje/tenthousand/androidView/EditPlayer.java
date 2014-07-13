package de.runje.tenthousand.androidView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;
import de.runje.tenthousand.R;

public class EditPlayer extends DialogFragment {
	
	private TextView tv;
	public EditPlayer() {
		//this.tv = tv;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    // Get the layout inflater
    LayoutInflater inflater = getActivity().getLayoutInflater();

    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setView(inflater.inflate(R.layout.edit_player, null))
    // Add action buttons
           .setPositiveButton("Change Name", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int id) {
                   //this.tv.setText("Test");
            	   // TODO
               }
           })
           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   EditPlayer.this.getDialog().cancel();
               }
           });      
    return builder.create();
	}

}