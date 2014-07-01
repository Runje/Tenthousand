package de.runje.tenthousand.controller;

public enum Action {
	Roll, Merge,  Takeover, Switch, Next;
	
	// Index of the Dice to switch in case of Switch Action
	public int index;
}
