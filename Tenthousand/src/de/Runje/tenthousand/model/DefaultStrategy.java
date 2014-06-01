package de.Runje.tenthousand.model;

public class DefaultStrategy implements IStrategy {


	public DefaultStrategy() {
	}
	@Override
	public boolean endMove() {
		return false;
	}

	@Override
	public boolean takeover() {
		return true;
	}

	@Override
	public boolean merge() {
		return true;
	}

	@Override
	public int[] releaseDices() {
		return new int[0];
	}

}
