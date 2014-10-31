package com.jed.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jed.actor.Ball;
import com.jed.actor.CircleBoundary;
import com.jed.actor.Entity;
import com.jed.core.MotherBrain;
import com.jed.core.QuadTree;
import com.jed.util.Rectangle;
import com.jed.util.Vector;

public class DiscoState extends GameState {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscoState.class);

    private QuadTree quadTree;

    private Stack<Ball> scene;

    private int WIDTH;
    private int HEIGHT;

    public DiscoState(GameStateManager manager) {
        super(manager);
    }

    @Override
    public void entered() {
        WIDTH = MotherBrain.getInstance().WIDTH;
        HEIGHT = MotherBrain.getInstance().HEIGHT;

        scene = new Stack<Ball>();
        quadTree = new QuadTree(new Vector(0, 0), 0, new Rectangle(WIDTH, HEIGHT), this);

        Random rand = new Random();

        int randW, randH, randR;
        float randXS, randYS, randRed, randGreen, randBlue;

        for (int i = 0; i < 25; i++) {
            randW = rand.nextInt(1024) + 1;
            randH = rand.nextInt(768) + 1;
            randR = rand.nextInt(25) + 1;
            randXS = rand.nextFloat() * 2;
            randYS = rand.nextFloat() * 2;
            randRed = rand.nextFloat();
            randGreen = rand.nextFloat();
            randBlue = rand.nextFloat();

            Ball newBall = new Ball(
                    new Vector(randW, randH),
                    new Vector(randXS, randYS),
                    new CircleBoundary(randR),
                    25,
                    randRed, randGreen, randBlue);
            scene.push(newBall);
        }

        /*
        Ball ball1 = new Ball(new Vector(50,50), new Vector(.05f,.03f), 30, 25);
        scene.push(ball1);

        Ball ball2 = new Ball(new Vector(100,100), new Vector(-.04f,0.05f), 10, 25);
        scene.push(ball2);

        Ball ball3 = new Ball(new Vector(800,300), new Vector(.04f,0.07f), 100, 25);
        scene.push(ball3);

        Ball ball4 = new Ball(new Vector(200,50), new Vector(.05f,.03f), 10, 25);
        scene.push(ball4);

        Ball ball5 = new Ball(new Vector(50,400), new Vector(-.04f,0.05f), 10, 25);
        scene.push(ball5);

        Ball ball6 = new Ball(new Vector(1000,20), new Vector(.4f,0.07f), 5, 25);
        scene.push(ball6);

        Ball ball7 = new Ball(new Vector(500,35), new Vector(.05f,.03f), 15, 25);
        scene.push(ball7);

        Ball ball8 = new Ball(new Vector(600,100), new Vector(-.04f,0.05f), 10, 25);
        scene.push(ball8);

        Ball ball9 = new Ball(new Vector(270,300), new Vector(.04f,0.07f), 100, 25);
        scene.push(ball9);

        Ball ball10 = new Ball(new Vector(890,50), new Vector(.05f,.03f), 10, 25);
        scene.push(ball10);

        Ball ball11 = new Ball(new Vector(65,400), new Vector(-.04f,0.05f), 10, 25);
        scene.push(ball11);

        Ball ball12 = new Ball(new Vector(88,220), new Vector(.04f,0.07f), 10, 25);
        scene.push(ball12);

        Ball ball13 = new Ball(new Vector(18,20), new Vector(.4f,0.07f), 5, 25);
        scene.push(ball13);

        Ball ball14 = new Ball(new Vector(500,20), new Vector(.4f,0.07f), 5, 25);
        scene.push(ball14);

        Ball ball15 = new Ball(new Vector(600,20), new Vector(.4f,0.07f), 5, 25);
        scene.push(ball15);

        Ball ball16 = new Ball(new Vector(700,20), new Vector(.4f,0.07f), 5, 25);
        scene.push(ball16);

        Ball ball17 = new Ball(new Vector(900,20), new Vector(.4f,0.07f), 5, 25);
        scene.push(ball17);

        Ball ball18 = new Ball(new Vector(601,20), new Vector(.4f,0.07f), 5, 25);
        scene.push(ball18);*/
    }

    @Override
    public void leaving() {
    }

    @Override
    public void update() {
        quadTree.clear();
        for (Entity each : scene) {
            quadTree.insert(each);
        }

        handleCollisions();
    }

    @Override
    public void draw() {
        quadTree.draw();
        for (Entity each : scene) {
            each.draw();
        }
    }

    private void handleCollisions() {
        boolean collide = false;
        for (int i = 0; i < scene.size(); i++) {
            List<Entity> returnObjects = new ArrayList<Entity>();

            quadTree.retrieve(returnObjects, scene.get(i));

            for (int j = 0; j < returnObjects.size(); j++) {
                if (returnObjects.get(j).equals(scene.get(i))) {
                    continue;
                } else {
                    Ball p1 = scene.get(i);
                    Ball p2 = (Ball) returnObjects.get(j);
                    if (detectCollision(p1, p2)) {
                        if (!collide) {
                            LOGGER.debug("Handling Collisions");
                            collide = true;
                        }

                        collide(p1, p2);
                    }
                }
            }
        }

        //Boundary collisions
        for (Ball each : scene) {
            double yPosition = each.position.y;
            double xPosition = each.position.x;
            float radius = each.getRadius();

            //Alter the movement vector, move the ball in the opposite
            //direction, then
            //Adjust the position vector so that the ball
            //does not get stuck in the wall
            if (yPosition + radius >= HEIGHT) {
                each.movement.y = each.movement.y * -1;
                each.position.y = HEIGHT - each.getRadius();
            } else if (yPosition - radius <= 0) {
                each.movement.y = each.movement.y * -1;
                each.position.y = each.getRadius();
            }

            if (xPosition + radius >= WIDTH) {
                each.movement.x = each.movement.x * -1;
                each.position.x = WIDTH - each.getRadius();
            } else if (xPosition - radius <= 0) {
                each.movement.x = each.movement.x * -1;
                each.position.x = each.getRadius();
            }
        }
    }

    private boolean detectCollision(Ball p1, Ball p2) {
        /**
         * Subtract p2's movement vector from p1 the resultant vector
         * Represents where the two balls will collide, if they do
         * by assuming p2 is static
         */
        Vector mv = p1.movement.subtract(p2.movement);

        /**
         * The movement vector must be at least the distance between
         * the centers of the circles minus the radius of each.
         * If it is not, then there is no way that the circles
         * will collide.
         */
        double dist = p1.position.distance(p2.position);
        double sumRadii = p1.getRadius() + p2.getRadius();
        dist -= sumRadii;
        double mvMagnitude = mv.magnitude();
        if (mvMagnitude < dist) {
            return false;
        }

        /**
         * Find c, the vector from the center of p1 to the center of p2
         */
        Vector c = p2.position.subtract(p1.position);

        /**
         * Normalize the movement vector to determine if p1 is moving towards p2
         */
        Vector mvN = mv.normalize();

        /**
         * Dot product of the normalized movement vector and the difference
         * between the position vectors, this will tell how far along the
         * movement vector the ball will collide with the other, if
         * the result is zero or less, the balls will never collide
         */
        double d = mvN.dotProduct(c);
        if (d <= 0) {
            return false;
        }


        /**
         * f is the distance between the center of p1 and the movement vector
         * if this distance is greater than the radiuses squared, the balls
         * will never touch
         */
        double lengthC = c.magnitude();
        double f = (lengthC * lengthC) - (d * d);

        double sumRadiiSquared = sumRadii * sumRadii;
        if (f >= sumRadiiSquared) {
            return false;
        }

        /**
         * sqrt(t) represents the distance between the 90 degree intersection
         * of the point on the movement vector and the ball minus the
         * 2 radii of the balls. So it's the square root of the distance along the movement
         * vector where the balls WILL intersect
         */
        double t = sumRadiiSquared - f;

        /**
         * If there is no such right triangle with sides length of
         * sumRadii and sqrt(f), T will probably be less than 0.
         * Better to check now than perform a square root of a
         * negative number.
         */
        if (t < 0) {
            return false;
        }

        /**
         * The distance the circle has to travel along
         *    movevec is D - sqrt(T)
         */
        double mvDistance = d - Math.sqrt(t);

        /**
         * Finally, make sure that the distance A has to move
         * to touch B is not greater than the magnitude of the
         * movement vector.
         */
        if (mvMagnitude < mvDistance) {
            return false;
        }

        //TODO: fix this to adjust the ball
        /**
         * Adjust the displacement of p1 so that it doesn't become "entwined"
         * with the other ball. Place it right where the collision would have occurred
         */


        float blarg = (float) (mvDistance / mv.magnitude());
        blarg = blarg + (blarg > 0 ? .5f : -.5f);
//        p1.setDisplacement(new Vector(0,0));
//        p2.setDisplacement(p2.position.add(p2.movement.scale(blarg)));
//
//        LOGGER.debug("p1 mag = " + p1.movement.magnitude());
//        LOGGER.debug(d);
//        LOGGER.debug("SQRT T = " + Math.sqrt(t));
//        LOGGER.debug("distance to move " + mvDistance);
        LOGGER.debug("result = " + mv.magnitude() / p1.movement.magnitude());
//        LOGGER.debug("");

        return true;
    }

    private void collide(Ball p1, Ball p2) {
        // First, find the normalized vector n from the center of
        // circle1 to the center of circle2
        Vector n = (p1.position.subtract(p2.position)).normalize();

        // Find the length of the component of each of the movement
        // vectors along n.
        // a1 = v1 . n
        // a2 = v2 . n
        double a1 = p1.movement.dotProduct(n);
        double a2 = p2.movement.dotProduct(n);

        double optimizedP = (2.0 * (a1 - a2)) / (p1.mass() + p2.mass());

        // Calculate v1', the new movement vector of circle1
        // v1' = v1 - optimizedP * m2 * n
        Vector v1 = p1.movement.subtract(n.scale((float) (optimizedP * p2.mass())));

        // Calculate v1', the new movement vector of circle1
        // v2' = v2 + optimizedP * m1 * n
        Vector v2 = p2.movement.add(n.scale((float) (optimizedP * p1.mass())));

        p1.movement = v1;
        p2.movement = v2;
    }
}
