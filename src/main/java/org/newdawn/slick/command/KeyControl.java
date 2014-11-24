package org.newdawn.slick.command;

/**
 * A control relating to a command indicate that it should be fired when a specific key is pressed
 * or released.
 * 
 * @author joverton
 */
class KeyControl implements Control {
    /** The key code that needs to be pressed. */
    private final int keycode;
    
    /**
     * Create a new control that caused an command to be fired on a key pressed/released.
     * 
     * @param keycode The code of the key that causes the command
     */
    public KeyControl(int keycode) {     
        this.keycode = keycode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + keycode;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof KeyControl)) {
            return false;
        }
        KeyControl other = (KeyControl) obj;
        if (keycode != other.keycode) {
            return false;
        }
        return true;
    }
}
