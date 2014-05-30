package de.Runje.tenthousand.simulatorView;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.simulator.Simulator;

public class SimulatorView {
	
	Simulator simulator;
	
	JFrame frame;
	JPanel panelDices;
	JButton buttonChange;
	JComboBox<String> diceSelect;
	JComboBox<Integer> numberSelect;
	JComboBox<DiceState> stateSelect;
	
	public SimulatorView(Simulator simulator) {
		this.simulator = simulator;
		//1. Create the frame.
		frame = new JFrame("Simulator");
		
		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createPanelDices();
		frame.getContentPane().add(panelDices, BorderLayout.SOUTH);
		//4. Size the frame.
		frame.pack();
		
		//5. Show it.
		frame.setVisible(true);
	}

	private void createPanelDices() {
		panelDices = new JPanel();
		ActionListener al = new ALDiceSelect(simulator);
		
		JLabel diceLabel = new JLabel("Dice: ");
		String[] diceList = { "1", "2", "3", "4", "5" };
		diceSelect = new JComboBox<String>(diceList);
		diceSelect.setName("Dice");
		diceSelect.addActionListener(al);

		JLabel numberLabel = new JLabel("Number: ");
		Integer[] numberList = {1,2,3,4,5,6};
		numberSelect = new JComboBox<Integer>(numberList);
		numberSelect.setName("Number");
		numberSelect.addActionListener(al);

		JLabel stateLabel = new JLabel("State: ");
		stateSelect = new JComboBox<DiceState>(DiceState.values());
		stateSelect.setName("State");
		stateSelect.setSelectedIndex(1);
		stateSelect.addActionListener(al);

		buttonChange = new JButton("change Dice");
		buttonChange.addActionListener(new ALChangeDice(simulator));
		
		panelDices.add(diceLabel);
		panelDices.add(diceSelect);
		panelDices.add(numberLabel);
		panelDices.add(numberSelect);
		panelDices.add(stateLabel);
		panelDices.add(stateSelect);
		panelDices.add(buttonChange);
	}
}
