package de.Runje.tenthousand.simulatorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.simulator.Simulator;

public class ALDiceSelect implements ActionListener {

	Simulator simulator;

	public ALDiceSelect(Simulator simulator) {
		this.simulator = simulator;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		switch (cb.getName()) {
			case "Dice":
				simulator.setDice(cb.getSelectedIndex());
				break;
			case "Number":
				simulator.setNumber((Integer) cb.getSelectedItem());
				break;
			case "State":
				simulator.setState((DiceState) cb.getSelectedItem());
		}
	}

}
