import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Represents an abstract CodeBlock that is both Serializable and Drawable.
 *
 * @author Connor Hickey
 */
public abstract class CodeBlock implements Serializable, Drawable {
    private Shape shape;

//    private ArrayList<Line> inboundLines;
//    private ArrayList<Line> outboundLines;

    private ArrayList<CodeBlock> inboundCodeBlocks;
    private ArrayList<CodeBlock> outboundCodeBlocks;
    private int maxInboundCount;
    private int maxOutboundCount;

    /**
     * Constructs a new CodeBlock with the given shape, max inbound count, and max outbound count.
     * @param shape The Shape of the CodeBlock.
     * @param maxInboundCount The maximum number of inbound connections.
     * @param maxOutboundCount The maximum number of outbound connections.
     */
    public CodeBlock(Shape shape, int maxInboundCount, int maxOutboundCount) {
        this.shape = shape;
        this.inboundCodeBlocks = new ArrayList<>();
        this.outboundCodeBlocks = new ArrayList<>();
        this.maxInboundCount = maxInboundCount;
        this.maxOutboundCount = maxOutboundCount;
    }

    /**
     * Sets the x-coordinate of the center of the CodeBlock.
     * @param x The x-coordinate.
     */
    public void setXCenter(int x) {
        shape.setXCenter(x);
    }

    /**
     * Sets the y-coordinate of the center of the CodeBlock.
     * @param y The y-coordinate.
     */
    public void setYCenter(int y) {
        shape.setYCenter(y);
    }

    /**
     * Gets the x-coordinate of the center of the CodeBlock.
     * @return The x-coordinate.
     */
    public int getXCenter() {
        return shape.getXCenter();
    }

    /**
     * Gets the y-coordinate of the center of the CodeBlock.
     * @return The y-coordinate.
     */
    public int getYCenter() {
        return shape.getYCenter();
    }

    /**
     * Returns if an inbound code block can be added.
     * @return True if an inbound code block can be added, false otherwise.
     */
    public boolean canAddIn() {
        return inboundCodeBlocks.size() < maxInboundCount;
    }

    /**
     * Returns if an outbound code block can be added.
     * @return True if an outbout code block can be added, false otherwise.
     */
    public boolean canAddOut() {
        return outboundCodeBlocks.size() < maxOutboundCount;
    }

    /**
     * Adds a CodeBlock to the inbound connections if possible.
     * @param block The CodeBlock to add.
     * @return true if the CodeBlock was added, false otherwise.
     */
    public boolean addToInbound(CodeBlock block) {
        if (inboundCodeBlocks.size() < maxInboundCount) {
            return inboundCodeBlocks.add(block);
        } else {
            return false;
        }
    }

    /**
     * Adds a CodeBlock to the outbound connections if possible.
     * @param block The CodeBlock to add.
     * @return true if the CodeBlock was added, false otherwise.
     */
    public boolean addToOutbound(CodeBlock block) {
        if (outboundCodeBlocks.size() < maxOutboundCount) {
            return outboundCodeBlocks.add(block);
        } else {
            return false;
        }
    }

    /**
     * Gets the inbound connections of the CodeBlock.
     * @return The ArrayList of inbound connections.
     */
    public ArrayList<CodeBlock> getInboundCodeBlocks() {
        return this.inboundCodeBlocks;
    }

    /**
     * Sets the inbound connections of the CodeBlock.
     * @param inboundCodeBlocks The ArrayList of inbound connections.
     */
    public void setInboundCodeBlocks(ArrayList<CodeBlock> inboundCodeBlocks) {
        this.inboundCodeBlocks = inboundCodeBlocks;
    }

    /**
     * Gets the outbound connections of the CodeBlock.
     * @return The ArrayList of outbound connections.
     */
    public ArrayList<CodeBlock> getOutboundCodeBlocks() {
        return this.outboundCodeBlocks;
    }

    /**
     * Sets the outbound connections of the CodeBlock.
     * @param outboundCodeBlocks The ArrayList of outbound connections.
     */
    public void setOutboundCodeBlocks(ArrayList<CodeBlock> outboundCodeBlocks) {
        this.outboundCodeBlocks = outboundCodeBlocks;
    }

    /**
     * Gets the maximum number of inbound connections for the CodeBlock.
     * @return The maximum number of inbound connections.
     */
    public int getMaxInboundCount() {
        return this.maxInboundCount;
    }

    /**
     * Sets the maximum number of inbound connections for the CodeBlock.
     * @param maxInboundCount The maximum number of inbound connections.
     */
    public void setMaxInboundCount(int maxInboundCount) {
        this.maxInboundCount = maxInboundCount;
    }

    /**
     * Gets the maximum number of outbound connections for the CodeBlock.
     * @return The maximum number of outbound connections.
     */
    public int getMaxOutboundCount() {
        return this.maxOutboundCount;
    }

    /**
     * Sets the maximum number of outbound connections for the CodeBlock.
     * @param maxOutboundCount The maximum number of outbound connections.
     */
    public void setMaxOutboundCount(int maxOutboundCount) {
        this.maxOutboundCount = maxOutboundCount;
    }

    /**
     * Draws the CodeBlock using the given Graphics object.
     * @param g The Graphics object to draw with.
     */
    public void draw(Graphics g){
        shape.draw(g);
    }

    /**
     * Determines if the given coordinates are within the bounds of the CodeBlock.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return true if within bounds, false otherwise.
     */
    public boolean isInBounds(int x, int y) {
        return shape.isInBounds(x, y);
    }

    /**
     * Returns the height of the CodeBlock's shape.
     * @return The height of the shape.
     */
    public int getHeight(){
        return shape.getHeight();
    }
}
