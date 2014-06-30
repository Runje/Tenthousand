package de.Runje.tenthousand.simulatorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import de.Runje.tenthousand.logger.LogLevel;
import de.Runje.tenthousand.logger.Logger;
import de.Runje.tenthousand.simulator.Simulator;

public class ALPlayerSelect implements ActionListener {

	private Simulator simulator;
	public ALPlayerSelect(Simulator simulator) {
		this.simulator = simulator;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		if (cb.getName().equals("Name")) {
				simulator.setName((String)cb.getSelectedItem());
		} else if (cb.getName().equals("Rolls")) {
				Logger.log(LogLevel.DEBUG, "Simulator", "Rolls set to " + (Integer) cb.getSelectedItem());
				simulator.setRolls((Integer) cb.getSelectedItem());
		} else if (cb.getName().equals("Strikes")) {
				Logger.log(LogLevel.DEBUG, "Simulator", "Strikes set to " + (Integer) cb.getSelectedItem());
				simulator.setStrikes((Integer) cb.getSelectedItem());
		}
	}

}
