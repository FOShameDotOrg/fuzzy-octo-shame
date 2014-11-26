package com.jed.state;

import org.colapietro.lang.LangConstants;
import org.colapietro.lang.NotImplementedException;

import java.util.Stack;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public final class GameStateManager implements StateManager {

    /**
     * 
     */
    private final Stack<AbstractDisplayableState> states = new GameStateStack<>();

    /**
     * 
     * @param state to change to
     */
    @Override
    public <E extends State> void changeState(E state) {
        clear();
        states.push((AbstractDisplayableState)state);//FIXME
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
    public State pop() {
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

    @Override
    public void entered() throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);

    }

    @Override
    public void leaving() throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);

    }

    /**
     * 
     */
    public void update() {
        for (State eachState : states) {
            eachState.update();
        }
    }

    /**
     * 
     */
    public void render() {
        for (AbstractDisplayableState eachState : states) {
            eachState.render();
        }
    }

}
