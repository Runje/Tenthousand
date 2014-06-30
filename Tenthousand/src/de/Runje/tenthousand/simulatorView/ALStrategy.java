package de.Runje.tenthousand.simulatorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import de.Runje.tenthousand.model.DefaultStrategy;
import de.Runje.tenthousand.model.MyStrategy;
import de.Runje.tenthousand.simulator.Simulator;

public class ALStrategy implements ActionListener {

	private Simulator simulator;
	public ALStrategy(Simulator simulator) {
		this.simulator = simulator;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		String s = (String) cb.getSelectedItem();
		
		if (s.equals( "MyStrategy")) {
				simulator.setStrategy(new MyStrategy());
		} else if (s.equals("DefaultStrategy")) {
				simulator.setStrategy(new DefaultStrategy());
		}
	}

}
