package main;

import java.awt.*;

/**
 * Represents a PerpendicularLineDecorator that decorates a Shape with perpendicular lines.
 * @author Connor Hickey
 */
public class PerpendicularLineDecorator extends ShapeDecorator {

    /**
     * Constructs a new PerpendicularLineDecorator with the given inner shape.
     * @param innerShape The Shape to be decorated.
     */
    public PerpendicularLineDecorator(Shape innerShape) {
        super(innerShape);
    }

    /**
     * Draws the decorated Shape along with perpendicular lines.
     * @param g The Graphics object to draw with.
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(Color.BLACK);
        int width = getWidth();
        int height = getHeight();
        g.drawLine(getXCenter() - width / 2, getYCenter() - height / 3,
                getXCenter() + width / 2, getYCenter() - height / 3);
        g.drawLine(getXCenter() - width / 3, getYCenter() + height / 2,
                getXCenter() - width / 3, getYCenter() - height / 2);
    }
}
