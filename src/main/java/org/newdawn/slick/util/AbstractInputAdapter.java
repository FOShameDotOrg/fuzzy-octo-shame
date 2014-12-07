package org.newdawn.slick.util;

import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;

/**
 * An implementation of the InputListener interface.
 *
 * @author kevin
 */
public abstract class AbstractInputAdapter implements InputListener {
    /** A flag to indicate if we're accepting input here. */
    private boolean acceptingInput = true;

    /**
     * @see org.newdawn.slick.InputListener#controllerButtonPressed(int, int)
     */
    @Override
    public void controllerButtonPressed(int controller, int button){}

    /**
     * @see org.newdawn.slick.InputListener#controllerButtonReleased(int, int)
     */
    @Override
    public void controllerButtonReleased(int controller, int button){}

    /**
     * @see org.newdawn.slick.InputListener#controllerDownPressed(int)
     */
    @Override
    public void controllerDownPressed(int controller){}

    /**
     * @see org.newdawn.slick.InputListener#controllerDownReleased(int)
     */
    @Override
    public void controllerDownReleased(int controller){}

    /**
     * @see org.newdawn.slick.InputListener#controllerLeftPressed(int)
     */
    @Override
    public void controllerLeftPressed(int controller){}

    /**
     * @see org.newdawn.slick.InputListener#controllerLeftReleased(int)
     */
    @Override
    public void controllerLeftReleased(int controller){}

    /**
     * @see org.newdawn.slick.InputListener#controllerRightPressed(int)
     */
    @Override
    public void controllerRightPressed(int controller){}

    /**
     * @see org.newdawn.slick.InputListener#controllerRightReleased(int)
     */
    @Override
    public void controllerRightReleased(int controller){}

    /**
     * @see org.newdawn.slick.InputListener#controllerUpPressed(int)
     */
    @Override
    public void controllerUpPressed(int controller){}

    /**
     * @see org.newdawn.slick.InputListener#controllerUpReleased(int)
     */
    @Override
    public void controllerUpReleased(int controller){}

    /**
     * @see org.newdawn.slick.InputListener#inputEnded()
     */
    @Override
    public void inputEnded(){}

    /**
     * @see org.newdawn.slick.InputListener#isAcceptingInput()
     */
    @Override
    public boolean isAcceptingInput() {
        return acceptingInput;
    }

    /**
     * Indicate if we should be accepting input of any sort.
     *
     * @param acceptingInput True if we should accept input
     */
    public void setAcceptingInput(boolean acceptingInput) {
        this.acceptingInput = acceptingInput;
    }

    /**
     * @see org.newdawn.slick.InputListener#keyPressed(int, char)
     */
    @Override
    public void keyPressed(int key, char c){}

    /**
     * @see org.newdawn.slick.InputListener#keyReleased(int, char)
     */
    @Override
    public void keyReleased(int key, char c){}

    /**
     * @see org.newdawn.slick.InputListener#mouseMoved(int, int, int, int)
     */
    @Override
    public void mouseMoved(int oldX, int oldY, int newX, int newY){}

    /**
     * @see org.newdawn.slick.InputListener#mousePressed(int, int, int)
     */
    @Override
    public void mousePressed(int button, int x, int y){}

    /**
     * @see org.newdawn.slick.InputListener#mouseReleased(int, int, int)
     */
    @Override
    public void mouseReleased(int button, int x, int y){}

    /**
     * @see org.newdawn.slick.InputListener#mouseWheelMoved(int)
     */
    @Override
    public void mouseWheelMoved(int change){}

    /**
     * @see org.newdawn.slick.InputListener#setInput(org.newdawn.slick.Input)
     */
    @Override
    public void setInput(Input input){}

    /**
     * @see org.newdawn.slick.InputListener#mouseClicked(int, int, int, int)
     */
    @Override
    public void mouseClicked(int button, int x, int y, int clickCount){}


    /**
     * @see org.newdawn.slick.InputListener#mouseDragged(int, int, int, int)
     */
    @Override
    public void mouseDragged(int oldX, int oldY, int newX, int newY){}

    /**
     * @see org.newdawn.slick.ControlledInputReceiver#inputStarted()
     */
    @Override
    public void inputStarted(){}
}
