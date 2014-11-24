package org.colapietro.lwjgl.physics;

import com.jed.actor.AbstractEntity;

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
     */
    void collideDown(AbstractEntity entity);

    /**
     *
     * @param entity to collide up with.
     */
    void collideUp(AbstractEntity entity);

    /**
     *
     * @param entity to collide left with.
     */
    void collideLeft(AbstractEntity entity);

    /**
     *
     * @param entity to collide right with.
     */
    void collideRight(AbstractEntity entity);
}
