package com.jed.state;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public abstract class GameState extends AbstractDisplayableState  {

    /**
     * 
     */
    protected final GameStateManager manager;

    /**
     * 
     * @param manager game state manager
     */
    public GameState(GameStateManager manager) {
        this.manager = manager;
    }

}
