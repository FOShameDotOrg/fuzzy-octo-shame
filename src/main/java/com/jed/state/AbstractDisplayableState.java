package com.jed.state;

import com.jed.core.Displayable;
import org.colapietro.lang.LangConstants;
import org.colapietro.lang.NotImplementedException;

/**
 * Created by Peter Colapietro on 11/22/14.
 *
 * @author Peter Colapietro
 * @since 0.1.8
 */
public abstract class AbstractDisplayableState extends AbstractState implements Displayable {

    @Override
    public void render() throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void drawChildVertex2f(float x, float y) throws NotImplementedException  {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }
}
