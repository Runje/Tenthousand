package de.Runje.tenthousand.simulatorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.Runje.tenthousand.logger.LogLevel;
import de.Runje.tenthousand.logger.Logger;
import de.Runje.tenthousand.simulator.Simulator;


public class ALCalc implements ActionListener {

	private Simulator simulator;
	private JTextField tfPoints;
	private JLabel lResult;
	private ButtonGroup group;
	public ALCalc(Simulator simulator, JTextField tfPoints, JLabel lResult, ButtonGroup group) {
		this.simulator = simulator;
		this.tfPoints = tfPoints;
		this.lResult = lResult;
		this.group = group;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int points = -1;
		try {
			points = Integer.parseInt(tfPoints.getText());
		} catch(Exception e) {
			//TODO
		}
		ButtonModel bm = group.getSelection();
		String cmd = bm.getActionCommand();
		Logger.log(LogLevel.INFO, "Simulator", cmd);
		double prob = -1;
		double ev = -1;
		
		if (cmd.equals("Next")) {
			prob = simulator.calcProbability(points);
			ev = simulator.calcExpectedValue();
		} else if (cmd.equals("End")) {
			prob = simulator.calcProbabilityTillEnd(points);
			ev = simulator.calcExpectedValueTillEnd();
		} else assert(false);
		
		lResult.setText("Probability: " + prob + ". EV: " + ev);
	}

}
