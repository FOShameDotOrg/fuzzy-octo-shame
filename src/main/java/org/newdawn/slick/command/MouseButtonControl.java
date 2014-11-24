package org.newdawn.slick.command;

/**
 * A control indicating that a mouse button must be pressed or released to cause an command.
 * 
 * @author joverton
 */
class MouseButtonControl implements Control {
    /** The button to be pressed. */
    private final int button;
    
    /**
     * Create a new control that indicates a mouse button to be pressed or released.
     * 
     * @param button The button that should be pressed to cause the command
     */
    public MouseButtonControl(int button) {
        this.button = button;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + button;
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
        if (!(obj instanceof MouseButtonControl)) {
            return false;
        }
        MouseButtonControl other = (MouseButtonControl) obj;
        if (button != other.button) {
            return false;
        }
        return true;
    }

}
