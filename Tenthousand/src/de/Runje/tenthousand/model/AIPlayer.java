package de.Runje.tenthousand.model;

public class AIPlayer extends Player {

	private IStrategy strategy;
	public AIPlayer(String name, IStrategy strategy) {
		super(name);
		this.strategy = strategy;
		this.ai = true;
	}
	/**
	 * @return the strategy
	 */
	public IStrategy getStrategy() {
		return strategy;
	}

}
