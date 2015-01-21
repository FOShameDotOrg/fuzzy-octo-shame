package com.jed.core;

import com.jed.actor.AbstractEntity;
import com.jed.util.Vector2f;
import org.colapietro.number.util.Doubles;
import com.jed.actor.Boundary;
import com.jed.state.MapTile;

import javax.annotation.Nonnull;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class Collision implements Comparable<Collision> {

    /**
     * 
     */
    private static final int NONE = 0;
    
    /**
     * 
     */
    private static final int SAT = 1;
    
    /**
     * 
     */
    private static final int SWEPT_X = 2;
    
    /**
     * 
     */
    private static final int SWEPT_Y = 3;

    /**
     * 
     */
    private int collisionType = NONE;

    /**
     * 
     */
    private final AbstractEntity a;

    /**
     *
     */
    private final AbstractEntity b;
    
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
    private MinMax xEntityMinMax;

    /**
     *
     */
    private MinMax xSEntityMinMax;

    /**
     *
     */
    private MinMax yEntityMinMax;

    /**
     *
     */
    private MinMax ySEntityMinMax;
    
    /**
     * 
     */
    private final boolean isDebugViewEnabled;

    /**
     * 
     * Use {@link #Collision(AbstractEntity, AbstractEntity, boolean)}.
     * 
     * @param a entity a.
     * @param b entity b.
     * 
     */
    @Deprecated
    public Collision(AbstractEntity a, AbstractEntity b) {
        this.a = a;
        this.b = b;
        this.isDebugViewEnabled = false;
    }
    
    /**
     * @since 0.1.8
     * 
     * @param a entity a.
     * @param b entity b.
     * @param isDebugViewEnabled isDebugViewEnabled
     */
    public Collision(AbstractEntity a, AbstractEntity b, boolean isDebugViewEnabled) {
        this.a = a;
        this.b = b;
        this.isDebugViewEnabled = isDebugViewEnabled ;
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

        Vector2f xAxis = new Vector2f(1, 0);
        Vector2f yAxis = new Vector2f(0, 1);

        xEntityMinMax = new MinMax(a.getBounds(), xAxis);
        xSEntityMinMax = new MinMax(b.getBounds(), xAxis);
        minXDistance = Math.abs(xEntityMinMax.getIntervalDistance(xSEntityMinMax));

        yEntityMinMax = new MinMax(a.getBounds(), yAxis);
        ySEntityMinMax = new MinMax(b.getBounds(), yAxis);
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
            if (a.getMovement().x != 0 || a.getMovement().y != 0) {
                if (Math.abs(a.getMovement().dotProduct(yAxis)) > minYDistance &&
                        !(xEntityMinMax.max <= xSEntityMinMax.min || xSEntityMinMax.max <= xEntityMinMax.min)) {

                    collisionType = SWEPT_Y;
                } else if (Math.abs(a.getMovement().dotProduct(xAxis)) > minXDistance &&
                        !(yEntityMinMax.max <= ySEntityMinMax.min || ySEntityMinMax.max <= yEntityMinMax.min)) {

                    collisionType = SWEPT_X;
                }
            }

        smallestDisplacement = minXDistance < minYDistance ? minXDistance : minYDistance;

        //TODO: Temporary!
        if(isDebugViewEnabled) {
            if (collisionType != NONE) {
                ((MapTile) b).setColliding(true);
            } else {
                ((MapTile) b).setEvaluating(true);
            }
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
                if (Doubles.compareDoubles(minXDistance, a.getAcceleration()) || minXDistance < minYDistance) {
                    if (xEntityMinMax.min > xSEntityMinMax.min) {
                        a.getPosition().x += minXDistance;
                    } else {
                        a.getPosition().x -= minXDistance;
                    }

                    a.getMovement().x = 0;
                }
                //Resolve Ceiling / Floor Collisions
                else {
                    if (yEntityMinMax.min > ySEntityMinMax.min) {
                        a.getPosition().y += minYDistance;
                    } else {
                        a.getPosition().y -= minYDistance;
                        a.collideDown(b);
                    }
                    a.getMovement().y = 0;

                }
            }

            //Notify Entity they're standing on a platform and don't have to start falling...
            if (minYDistance == 0 && a.getMovement().y == 0 && yEntityMinMax.min < ySEntityMinMax.min) {
                a.collideDown(b);
            }
        }
        //Y Collision on the next game update - accounts for fast moving objects
        else if (collisionType == SWEPT_Y) {

            if (yEntityMinMax.min > ySEntityMinMax.min) {
                //Closest Edge is above the entity, only collide if moving towards it
                if (a.getMovement().y <= 0 && minXDistance != 0) {
                    a.getPosition().y -= minYDistance;
                    a.getMovement().y = 0;
                }
            } else {
                //Closest Edge is below the entity, only collide if moving towards it
                if (a.getMovement().y >= 0 && minXDistance != 0) {
                    a.getMovement().y = 0;
                    a.getPosition().y += minYDistance;
                    a.collideDown(b);
                }
            }

        }
        //X Collision on the next game update - accounts for fast moving objects
        else if (collisionType == SWEPT_X) {
            if (xEntityMinMax.min > xSEntityMinMax.min) {
                a.getPosition().x -= minXDistance;
            } else {
                a.getPosition().x += minXDistance;
            }
        }

    }

    /**
     * 
     * @author jlinde, Peter Colapietro
     *
     */
    private static class MinMax {

        /**
         * 
         */
        private double min;

        /**
         *
         */
        private double max;

        /**
         * 
         * @param boundary boundary
         * @param axis axis
         */
        public MinMax(@Nonnull Boundary boundary, Vector2f axis) {
            max = boundary.vertices[0].add(boundary.getWorldPosition()).dotProduct(axis);
            min = boundary.vertices[0].add(boundary.getWorldPosition()).dotProduct(axis);

            double current;
            for (int i = 1; i < boundary.vertices.length; i++) {
                current = boundary.vertices[i].add(boundary.getWorldPosition()).dotProduct(axis);
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
        public double getIntervalDistance(@Nonnull MinMax b) {
            if (this.min < b.min) {
                return b.min - this.max;
            } else {
                return this.min - b.max;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MinMax minMax = (MinMax) o;

            if (Double.compare(minMax.max, max) != 0) return false;
            if (Double.compare(minMax.min, min) != 0) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            temp = Double.doubleToLongBits(min);
            result = (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(max);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }

    @Override
    public int compareTo(@Nonnull Collision c) {
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
