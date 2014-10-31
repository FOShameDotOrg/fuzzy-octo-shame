package com.jed.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBed {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestBed.class);

    public static void main(String[] args) {
        double value = 3d;
        test(value);
        LOGGER.debug(String.valueOf(value));
    }

    public static void test(Double value) {
        value = 5d;
        LOGGER.debug(String.valueOf(value));//FIXME
    }

}
