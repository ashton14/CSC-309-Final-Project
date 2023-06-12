package src.main;

import java.awt.*;
import java.awt.event.MouseEvent;
/**
 * Decorator class for labeled lines
 * @author Cameron Hardy
 */
public class LabelDecorator extends LineDecorator {
    private Line parentLine;
    private String label;

    /**
     * Constructs a new PerpendicularLineDecorator with the given inner shape.
     * @param lab       The string labeling this line
     * @param innerLine The line being decorated
     */
    public LabelDecorator(String lab, Line innerLine){
        super(innerLine);
        parentLine = innerLine;
        label = lab;
    }
    /**
     * Draws the decoration along with the line
     * @param g The Graphics object to reference in drawing
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        Point center = parentLine.getCenter();
        g.drawString(label, (int) center.getX(), (int) center.getY());

        ((DataRepository) DataRepository.getInstance()).notifyObservers();
    }
    /**
     * Splits the labeled line ... Labeled line always stays directly adjacent to base code block
     * @param e The mouse position when splitting
     */
    @Override
    public void split(MouseEvent e) {
        Node midpt = new Node(new Circle(e.getX(), e.getY(), 5, new Color(0, 0, 0)));
        Line newL = new Line(midpt, parentLine.getEnd());

        newL.setLink(parentLine.getLink());
        parentLine.getLink().addLine(newL);
        parentLine.getLink().addNode(midpt);
        setEnd(midpt);
        parentLine.setEnd(midpt);
        return;
    }
}
