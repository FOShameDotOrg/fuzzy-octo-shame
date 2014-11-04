package com.jed.state;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public abstract class GameState implements State {

    /**
     * 
     */
    protected GameStateManager manager;

    /**
     * 
     * @param manager game state manager
     */
    public GameState(GameStateManager manager) {
        this.manager = manager;
    }

    @Override
    public void drawChildVertex2f(float x, float y) {
    }
}
