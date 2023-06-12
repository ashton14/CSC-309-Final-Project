package src.main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
/**
 * Link class - Encapsulates connections into lines that can be split
 * @author Cameron Hardy
 */
public class Link extends Line {
    private static int LINE_SELECT_DISTANCE = 8;
    private static int DEFAULT_STROKE = 2;
    /**
     * @Field lines - Lines contained in this link
     * @Field nodes - Nodes contained in this link
     * @Field start - The starting CodeBlock
     * @Field end - The ending CodeBlock
     */
    private ArrayList<Line> lines;
    private ArrayList<Node> nodes;
    private CodeBlock start = null;
    private CodeBlock end = null;

    /**
     * Constructs a new Link
     * with starting and ending CodeBlocks s and e
     * @param s - Starting CodeBlock
     * @param e - Ending CodeBlock
     */
    public Link(CodeBlock s, CodeBlock e) {
        super(s, e);
        start = s;
        end = e;
        lines = new ArrayList<Line>();
        nodes = new ArrayList<Node>();
    }
    public void setSelected() {
        for(int i = 0; i < lines.size(); i++) {
            lines.get(i).setStrokeWidth(DEFAULT_STROKE * 2);
        }
    }
    public void deSelect() {
        System.out.println("deselected");
        for(int i = 0; i < lines.size(); i++) {
            lines.get(i).setStrokeWidth(DEFAULT_STROKE);
        }
    }
    /**
     * Draw this Link, namely all lines and nodes
     * @param g - The graphics object being drawn to
     */
    @Override
    public void draw(Graphics g) {
        for(Line l : lines) {
            l.draw(g);
        }
        for(Node n : nodes) {
            n.draw(g);
        }
    }
    /**
     * Add a new Line to this Link
     * @param l - The Line to be added
     */
    public void addLine(Line l) {
        lines.add(l);
    }
    /**
     * Add a new Node to this Link
     * @param n - The Node to be added
     */
    public void addNode(Node n) {
        nodes.add(n);
    }
    /**
     * Checks if any lines are close enough to the click to be selected
     * @param e - The mouse event used
     * @return Line - The line that was clicked
     */
    public Line selectLine(MouseEvent e) {
        for(Line l : lines) {
            if(l.pointDistanceFromLine(e.getX(), e.getY()) < LINE_SELECT_DISTANCE) {
                return l;
            }
        }
        return null;
    }
    /**
     * Replace a Line in this Link with a new one (Only ever used to label lines)
     * @param oldL - The old Line being replaced/labeled
     * @param newL - The LabelDecorator replacing the old line
     */
    public void replace(Line oldL, LabelDecorator newL) {
        lines.remove(oldL);
        lines.add(newL);
    }
    /**
     * Getter method for all Nodes in Link
     * @return ArrayList<Node> - Nodes
     */
    public ArrayList<Node> getNodes() { return nodes; }
    /**
     * Getter method for start of Link
     * @return CodeBlock - starting CodeBlock
     */
    public CodeBlock getStart() { return start; }
    /**
     * Getter method for end of Link
     * @return CodeBlock - ending CodeBlock
     */
    public CodeBlock getEnd() { return end; }
    public void disconnect() {
        start.removeConnection(end);
        end.removeConnection(start);
    }
}
