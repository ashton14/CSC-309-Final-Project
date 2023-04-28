package main;

import java.awt.*;
/**
 * DotDecorator class responsible for dot inside EndBlock
 * @author Cameron Hardy
 */
public class DotDecorator extends ShapeDecorator {
    /**
     * Constructs a DotDecorator object, given shape as a basis
     * @param innerShape
     */
    public DotDecorator(Shape innerShape) {
        super(innerShape);
    }

    /**
     * Draw the decorator, a circle with radius slightly smaller than the decorators width
     * @param g - Graphics object used for drawing
     */
    @Override
    public void draw(Graphics g) {
        // Call draw for the base decorator, draw oval with rad slightly smaller than width
        super.draw(g);
        int radius = getWidth() / 2 - 15;
        g.fillOval(getXCenter() - radius, getYCenter() - radius,
                radius * 2, radius * 2);
    }
}