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
     * @param <E> Type which extends com.jed.state.State
     */
    <E extends State> void changeState(E state);

}
