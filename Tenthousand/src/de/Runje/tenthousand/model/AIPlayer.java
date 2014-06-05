package de.Runje.tenthousand.model;

public class AIPlayer extends Player {

	private IStrategy strategy;
	public AIPlayer(String name, IStrategy strategy) {
		super(name);
		this.strategy = strategy;
		this.human = false;
	}
	public AIPlayer(Player player, IStrategy strategy) {
		super(player);
		this.strategy = strategy;
	}
	/**
	 * @return the strategy
	 */
	public IStrategy getStrategy() {
		return strategy;
	}

}
