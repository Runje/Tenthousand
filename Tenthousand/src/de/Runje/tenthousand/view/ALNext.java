package de.Runje.tenthousand.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.Runje.tenthousand.controller.Action;
import de.Runje.tenthousand.controller.Controller;

public class ALNext implements ActionListener {

	private Controller controller;

	public ALNext(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		controller.handleAction(Action.Next);
	}

}
