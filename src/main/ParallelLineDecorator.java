package src.main;

import java.awt.*;

/**
 * Represents a ParallelLineDecorator, which is a ShapeDecorator that adds parallel lines
 * to an inner Shape.
 * @author Connor Hickey
 */
public class ParallelLineDecorator extends ShapeDecorator {
    /**
     * Constructs a new ParallelLineDecorator with the given inner Shape.
     * @param innerShape The inner Shape to be decorated.
     */
    public ParallelLineDecorator(Shape innerShape) {
        super(innerShape);
    }


    /**
     * Draws the inner Shape and the parallel lines using the given Graphics object.
     * @param g The Graphics object to draw with.
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(Color.BLACK);
        Shape shape = getInnerShapeCopy();

        int width = shape.getWidth();
        int height = shape.getHeight();
        g.drawLine(getXCenter() - width / 3, getYCenter() - height / 2,
                getXCenter() - width / 3, getYCenter() + height / 2);

        g.drawLine(getXCenter() + width / 3, getYCenter() - height / 2,
                getXCenter() + width / 3, getYCenter() + height / 2);
    }
}
