package de.runje.tenthousand.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.runje.tenthousand.model.Rules;
import de.runje.tenthousand.model.Value;
import de.runje.tenthousand.model.ValuePair;

public class RulesTest {

	Rules rules = new Rules();
	@Test
	public void testCalcPoints() {
		
		//Empty
		assertEquals( 0, rules.calcPoints(new ArrayList<ValuePair>()));
		
		List<ValuePair> list = new ArrayList<ValuePair>();
		list.add(new ValuePair(Value.FIVE));
		list.add(new ValuePair(Value.ONE));
		list.add(new ValuePair(Value.FIVE_OF_A_KIND, 1));
		list.add(new ValuePair(Value.FIVE_OF_A_KIND, 6));
		list.add(new ValuePair(Value.FOUR_OF_A_KIND, 1));
		list.add(new ValuePair(Value.FOUR_OF_A_KIND, 6));
		list.add(new ValuePair(Value.THREE_OF_A_KIND, 1));
		list.add(new ValuePair(Value.THREE_OF_A_KIND, 6));
		list.add(new ValuePair(Value.STRAIGHT));
		
		assertEquals( 11350, rules.calcPoints(list));
		
	}

}
