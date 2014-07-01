package de.runje.tenthousand.model;

public class DefaultStrategy implements IStrategy {


	public DefaultStrategy() {
	}
	@Override
	public boolean endMove(GameModel model) {
		return false;
	}

	@Override
	public boolean takeover(GameModel model) {
		return true;
	}

	@Override
	public boolean merge(GameModel model) {
		return true;
	}

	@Override
	public int[] releaseDices(GameModel model) {
		return new int[0];
	}

}
