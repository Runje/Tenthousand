package de.Runje.tenthousand.model;

public class AIPlayer extends Player {

	private IStrategy strategy;
	public AIPlayer(String name, IStrategy strategy) {
		super(name);
		this.strategy = strategy;
		this.human = false;
	}
	public AIPlayer(Player player, DefaultStrategy strategy) {
		super(player);
		this.strategy = strategy;
		this.human = false;
	}
	/**
	 * @return the strategy
	 */
	public IStrategy getStrategy() {
		return strategy;
	}

}
