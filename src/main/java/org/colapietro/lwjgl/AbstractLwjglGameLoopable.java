package org.colapietro.lwjgl;

import static org.colapietro.lang.LangConstants.*;
import org.colapietro.lang.NotImplementedException;

/**
 * Created by Peter Colapietro on 11/22/14.
 *
 * @author Peter Colapietro
 * @since 0.1.7
 */
public abstract class AbstractLwjglGameLoopable implements LwjglGameLoopable {

    @Override
    public void initialize() throws NotImplementedException { throw new NotImplementedException(NOT_IMPLEMENTED_YET_MESSAGE); }

    @Override
    public void processInput() throws NotImplementedException { throw new NotImplementedException(NOT_IMPLEMENTED_YET_MESSAGE); }

    @Override
    public void update() throws NotImplementedException { throw new NotImplementedException(NOT_IMPLEMENTED_YET_MESSAGE); }

    @Override
    public void render() throws NotImplementedException { throw new NotImplementedException(NOT_IMPLEMENTED_YET_MESSAGE); }
}
