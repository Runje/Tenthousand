package de.Runje.tenthousand.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.Runje.tenthousand.controller.Controller;
import de.Runje.tenthousand.model.Dice;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.Dices;
import de.Runje.tenthousand.model.Player;
import de.Runje.tenthousand.observer.IObserver;

public class GameView implements IObserver{

	JFrame frame;
	JPanel panelActions;
	JButton buttonRoll;
	JPanel panelDices;
	List<JButton> buttonDices;
	Controller controller;
	private JButton buttonNext;
	private JTextArea textPoints;
	private JLabel labelActualPoints;
	private JButton buttonTakeOver;
	private JButton buttonMerge;
	public GameView(Controller controller) {
		this.controller = controller;
		//1. Create the frame.
		frame = new JFrame("FrameDemo");

		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelActions = new JPanel();
		buttonRoll = new JButton("roll");
		buttonRoll.addActionListener(new ALRollDices(controller));
		buttonNext = new JButton("Next");
		buttonNext.setEnabled(false);
		buttonNext.addActionListener(new ALNext(controller));
		buttonTakeOver = new JButton("Take over");
		buttonTakeOver.setVisible(false);
		buttonTakeOver.addActionListener(new ALTakeOver(controller));
		buttonMerge = new JButton("Merge two Fives");
		buttonMerge.setEnabled(false);
		buttonMerge.addActionListener(new ALMerge(controller));
		panelActions.add(buttonMerge);
		panelActions.add(buttonTakeOver);
		panelActions.add(buttonNext);
		panelActions.add(buttonRoll);
		buttonDices = new ArrayList<JButton>();
		for (int i=0; i < 5; ++i) {
			buttonDices.add(new JButton("0"));
			
		}
		panelDices = new JPanel();
		int index = 0;
		for (JButton button : buttonDices) {
			button.addActionListener(new ALDice(controller));
			button.setName(Integer.toString(index));
			panelDices.add(button);
			index++;
		}
		
		textPoints = new JTextArea(2, 20);
		
		labelActualPoints = new JLabel("Points: 0");
		
		frame.getContentPane().add(labelActualPoints, BorderLayout.WEST);
		frame.getContentPane().add(textPoints, BorderLayout.NORTH);
		frame.getContentPane().add(panelDices, BorderLayout.CENTER);
		frame.getContentPane().add(panelActions, BorderLayout.SOUTH);
		updatePoints();
		//4. Size the frame.
		frame.pack();

		//5. Show it.
		frame.setVisible(true);
		controller.model.addObserver(this);
	}
	
	private void updatePossibleMoves() {
		buttonRoll.setEnabled(controller.isPossibleToRoll());
		buttonTakeOver.setVisible(controller.isPossibleToTakeOver());
		buttonNext.setEnabled(controller.nextIsPossible());
		buttonMerge.setEnabled(controller.isPossibleToMerge());
	}

	private void updateDices() {
		//TODO: Make dices iterable
		Dices dices = controller.getDices();
		for (int i = 0; i < dices.getDices().size(); i++) {
			Dice dice = dices.getDices().get(i);
			int value = dice.getNumber();
			DiceState state = dice.getState();
			buttonDices.get(i).setText(Integer.toString(value));
			Color color = Color.BLUE;
			if (state == DiceState.NO_POINTS) {
				color = Color.GRAY;
			} else if (state == DiceState.POINTS) {
				color = Color.WHITE;
			} else if (state == DiceState.FIX) {
				color = Color.RED;
			}
				
			buttonDices.get(i).setBackground(color);
			
		}
	}
	@Override
	public void update() {
		updateDices();
		updatePoints();
		updatePossibleMoves();
		frame.pack();
	}

	private void updatePoints() {
		ArrayList<Player> players = controller.getPlayers();
		textPoints.setText("");
		for (Player player : players) {
			textPoints.append(player.getName() + "\t" + player.getPoints() + "\n");
		}
		textPoints.append("Turn: " + controller.getNameOfPlayingPlayer());
		
		if (controller.model.isGameFinished()) {
			textPoints.append("\nGame has ended");
		}
		int actualPoints = controller.getActualPoints();
		labelActualPoints.setText("Points: " + actualPoints ) ;
	}

}
