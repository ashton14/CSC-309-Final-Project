package src.main;

import java.awt.*;

/**
 * Diamond of width 100px and height of 40px
 * @author Patrick Whitlock
 * @author Aaron Bettencourt
 */
public class Diamond extends Shape {

    /**
     * Creates a Diamond object
     * @param color Color in which to draw the shape
     */
    public Diamond(int xPosCenter, int yPosCenter, int width, int height, Color color) {
        super(xPosCenter, yPosCenter,width,height, color);
    }
    /**
     * Sets the width for this Diamond and makes its height
     * match.
     * @param width   The width of this Shape as an int.
     */
    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    /**
     * Sets the height for this Diamond and makes its
     * width match.
     * @param height   The height of this Shape as an int.
     */
    @Override
    public void setHeight(int height) {
        super.setWidth(height);
        super.setHeight(height);
    }
    /**
     * Returns a deep copy of this Diamond as a Shape.
     * @return a deep copy of this Diamond as a Shape.
     */
    @Override
    public Shape copyShape() {
        return new Diamond(getXCenter(), getYCenter(), getWidth(),
                getHeight(), getColor());
    }

    /**
     * Calculates the halfway points of the lines
     * of a rectangle, then connecting those points
     * with lines, resulting in a diamond
     * @param g Graphics in which to draw the shape with
     */
    public void draw(Graphics g) {
        int xPoly[] = {0,0,0,0}; //Top, right, bottom, left
        int yPoly[] = {0,0,0,0};

        //calculate halfway points
        xPoly[0] = getXCenter();
        yPoly[0] = getYCenter() - getHeight() / 2;

        xPoly[1] = getXCenter() + getWidth() / 2;
        yPoly[1] = getYCenter();

        xPoly[2] = getXCenter();
        yPoly[2] = getYCenter() + getHeight() / 2;

        xPoly[3] = getXCenter() - (getWidth() / 2);
        yPoly[3] = getYCenter();

        g.setColor(getColor());
        Polygon polygon = new Polygon(xPoly,yPoly,xPoly.length);
        g.fillPolygon(polygon);
    }
}