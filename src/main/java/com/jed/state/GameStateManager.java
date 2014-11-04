package com.jed.state;

import java.util.Stack;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class GameStateManager {

    Stack<GameState> states = new GameStateStack<GameState>();

    public void changeState(GameState state) {
        clear();
        states.push(state);
    }

    public void push(GameState state) {
        states.push(state);
    }

    public GameState pop() {
        return states.pop();
    }

    public void clear() {
        while (!states.isEmpty()) {
            states.pop();
        }
    }

    public void update() {
        for (GameState eachState : states) {
            eachState.update();
        }
    }

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

        /**
         * TODO Test me after adding synchronized.
         */
        @Override
        public synchronized E pop() {
            E o = super.pop();
            ((GameState) o).leaving();
            return o;
        }

    }

}
