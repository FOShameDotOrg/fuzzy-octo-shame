package com.jed.util;

/**
 * Created by Peter Colapietro on 11/23/14.
 *
 * @author Peter Colapietro
 * @since 0.1.8
 */
public enum ExitStatusCode {

    /**
     *
     */
    NORMAL(0),
    /**
     *
     */
    ERROR(1),
    /**
     *
     */
    TERMINATED_BY_A_SIGNAL(128),
    /**
     * The SIGKILL signal is sent to a process to cause it to terminate immediately (kill). In contrast to SIGTERM and SIGINT, this signal cannot be caught or ignored, and the receiving process cannot perform any clean-up upon receiving this signal.
     */
    SIGKILL(9);

    /**
     *
     */
    private final int statusCode;

    /**
     *
     * @param statusCode statusCode
     */
    private ExitStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     *
     * @return statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }
}
