package org.colapietro.lwjgl.controllers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter Colapietro on 11/23/14.
 *
 * @author Peter Colapietro
 * @since 0.1.8
 */
public enum ButtonState {
    /**
     *
     */
    PRESSED(true),
    /**
     *
     */
    RELEASED(false);
    /**
     *
     */
    private final boolean isPressed;

    /**
     *
     */
    private static final Map<Boolean, ButtonState> MAP = new HashMap<>();

    static {
        for (ButtonState controllerEvenIndexEnum : ButtonState.values()) {
            MAP.put(
                    controllerEvenIndexEnum.isPressed(), controllerEvenIndexEnum
            );
        }
    }

    /**
     *
     * @param isPressed isPressed
     */
    private ButtonState(boolean isPressed) {
        this.isPressed = isPressed;
    }

    /**
     *
     * @return isPressed
     */
    private boolean isPressed() {
        return isPressed;
    }

    /**
     *
     * @param isPressed isPressed
     * @return ButtonState
     */
    public static ButtonState valueOf(boolean isPressed) {
        return MAP.get(isPressed);
    }
}
