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
