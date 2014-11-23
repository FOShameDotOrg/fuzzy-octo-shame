package com.jed.state;

import org.lwjgl.input.Keyboard;

import com.jed.util.MapLoader;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class PlayState extends GameState implements StateManager {

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

    @Override
    public void changeState(State state) {
        state.entered();
    }

    @Override
    public void entered() {
        currentMap = MapLoader.loadMap();
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
