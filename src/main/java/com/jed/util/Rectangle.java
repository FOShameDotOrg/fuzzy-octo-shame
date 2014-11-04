package com.jed.util;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class Rectangle {
    private int width, height;

    /**
     *
     * @param width Width of the Rectangle.
     * @param height Height of the Rectangle.
     */

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
