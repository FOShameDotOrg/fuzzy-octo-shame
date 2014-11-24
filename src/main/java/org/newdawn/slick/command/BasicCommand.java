package org.newdawn.slick.command;

/**
 * A simple named command
 * 
 * @author kevin
 */
class BasicCommand implements Command {
    /** The name of the command */
    private final String name;

    /**
     * Create a new basic command
     *
     * @param name The name to give this command
     */
    public BasicCommand(String name) {
        this.name = name;
    }

    /**
     * Get the name given for this basic command
     *
     * @return The name given for this basic command
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[Command="+name+"]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BasicCommand)) {
            return false;
        }
        BasicCommand other = (BasicCommand) obj;
        if (name == null) { 
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
    
    
}
