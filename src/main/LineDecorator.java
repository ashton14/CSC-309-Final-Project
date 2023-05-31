package src.main;

import java.awt.*;

public class LineDecorator extends Line {
    /**
     * @Field parentLine - Line being decorated
     */
    private Line parentLine;
    /**
     * Constructor for objects of type Shape to construct shapes using a pair of x
     * and y coordinates, width, height and color.
     *
     * @param innerLine - the line being decorated (parentLine)
     */
    public LineDecorator(Line innerLine){
        super(innerLine.getStart(), innerLine.getEnd());
    }
    /**
     * Get the starting CodeBlock of the parentLine
     */
    @Override
    public CodeBlock getStart(){
        return super.getStart();
    }
    /**
     * Draws the decorated line
     * @param g - The graphics object to draw onto
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}
