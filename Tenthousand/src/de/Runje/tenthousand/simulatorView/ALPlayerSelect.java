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
		switch (cb.getName()) {
			case "Name":
				simulator.setName((String)cb.getSelectedItem());
				break;
			case "Rolls":
				Logger.log(LogLevel.DEBUG, "Simulator", "Rolls set to " + (int) cb.getSelectedItem());
				simulator.setRolls((int) cb.getSelectedItem());
				break;
			case "Strikes":
				Logger.log(LogLevel.DEBUG, "Simulator", "Strikes set to " + (int) cb.getSelectedItem());
				simulator.setStrikes((int) cb.getSelectedItem());
				break;
		}
	}

}
