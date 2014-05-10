package de.Runje.tenthousand.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import de.Runje.tenthousand.controller.Controller;
import de.Runje.tenthousand.model.Dices;
import de.Runje.tenthousand.observer.IObserver;

public class GameView implements IObserver{

	JFrame frame;
	JButton buttonRoll;
	JLabel labelDices;
	JLabel labelPoints;
	Controller controller;
	public GameView(Controller controller) {
		this.controller = controller;
		//1. Create the frame.
		frame = new JFrame("FrameDemo");

		//2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buttonRoll = new JButton("roll");
		buttonRoll.addActionListener(new ALRollDices(controller));
		labelDices = new JLabel("");
		labelPoints = new JLabel("Points: 0");
		frame.getContentPane().add(buttonRoll, BorderLayout.CENTER);
		frame.getContentPane().add(labelDices, BorderLayout.NORTH);
		frame.getContentPane().add(labelPoints, BorderLayout.WEST);

		//4. Size the frame.
		frame.pack();

		//5. Show it.
		frame.setVisible(true);
		controller.addObserver(this);
	}

	private void updateDices() {
		Dices dices = controller.getDices();
		labelDices.setText(dices.toString());
	}
	@Override
	public void update() {
		updateDices();
		updatePoints();
		frame.pack();
	}

	private void updatePoints() {
		int points = controller.getPoints();
		labelPoints.setText("Points: " + Integer.toString(points));
	}

}
