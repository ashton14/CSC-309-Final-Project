package main;

import java.awt.*;

public class LineDecorator extends Line {
    private Line parentLine;

    public LineDecorator(Line innerLine){
        super(innerLine.getStart(), innerLine.getEnd());
    }
    @Override
    public CodeBlock getStart(){
        return super.getStart();
    }
    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}
