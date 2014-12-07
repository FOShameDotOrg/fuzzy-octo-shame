package org.colapietro.lwjgl.physics;

import com.jed.actor.AbstractEntity;
import org.colapietro.lang.NotImplementedException;

/**
 * Created by Peter Colapietro on 11/22/14.
 *
 * @author Peter Colapietro
 * @since 0.1.8
 */
public interface Collidable {

    /**
     *
     * @param entity to collide down with.
     * @throws NotImplementedException NotImplementedException
     */
    void collideDown(AbstractEntity entity) throws NotImplementedException;

    /**
     *
     * @param entity to collide up with.
     * @throws NotImplementedException NotImplementedException
     */
    void collideUp(AbstractEntity entity) throws NotImplementedException;

    /**
     *
     * @param entity to collide left with.
     * @throws NotImplementedException NotImplementedException
     */
    void collideLeft(AbstractEntity entity) throws NotImplementedException;

    /**
     *
     * @param entity to collide right with.
     * @throws NotImplementedException NotImplementedException
     */
    void collideRight(AbstractEntity entity) throws NotImplementedException;
}
