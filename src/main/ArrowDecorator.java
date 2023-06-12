package src.main;

import java.awt.*;
/**
 * Decorator class for the arrowhead at the end of a Link
 * @author Cameron Hardy
 */
public class ArrowDecorator extends LineDecorator {

    private Line parentLine;

    private int arrowLen = 15;

    public ArrowDecorator(Line innerLine){
        super(innerLine);
        parentLine = innerLine;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        Graphics2D g2d = (Graphics2D) g;

        CodeBlock start = super.getStart();
        CodeBlock end = super.getEnd();

        Point startPos = new Point(start.getXCenter(), start.getYCenter() + (start.getHeight() / 2));
        Point endPos = new Point(end.getXCenter(), end.getYCenter() - (end.getHeight() / 2));

        // Calculates the angle the line makes
        double theta = Math.atan2((double) (endPos.y - startPos.y), (double) (endPos.x - startPos.x));

        // Calculate the points for arrow head drawing
        Point ahead1 = new Point(endPos.x - (int) (arrowLen * Math.cos(theta - Math.toRadians(45))),
                endPos.y - (int) (arrowLen * Math.sin(theta - Math.toRadians(45))));
        Point ahead2 = new Point(endPos.x - (int) (arrowLen * Math.cos(theta + Math.toRadians(45))),
                endPos.y - (int) (arrowLen * Math.sin(theta + Math.toRadians(45))));

        g2d.drawLine(endPos.x, endPos.y, ahead1.x, ahead1.y);
        g2d.drawLine(endPos.x, endPos.y, ahead2.x, ahead2.y);
        ((DataRepository) DataRepository.getInstance()).notifyObservers();
    }
}
