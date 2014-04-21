package de.Runje.tenthousand.model;


public class ValuePair {
	
	public Value value;
	public int dice;

	public ValuePair( Value v, int d) {
		value = v;
		dice = d;
	}
	
	public ValuePair( Value v) {
		value = v;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ValuePair [value=" + value + ", dice=" + dice + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dice;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		ValuePair other = (ValuePair) obj;
		if (dice != other.dice)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
}
