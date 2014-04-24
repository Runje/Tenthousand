package de.Runje.tenthousand.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.Runje.tenthousand.model.Dice;
import de.Runje.tenthousand.model.DiceState;
import de.Runje.tenthousand.model.Dices;
import de.Runje.tenthousand.model.Tenthousand;
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
		
		Dices noPoints = new Dices(2,2,3,4,6);
		result = noPoints.determineValuePairs();
		assertFalse(result.contains(one));
		assertFalse(result.contains(five));

		Dices oneOne = new Dices(1,2,3,4,6);
		result = oneOne.determineValuePairs();
		assertTrue(result.contains(one));
		
		Dices oneFive = new Dices(2,3,4,5,4);
		result = oneFive.determineValuePairs();
		assertTrue(result.contains(five));
	}

	private void testTwoOfAKind() {
		Tenthousand.getRules().setStraightActive(true);
		ValuePair one = new ValuePair(Value.ONE);
		ValuePair five = new ValuePair(Value.FIVE);
		List<ValuePair> result;
		List<ValuePair> expectedResult = new ArrayList<ValuePair>();

		Dices twoFives = new Dices(2,3,4,5,5);
		result = twoFives.determineValuePairs();
		expectedResult.add(five);
		expectedResult.add(five);
		assertTrue(result.equals(expectedResult));

		Dices twoOnes= new Dices(1,1,4,6,6);
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
		
		Dices threeOnes= new Dices(1,1,1,6,6);
		result = threeOnes.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(ones);
		assertTrue(result.equals(expectedResult));

		Dices threeFives= new Dices(5,5,5,6,6);
		result = threeFives.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(fives);
		assertTrue(result.equals(expectedResult));

		Dices threeSixes= new Dices(2,3,6,6,6);
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
		
		Dices threeOnes= new Dices(1,1,1,1,6);
		result = threeOnes.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(ones);
		assertTrue(result.equals(expectedResult));

		Dices threeFives= new Dices(5,5,5,5,6);
		result = threeFives.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(fives);
		assertTrue(result.equals(expectedResult));

		Dices threeSixes= new Dices(2,6,6,6,6);
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
		
		Dices threeOnes= new Dices(1,1,1,1,1);
		result = threeOnes.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(ones);
		assertTrue(result.equals(expectedResult));

		Dices threeFives= new Dices(5,5,5,5,5);
		result = threeFives.determineValuePairs();
		expectedResult.clear();
		expectedResult.add(fives);
		assertTrue(result.equals(expectedResult));

		Dices threeSixes= new Dices(6,6,6,6,6);
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
		
		Dices noStraight = new Dices(1,2,3,4,6);
		result = noStraight.determineValuePairs();
		assertFalse(result.contains(oneStraight));
		assertFalse(result.contains(twoStraight));

		Dices noStraight2 = new Dices(2,3,4,5,5);
		result = noStraight2.determineValuePairs();
		assertFalse(result.contains(oneStraight));
		assertFalse(result.contains(twoStraight));

		ArrayList<Dice> dices = new ArrayList<Dice>();
		dices.add(new Dice(1, DiceState.FIX));
		dices.add(new Dice(2, DiceState.FREE));
		dices.add(new Dice(3, DiceState.FREE));
		dices.add(new Dice(4, DiceState.FREE));
		dices.add(new Dice(5, DiceState.FREE));
		Dices noStraight3 = new Dices(dices);
		result = noStraight3.determineValuePairs();
		assertFalse(result.contains(oneStraight));
		assertFalse(result.contains(twoStraight));

		dices = new ArrayList<Dice>();
		dices.add(new Dice(2, DiceState.FREE));
		dices.add(new Dice(3, DiceState.FREE));
		dices.add(new Dice(4, DiceState.FREE));
		dices.add(new Dice(5, DiceState.FREE));
		dices.add(new Dice(6, DiceState.FIX));
		Dices noStraight4 = new Dices(dices);
		result = noStraight4.determineValuePairs();
		assertFalse(result.contains(oneStraight));
		assertFalse(result.contains(twoStraight));

		Dices straight = new Dices(2,3,4,5,6);
		result = straight.determineValuePairs();
		assertTrue(result.contains(twoStraight));

		Dices straight2 = new Dices(1,2,3,4,5);
		result = straight2.determineValuePairs();
		assertTrue(result.contains(oneStraight));
	}
}
