package src.main;

import java.awt.*;

public class LineDecorator extends Line {
    /**
     * @Field parentLine - Line being decorated
     */
    private Line parentLine;

    public LineDecorator(Line innerLine){
        super(innerLine.getStart(), innerLine.getEnd());
    }
    /**
     * Get the starting CodeBlock of the parentLine
     */
    @Override
    public Link getConnection() {
        return super.getConnection();
    }
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
