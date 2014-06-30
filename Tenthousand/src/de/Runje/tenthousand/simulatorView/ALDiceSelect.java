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
		if (cb.getName().equals("Dice")) {
				simulator.setDice(cb.getSelectedIndex());
		} else if (cb.getName().equals("Number"))
		{
				simulator.setNumber((Integer) cb.getSelectedItem());
		} else if (cb.getName().equals("State")) {
				simulator.setState((DiceState) cb.getSelectedItem());
		}
	}

}
