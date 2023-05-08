package main;

import java.awt.*;

/**
 * A class to be instantiated to decorate Shapes in the center
 * with text.
 * @deprecated
 * @author Aaron Bettencourt
 */
public class TextDecorator extends ShapeDecorator {

    private String text;


    /**
     * Instantiates an object of type TextDecorator given
     * a shape to decorate and a String of text.
     * @param innerShape   The Shape to decorate.
     * @param text   The String of text to decorate with.
     */
    public TextDecorator(Shape innerShape, String text) {
        super(innerShape);
        if(text == null){
            throw new NullPointerException("TextDecorator: text can't be null");
        }
        this.text = text;
    }

    /**
     * Draws text on top of the Shape contained within this TextDecorator.
     * @param g   The Graphics instance to draw on top of.
     */
    @Override
    public void draw(Graphics g) {
        int textWidth = g.getFontMetrics().stringWidth(text);
        super.draw(g);
        g.setColor(Color.BLACK);
        g.drawString(text,  (getXCenter() - textWidth / 2), getYCenter());
    }

}
