package com.jed.state;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public interface StateManager extends State {
    void changeState(State state);
}
