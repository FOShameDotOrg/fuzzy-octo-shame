package org.colapietro.lwjgl.controllers;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter Colapietro on 11/23/14.
 *
 * @author Peter Colapietro
 * @since 0.1.8
 */
public enum Xbox360ControllerButton {

    /**
     *
     */
    DPAD_UP(0),
    /**
     *
     */
    DPAD_DOWN(1),
    /**
     *
     */
    DPAD_LEFT(2),
    /**
     *
     */
    DPAD_RIGHT(3),
    /**
     *
     */
    START(4),
    /**
     *
     */
    BACK(5),
    /**
     *
     */
    LEFT_ANALOG_STICK(6),
    /**
     *
     */
    RIGHT_ANALOG_STICK(7),
    /**
     *
     */
    LEFT_BUMPER(8),
    /**
     *
     */
    RIGHT_BUMPER(9),
    /**
     *
     */
    GUIDE(10),
    /**
     *
     */
    A(11),
    /**
     *
     */
    B(12),
    /**
     *
     */
    X(13),
    /**
     *
     */
    Y(14);

    /**
     *
     */
    private int controlIndex;

    /**
     *
     */
    private static final Map<Integer, Xbox360ControllerButton> MAP = new HashMap<>();

    /**
     *
     */
    private static final Map<Xbox360ControllerButton, Integer> REVERSE_MAP =
            Collections.synchronizedMap(new EnumMap<>(Xbox360ControllerButton.class));


    static {
        for (Xbox360ControllerButton controllerEvenIndexEnum : Xbox360ControllerButton.values()) {
            MAP.put(
                    controllerEvenIndexEnum.getControlIndex(),
                    controllerEvenIndexEnum
            );
            REVERSE_MAP.put(
                    controllerEvenIndexEnum,
                    controllerEvenIndexEnum.getControlIndex()
            );
        }
    }

    /**
     *
     * @param controlIndex controlIndex
     */
    private Xbox360ControllerButton(int controlIndex) {
        this.controlIndex = controlIndex;
    }

    /**
     *
     * @return controlIndex
     */
    private int getControlIndex() {
        return controlIndex;
    }

    /**
     *
     * @param controlIndex controlIndex
     * @return Xbox360ControllerButton
     */
    public static Xbox360ControllerButton valueOf(int controlIndex) {
        return MAP.get(controlIndex);
    }

    /**
     *
     * @param controlIndex controlIndex
     * @param isOffByOne isOffByOne
     * @return Xbox360ControllerButton
     */
    public static Xbox360ControllerButton valueOf(int controlIndex, boolean isOffByOne) {
        if(isOffByOne) {
            return MAP.get(controlIndex - 1);
        } else {
            return valueOf(controlIndex);
        }
    }

    /**
     *
     * @param xbox360ControllerButton xbox360ControllerButton
     * @return controlIndex
     */
    public static int valueOf(Xbox360ControllerButton xbox360ControllerButton) {
        return REVERSE_MAP.get(xbox360ControllerButton);
    }

    /**
     *
     * @param xbox360ControllerButton xbox360ControllerButton
     * @param isOffByOne isOffByOne
     * @return controlIndex
     */
    public static int valueOf(Xbox360ControllerButton xbox360ControllerButton, boolean isOffByOne) {
        if(isOffByOne) {
            return REVERSE_MAP.get(xbox360ControllerButton) + 1;
        } else {
            return valueOf(xbox360ControllerButton);
        }
    }

}
