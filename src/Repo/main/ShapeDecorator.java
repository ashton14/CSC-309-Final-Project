package Repo.main;

import java.awt.*;

/**
 * Abstract class to be extended to represent a ShapeDecorator
 * that can decorate itself or another Shape.
 *
 * @author Aaron Bettencourt
 */
public abstract class ShapeDecorator extends Shape {
    private Shape innerShape;

    /**
     * Constructs an instance of ShapeDecorator given a Shape
     * to decorate.
     *
     * @param innerShape   The Shape to decorate.
     */
    public ShapeDecorator(Shape innerShape){
        super(innerShape.getXCenter(), innerShape.getYCenter(),
                innerShape.getWidth(), innerShape.getHeight(),
                innerShape.getColor());
        this.innerShape = innerShape;
    }

    /**
     * Draws the Shape(s) inside of this ShapeDecorator
     * onto the screen.
     *
     * @param g   The Graphics instance to draw on top of.
     */
    @Override
    public void draw(Graphics g) {
        innerShape.draw(g);
    }

    /**
     * Sets the center x position of this ShapeDecorator to a new value.
     *
     * @param xPosCenter   The new value of the center x position
     *                     of this ShapeDecorator.
     */
    @Override
    public void setXCenter(int xPosCenter) {
        super.setXCenter(xPosCenter);
        innerShape.setXCenter(xPosCenter);
    }

    /**
     * Sets the center y position of this ShapeDecorator to a new value.
     *
     * @param yPosCenter   The new value of the center y position
     *                     of this ShapeDecorator.
     */
    @Override
    public void setYCenter(int yPosCenter) {
        super.setYCenter(yPosCenter);
        innerShape.setYCenter(yPosCenter);
    }

    /**
     * Creates a deep copy of the innermost Shape within this ShapeDecorator.
     * @return   A deep copy of the innermost Shape within this ShapeDecorator
     * as an object of type Shape.
     */
    @Override
    public Shape copyShape(){
        return innerShape.copyShape();
    }
}
