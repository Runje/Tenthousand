package de.Runje.tenthousand.model;

import java.util.List;

public class Rules {
	
	private boolean straightActive;
	
	public Rules(boolean straightActive) {
		this.straightActive = straightActive;
	}
	
	public Rules() {
		this(false);
	}
	
	/**
	 * @return the straightActive
	 */
	public boolean isStraightActive() {
		return straightActive;
	}

	/**
	 * @param straightActive the straightActive to set
	 */
	public void setStraightActive(boolean straightActive) {
		this.straightActive = straightActive;
	}

	public int calcPoints(List<ValuePair> valuePairs) {
		int points = 0;
		for(ValuePair valuePair : valuePairs) {
			points += calcPoints(valuePair);
		}
		return points;
	}

	private int calcPoints(ValuePair valuePair) {
		int dice = valuePair.dice;
		assert(dice > 0);
		assert(dice < 7);
		int points = 0;
		switch (valuePair.value) {
		case FIVE:
			points = pointsFive();
			break;
		case FIVE_OF_A_KIND:
			points = pointsFiveOfAKind(dice);
			break;
		case FOUR_OF_A_KIND:
			points = pointsFourOfAKind(dice);
			break;
		case ONE:
			points = pointsOne(); 
			break;
		case STRAIGHT:
			points = pointsStraight(dice);
			break;
		case THREE_OF_A_KIND:
			points = pointsThreeOfAKind(dice);
			break;
		default:
			assert(false);
			break;
		}
		return points;
	}

	private int pointsThreeOfAKind(int dice) {
		if (dice == 1) {
			return 1000 * dice;
		} else {
			return 100 * dice;
		}
	}

	private int pointsStraight(int dice) {
		return 0;
	}

	private int pointsOne() {
		return 100;
	}

	private int pointsFourOfAKind(int dice) {
		if (dice == 1) {
			return 2000 * dice;
		} else {
			return 200 * dice;
		}
	}

	private int pointsFiveOfAKind(int dice) {
		if (dice == 1) {
			return 4000 * dice;
		} else {
			return 400 * dice;
		}
	}

	private int pointsFive() {
		return 50;
	}
}
