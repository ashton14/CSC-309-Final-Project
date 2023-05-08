package Repo.main;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Line class responsible for drawing connections between two CodeBlocks, with directionality
 * @author Cameron Hardy
 * @author Connor Hickey
 */
public class Line implements Drawable {
    /**
     * @Field start - CodeBlock being drawn from
     * @Field end - CodeBlock being drawn to
     */
    private CodeBlock start;
    private CodeBlock end;

    private Color color = Color.BLACK;

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
        g.setColor(color);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(startPos.x, startPos.y, endPos.x, endPos.y);
        g2d.drawLine(endPos.x, endPos.y, ahead1.x, ahead1.y);
        g2d.drawLine(endPos.x, endPos.y, ahead2.x, ahead2.y);
    }

    /**
     * Returns the distance from the line to the given point
     * @param x - x coordinate of the point
     * @param y - y coordinate of the point
     * @return The distance from the line to the given point
     */

    double pointDistanceFromLine(double x, double y) {
        Point2D p = new Point((int) x, (int) y);

        Point startPos = new Point(start.getXCenter(), start.getYCenter() + (start.getHeight() / 2));
        Point endPos = new Point(end.getXCenter(), end.getYCenter() - (end.getHeight() / 2));

        double x0 = p.getX();
        double y0 = p.getY();
        double x1 = startPos.getX();
        double y1 = startPos.getY();
        double x2 = endPos.getX();
        double y2 = endPos.getY();

        double lineLengthSquared = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
        if (lineLengthSquared == 0) {
            return p.distance(x1, y1);
        }

        double dotProduct = ((x0 - x1) * (x2 - x1) + (y0 - y1) * (y2 - y1)) / lineLengthSquared;

        double t = Math.max(0, Math.min(1, dotProduct));

        double nearestX = x1 + t * (x2 - x1);
        double nearestY = y1 + t * (y2 - y1);

        return p.distance(nearestX, nearestY);
    }

    /**
     * Sets the color of the line
     * @param c - Color to set the line to
     */
    public void setColor(Color c) {
        color = c;
    }
}