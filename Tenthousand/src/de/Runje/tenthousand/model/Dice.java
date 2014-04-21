package de.Runje.tenthousand.model;
import java.util.Random;

public class Dice implements Comparable<Dice>
{
	private DiceState state;

	private int number;
	
	public Dice( DiceState s)
	{
		state = s;
	}

	public Dice(int n, DiceState s)
	{
		state = s;
		number = n;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dice other = (Dice) obj;
		if (number != other.number)
			return false;
		return true;
	}

	public Dice(int n)
	{
		state = DiceState.FREE;
		number = n;
	}
	
	public boolean isFree() {
		return this.state == DiceState.FREE;
	}

	/**
	 * @return the state
	 */
	public DiceState getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(DiceState state) {
		this.state = state;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return number;
	}
	public int roll()
	{
		Random random = new Random();
		this.number = random.nextInt(6) + 1; 
		return this.number;
	}
	@Override
	public int compareTo(Dice other) {
		return Integer.compare(this.number, other.number);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Dice [state=" + state + ", value=" + number + "]";
	}
}