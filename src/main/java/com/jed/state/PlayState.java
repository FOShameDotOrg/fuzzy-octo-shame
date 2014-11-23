package com.jed.state;

import org.lwjgl.input.Keyboard;

import com.jed.util.MapLoader;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class PlayState extends AbstractGameState implements StateManager {

    /**
     * 
     */
    private GameMap currentMap;

    /**
     * 
     */
    private boolean paused = true;
    
    /**
     * 
     */
    private boolean stepFrame = false;
    
    /**
     * 
     */
    private final boolean isDebugViewEnabled;
    
    /**
     * @since 0.1.8
     */
    public PlayState() {
        isDebugViewEnabled = false;
    }
    
    /**
     * @since 0.1.8
     * 
     * @param isDebugViewEnabled isDebugViewEnabled
     */
    public PlayState(boolean isDebugViewEnabled) {
        this.isDebugViewEnabled = isDebugViewEnabled;
    }

    @Override
    public void changeState(State state) {
        state.entered();
    }

    @Override
    public void entered() {
        currentMap = MapLoader.loadMap();
        currentMap.setDebugViewEnabled(isDebugViewEnabled);
        changeState(currentMap);
    }

    @Override
    public void leaving() {
    }

    @Override
    public void update() {
        getInput();
        if (!paused || stepFrame) {
            currentMap.update();
        }
        stepFrame = false;
    }

    /**
     * 
     */
    private void getInput() {
        while (Keyboard.next()) {
            currentMap.keyPress();
            if (Keyboard.getEventKey() == Keyboard.KEY_LMENU && Keyboard.getEventKeyState()) {
                paused = !paused;
            }

            if (paused) {
                if (Keyboard.getEventKey() == Keyboard.KEY_RMENU && Keyboard.getEventKeyState()) {
                    stepFrame = true;
                }
            }
        }
    }

    @Override
    public void render() {
        currentMap.render();
    }
}
