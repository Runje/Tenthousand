package de.Runje.tenthousand.simulatorView;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.Rules;
import de.Runje.tenthousand.simulator.Simulator;

public class SimulatorView {
	
	Simulator simulator;
	
	JFrame frame;
	JPanel panelDices;
	JButton buttonChange;
	JComboBox<String> diceSelect;
	JComboBox<Integer> numberSelect;
	JComboBox<DiceState> stateSelect;

	private JPanel panelPlayer;

	private JPanel panelCalculator;
	
	public SimulatorView(Simulator simulator) {
		this.simulator = simulator;
		//1. Create the frame.
		frame = new JFrame("Simulator");
		
		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createPanelDices();
		createPanelPlayer();
		createPanelCalculator();
		frame.getContentPane().add(panelPlayer, BorderLayout.SOUTH);
		frame.getContentPane().add(panelDices, BorderLayout.NORTH);
		frame.getContentPane().add(panelCalculator, BorderLayout.EAST);
		//4. Size the frame.
		frame.pack();
		
		//5. Show it.
		frame.setVisible(true);
	}

	private void createPanelCalculator() {
		panelCalculator = new JPanel();
		
		JTextField points = new JTextField();
		points.setColumns(5);
		JLabel result = new JLabel( "??? %");
		
		JButton calc = new JButton("Calculate probability");
		calc.addActionListener(new ALCalc(simulator, points, result));
		panelCalculator.add(points);
		panelCalculator.add(calc);
		panelCalculator.add(result);
	}

	private void createPanelPlayer() {
		panelPlayer = new JPanel();
		ActionListener al = new ALPlayerSelect(simulator);
		String[] playersList = simulator.getPlayersList();
		JComboBox<String> cbName = new JComboBox<String>(playersList);
		cbName.addActionListener(al);
		cbName.setName("Name");
		//Points
		JTextField points = new JTextField();
		points.setColumns(5);
		//Rolls
		Integer[] rollList = new Integer[Rules.MaxRolls + 1];
		for (int i = 0; i < Rules.MaxRolls + 1; i++) {
			rollList[i] = i;
		}
		JComboBox<Integer> cbRolls = new JComboBox<Integer>(rollList);
		cbRolls.addActionListener(al);
		cbRolls.setName("Rolls");
		//Strikes
		Integer[] strikeList = new Integer[Rules.MaxRolls + 1];
		for (int i = 0; i < Rules.MaxRolls + 1; i++) {
			strikeList[i] = i;
		}
		JComboBox<Integer> cbStrikes = new JComboBox<Integer>(strikeList);
		cbStrikes.addActionListener(al);
		cbStrikes.setName("Strikes");
		JButton buttonPlayer = new JButton("change Player");
		buttonPlayer.addActionListener(new ALChangePlayer(simulator, points));
		
		JLabel pointsLabel = new JLabel("Points: ");
		JLabel rollsLabel = new JLabel("Rolls: ");
		JLabel strikesLabel = new JLabel("Strikes: ");
		panelPlayer.add(cbName);
		panelPlayer.add(pointsLabel);
		panelPlayer.add(points);
		panelPlayer.add(rollsLabel);
		panelPlayer.add(cbRolls);
		panelPlayer.add(strikesLabel);
		panelPlayer.add(cbStrikes);
		panelPlayer.add(buttonPlayer);

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
