package de.Runje.tenthousand.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.Runje.tenthousand.controller.Action;
import de.Runje.tenthousand.controller.Controller;

public class ALTakeOver implements ActionListener {

	
	private Controller controller;
	public ALTakeOver(Controller controller) {
		this.controller = controller;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		controller.handleAction(Action.Takeover);
	}

}
