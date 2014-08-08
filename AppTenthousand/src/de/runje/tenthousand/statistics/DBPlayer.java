package de.runje.tenthousand.statistics;


public class DBPlayer {
	private String name;
	private int games;
	private int playing;
	public DBPlayer(String name, int playing, int games) {
		this.name = name;
		this.playing = playing;
		this.games = games;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DBPlayer [name=" + name + ", games=" + games + ", playing="
				+ playing + "]";
	}

	/**
	 * @return the playing
	 */
	public int getPlaying() {
		return playing;
	}

	/**
	 * @param playing the playing to set
	 */
	public void setPlaying(int playing) {
		this.playing = playing;
	}

	public DBPlayer() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the games
	 */
	public int getGames() {
		return games;
	}
	/**
	 * @param games the games to set
	 */
	public void setGames(int games) {
		this.games = games;
	}
	
}
