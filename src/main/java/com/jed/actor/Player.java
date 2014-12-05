package com.jed.actor;

import com.jed.state.AbstractDisplayableState;
import com.jed.state.GameMap;
import com.jed.state.State;
import com.jed.util.Util;
import com.jed.util.Vector2f;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

/**
 *
 * @author jlinde, Peter Colapietro
 *
 */
public class Player extends AbstractEntity {

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    /**
     *
     */
    private static final float X_MOVEMENT_SCALAR = 0.5f;
    /**
     * 
     */
    private final int height;

    /**
     *
     */
    private final int width;

    /**
     *
     */
    private int xDir;

    /**
     *
     */
    private static final String TEXTURE_PATH = "MEGA_MAN_SH.png";

    /**
     *
     */
    @Nonnull
    private Texture texture;

    /**
     *
     */
    private static final int PLAYER_RIGHT = 1;

    /**
     *
     */
    private static final int PLAYER_LEFT = 0;

    /**
     *
     */
    @Nonnull
    private AbstractPlayerState currentState;

    /**
     *
     */
    @Nonnull
    private final AbstractPlayerState fallingState;

    /**
     *
     */
    @Nonnull
    private final AbstractPlayerState idleState;

    /**
     *
     */
    @Nonnull
    private final AbstractPlayerState walkingState;

    /**
     *
     */
    @Nonnull
    private final AbstractPlayerState jumpingState;

    /**
     * Indicates the player is currently colliding with a map tile below it.
     */
    private boolean collideDown = false;

    /**
     * TODO: Friction should come from the individual map tiles or from the tileset.
     */
    private static final float FRICTION = .046875f;

    /**
     *
     */
    private int jumpCount = 0;

    /**
     *
     */
    private final GameMap map;

    /**
     *
     */
    private boolean isMovingLeft;

    /**
     *
     */
    private boolean isMovingRight;

    /**
     *
     */
    private boolean isJumping;

    /**
     *
     * TODO: The Bounds should be scaled to the size of the player sprite so that it can be scaled.
     * 
     * @param position position vector
     * @param height height
     * @param width width
     * @param map game map
     */
    public Player(Vector2f position, int height, int width, GameMap map) {
        super(
                position,
                new Vector2f(0, 0),
                new PolygonBoundary(
                        new Vector2f(110, 130),
                        new Vector2f[]{
                                new Vector2f(0, 0),
                                new Vector2f(40, 0),
                                new Vector2f(40, 120),
                                new Vector2f(0, 120)
                        })
        );

        this.acceleration = FRICTION;
        this.height = height;
        this.width = width;
        this.map = map;
        this.texture = Util.loadTexture(TEXTURE_PATH);

        this.fallingState = new Falling();
        this.idleState = new Idle();
        this.walkingState = new Walking();
        this.jumpingState = new Jumping();
    }

    /**
     * @param state state to change current player to.
     */
    public void changeState(State state) {
        currentState = (AbstractPlayerState) state;
        currentState.entered();
    }

    @Override
    public void entered() {
        changeState(fallingState);
    }

    @Override
    public void update() {
        currentState.handleInput();

        if (!currentState.falling && !collideDown) {
            changeState(fallingState);
        }
        collideDown = false;

        if (currentState.falling) {
            movement.y += map.getGravity();
        }

        position.x = this.position.x + movement.x;
        position.y = position.y + movement.y;

        currentState.update();
    }

    @Override
    public void render() {
        currentState.render();
        bounds.render();
    }

    /**
     *
     * TODO: Name this something else or refactor.
     * indicates player has just landed on a tile and to stop "Falling" (changes animation).
     *
     * @param sEntity
     */
    @Override
    public void collideDown(AbstractEntity sEntity) {
        collideDown = true;
        if (currentState.falling) {
            changeState(idleState);
        }
    }

    /**
     *
     * @author jlinde, Peter Colapietro
     *
     */
    private abstract class AbstractPlayerState extends AbstractDisplayableState {

        /**
         *
         */
        boolean falling;

        /**
         *
         */
        public AbstractPlayerState() {
            falling = false;
        }

        /**
         *
         */
        public abstract void handleInput();
    }

    /**
     *
     */
    private abstract class AbstractNonEnterablePlayerState extends AbstractPlayerState {

        /**
         *
         */
        final Logger LOGGER = LoggerFactory.getLogger(AbstractNonEnterablePlayerState.class);

        @Override
        public void entered() {
            LOGGER.debug("com.jed.actor.Player.AbstractNonEnterablePlayerState#entered");
        }
    }

    /**
     *
     * @author jlinde, Peter Colapietro
     *
     */
    private class Falling extends AbstractNonEnterablePlayerState {

        /**
         *
         */
        private float bottomLeftX;

        /**
         *
         */
        private float bottomRightX;

        /**
         *
         */
        private float topRightX;

        /**
         *
         */
        private float topLeftX;

        /**
         *
         */
        public Falling() {
            this.falling = true;
        }

        @Override
        public void update() {
            //Player Landed on something
            if (movement.y == 0) {
                if (movement.x != 0) {
                    changeState(walkingState);
                } else {
                    changeState(idleState);
                }
            }
        }

        @Override
        public void handleInput() {
            keyHoldEvent();
        }

        @Override
        public void render() {
            Color.white.bind();
            texture.bind();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glBegin(GL11.GL_QUADS);

            if (xDir == PLAYER_LEFT) {
                bottomLeftX  = position.x + width;
                bottomRightX = position.x;
                topLeftX     = position.x;
                topRightX    = position.x + width;
            } else {
                bottomLeftX  = position.x;
                bottomRightX = position.x + width;
                topLeftX     = position.x + width;
                topRightX    = position.x;
            }

            GL11.glTexCoord2f(.25f, .5f);
            map.drawChildVertex2f(bottomLeftX, position.y);
            GL11.glTexCoord2f(.3125f, .5f);
            map.drawChildVertex2f(bottomRightX, position.y);
            GL11.glTexCoord2f(.3125f, 1);
            map.drawChildVertex2f(topLeftX, position.y + height);
            GL11.glTexCoord2f(.25f, 1);
            map.drawChildVertex2f(topRightX, position.y + height);
            GL11.glEnd();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }

    }

    /**
     *
     * @author jlinde, Peter Colapietro
     *
     */
    private final class Jumping extends Falling {

        /**
         *
         */
        final float[] animation = {.0625f, .125f, .1875f, .25f, .3125f, .375f, .4375f};

        /**
         *
         */
        final float frameWidth = .0625f;

        /**
         *
         */
        int frame, ticks;

        /**
         *
         */
        public Jumping() {
            this.falling = true;
        }

        @Override
        public void entered() {
            frame = 0;
            ticks = 0;
        }

        @Override
        public void update() {
            ticks++;
            if (ticks % 16 == 0) {
                frame = frame == animation.length - 1 ? frame : frame + 1;
            }

            super.update();
        }

        @Override
        public void render() {
            Color.white.bind();
            texture.bind();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glBegin(GL11.GL_QUADS);
            if (xDir != PLAYER_LEFT) {
                GL11.glTexCoord2f(animation[frame] - frameWidth, .5f);
                map.drawChildVertex2f(position.x, position.y);
                GL11.glTexCoord2f(animation[frame], .5f);
                map.drawChildVertex2f(position.x + width, position.y);
                GL11.glTexCoord2f(animation[frame], 1);
                map.drawChildVertex2f(position.x + width, position.y + height);
                GL11.glTexCoord2f(animation[frame] - frameWidth, 1);
                map.drawChildVertex2f(position.x, position.y + height);
            } else {
                GL11.glTexCoord2f(animation[frame] - frameWidth, .5f);
                map.drawChildVertex2f(position.x + width, position.y);
                GL11.glTexCoord2f(animation[frame], .5f);
                map.drawChildVertex2f(position.x, position.y);
                GL11.glTexCoord2f(animation[frame], 1);
                map.drawChildVertex2f(position.x, position.y + height);
                GL11.glTexCoord2f(animation[frame] - frameWidth, 1);
                map.drawChildVertex2f(position.x + width, position.y + height);
            }
            GL11.glEnd();
            GL11.glDisable(GL11.GL_TEXTURE_2D);

        }

    }

    /**
     *
     * @author jlinde, Peter Colapietro
     *
     */
    private final class Idle extends AbstractNonEnterablePlayerState {

        @Override
        public void update() {
            if (movement.y != 0) {
                changeState(fallingState);
            } else if (movement.x != 0) {
                changeState(walkingState);
            }
        }

        @Override
        public void handleInput() {
            keyHoldEvent();
        }

        @Override
        public void render() {
            Color.white.bind();
            texture.bind();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glBegin(GL11.GL_QUADS);

            if (xDir == PLAYER_LEFT) {
                GL11.glTexCoord2f(0, 0);
                map.drawChildVertex2f(position.x + width, position.y);
                GL11.glTexCoord2f(.0625f, 0);
                map.drawChildVertex2f(position.x, position.y);
                GL11.glTexCoord2f(.0625f, .5f);
                map.drawChildVertex2f(position.x, position.y + height);
                GL11.glTexCoord2f(0, .5f);
                map.drawChildVertex2f(position.x + width, position.y + height);
            } else {
                GL11.glTexCoord2f(0, 0);
                map.drawChildVertex2f(position.x, position.y);
                GL11.glTexCoord2f(.0625f, 0);
                map.drawChildVertex2f(position.x + width, position.y);
                GL11.glTexCoord2f(.0625f, .5f);
                map.drawChildVertex2f(position.x + width, position.y + height);
                GL11.glTexCoord2f(0, .5f);
                map.drawChildVertex2f(position.x, position.y + height);
            }
            GL11.glEnd();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }

    }

    /**
     *
     * @author jlinde, Peter Colapietro
     *
     */
    private final class Walking extends AbstractPlayerState {

        /**
         *
         */
        final float[] animation = {.125f, .1875f, .25f, .3125f, .375f, .4375f, .5f, .5625f, .625f, .6875f, .75f};

        /**
         *
         */
        final float frameWidth = .0625f;

        /**
         *
         */
        int frame, ticks;

        @Override
        public void entered() {
            frame = 0;
            ticks = 0;
        }

        @Override
        public void handleInput() {
            keyHoldEvent();
        }

        @Override
        public void update() {
            ticks++;
            if (ticks % 8 == 0) {
                frame = frame == animation.length - 1 ? 1 : frame + 1;
            }

            if (movement.x == 0) {
                changeState(idleState);
            }
        }

        @Override
        public void render() {
            Color.white.bind();
            texture.bind();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glBegin(GL11.GL_QUADS);
            if (movement.x > 0) {
                GL11.glTexCoord2f(animation[frame] - frameWidth, 0);
                map.drawChildVertex2f(position.x, position.y);
                GL11.glTexCoord2f(animation[frame], 0);
                map.drawChildVertex2f(position.x + width, position.y);
                GL11.glTexCoord2f(animation[frame], .5f);
                map.drawChildVertex2f(position.x + width, position.y + height);
                GL11.glTexCoord2f(animation[frame] - frameWidth, .5f);
                map.drawChildVertex2f(position.x, position.y + height);
            } else {
                GL11.glTexCoord2f(animation[frame] - frameWidth, 0);
                map.drawChildVertex2f(position.x + width, position.y);
                GL11.glTexCoord2f(animation[frame], 0);
                map.drawChildVertex2f(position.x, position.y);
                GL11.glTexCoord2f(animation[frame], .5f);
                map.drawChildVertex2f(position.x, position.y + height);
                GL11.glTexCoord2f(animation[frame] - frameWidth, .5f);
                map.drawChildVertex2f(position.x + width, position.y + height);
            }
            GL11.glEnd();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }
    }

    @Override
    public void drawChildVertex2f(float x, float y) {
        map.drawChildVertex2f(position.x + x, position.y + y);
    }

    /**
     *
     */
    private void jump() {
            boolean isJumpCountLessThanTwo = jumpCount < 2;
            int heightOffsetWithYPosition = Math.round(position.y) + height; //TODO Test me.
            if (isJumpCountLessThanTwo || heightOffsetWithYPosition == map.getHeight() * map.getTileHeight()) {
                movement.y = -8;
                jumpCount++;
                changeState(jumpingState);
            }
        isJumping = false;
    }

    /**
     *
     */
    private void moveRight() {
        LOGGER.info("moveRight");
        if (Float.compare(movement.x, 0) < 0) {
            movement.x += X_MOVEMENT_SCALAR;
        } else {
            movement.x += acceleration;
            xDir = PLAYER_RIGHT;
        }
    }

    /**
     *
     */
    private void moveLeft() {
        LOGGER.info("moveLeft");
        if (Float.compare(movement.x, 0) > 0) {
            movement.x -= X_MOVEMENT_SCALAR;
        } else {
            movement.x -= acceleration;
            xDir = PLAYER_LEFT;
        }
    }

    /**
     * Key Hold Events (walking etc).
     *
     * Constant key "hold" events
     */
    private void keyHoldEvent() {
        if(isJumping) {
            jump();
        }
        if(isMovingLeft) {
            moveLeft();
        } else if(isMovingRight) {
            moveRight();
        } else if (Float.compare(movement.x, 0) != 0) {
            movement.x = movement.x - Math.min(Math.abs(movement.x), FRICTION) * Math.signum(movement.x);
        }
        if (!isJumping && !currentState.falling) {
            jumpCount = 0;
        }
    }

    /**
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @param isMovingLeft isMovingLeft
     */
    public void setMovingLeft(boolean isMovingLeft) {
        this.isMovingLeft = isMovingLeft;
    }

    /**
     *
     * @param isMovingRight isMovingRight
     */
    public void setMovingRight(boolean isMovingRight) {
        this.isMovingRight = isMovingRight;
    }

    /**
     *
     * @param isJumping isJumping
     */
    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }
}
