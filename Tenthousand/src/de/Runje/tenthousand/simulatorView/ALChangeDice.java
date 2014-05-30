package de.Runje.tenthousand.simulatorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.Runje.tenthousand.simulator.Simulator;

public class ALChangeDice implements ActionListener {

	Simulator simulator;
	
	public ALChangeDice(Simulator simulator){
		this.simulator = simulator;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		simulator.changeDice();
	}

}
