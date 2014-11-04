package com.jed.util;

import org.lwjgl.opengl.GL11;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class BasicShapeRenderer {

    public static void drawFilledCircle(float cx, float cy, float r, int segments, float red, float g, float b) {
        double theta = 2 * 3.1415926 / (double) segments;
        double c = Math.cos(theta);
        double s = Math.sin(theta);
        double t;

        float x = r;
        float y = 0;

        GL11.glColor3f(red, g, b);

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        for (int ii = 0; ii < segments; ii++) {
            GL11.glVertex2f(x + cx, y + cy);//output vertex

            //apply the rotation matrix
            t = x;
            x = (float) (c * x - s * y);
            y = (float) (s * t + c * y);
        }
        GL11.glEnd();
    }

}
