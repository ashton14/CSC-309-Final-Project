package src.main;

import java.awt.*;
/**
 * Parallelogram
 * @author Patrick Whitlock
 * @author Ashton Alonge
 */
public class Parallelogram extends Shape {
    public static final int offset = 30;

    /**
     * Creates a parallelogram object
     *
     * @param color Color in which to draw the shape
     * @param xPosCenter X location at which the shape is located
     * @param yPosCenter Y location at which the shape is located
     * @param height how many px high the shape is
     * @param width how many px wide the shape is
     */
    public Parallelogram(int xPosCenter, int yPosCenter, int width, int height, Color color) {
        super(xPosCenter, yPosCenter,width,height, color);
    }

    @Override
    public Shape copyShape() {
        return new Parallelogram(getXCenter(), getYCenter(), getWidth(), getHeight(), getColor());
    }

    /**
     * Draws the shape on screen
     * @param g Graphics object to draw with
     */
    public void draw(Graphics g) {
        int xPoly[] = {0,0,0,0}; //Top, right, bottom, left
        int yPoly[] = {0,0,0,0};

        //calculate corner points
        xPoly[0] = getXCenter() - getWidth()/2 - offset;
        yPoly[0] = getYCenter() + getHeight() / 2;

        xPoly[1] = getXCenter() + getWidth() / 2;
        yPoly[1] = getYCenter() + getHeight() / 2;

        xPoly[2] = getXCenter() + (getWidth()/2) + offset;
        yPoly[2] = getYCenter() - getHeight() / 2;

        xPoly[3] = getXCenter() - getWidth() / 2;
        yPoly[3] = getYCenter() - getHeight() / 2;

        g.setColor(getColor());
        Polygon polygon = new Polygon(xPoly,yPoly,xPoly.length);
        g.fillPolygon(polygon);
    }
}