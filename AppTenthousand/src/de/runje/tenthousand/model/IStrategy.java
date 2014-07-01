package de.runje.tenthousand.model;

public interface IStrategy {
	public boolean endMove(GameModel model);
	public boolean takeover(GameModel model);
	public boolean merge(GameModel model);
	
	/**
	 * Determines if a dice with state POINTS is set to FORCE_NO_POINTS
	 * @return Array of integer to indicate which dices to release
	 */
	public int[] releaseDices(GameModel model);
}
