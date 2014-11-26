package com.jed.state;

import javax.annotation.Nonnull;
import java.util.Stack;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class GameStateManager {

    /**
     * 
     */
    private final Stack<AbstractGameState> states = new GameStateStack();

    /**
     * 
     * @param state to change to
     */
    public void changeState(AbstractGameState state) {
        clear();
        states.push(state);
    }

    /**
     * 
     * @param state state to push onto stack
     */
    public void push(AbstractGameState state) {
        states.push(state);
    }

    /**
     * 
     * @return state from top of stack
     */
    public AbstractGameState pop() {
        return states.pop();
    }

    /**
     * 
     */
    void clear() {
        while (!states.isEmpty()) {
            states.pop();
        }
    }

    /**
     * 
     */
    public void update() {
        for (AbstractGameState eachState : states) {
            eachState.update();
        }
    }

    /**
     * 
     */
    public void render() {
        for (AbstractGameState eachState : states) {
            eachState.render();
        }
    }

}
