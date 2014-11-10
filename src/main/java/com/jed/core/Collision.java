package com.jed.core;

import com.colapietro.number.util.Doubles;
import com.jed.actor.Boundary;
import com.jed.actor.Entity;
import com.jed.state.MapTile;
import com.jed.util.Vector;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class Collision implements Comparable<Collision> {

    /**
     * 
     */
    public static final int NONE = 0;
    
    /**
     * 
     */
    public static final int SAT = 1;
    
    /**
     * 
     */
    public static final int SWEPT_X = 2;
    
    /**
     * 
     */
    public static final int SWEPT_Y = 3;

    /**
     * 
     */
    public int collisionType = NONE;

    /**
     * 
     */
    public Entity a, b;
    
    /**
     * 
     */
    private double minXDistance, minYDistance;
    
    /**
     * 
     */
    private double smallestDisplacement;
    
    /**
     * 
     */
    private MinMax xEntityMinMax, xSEntityMinMax, yEntityMinMax, ySEntityMinMax;

    /**
     * 
     * @param a entity a.
     * @param b entity b.
     */
    public Collision(Entity a, Entity b) {
        this.a = a;
        this.b = b;
    }

    /**
     * 
     * @return the smallest displacement.
     */
    public double smallestDisplacement() {
        return smallestDisplacement;
    }

    /**
     * 
     * @return if a collision was detected.
     */
    public boolean detectCollision() {

        //TODO: must take into account relative motion vector when dealing w/ 2 moving objects for swept test!!!

        Vector xAxis = new Vector(1, 0);
        Vector yAxis = new Vector(0, 1);

        xEntityMinMax = new MinMax(a.bounds, xAxis);
        xSEntityMinMax = new MinMax(b.bounds, xAxis);
        minXDistance = Math.abs(xEntityMinMax.getIntervalDistance(xSEntityMinMax));

        yEntityMinMax = new MinMax(a.bounds, yAxis);
        ySEntityMinMax = new MinMax(b.bounds, yAxis);
        minYDistance = Math.abs(yEntityMinMax.getIntervalDistance(ySEntityMinMax));

        boolean separateX =
                xEntityMinMax.max < xSEntityMinMax.min ||
                        xSEntityMinMax.max < xEntityMinMax.min;

        boolean separateY =
                yEntityMinMax.max < ySEntityMinMax.min ||
                        ySEntityMinMax.max < yEntityMinMax.min;

        //Separating AXIS Theorem
        if (!separateX && !separateY) {
            collisionType = SAT;
        } else

            //Swept Separating Axis Theorem
            if (a.movement.x != 0 || a.movement.y != 0) {
                if (Math.abs(a.movement.dotProduct(yAxis)) > minYDistance &&
                        !(xEntityMinMax.max <= xSEntityMinMax.min || xSEntityMinMax.max <= xEntityMinMax.min)) {

                    collisionType = SWEPT_Y;
                } else if (Math.abs(a.movement.dotProduct(xAxis)) > minXDistance &&
                        !(yEntityMinMax.max <= ySEntityMinMax.min || ySEntityMinMax.max <= yEntityMinMax.min)) {

                    collisionType = SWEPT_X;
                }
            }

        smallestDisplacement = minXDistance < minYDistance ? minXDistance : minYDistance;

        //TODO: Temporary!
        if (collisionType != NONE) {
            ((MapTile) b).colliding = true;
        } else {
            ((MapTile) b).evaluating = true;
        }

        return collisionType != NONE;

    }

    /**
     * 
     */
    public void resolveCollision() {

        //OVERLAPS
        if (collisionType == SAT) {

            if (minYDistance != 0) {
                /**
                 * Resolve Wall Collision if this is the 1st frame
                 * (i.e. player is holding over against the wall minX is = to acceleration)
                 *  Or
                 * if the x axis overlap is smaller push out
                 */

                //FIXME test
                if (Doubles.compareDoubles(minXDistance, a.acceleration) || minXDistance < minYDistance) {
                    if (xEntityMinMax.min > xSEntityMinMax.min) {
                        a.position.x += minXDistance;
                    } else {
                        a.position.x -= minXDistance;
                    }

                    a.movement.x = 0;
                }
                //Resolve Ceiling / Floor Collisions
                else {
                    if (yEntityMinMax.min > ySEntityMinMax.min) {
                        a.position.y += minYDistance;
                    } else {
                        a.position.y -= minYDistance;
                        a.collideDown(b);
                    }
                    a.movement.y = 0;

                }
            }

            //Notify Entity they're standing on a platform and don't have to start falling...
            if (minYDistance == 0 && a.movement.y == 0 && yEntityMinMax.min < ySEntityMinMax.min) {
                a.collideDown(b);
            }
        }
        //Y Collision on the next game update - accounts for fast moving objects
        else if (collisionType == SWEPT_Y) {

            if (yEntityMinMax.min > ySEntityMinMax.min) {
                //Closest Edge is above the entity, only collide if moving towards it
                if (a.movement.y <= 0 && minXDistance != 0) {
                    a.position.y -= minYDistance;
                    a.movement.y = 0;
                }
            } else {
                //Closest Edge is below the entity, only collide if moving towards it
                if (a.movement.y >= 0 && minXDistance != 0) {
                    a.movement.y = 0;
                    a.position.y += minYDistance;
                    a.collideDown(b);
                }
            }

        }
        //X Collision on the next game update - accounts for fast moving objects
        else if (collisionType == SWEPT_X) {
            if (xEntityMinMax.min > xSEntityMinMax.min) {
                a.position.x -= minXDistance;
            } else {
                a.position.x += minXDistance;
            }
        }

    }

    /**
     * 
     * @author jlinde, Peter Colapietro
     *
     */
    private class MinMax {

        /**
         * 
         */
        public double min, max;

        /**
         * 
         * @param boundary boundary
         * @param axis axis
         */
        public MinMax(Boundary boundary, Vector axis) {
            max = boundary.verticies[0].add(boundary.getWorldPosition()).dotProduct(axis);
            min = boundary.verticies[0].add(boundary.getWorldPosition()).dotProduct(axis);

            double current;
            for (int i = 1; i < boundary.verticies.length; i++) {
                current = boundary.verticies[i].add(boundary.getWorldPosition()).dotProduct(axis);
                if (min > current) {
                    min = current;
                }

                if (current > max) {
                    max = current;
                }
            }
        }

        /**
         * 
         * @param b another minmax
         * @return interval distance
         */
        public double getIntervalDistance(MinMax b) {
            if (this.min < b.min) {
                return b.min - this.max;
            } else {
                return this.min - b.max;
            }
        }

    }

    @Override
    public int compareTo(Collision c) {
        if (collisionType == SAT) {
            if (c.collisionType != SAT) {
                return -1;
            } else {
                return
                        Doubles.compareDoubles(smallestDisplacement, c.smallestDisplacement) ? 0 :
                                smallestDisplacement < c.smallestDisplacement ? -1 : 1;//FIXME test
            }
        } else if (collisionType == SWEPT_Y) {
            if (c.collisionType == SAT) {
                return 1;
            } else if (c.collisionType == SWEPT_X) {
                return -1;
            } else {
                return
                        Doubles.compareDoubles(minYDistance,c.minYDistance) ? 0 :
                                minYDistance < c.minYDistance ? -1 : 1;//FIXME test
            }
        } else if (collisionType == SWEPT_X) {
            if (c.collisionType != SWEPT_X) {
                return 1;
            } else {
                return
                        Doubles.compareDoubles(minXDistance,c.minXDistance) ? 0 :
                                minXDistance < c.minXDistance ? -1 : 1; //FIXME test
            }

        }
        return 0;
    }
}
