package org.colapietro.slick;

import org.colapietro.lang.LangConstants;
import org.colapietro.lang.NotImplementedException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;

/**
 * Created by Peter Colapietro on 11/23/14.
 * @author Peter Colapietro
 * @since 0.1.8
 */
public abstract class AbstractInputProviderListener implements InputProviderListener {

    @Override
    public void controlPressed(Command command) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);

    }

    @Override
    public void controlReleased(Command command) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }
}
