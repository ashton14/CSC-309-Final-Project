import java.awt.*;
import java.io.Serializable;

/**
 * Abstract class to be extended to represent a shape with a
 * defined width, height, x and y location and color. Can
 * be drawn on the screen and saved through serialization.
 *
 * @author Aaron Bettencourt
 */
public abstract class Shape implements Drawable, Serializable {
    private int xPosCenter;
    private int yPosCenter;
    private int width;
    private int height;
    private Color color;

    /**
     * Constructor for objects of type Shape to construct shapes using a pair of x
     * and y coordinates, width, height and color.
     *
     * @param xPosCenter   The x position of the center of this Shape as an int.
     * @param yPosCenter   The y position of the center of this Shape as an int.
     * @param width        The width of this shape as an int.
     * @param height       The height of this shape as an int.
     * @param color        The color of this shape as an object of type Color.
     * @throws NullPointerException   When the given color is null.
     * @throws IllegalArgumentException   When the width or height is less than 0.
     */
    public Shape(int xPosCenter, int yPosCenter, int width, int height, Color color){
        if(color == null){
            throw new NullPointerException();
        }
        if(width < 0){
            throw new IllegalArgumentException("Width must be greater than 0");
        }
        if(height < 0){
            throw new IllegalArgumentException("Height must be greater than 0");
        }
        this.xPosCenter = xPosCenter;
        this.yPosCenter = yPosCenter;
        this.width = width;
        this.height = height;
        this.color = color;
    }


    /**
     * Determines if a pair of x and y coordinates are in the bounds
     * of this Shape and returns the result as a boolean.
     *
     * @param x     The x coordinate to check.
     * @param y     The y coordinate to check.
     * @return   A value of true as a boolean if the x,y coordinate
     * pair is within the bounds of this Shape, otherwise false.
     */
    public boolean isInBounds(int x, int y) {
        return x <= xPosCenter + width/2 && x >= xPosCenter - width/2
                && y < yPosCenter + height/2 && y > yPosCenter - height/2;
    }

    /**
     * Returns the center x position of this Shape as an int.
     *
     * @return The center x position of this Shape as an int.
     */
    public int getXCenter(){
        return xPosCenter;
    }

    /**
     * Sets the center x position of this Shape to a new value.
     *
     * @param xPosCenter   The new value of the center x position
     *                     of this Shape.
     */
    public void setXCenter(int xPosCenter){
        this.xPosCenter = xPosCenter;
    }

    /**
     * Returns the center y position of this Shape as an int.
     *
     * @return The center y position of this Shape as an int.
     */
    public int getYCenter(){
        return yPosCenter;
    }

    /**
     * Sets the center x position of this Shape to a new value.
     *
     * @param yPosCenter   The new value of the center x position
     *                     of this Shape.
     */
    public void setYCenter(int yPosCenter){
        this.yPosCenter = yPosCenter;
    }

    /**
     * Returns the height of this Shape as an int.
     *
     * @return The height of this Shape as an int.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the width of this Shape as an int.
     *
     * @return The width of this Shape as an int.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the color of this Shape as an object of type Color.
     *
     * @return The color of this Shape as an object of type Color.
     */
    public Color getColor() {
        return color;
    }
}