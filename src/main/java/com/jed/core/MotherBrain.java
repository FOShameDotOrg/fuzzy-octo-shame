package com.jed.core;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.jed.util.ExitStatusCode;
import org.colapietro.lwjgl.AbstractLwjglGameLoopable;
import org.colapietro.lwjgl.controllers.ButtonState;
import org.colapietro.lwjgl.controllers.Xbox360ControllerButton;
import org.colapietro.slick.BasicInputListener;
import org.colapietro.slick.InputListenable;
import org.colapietro.slick.InputProviderListenable;
import org.colapietro.slick.LoggableInputProviderListener;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.*;
import org.newdawn.slick.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jed.state.DiscoState;
import com.jed.state.GameStateManager;
import com.jed.state.MenuState;
import com.jed.state.PlayState;

import java.util.*;

/**
 * @author jlinde, Peter Colapietro
 */
public final class MotherBrain extends AbstractLwjglGameLoopable implements Startable, InputListenable,
        InputProviderListenable {

    /**
     * 
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MotherBrain.class);

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
     *
     */
    private final List<Controller> controllers = new ArrayList<>();

    /**
     *
     */
    private final Map<Xbox360ControllerButton, ButtonState> buttonStateMap =
            new EnumMap<>(Xbox360ControllerButton.class);

    /**
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new MotherBrainModule());
        final MotherBrain motherBrain = injector.getInstance(MotherBrain.class);
        final InputListener basicInputListener = injector.getInstance(BasicInputListener.class);
        final InputProviderListener loggableInputProviderListener =
                injector.getInstance(LoggableInputProviderListener.class);
        motherBrain.setInputListener(basicInputListener);
        motherBrain.setInputProviderListener(loggableInputProviderListener);
        motherBrain.start();
    }

    /**
     * 
     */
    @Override
    public void initialize() {
        try {
            Log.debug("org.lwjgl.input.Controllers#create");
            Controllers.create();
            for (int i = 0; i < Controllers.getControllerCount(); i++) {
                final Controller controller = Controllers.getController(i);
                Log.debug(controller.getName());
                final int controllerAxisCount = controller.getAxisCount();
                Log.debug("org.lwjgl.input.Controller#getAxisName");
                for (int j = 0; j < controllerAxisCount; j++) {
                    Log.debug(controller.getAxisName(j));
                }
                final int controllerButtonCount = controller.getButtonCount();
                Log.debug("org.lwjgl.input.Controller#getButtonCount");
                Log.debug(String.valueOf(controllerButtonCount));
                Log.debug("org.lwjgl.input.Controller#getButtonName");
                for (int j = 0; j < controllerButtonCount; j++) {
                    Log.debug(controller.getButtonName(j));
                }
                final int controllerRumblerCount = controller.getRumblerCount();
                Log.debug("org.lwjgl.input.Controller#getRumblerName");
                for (int j = 0; j < controllerRumblerCount; j++) {
                    Log.debug(controller.getRumblerName(j));
                }
                controllers.add(controller);
            }
        } catch (LWJGLException e) {
            Log.error("{}",e);
        }
        initializeDisplay();
        initializeOpenGl();
        initializeStateManager();
        initializeFrames();
    }

    /**
     *
     */
    private void initializeInputs() {
        final Input input = new Input(MotherBrainConstants.HEIGHT);
        try {
            input.initControllers();
        } catch (SlickException e) {
            Log.error(e);
        };
        inputListener.setInput(input);
        input.addListener(inputListener);
        inputProvider = new InputProvider(input);
        inputProvider.addListener(inputProviderListener);
        inputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), moveLeft);
        inputProvider.bindCommand(new KeyControl(Keyboard.KEY_LEFT), moveLeft);
        Log.debug("org.lwjgl.input.Controllers#create");
        for (int i = 0; i < Controllers.getControllerCount(); i++) {
            Log.debug(Controllers.getController(i).toString());
        }
    }

    /**
     *
     */
    private void initializeStateManager() {
        stateManager = new GameStateManager();
        pushDiscoStatesToStateManager(MotherBrainConstants.NUMBER_OF_DISCO_STATES);
        stateManager.push(new PlayState(MotherBrainConstants.IS_DEBUG_VIEW_ENABLED));
        if (MotherBrainConstants.IS_MENU_STATE_SHOWN) {
            pushMenuStateToStateManager();
        }
    }

    /**
     *
     */
    private void initializeFrames() {
        lastFPS = getTime();
    }

    /**
     *
     */
    private void initializeOpenGl() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, MotherBrainConstants.WIDTH, MotherBrainConstants.HEIGHT, 0, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    /**
     *
     */
    private void initializeDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(MotherBrainConstants.WIDTH, MotherBrainConstants.HEIGHT));
            Display.setFullscreen(MotherBrainConstants.IS_DISPLAY_FULLSCREEN);
            Display.create();
        } catch (LWJGLException e) {
            LOGGER.error("An exception occurred while creating the display", e);
            System.exit(ExitStatusCode.ERROR.getStatusCode());
        }
    }

    /**
     * 
     */
    private void pushMenuStateToStateManager() {
        final MenuState one = new MenuState();
        one.setDaString(MotherBrainConstants.DA_STRING);
        one.setCoordinates(MotherBrainConstants.MENU_STATE_COORDINATES);
        stateManager.push(one);
    }

    /**
     * 
     * @param numberOfStates numberOfStates
     */
    private void pushDiscoStatesToStateManager(int numberOfStates) {
        for (int i = 0; i < numberOfStates; i++) {
            stateManager.push(new DiscoState());
        }
    }

    /**
     * 
     */
    @Override
    public void start() {
        initialize();
        while (!Display.isCloseRequested()) {
            processInput();
            update();
            render();
        }

        Display.destroy();
    }

    @Override
    public void processInput() {
        //Controllers.poll();
        if(controllers.size() >= 1) {
            final Controller firstController = controllers.get(0);
            while(Controllers.next()) {
                if(Controllers.getEventSource().equals(firstController)) {
                    /**
                    if(Controllers.isEventAxis()) {
                        Log.debug("isEventAxis");
                            if(Controllers.isEventXAxis()) {
                            Log.debug("isEventXAxis");
                        }
                        if(Controllers.isEventYAxis()) {
                            Log.debug("isEventYAxis");
                        }
                    }
                     */
                    if(Controllers.isEventButton()) {
                        final int eventControlIndex = Controllers.getEventControlIndex();
                        final Xbox360ControllerButton xbox360ControllerButton =
                                Xbox360ControllerButton.valueOf(eventControlIndex);
                        final ButtonState buttonState = ButtonState.valueOf(Controllers.getEventButtonState());
                        buttonStateMap.put(xbox360ControllerButton, buttonState);
                        final Set<Map.Entry<Xbox360ControllerButton, ButtonState>> entrySet = buttonStateMap.entrySet();
                        for (Map.Entry<Xbox360ControllerButton, ButtonState> entry: entrySet) {
                            Log.debug(entry.getKey().name() + entry.getValue().name());
                        }
                    }
                    if(Controllers.isEventPovX()) {
                        Log.debug("isEventPovX");
                    }
                    if(Controllers.isEventPovY()) {
                        Log.debug("isEventPovY");
                    }
                }
            }
        }

    }

    @Override
    public void update() {
        updateFPS();
        stateManager.update();
    }

    @Override
    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        stateManager.render();
        Display.update();
        Display.sync(MotherBrainConstants.DISPLAY_FPS);
    }

    /**
     * 
     * @return time
     */
    private long getTime() {
        return (Sys.getTime() * MotherBrainConstants.HI_RESOLUTION_TIMER_TICKS_SCALAR) / Sys.getTimerResolution();
    }

    /**
     * 
     */
    private void updateFPS() {
        if (getTime() - lastFPS > MotherBrainConstants.HI_RESOLUTION_TIMER_TICKS_SCALAR) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += MotherBrainConstants.HI_RESOLUTION_TIMER_TICKS_SCALAR;
        }
        fps++;
    }

    @Override
    public void setInputListener(InputListener inputListener) {
        this.inputListener = inputListener;
    }

    @Override
    public void setInputProviderListener(InputProviderListener inputProviderListener) {
        this.inputProviderListener = inputProviderListener;
    }
}
