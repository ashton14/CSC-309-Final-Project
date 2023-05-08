package Repo.main;

import java.awt.*;

/**
 * Circle with a radius of 50px
 * @author Patrick Whitlock
 * @author Ashton Alonge
 * @author Aaron Bettencourt
 */
public class Circle extends Shape {
    private int radius;

    /**
     * Creates a new Circle object
     * @param color Color in which to draw the shape
     */
    public Circle(int xPosCenter, int yPosCenter, int radius, Color color) {
        super(xPosCenter, yPosCenter, radius * 2, radius * 2, color);
        this.radius = radius;
    }

    /**
     * Draws the shape on screen
     * @param g Graphics object to draw with
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        Stroke defaultStroke = graphics2D.getStroke();
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(getColor());
        graphics2D.drawOval(getXCenter() - radius,getYCenter() -
                radius ,radius * 2, radius * 2);
        graphics2D.setStroke(defaultStroke);
    }


    /**
     * Sets the radius of this Circle
     * to half the given width.
     * @param width  The new width of this Circle
     *               as an int.
     */
    @Override
    public void setWidth(int width) {
        this.radius = width / 2;
        super.setWidth(width);
    }

    /**
     * Sets the radius of this Circle
     * to half the given height.
     * @param height  The new height of this Circle
     *               as an int.
     */
    @Override
    public void setHeight(int height) {
        this.radius = height / 2;
        super.setHeight(height);
    }

    /**
     * Returns a deep copy of this Circle as a Shape.
     * @return a deep copy of this Circle as a Shape.
     */
    @Override
    public Shape copyShape() {
        return new Circle(getXCenter(), getYCenter(),
                radius, getColor());
    }
}