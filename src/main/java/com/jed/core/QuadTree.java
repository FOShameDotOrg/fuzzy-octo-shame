package com.jed.core;

import java.util.ArrayList;
import java.util.List;

import com.jed.actor.AbstractEntity;
import org.lwjgl.opengl.GL11;

import com.jed.util.Rectangle;
import com.jed.util.Vector;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class QuadTree implements Displayable {

    /**
     * 
     */
    private final int MAX_OBJECTS = 2;
    
    /**
     * 
     */
    private final int MAX_LEVELS = 5;

    /**
     * 
     */
    private int level;
    
    /**
     * 
     */
    private List<AbstractEntity> objects;
    
    /**
     * 
     */
    private Rectangle rectangle;
    
    /**
     * 
     */
    private QuadTree[] nodes;

    /**
     * 
     */
    private Displayable parent;

    /**
     * 
     */
    private Vector position;

    /**
     * 
     * @param position position vector
     * @param level level or quad tree
     * @param rectangle rectangle
     * @param parent parent
     */
    public QuadTree(Vector position, int level, Rectangle rectangle, Displayable parent) {
        this.position = position;
        this.level = level;
        this.objects = new ArrayList<AbstractEntity>();
        this.rectangle = rectangle;
        this.nodes = new QuadTree[4];
        this.parent = parent;
    }

    /**
     * 
     */
    public void clear() {
        objects.clear();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
            }
            nodes[i] = null;
        }
    }

    /**
     * 
     * @param returnObjects list of entities
     * @param o other entity
     */
    public void retrieve(List<AbstractEntity> returnObjects, AbstractEntity o) {
        int index = getIndex(o);
        if (index != -1) {
            if (nodes[0] != null) {
                nodes[index].retrieve(returnObjects, o);
            }
            returnObjects.addAll(objects);
        } else {
            returnObjects.addAll(getObjects());
        }
    }

    /**
     * 
     * @return ret
     */
    public List<AbstractEntity> getObjects() {
        List<AbstractEntity> ret = new ArrayList<AbstractEntity>();
        ret.addAll(objects);
        if (nodes[0] != null) {
            ret.addAll(nodes[0].getObjects());
            ret.addAll(nodes[1].getObjects());
            ret.addAll(nodes[2].getObjects());
            ret.addAll(nodes[3].getObjects());
        }
        return ret;
    }

    /**
     * 
     * @param o other entity
     * @return index in quad tree, range is 0-3 inclusive.
     */
    private int getIndex(AbstractEntity o) {
        int verticalMidpoint = (int) (position.x + (rectangle.getWidth() / 2));
        int horizontalMidpoint = (int) (position.y + (rectangle.getHeight() / 2));

        boolean topQuadrant = o.bounds.getNextWorldPosition().y + o.bounds.getHeight() < horizontalMidpoint;
        boolean bottomQuadrant = o.bounds.getNextWorldPosition().y > horizontalMidpoint;

        if (o.bounds.getNextWorldPosition().x + o.bounds.getWidth() < verticalMidpoint) {
            if (topQuadrant) {
                return 1;
            } else if (bottomQuadrant) {
                return 2;
            }
        } else if (o.bounds.getNextWorldPosition().x > verticalMidpoint) {
            if (topQuadrant) {
                return 0;
            } else if (bottomQuadrant) {
                return 3;
            }
        }

        return -1;
    }

    /**
     * 
     */
    private void split() {
        //TODO START pc 2014-10-31: Test me
        float halfWidth = Float.valueOf(rectangle.getWidth()) / 2.0f;
        int subWidth = Math.round(halfWidth);
        float halfHeight = Float.valueOf(rectangle.getHeight()) / 2.0f;
        int subHeight = Math.round(halfHeight);
        //TODO END pc 2014-10-31: Test me
        int x = Math.round(position.x);
        int y = Math.round(position.y);

        Rectangle rect = new Rectangle(subWidth, subHeight);

        this.nodes[0] = new QuadTree(new Vector(x + subWidth, y), this.level + 1, rect, parent);
        this.nodes[1] = new QuadTree(new Vector(x, y), this.level + 1, rect, parent);
        this.nodes[2] = new QuadTree(new Vector(x, y + subHeight), this.level + 1, rect, parent);
        this.nodes[3] = new QuadTree(new Vector(x + subWidth, y + subHeight), this.level + 1, rect, parent);
    }

    /**
     * 
     * @param o other entity to insert
     */
    public void insert(AbstractEntity o) {
        if (nodes[0] != null) {
            int index = getIndex(o);

            if (index != -1) {
                this.nodes[index].insert(o);
                return;
            }
        }

        objects.add(o);

        if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes[0] == null) {
                split();
            }

            int i = 0;
            while (i < objects.size()) {
                int index = getIndex(objects.get(i));
                if (index != -1) {
                    nodes[index].insert(objects.remove(i));
                } else {
                    i++;
                }
            }
        }
    }

    @Override
    public void render() {
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        GL11.glBegin(GL11.GL_LINE_LOOP);

        parent.drawChildVertex2f(position.x, position.y);
        parent.drawChildVertex2f(position.x + rectangle.getWidth(), position.y);
        parent.drawChildVertex2f(position.x + rectangle.getWidth(), position.y + rectangle.getHeight());
        parent.drawChildVertex2f(position.x + rectangle.getWidth(), position.y + rectangle.getHeight());
        parent.drawChildVertex2f(position.x, position.y + rectangle.getHeight());

        GL11.glEnd();

        if (nodes[0] != null) {
            for (QuadTree eachNode : nodes) {
                eachNode.render();
            }
        }
    }

    @Override
    public void drawChildVertex2f(float x, float y) {
    }
}
