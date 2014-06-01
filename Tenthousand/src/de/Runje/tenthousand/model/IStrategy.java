package de.Runje.tenthousand.model;

public interface IStrategy {
	public boolean endMove();
	public boolean takeover();
	public boolean merge();
	
	/**
	 * Determines if a dice with state POINTS is set to FORCE_NO_POINTS
	 * @return Array of integer to indicate which dices to release
	 */
	public int[] releaseDices();
}
