package org.colapietro.lwjgl.controllers;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter Colapietro on 11/23/14.
 * @author Peter Colapietro
 * @since 0.1.8
 */
public enum ControllerEvenIndex {

    /** Indicates the event was caused by a button */
    BUTTON(1),
    /** Indicates the event was caused by a axis */
    AXIS(2),
    /** Indicates the event was caused by a pov X */
    POVX(3),
    /** Indicates the event was caused by a pov Y */
    POVY(4);

    /**
     *
     */
    private static final Map<ControllerEvenIndex, Integer> CONTROLLER_EVEN_INDEX_TO_INTEGER_MAP = Collections.synchronizedMap(new EnumMap<>(ControllerEvenIndex.class));

    /**
     *
     */
    private static final Map<Integer, ControllerEvenIndex> INTEGER_TO_CONTROLLER_EVEN_INDEX_MAP = new HashMap<>();

    static {
        for (ControllerEvenIndex controllerEvenIndexEnum : ControllerEvenIndex.values()) {
            CONTROLLER_EVEN_INDEX_TO_INTEGER_MAP.put(controllerEvenIndexEnum, controllerEvenIndexEnum.getEventIndex());
            INTEGER_TO_CONTROLLER_EVEN_INDEX_MAP.put(controllerEvenIndexEnum.getEventIndex(), controllerEvenIndexEnum);
        }
    }

    /**
     *
     */
    private final int eventIndex;

    /**
     *
     * @param i index of event
     */
    ControllerEvenIndex(int i) {
        eventIndex = i;
    }

    /**
     *
     * @return eventIndex
     */
    public int getEventIndex() {
        return eventIndex;
    }

    /**
     *
     * @param eventIndex eventIndex
     * @return ControllerEvenIndex
     */
    public static ControllerEvenIndex valueOf(int eventIndex) {
        return INTEGER_TO_CONTROLLER_EVEN_INDEX_MAP.get(eventIndex);
    }
}
