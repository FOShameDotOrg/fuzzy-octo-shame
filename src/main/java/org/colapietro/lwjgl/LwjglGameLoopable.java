package org.colapietro.lwjgl;

/**
 *
 * @author Peter Colapietro
 * @since 0.1.7
 *
 * Created by Peter Colapietro on 11/22/14.
 *
 */
public interface LwjglGameLoopable {

    /**
     *
     */
    void initialize();

    /**
     *
     */
    void processInput();

    /**
     *
     */
    void update();

    /**
     *
     */
    void render();

}
