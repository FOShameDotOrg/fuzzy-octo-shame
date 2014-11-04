package com.jed.state;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public interface StateManager extends State {
    
    /**
     * 
     * @param state state
     */
    void changeState(State state);
}
