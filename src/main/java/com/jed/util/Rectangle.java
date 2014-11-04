package com.jed.util;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class Rectangle {
    
    /**
     * 
     */
    private int width;
    
    /**
     * 
     */
    private int height;

    /**
     *
     * @param width Width of the Rectangle.
     * @param height Height of the Rectangle.
     */

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
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
     * @param width width
     */
    public void setWidth(int width) {
        this.width = width;
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
     * @param height height
     */
    public void setHeight(int height) {
        this.height = height;
    }

}
