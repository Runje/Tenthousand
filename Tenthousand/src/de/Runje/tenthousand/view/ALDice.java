package de.Runje.tenthousand.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import de.Runje.tenthousand.controller.Action;
import de.Runje.tenthousand.controller.Controller;

public class ALDice implements ActionListener {

	private Controller controller;
	public ALDice(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Cast safeness?
		JButton button = (JButton) arg0.getSource();
		Action a = Action.Switch;
		a.index = Integer.parseInt(button.getName());
		this.controller.handleAction(a);
	}

}
