package de.Runje.tenthousand.simulatorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import de.Runje.tenthousand.simulator.Simulator;


public class ALCalc implements ActionListener {

	private Simulator simulator;
	private JTextField tfPoints;
	private JLabel lResult;
	public ALCalc(Simulator simulator, JTextField tfPoints, JLabel lResult) {
		this.simulator = simulator;
		this.tfPoints = tfPoints;
		this.lResult = lResult;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int points = -1;
		try {
			points = Integer.parseInt(tfPoints.getText());
		} catch(Exception e) {
			//TODO
		}
		double prob = simulator.calcProbability(points);
		double ev = simulator.calcExpectedValue();
		
		lResult.setText("Probability: " + prob + ". EV: " + ev);
	}

}
