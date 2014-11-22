package com.jed.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.jed.actor.AbstractEntity;
import com.jed.core.MotherBrainConstants;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.jed.actor.Player;
import com.jed.core.Collision;
import com.jed.core.QuadTree;
import com.jed.util.Rectangle;
import com.jed.util.Util;
import com.jed.util.Vector;


/**
 * 
 * @author jlinde, Peter Colapietro
 *
 * TODO Decouple from com.jed.core.MotherBrain / com.jed.core.MotherBrainConstants
 *
 */
public class GameMap implements State {

    /**
     * 
     */
    public int width, height, tileWidth, tileHeight;
    
    /**
     * 
     */
    public float glTexX, glTexY;
    
    /**
     * 
     */
    private String tileSetPath;

    /**
     * 
     */
    //TODO: this should be set when the map loads...
    public Vector position = new Vector(0, 0);

    /**
     * 
     */
    public MapTile[] tiles;

    /**
     * 
     */
    private Texture texture;

    /**
     * 
     */
    private Player player;
    
    /**
     * 
     */
    private Stack<AbstractEntity> scene;

    /**
     * 
     */
    private QuadTree quadTree;

    /**
     * 
     */
    public float gravity = 0.21875f;

    @Override
    public void entered() {
        texture = Util.loadTexture(tileSetPath);

        //TODO: initialize scene Stack by some data contained in the map i.e. start position or something like that...
        scene = new Stack<AbstractEntity>();
        player = new Player(new Vector(50, 200), 256, 256, this);
        scene.push(player);

        quadTree = new QuadTree(
                new Vector(0, 0), 0,
                new Rectangle(
                        width * tileWidth,
                        height * tileHeight),
                this);

        //TODO: the quad tree should come pre-populated with all map tiles
        // and should only add and remove map entities...

        //Draw the map once prior to the first update to ensure the quad tree
        //is populated with map tiles on the first frame
        drawMap();
    }

    @Override
    public void leaving() {
    }

    /**
     * 
     */
    public void keyPress() {
        player.keyPressEvent();
    }

    @Override
    public void update() {
        for (MapTile each : tiles) {
            each.colliding = false;
            each.evaluating = false;
        }

        for (AbstractEntity each : scene) {
            quadTree.insert(each);
        }

        detectCollisions();

        for (AbstractEntity each : scene) {
            each.update();
        }

        scrollMap();
    }

    /**
     * 
     */
    private void scrollMap() {
        if (player.movement.y > 0) {
            if ((player.position.y + (player.height / 2) - position.y) > MotherBrainConstants.HEIGHT / 2) {
                if (position.y + player.movement.y > height * tileHeight - MotherBrainConstants.HEIGHT) {
                    position.y = height * tileHeight - MotherBrainConstants.HEIGHT;
                } else {
                    position.y += player.movement.y;
                }
            }
        } else if (player.movement.y < 0) {
            if ((player.position.y + (player.height / 2) - position.y) < MotherBrainConstants.HEIGHT / 2) {
                if (player.movement.y + position.y < 0) {
                    position.y = 0;
                } else {
                    position.y += player.movement.y;
                }
            }
        }

        if (player.movement.x > 0) {
            if ((player.position.x + (player.width / 2) - position.x) > MotherBrainConstants.WIDTH / 2) {
                if (position.x + player.movement.x > width * tileWidth - MotherBrainConstants.WIDTH) {
                    position.x = width * tileWidth - MotherBrainConstants.WIDTH;
                } else {
                    position.x += player.movement.x;
                }
            }
        } else if (player.movement.x < 0) {
            if ((player.position.x + (player.width / 2) - position.x) < MotherBrainConstants.WIDTH / 2) {
                if (player.movement.x + position.x < 0) {
                    position.x = 0;
                } else {
                    position.x += player.movement.x;
                }
            }
        }
    }

    /**
     * 
     */
    private void detectCollisions() {

        for (int i = 0; i < scene.size(); i++) {
            List<AbstractEntity> returnObjects = new ArrayList<AbstractEntity>();
            AbstractEntity entity = scene.get(i);

            quadTree.retrieve(returnObjects, entity);

            List<Collision> collisions = new ArrayList<Collision>();

            for (int j = 0; j < returnObjects.size(); j++) {
                if (!returnObjects.get(j).equals(scene.get(i))) {
                    AbstractEntity sEntity = returnObjects.get(j);

                    Collision collision = new Collision(entity, sEntity);

                    //Detect all collisions that might occur this frame
                    if (collision.detectCollision()) {
                        collisions.add(collision);
                    }
                }
            }

            //Sort Collisions, resolve soonest depending on type in following order:
            //    OVERLAPS
            //    SWEPT Y
            //    SWEPT X
            while (collisions.size() > 0) {
                Collections.sort(collisions);
                collisions.get(0).resolveCollision();
                collisions.remove(0);

                Iterator<Collision> it = collisions.iterator();
                while (it.hasNext()) {
                    Collision each = it.next();
                    if (!each.detectCollision()) {
                        collisions.remove(each);
                    }
                }
            }
        }

    }


    @Override
    public void render() {
        quadTree.clear();
        drawMap();
        quadTree.render();
        for (AbstractEntity each : scene) {
            each.render();
        }
    }

    /**
     * 
     */
    private void drawMap() {
        Color.white.bind();
        texture.bind();
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        final float tileOffsetY = position.y / tileHeight;
        final double pixelOffsetY = tileHeight * (tileOffsetY % 1);

        final float tileOffsetX = position.x / tileWidth;
        final double pixelOffsetX = tileWidth * (tileOffsetX % 1);

        int tileIndex = (int) (width * (Math.floor(tileOffsetY)) + tileOffsetX);

        final int rows = (MotherBrainConstants.HEIGHT / tileHeight + (pixelOffsetY == 0 ? 0 : 1));
        final int columns = (MotherBrainConstants.WIDTH / tileWidth + (pixelOffsetX == 0 ? 0 : 1));
        final int nextRow = width - columns;

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
                if (tiles[tileIndex].getTileId() != 0) {
                    tiles[tileIndex].render();
                    quadTree.insert(tiles[tileIndex]);
                }
                tileIndex++;
            }
            tileIndex += nextRow;
        }

        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
    
    /**
     * @param x x
     * @param y y
     */
    public void drawChildVertex2f(float x, float y) {
        GL11.glVertex2f(x - position.x, y - position.y);
    }

    /**
     * @param tileSetPath path to tile set 
     * 
     */
    public void setTileSetPath(String tileSetPath) {
        this.tileSetPath = tileSetPath;
    }
}
