package org.colapietro.slick;

import org.colapietro.lang.LangConstants;
import org.colapietro.lang.NotImplementedException;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;

/**
 * Created by Peter Colapietro on 11/23/14.
 * @author Peter Colapietro
 * @since 0.1.8
 */
public abstract class AbstractInputListener implements InputListener {

    /** The maximum number of controllers supported by the basic game. */
    private static final int MAX_CONTROLLERS = 4;
    /** The maximum number of controller buttons supported by the basic game. */
    private static final int MAX_CONTROLLER_BUTTONS = 100;
    /** The state of the left control. */
    protected final boolean[] controllerLeft = new boolean[MAX_CONTROLLERS];
    /** The state of the right control. */
    protected final boolean[] controllerRight = new boolean[MAX_CONTROLLERS];
    /** The state of the up control. */
    protected final boolean[] controllerUp = new boolean[MAX_CONTROLLERS];
    /** The state of the down control. */
    protected final boolean[] controllerDown = new boolean[MAX_CONTROLLERS];
    /** The state of the button controls. */
    protected final boolean[][] controllerButton = new boolean[MAX_CONTROLLERS][MAX_CONTROLLER_BUTTONS];

    /** */
    private Input input;

    @Override
    public void controllerLeftPressed(int controller) {
        controllerLeft[controller] = true;
    }

    @Override
    public void controllerLeftReleased(int controller) {
        controllerLeft[controller] = false;
    }

    @Override
    public void controllerRightPressed(int controller) {
        controllerRight[controller] = true;
    }

    @Override
    public void controllerRightReleased(int controller) {
        controllerRight[controller] = false;
    }

    @Override
    public void controllerUpPressed(int controller) {
        controllerUp[controller] = true;
    }

    @Override
    public void controllerUpReleased(int controller) {
        controllerUp[controller] = false;
    }

    @Override
    public void controllerDownPressed(int controller) {
        controllerDown[controller] = true;
    }

    @Override
    public void controllerDownReleased(int controller) {
        controllerDown[controller] = false;
    }

    @Override
    public void controllerButtonPressed(int controller, int button) {
        controllerButton[controller][button] = true;
    }

    @Override
    public void controllerButtonReleased(int controller, int button) {
        controllerButton[controller][button] = false;
    }

    @Override
    public void keyPressed(int key, char c) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void keyReleased(int key, char c) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void mouseWheelMoved(int change) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void mousePressed(int button, int x, int y) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void mouseReleased(int button, int x, int y) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void mouseMoved(int oldX, int oldY, int newX, int newY) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void mouseDragged(int oldX, int oldY, int newX, int newY) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void setInput(Input input) {
        this.input = input;
    }

    /**
     *
     * @return input
     */
    public Input getInput() {
        return input;
    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void inputStarted() throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }
}
