package com.jed.state;

public interface StateManager extends State {
	void changeState(State state);
}
