package com.jed.state;

import java.util.Stack;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class GameStateManager {

    Stack<GameState> states = new GameStateStack<GameState>();

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
    public void draw() {
        for (GameState eachState : states) {
            eachState.draw();
        }
    }

    /**
     * 
     * @author jlinde, Peter Colapietro
     *
     */
    private class GameStateStack<E> extends Stack<E> {

        private static final long serialVersionUID = 1L;

        @Override
        public E push(E o) {
            super.push(o);
            ((GameState) o).entered();
            return o;
        }

        @Override
        public synchronized E pop() {
            E o = super.pop();
            ((GameState) o).leaving();
            return o;
        }

    }

}
