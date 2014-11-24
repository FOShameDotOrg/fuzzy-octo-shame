package org.colapietro.slick;

import org.colapietro.lang.NotImplementedException;
import org.colapietro.lwjgl.controllers.Xbox360ControllerButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Peter Colapietro on 11/23/14.
 *
 * @author Peter Colapietro
 * @since 0.1.8
 */
public final class LoggableInputListener extends AbstractInputListener {

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggableInputListener.class);

    @Override
    public void inputStarted() {
        LOGGER.debug("org.colapietro.slick.LoggableInputListener#inputStarted");
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        LOGGER.debug("org.colapietro.slick.LoggableInputListener#mouseMoved");
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) throws NotImplementedException {
        LOGGER.debug("org.colapietro.slick.LoggableInputListener#mouseClicked");
    }


    @Override
    public void mousePressed(int button, int x, int y) {
        LOGGER.debug("org.colapietro.slick.LoggableInputListener#mousePressed");
    }

    @Override
    public void mouseReleased(int button, int x, int y) throws NotImplementedException {
        LOGGER.debug("org.colapietro.slick.LoggableInputListener#mouseReleased");
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) throws NotImplementedException {
        LOGGER.debug("org.colapietro.slick.LoggableInputListener#mouseDragged");
    }

    @Override
    public void keyPressed(int key, char c) {
        LOGGER.debug("org.colapietro.slick.LoggableInputListener#keyPressed");
        LOGGER.info("keyPressed:[key: {} ,character:  {}]",key, c);
    }

    @Override
    public void keyReleased(int key, char c) {
        LOGGER.debug("org.colapietro.slick.LoggableInputListener#keyReleased");
    }

    @Override
    public void controllerButtonPressed(int controller, int button) {
        super.controllerButtonPressed(controller, button);
        LOGGER.debug("org.colapietro.slick.LoggableInputListener#controllerButtonPressed");
        LOGGER.info("controllerButtonPressed[controller: {}, button: {}]",
                controller,
                Xbox360ControllerButton.valueOf(button, true));
    }

    @Override
    public void inputEnded() {
        LOGGER.debug("org.colapietro.slick.LoggableInputListener#inputEnded");
    }
}
