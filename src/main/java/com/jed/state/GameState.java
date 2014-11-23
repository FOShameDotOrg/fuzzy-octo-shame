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
    private final GameStateManager manager;

    /**
     * 
     * @param manager game state manager
     */
    GameState(GameStateManager manager) {
        this.manager = manager;
    }

}
