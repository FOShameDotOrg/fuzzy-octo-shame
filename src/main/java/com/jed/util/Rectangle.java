package com.jed.util;

/**
 * Data object consisting of a height and width that is used to represent a Rectangle.
 * 
 * @author jlinde, Peter Colapietro
 * @since 0.1.0
 *
 */
public final class Rectangle {
    
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
