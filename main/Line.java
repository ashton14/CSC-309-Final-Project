package main;

import java.awt.*;

/**
 * Line class responsible for drawing connections between two CodeBlocks, with directionality
 * @author Cameron Hardy
 */
public class Line implements Drawable {
    /**
     * @Field start - CodeBlock being drawn from
     * @Field end - CodeBlock being drawn to
     */
    private CodeBlock start;
    private CodeBlock end;

    /**
     * @Field arrowLen - Defines the length of the arrow head lines
     */
    private int arrowLen = 15;

    /**
     * Assigns starting and ending CodeBlocks
     * Add CodeBlocks to inbound and outbound respectively
     * @param s - Starting CodeBlock
     * @param e - Ending CodeBlock
     */
    public Line(CodeBlock s, CodeBlock e) {
        start = s;
        end = e;
        s.addToOutbound(e);
        e.addToInbound(s);
    }

    /**
     * Getter function for the first CodeBlock in this Line.
     * @return The first CodeBlock in this Line.
     */
    public CodeBlock getStart(){
        return start;
    }

    /**
     * Getter function for the last CodeBlock in this Line.
     * @return The last CodeBlock in this Line.
     */
    public CodeBlock getEnd(){
        return end;
    }

    /**
     * Draws the line, and arrow head
     * @param g - Graphics object used for drawing
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Sets the start and end positions
        Point startPos = new Point(start.getXCenter(), start.getYCenter() + (start.getHeight() / 2));
        Point endPos = new Point(end.getXCenter(), end.getYCenter() - (end.getHeight() / 2));

        // Calculates the angle the line makes
        double theta = Math.atan2((double) (endPos.y - startPos.y), (double)(endPos.x - startPos.x));
        // Calculate the points for arrow head drawing
        Point ahead1 = new Point(endPos.x - (int) (arrowLen * Math.cos(theta - Math.toRadians(45))),
                endPos.y - (int) (arrowLen * Math.sin(theta - Math.toRadians(45))));
        Point ahead2 = new Point(endPos.x - (int) (arrowLen * Math.cos(theta + Math.toRadians(45))),
                endPos.y - (int) (arrowLen * Math.sin(theta + Math.toRadians(45))));

        // Set the color, stroke, and draw line and arrow head
        g.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(startPos.x, startPos.y, endPos.x, endPos.y);
        g2d.drawLine(endPos.x, endPos.y, ahead1.x, ahead1.y);
        g2d.drawLine(endPos.x, endPos.y, ahead2.x, ahead2.y);
    }
}