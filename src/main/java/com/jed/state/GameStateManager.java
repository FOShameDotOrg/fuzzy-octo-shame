package com.jed.state;

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
    private final Stack<GameState> states = new GameStateStack();

    /**
     * 
     * @param state to change to
     */
    public void changeState(GameState state) {
        clear();
        states.push(state);
    }

    /**
     * 
     * @param state state to push onto stack
     */
    public void push(GameState state) {
        states.push(state);
    }

    /**
     * 
     * @return state from top of stack
     */
    public GameState pop() {
        return states.pop();
    }

    /**
     * 
     */
    public void clear() {
        while (!states.isEmpty()) {
            states.pop();
        }
    }

    /**
     * 
     */
    public void update() {
        for (GameState eachState : states) {
            eachState.update();
        }
    }

    /**
     * 
     */
    public void render() {
        for (GameState eachState : states) {
            eachState.render();
        }
    }

    /**
     * 
     * @author jlinde, Peter Colapietro
     *
     */
    private final class GameStateStack extends Stack<GameState> {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        @Override
        public GameState push(GameState o) {
            super.push(o);
            o.entered();
            return o;
        }

        @Override
        public synchronized GameState pop() {
            GameState o = super.pop();
            o.leaving();
            return o;
        }

    }

}
