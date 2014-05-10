package de.Runje.tenthousand.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.Runje.tenthousand.controller.Controller;

public class ALRollDices implements ActionListener {

	Controller controller;
	public ALRollDices(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.controller.rollDices();
	}

}
