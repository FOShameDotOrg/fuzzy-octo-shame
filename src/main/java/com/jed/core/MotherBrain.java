package com.jed.core;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jed.state.DiscoState;
import com.jed.state.GameStateManager;
import com.jed.state.MenuState;
import com.jed.state.PlayState;
import com.jed.util.Vector;

/**
 * @author jlinde, Peter Colapietro
 */
public final class MotherBrain implements Startable {

    /**
     * 
     */
    private static final Vector MENU_STATE_COORDS = new Vector(20,20);

    /**
     * 
     */
    private static final String DA_STRING = "Hello there!";

    /**
     * 
     */
    private static final int NUMBER_OF_DISCO_STATES = 0;

    /**
     * 
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MotherBrain.class);

    /**
     * 
     */
    public static final int WIDTH = 1024;
    
    /**
     * 
     */
    public static final int HEIGHT = 768;

    /**
     * 
     */
    private static final boolean IS_MENU_STATE_SHOWN = false;

    /**
     * Frames per second.
     */
    public static final int FPS = 120;

    /**
     * 
     */
    private long lastFrame;
    
    /**
     * 
     */
    private int fps;
    
    /**
     * 
     */
    private long lastFPS;

    /**
     * 
     */
    private GameStateManager stateManager;

    /**
     * @param args Command-line arguments
     * @see <a href="http://projects.lidalia.org.uk/sysout-over-slf4j/quickstart.html">System Out and Err redirected to SLF4J</a>
     */
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new MotherBrainModule());
        final MotherBrain motherBrain = injector.getInstance(MotherBrain.class);
        motherBrain.start();
    }

    /**
     * 
     */
    private void init() {
        stateManager = new GameStateManager();
        pushDiscoStatesToStateManager(NUMBER_OF_DISCO_STATES);
        stateManager.push(new PlayState(stateManager));
        if (IS_MENU_STATE_SHOWN) {
            pushMenuStateToStateManager();
        }
        
        getDelta();
        lastFPS = getTime();
    }

    /**
     * 
     */
    private void pushMenuStateToStateManager() {
        final MenuState one = new MenuState(stateManager);
        one.setDaString(DA_STRING);
        one.setCoords(MENU_STATE_COORDS);
        stateManager.push(one);
    }

    /**
     * 
     * @param numberOfStates numberOfStates
     */
    private void pushDiscoStatesToStateManager(int numberOfStates) {
        for (int i = 0; i < numberOfStates; i++) {
            stateManager.push(new DiscoState(stateManager));
        }
    }

    /**
     * 
     */
    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setFullscreen(true);
            Display.create();
        } catch (LWJGLException e) {
            LOGGER.error("An exception occurred while creating the display", e);
            System.exit(1);
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, WIDTH, HEIGHT, 0, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        init();

        while (!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            //int delta = getDelta();

            updateFPS();

            stateManager.update();
            stateManager.draw();

            Display.update();

            Display.sync(FPS);
        }

        Display.destroy();
    }

    /**
     * 
     * @return delta
     */
    private int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
        return delta;
    }

    /**
     * 
     * @return time
     */
    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * 
     */
    private void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }

        fps++;
    }

}
