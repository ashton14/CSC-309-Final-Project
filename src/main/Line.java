package src.main;

import java.awt.*;
import java.awt.event.MouseEvent;
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
     * @Field parent - The link containing this line
     * @Field strokeWidth - stroke width of the line
     * @Field color - color of the line
     */
    private CodeBlock start;
    private CodeBlock end;
    private Link parent;
    private int strokeWidth;
    private Color color = Color.BLACK;

    /**
     * Assigns starting and ending CodeBlocks
     * Add CodeBlocks to inbound and outbound respectively
     * @param s - Starting CodeBlock
     * @param e - Ending CodeBlock
     */
    public Line(CodeBlock s, CodeBlock e) {
        strokeWidth = 2;
        start = s;
        end = e;
        s.addToOutbound(e);
        e.addToInbound(s);
    }
    /**
     * Setter function for parent link of this Line.
     */
    public void setLink(Link c) {
        parent = c;
    }
    /**
     * Getter function for parent link of this Line.
     * @return link containing this line
     */
    public Link getLink() { return (Link) parent; }
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
    public CodeBlock getEnd() { return end; }
    /**
     * Setter function for the last CodeBlock in this Line.
     */
    public void setEnd(CodeBlock c) {
        end = c;
    }
    /**
     * Setter function for the stroke width
     */
    public void setStrokeWidth(int strokeWidth){
        this.strokeWidth = strokeWidth;
    }
    /**
     * Draws the line, and arrow head
     * @param g - Graphics object used for drawing
     */
    /**
     * Sets the color of the line
     * @param c - Color to set the line to
     */
    public void setColor(Color c) {
        color = c;
    }
    /**
     * Gets the center of this line... used to draw the labels
     */
    public Point getCenter() {
        return new Point(((end.getXCenter() - start.getXCenter()) / 2) + start.getXCenter(), ((end.getYCenter() - start.getYCenter()) / 2) + start.getYCenter());
    }
    /**
     * Draw this line, from the start CodeBlock to end CodeBlock
     * @param g - The graphics object being drawn to
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // Sets the start and end positions
        Point startPos = new Point(start.getXCenter(), start.getYCenter() + (start.getHeight() / 2));
        Point endPos = new Point(end.getXCenter(), end.getYCenter() - (end.getHeight() / 2));

        // Set the color, stroke, and draw line and arrow head
        g.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth));
        g2d.drawLine(startPos.x, startPos.y, endPos.x, endPos.y);
    }
    /**
     * Split the given line into two, the arrow head always staying at the end
     * @param e - Mouse position when splitting line
     */
    public void split(MouseEvent e) {
        Node midpt = new Node(new Circle(e.getX(), e.getY(), 5, new Color(0, 0, 0)));
        Line newL = new Line(start, midpt);

        newL.setLink(parent);
        parent.addLine(newL);
        parent.addNode(midpt);

        start = midpt;
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
     * "Label" this line - (Replace this line with a LabelDecorator containing this line)
     * @param text - The label to be used
     */
    public void label(String text) {
        parent.replace(this, new LabelDecorator(text, this));
    }
}