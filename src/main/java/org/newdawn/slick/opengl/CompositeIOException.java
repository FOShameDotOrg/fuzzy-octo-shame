package org.newdawn.slick.opengl;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A collection of IOException that failed image data loading
 * 
 * @author kevin
 */
class CompositeIOException extends IOException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** The list of exceptions causing this one */
    @Nonnull
    private final List<Exception> exceptions = new ArrayList<>();

    /**
     * Create a new composite IO Exception
     */
    public CompositeIOException() {
        super();
    }

    /**
     * Add an exception that caused this exceptino
     *
     * @param e The exception
     */
    public void addException(Exception e) {
        exceptions.add(e);
    }

    /**
     * @see java.lang.Throwable#getMessage()
     */
    @Nonnull
    public String getMessage() {
        String msg = "Composite Exception: \n";
        for (Exception exception : exceptions) {
            msg += "\t" + exception.getMessage() + "\n";
        }

        return msg;
    }
}
