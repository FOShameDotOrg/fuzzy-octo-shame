package org.colapietro.slick;

import org.newdawn.slick.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Peter Colapietro on 11/23/14.
 * @author Peter Colapietro
 * @since 0.1.8
 */
public final class LoggableInputProviderListener extends AbstractInputProviderListener {

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggableInputProviderListener.class);

    @Override
    public void controlPressed(Command command) {
        LOGGER.debug("Pressed ",command.toString());
        LOGGER.info("Command {}",command.toString());
    }

    @Override
    public void controlReleased(Command command) {
        LOGGER.debug("Released ",command.toString());
    }
}
