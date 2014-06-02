package de.Runje.tenthousand.model;

public class MyStrategy extends DefaultStrategy {
	
	@Override
	public boolean endMove(GameModel model) {
		int freeDices = model.getFreeDices();
		int points = model.getPoints();
		if (freeDices == 3 && points >= 1000) {
			return true;
		}
		if (freeDices == 0) {
			return false;
		}
		if (freeDices < 3 && points >= 300) {
			return true;
		}
		return false;
	}

	@Override
	public boolean takeover(GameModel model) {
		int freeDices = model.getFreeDices();
		int points = model.getPoints();
		if (freeDices > 2) {
			return true;
		}
		if (points > 800) {
			return true;
		}
		return false;
	}

	@Override
	public int[] releaseDices(GameModel model) {
		return new int[0];
	}

}
