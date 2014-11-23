package com.jed.util;

/**
 * Created by Peter Colapietro on 11/23/14.
 *
 * @author Peter Colapietro
 * @since 0.1.8
 */
public enum StatusCode {

    /**
     *
     */
    NORMAL(0),
    /**
     *
     */
    ERROR(1);

    /**
     *
     */
    private final int statusCode;

    /**
     *
     * @param statusCode statusCode
     */
    private StatusCode(int statusCode) {
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
