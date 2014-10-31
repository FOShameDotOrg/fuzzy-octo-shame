package com.jed.state;

import java.util.Stack;

import org.lwjgl.input.Keyboard;

import com.jed.actor.Entity;
import com.jed.util.MapLoader;

public class PlayState extends GameState implements StateManager {

    /**
     * FIXME Make relative to classpath
     */
    public static final String LEVEL_ONE_PATH = MapLoader.RESOURCES_DIRECTORY + "POC_MAP.tmx";

    private Stack<Entity> scene;

    private GameMap currentMap;

    private boolean paused = true;
    private boolean stepFrame = false;

    public PlayState(GameStateManager manager) {
        super(manager);
    }

    @Override
    public void changeState(State state) {
        state.entered();
    }

    @Override
    public void entered() {
        currentMap = MapLoader.loadMap(LEVEL_ONE_PATH);
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
    public void draw() {
        currentMap.draw();
    }
}
