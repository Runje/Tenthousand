package de.Runje.tenthousand.simulatorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import de.Runje.tenthousand.simulator.Simulator;

public class ALChangePlayer implements ActionListener {

	
	private Simulator simulator;
	private JTextField tfPoints;
	public ALChangePlayer(Simulator simulator, JTextField tfPoints) {
		this.simulator = simulator;
		this.tfPoints = tfPoints;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int points = -1;
		try {
			points = Integer.parseInt(tfPoints.getText());
		} catch(Exception e) {
			//TODO
		}
		simulator.changePlayer(points);
	}

}
