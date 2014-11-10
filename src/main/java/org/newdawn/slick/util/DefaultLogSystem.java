package org.newdawn.slick.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.Date;

/**
 * The default implementation that just spits the messages out to stdout
 *
 * @author kevin, Peter Colapietro
 *
 * Updated on 2014-11-10 to use org.slf4j.Logger instead of java.io.PrintStream
 */
public class DefaultLogSystem implements LogSystem {
    /**
     * The Logger for dumping the log out on
     */
    public static final Logger out = LoggerFactory.getLogger(DefaultLogSystem.class);

    /**
     * Log an error
     *
     * @param message The message describing the error
     * @param e       The exception causing the error
     */
    public void error(String message, Throwable e) {
        error(message);
        error(e);
    }

    /**
     * Log an error
     *
     * @param e The exception causing the error
     */
    public void error(Throwable e) {
        out.error(new Date() + " ERROR:" + e.getMessage());
    }

    /**
     * Log an error
     *
     * @param message The message describing the error
     */
    public void error(String message) {
        out.error(new Date() + " ERROR:" + message);
    }

    /**
     * Log a warning
     *
     * @param message The message describing the warning
     */
    public void warn(String message) {
        out.warn(new Date() + " WARN:" + message);
    }

    /**
     * Log an information message
     *
     * @param message The message describing the infomation
     */
    public void info(String message) {
        out.info(new Date() + " INFO:" + message);
    }

    /**
     * Log a debug message
     *
     * @param message The message describing the debug
     */
    public void debug(String message) {
        out.debug(new Date() + " DEBUG:" + message);
    }

    /**
     * Log a warning with an exception that caused it
     *
     * @param message The message describing the warning
     * @param e       The cause of the warning
     */
    public void warn(String message, Throwable e) {
        warn(message);
    }
}
