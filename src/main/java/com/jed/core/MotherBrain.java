package com.jed.core;

import com.jed.actor.Player;
import com.jed.state.DiscoState;
import com.jed.state.GameStateManager;
import com.jed.state.MenuState;
import com.jed.state.PlayState;
import com.jed.util.ExitStatusCode;
import org.colapietro.lwjgl.AbstractLwjglGameLoopable;
import org.colapietro.lwjgl.controllers.ButtonState;
import org.colapietro.lwjgl.controllers.Xbox360ControllerButton;
import org.colapietro.slick.InputListenable;
import org.colapietro.slick.command.BasicCommandConstants;
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

import java.util.*;

import static org.colapietro.lwjgl.controllers.Xbox360ControllerButton.*;

/**
 * @author jlinde, Peter Colapietro
 */
final class MotherBrain extends AbstractLwjglGameLoopable implements Startable, InputListenable {

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
    private InputProvider inputProvider;

    /**
     *
     */
    private InputListener inputListener;

    /**
     *
     */
    private final List<Controller> controllers = new ArrayList<>();

    /**
     *
     */
    private final Command moveLeft = new BasicCommand(BasicCommandConstants.MOVE_LEFT);

    /**
     *
     */
    private final Command bindControllerLeftDpad = new BasicCommand("bindControllerLeftDpad");

    /**
     *
     */
    private final Command pauseToggle = new BasicCommand("pauseToggle");


    /**
     *
     */
    private final Map<Xbox360ControllerButton, ButtonState> buttonStateMap =
            new EnumMap<>(Xbox360ControllerButton.class);

    /**
     *
     */
    private final Input input = new Input(MotherBrainConstants.HEIGHT);

    /**
     *
     */
    private final Command stepFrame = new BasicCommand("stepFrame");

    /**
     *
     */
    private final Command jump = new BasicCommand(BasicCommandConstants.JUMP);

    /**
     *
     */
    private final Command moveRight = new BasicCommand(BasicCommandConstants.MOVE_RIGHT);

    /**
     * 
     */
    @Override
    public void initialize() {
        initializeInputs();
        initializeDisplay();
        initializeOpenGl();
        initializeStateManager();
        initializeFrames();
    }

    /**
     *
     */
    private void initializeInputs() {
        try {
            input.initControllers();
        } catch (SlickException e) {
            Log.error(e);
        }

        inputProvider = new InputProvider(input);
        inputProvider.bindCommand(new ControllerButtonControl(0,valueOf(START, true)), pauseToggle);
        inputProvider.bindCommand(new KeyControl(Keyboard.KEY_LMENU), pauseToggle);

        inputProvider.bindCommand(new ControllerButtonControl(0,valueOf(X, true)), stepFrame);
        inputProvider.bindCommand(new KeyControl(Keyboard.KEY_RMENU), stepFrame);

        inputProvider.bindCommand(new ControllerButtonControl(0,valueOf(A, true)), jump);
        inputProvider.bindCommand(new KeyControl(Keyboard.KEY_SPACE), jump);

        inputProvider.bindCommand(new ControllerButtonControl(0,valueOf(DPAD_LEFT, true)), bindControllerLeftDpad);

        inputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT,
                valueOf(DPAD_LEFT, true)), moveLeft);
        inputProvider.bindCommand(new ControllerButtonControl(0,valueOf(DPAD_LEFT, true)), moveLeft);
        inputProvider.bindCommand(new KeyControl(Keyboard.KEY_LEFT), moveLeft);

        inputProvider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.RIGHT,
                valueOf(DPAD_RIGHT, true)), moveRight);
        inputProvider.bindCommand(new ControllerButtonControl(0,valueOf(DPAD_RIGHT, true)), moveRight);
        inputProvider.bindCommand(new KeyControl(Keyboard.KEY_RIGHT), moveRight);

        inputListener.setInput(input);

        input.addPrimaryListener(inputListener);

        logAllControllers();
    }

    /**
     *
     */
    private void logAllControllers() {
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
    }

    /**
     *
     */
    private void initializeStateManager() {
        stateManager = new GameStateManager();
        pushDiscoStatesToStateManager(MotherBrainConstants.NUMBER_OF_DISCO_STATES);
        final PlayState playState = new PlayState(MotherBrainConstants.IS_DEBUG_VIEW_ENABLED);
        final Player player = playState.getCurrentMap().getPlayer();

        inputProvider.addListener(playState);
        inputProvider.addListener(player);

        stateManager.push(playState);
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
        input.poll(MotherBrainConstants.WIDTH, MotherBrainConstants.HEIGHT);
    }

    /**
     *
     */
    @SuppressWarnings("unused")
    private void logFirstController() {
        if(controllers.size() >= 1) {
            final Controller firstController = controllers.get(0);
            while(Controllers.next()) {
                if(Controllers.getEventSource().equals(firstController)) {
                    if(Controllers.isEventAxis()) {
                        Log.debug("isEventAxis");
                            if(Controllers.isEventXAxis()) {
                            Log.debug("isEventXAxis");
                        }
                        if(Controllers.isEventYAxis()) {
                            Log.debug("isEventYAxis");
                        }
                    }
                    if(Controllers.isEventButton()) {
                        final int eventControlIndex = Controllers.getEventControlIndex();
                        final Xbox360ControllerButton xbox360ControllerButton =
                                valueOf(eventControlIndex);
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

}
