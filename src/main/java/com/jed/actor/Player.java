package com.jed.actor;

import com.jed.state.*;
import com.jed.util.Vector3f;
import org.colapietro.lang.NotImplementedException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.opengl.Texture;

import com.jed.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class Player extends AbstractEntity implements StateManager, InputProviderListener {

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    /**
     *
     */
    public static final double X_MOVEMENT_SCALAR = 0.5d;
    /**
     * 
     */
    public final int height;

    /**
     *
     */
    public final int width;
    
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
    @Nullable
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
    //Player States
    private AbstractPlayerState currentState;
    
    /**
     * 
     */
    private AbstractPlayerState fallingState;
    
    /**
     * 
     */
    private AbstractPlayerState idleState;
    
    /**
     * 
     */
    private AbstractPlayerState walkingState;
    
    /**
     * 
     */
    private AbstractPlayerState jumpingState;

    /**
     * 
     */
    //Indicates the player is currently colliding with a map tile below it
    private boolean collideDown = false;

    /**
     * 
     */
    //TODO: Friction should come from the individual map tiles or from the tileset
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
     * @param position position vector
     * @param height height
     * @param width width
     * @param map game map
     */
    public Player(Vector3f position, int height, int width, GameMap map) {
        //TODO: The Bounds should be scaled to the size of the player sprite so that
        //it can be scaled
        super(
                position,
                new Vector3f(0, 0),
                new PolygonBoundary(
                        new Vector3f(110, 130),
                        new Vector3f[]{
                                new Vector3f(0, 0),
                                new Vector3f(40, 0),
                                new Vector3f(40, 120),
                                new Vector3f(0, 120)
                        })
        );

        this.acceleration = .046875f;
        this.height = height;
        this.width = width;
        this.map = map;
        entered(); // FIXME See: http://stackoverflow.com/a/3404369
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

        texture = Util.loadTexture(TEXTURE_PATH);

        fallingState = new Falling();
        idleState = new Idle();
        walkingState = new Walking();
        jumpingState = new Jumping();

        changeState(fallingState);
    }

    @Override
    public void update() {
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

    //TODO: Name this something else or refactor... indicates player has just landed on a tile
    //and to stop "Falling" (changes animation)
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


    @Override
    public void controlPressed(Command command) {
        LOGGER.debug("Pressed ",command.toString());
        LOGGER.info("Command {}",command.toString());
        if (command.equals(new BasicCommand("jump"))) {
            boolean isJumpCountLessThanTwo = jumpCount < 2;
            int heightOffsetWithYPosition = Math.round(position.y) + height;
            if (isJumpCountLessThanTwo || heightOffsetWithYPosition == map.getHeight() * map.getTileHeight()) {
                final int fallSpeedScalar = -8;
                movement.y = fallSpeedScalar;
                jumpCount++;
                changeState(jumpingState);
            }
        }

        if(command.equals(new BasicCommand("moveRight"))) {
            if (movement.x < 0) {
                movement.x += X_MOVEMENT_SCALAR;
            } else {
                movement.x += acceleration;
                xDir = PLAYER_RIGHT;
            }
        } else if(command.equals(new BasicCommand("moveLeft"))) {
            if (movement.x > 0) {
                movement.x -= X_MOVEMENT_SCALAR;
            } else {
                movement.x -= acceleration;
                xDir = PLAYER_LEFT;
            }
        } else if (movement.x != 0) {
            movement.x = movement.x - Math.min(Math.abs(movement.x), FRICTION) * Math.signum(movement.x);
        }

        if (!command.equals(new BasicCommand("jump")) && !currentState.falling) {
            jumpCount = 0;
        }
    }

    @Override
    public void controlReleased(Command command) {
        LOGGER.debug("Released ",command.toString());
        LOGGER.info("Command {}",command.toString());
    }

}
