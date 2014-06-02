package de.Runje.tenthousand.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.Runje.tenthousand.Tenthousand;
import de.Runje.tenthousand.model.Dice;
import de.Runje.tenthousand.model.DiceHandler;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.Dices;
import de.Runje.tenthousand.model.Value;
import de.Runje.tenthousand.model.ValuePair;

public class DicesTest {

	@Test
	public void testDetermineValuePairs() {
		testStraight();
		testOneOfAKind();
		testTwoOfAKind();
		testThreeOfAKind();
		testFourOfAKind();
		testFiveOfAKind();
	}
	
	private void testOneOfAKind() {
		Tenthousand.getRules().setStraightActive(true);
		ValuePair one = new ValuePair(Value.ONE);
		ValuePair five = new ValuePair(Value.FIVE);
		List<ValuePair> result;
		
		DiceHandler noPoints = new DiceHandler(new Dices(2,2,3,4,6));
		result = noPoints.determineValuePairs();
		assertFalse(result.contains(one));
		assertFalse(result.contains(five));

		DiceHandler oneOne = new DiceHandler(new Dices(1,2,3,4,6));
		result = oneOne.determineValuePairs();
		assertTrue(result.contains(one));
		
		DiceHandler oneFive = new DiceHandler(new Dices(2,3,4,5,4));
		result = oneFive.determineValuePairs();
		assertTrue(result.contains(five));
	}

	private void testTwoOfAKind() {
		Tenthousand.getRules().setStraightActive(true);
		ValuePair one = new ValuePair(Value.ONE);
		ValuePair five = new ValuePair(Value.FIVE);
		List<ValuePair> result;
		List<ValuePair> expectedResult = new ArrayList<ValuePair>();

		DiceHandler twoFives = new DiceHandler(new Dices(2,3,4,5,5));
		result = twoFives.determineValuePairs();
		expectedResult.add(five);
		expectedResult.add(five);
		assertTrue(result.equals(expectedResult));

		DiceHandler twoOnes= new DiceHandler(new Dices(1,1,4,6,6));
		result = twoOnes.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(one);
		expectedResult.add(one);
		assertTrue(result.equals(expectedResult));
	}

	private void testThreeOfAKind() {
		Tenthousand.getRules().setStraightActive(true);
		ValuePair ones = new ValuePair(Value.THREE_OF_A_KIND, 1);
		ValuePair fives = new ValuePair(Value.THREE_OF_A_KIND, 5);
		ValuePair sixes = new ValuePair(Value.THREE_OF_A_KIND, 6);
		List<ValuePair> result;
		List<ValuePair> expectedResult = new ArrayList<ValuePair>();
		
		DiceHandler threeOnes= new DiceHandler(new Dices(1,1,1,6,6));
		result = threeOnes.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(ones);
		assertTrue(result.equals(expectedResult));

		DiceHandler threeFives= new DiceHandler(new Dices(5,5,5,6,6));
		result = threeFives.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(fives);
		assertTrue(result.equals(expectedResult));

		DiceHandler threeSixes= new DiceHandler(new Dices(2,3,6,6,6));
		result = threeSixes.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(sixes);
		assertTrue(result.equals(expectedResult));
	}

	private void testFourOfAKind() {
		Tenthousand.getRules().setStraightActive(true);
		ValuePair ones = new ValuePair(Value.FOUR_OF_A_KIND, 1);
		ValuePair fives = new ValuePair(Value.FOUR_OF_A_KIND, 5);
		ValuePair sixes = new ValuePair(Value.FOUR_OF_A_KIND, 6);
		List<ValuePair> result;
		List<ValuePair> expectedResult = new ArrayList<ValuePair>();
		
		DiceHandler threeOnes= new DiceHandler(new Dices(1,1,1,1,6));
		result = threeOnes.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(ones);
		assertTrue(result.equals(expectedResult));

		DiceHandler threeFives= new DiceHandler(new Dices(5,5,5,5,6));
		result = threeFives.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(fives);
		assertTrue(result.equals(expectedResult));

		DiceHandler threeSixes= new DiceHandler(new Dices(2,6,6,6,6));
		result = threeSixes.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(sixes);
		assertTrue(result.equals(expectedResult));
		
	}

	private void testFiveOfAKind() {
		Tenthousand.getRules().setStraightActive(true);
		ValuePair ones = new ValuePair(Value.FIVE_OF_A_KIND, 1);
		ValuePair fives = new ValuePair(Value.FIVE_OF_A_KIND, 5);
		ValuePair sixes = new ValuePair(Value.FIVE_OF_A_KIND, 6);
		List<ValuePair> result;
		List<ValuePair> expectedResult = new ArrayList<ValuePair>();
		
		DiceHandler threeOnes= new DiceHandler(new Dices(1,1,1,1,1));
		result = threeOnes.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(ones);
		assertTrue(result.equals(expectedResult));

		DiceHandler threeFives= new DiceHandler(new Dices(5,5,5,5,5));
		result = threeFives.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(fives);
		assertTrue(result.equals(expectedResult));

		DiceHandler threeSixes= new DiceHandler(new Dices(6,6,6,6,6));
		result = threeSixes.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(sixes);
		assertTrue(result.equals(expectedResult));
		
	}

	private void testStraight() {
		Tenthousand.getRules().setStraightActive(true);
		ValuePair oneStraight = new ValuePair(Value.STRAIGHT, 1);
		ValuePair twoStraight = new ValuePair(Value.STRAIGHT, 2);
		List<ValuePair> result;
		
		DiceHandler noStraight = new DiceHandler(new Dices(1,2,3,4,6));
		result = noStraight.determineValuePairs();
		assertFalse(result.contains(oneStraight));
		assertFalse(result.contains(twoStraight));

		DiceHandler noStraight2 = new DiceHandler(new Dices(2,3,4,5,5));
		result = noStraight2.determineValuePairs();
		assertFalse(result.contains(oneStraight));
		assertFalse(result.contains(twoStraight));

		ArrayList<Dice> dices= new ArrayList<Dice>();
		dices.add(new Dice(1, DiceState.FIX));
		dices.add(new Dice(2, DiceState.NO_POINTS));
		dices.add(new Dice(3, DiceState.NO_POINTS));
		dices.add(new Dice(4, DiceState.NO_POINTS));
		dices.add(new Dice(5, DiceState.NO_POINTS));
		DiceHandler noStraight3 = new DiceHandler(new Dices(dices));
		result = noStraight3.determineValuePairs();
		assertFalse(result.contains(oneStraight));
		assertFalse(result.contains(twoStraight));

		dices = new ArrayList<Dice>();
		dices.add(new Dice(2, DiceState.NO_POINTS));
		dices.add(new Dice(3, DiceState.NO_POINTS));
		dices.add(new Dice(4, DiceState.NO_POINTS));
		dices.add(new Dice(5, DiceState.NO_POINTS));
		dices.add(new Dice(6, DiceState.FIX));
		DiceHandler noStraight4 = new DiceHandler(new Dices(dices));
		result = noStraight4.determineValuePairs();
		assertFalse(result.contains(oneStraight));
		assertFalse(result.contains(twoStraight));

		DiceHandler straight = new DiceHandler(new Dices(2,3,4,5,6));
		result = straight.determineValuePairs();
		assertTrue(result.contains(twoStraight));

		DiceHandler straight2 = new DiceHandler(new Dices(1,2,3,4,5));
		result = straight2.determineValuePairs();
		assertTrue(result.contains(oneStraight));
	}
}
