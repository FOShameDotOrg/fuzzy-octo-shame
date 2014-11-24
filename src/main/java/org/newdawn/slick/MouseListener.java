package org.newdawn.slick;

/**
 * Description of classes that respond to mouse related input events.
 * 
 * @author kevin
 */
public interface MouseListener extends ControlledInputReceiver {

    /**
     * Notification that the mouse wheel position was updated.
     *
     * @param change The amount of the wheel has moved
     */
    public abstract void mouseWheelMoved(int change);

    /**
     * Notification that a mouse button was clicked. Due to double click
     * handling the single click may be delayed slightly. For absolute notification
     * of single clicks use mousePressed().
     *
     * To be absolute this method should only be used when considering double clicks
     *
     * @param button The index of the button (starting at 0)
     * @param x The x position of the mouse when the button was pressed
     * @param y The y position of the mouse when the button was pressed
     * @param clickCount The number of times the button was clicked
     */
    public abstract void mouseClicked(int button, int x, int y, int clickCount);

    /**
     * Notification that a mouse button was pressed.
     *
     * @param button The index of the button (starting at 0)
     * @param x The x position of the mouse when the button was pressed
     * @param y The y position of the mouse when the button was pressed
     */
    public abstract void mousePressed(int button, int x, int y);

    /**
     * Notification that a mouse button was released.
     *
     * @param button The index of the button (starting at 0)
     * @param x The x position of the mouse when the button was released
     * @param y The y position of the mouse when the button was released
     */
    public abstract void mouseReleased(int button, int x, int y);

    /**
     * Notification that mouse cursor was moved.
     *
     * @param oldX The old x position of the mouse
     * @param oldY The old y position of the mouse
     * @param newX The new x position of the mouse
     * @param newY The new y position of the mouse
     */
    public abstract void mouseMoved(int oldX, int oldY, int newX, int newY);

    /**
     * Notification that mouse cursor was dragged.
     *
     * @param oldX The old x position of the mouse
     * @param oldY The old y position of the mouse
     * @param newX The new x position of the mouse
     * @param newY The new y position of the mouse
     */
    public abstract void mouseDragged(int oldX, int oldY, int newX, int newY);

}