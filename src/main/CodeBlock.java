package src.main;
import java.awt.*;
import java.util.ArrayList;


/**
 * Represents an abstract CodeBlock that is both Serializable and Drawable.
 *
 * @author Connor Hickey
 */
public abstract class CodeBlock implements Drawable {
    private Shape shape;
    private String text;
    private ArrayList<CodeBlock> inboundCodeBlocks;
    private ArrayList<CodeBlock> outboundCodeBlocks;

    private ArrayList<Boolean> outboundCodeBlocksMarkings;
    private int maxInboundCount;
    private int maxOutboundCount;
    private ArrayList<Boolean> discovered;

    public static final int INBOUND_INFINITE = -1;

    /**
     * Constructs a new CodeBlock with the given shape, max inbound count, and max outbound count.
     * @param shape The Shape of the CodeBlock.
     * @param maxInboundCount The maximum number of inbound connections.
     * @param maxOutboundCount The maximum number of outbound connections.
     */
    public CodeBlock(Shape shape, int maxInboundCount, int maxOutboundCount, String text) {
        this.shape = shape;
        this.inboundCodeBlocks = new ArrayList<>();
        this.outboundCodeBlocks = new ArrayList<>();
        this.maxInboundCount = maxInboundCount;
        this.maxOutboundCount = maxOutboundCount;
        this.text = text;
        outboundCodeBlocksMarkings = new ArrayList<>();
    }

    /**
     * Returns an ArrayList of type Boolean
     * with one index per inbound CodeBlock.
     * @return   An ArrayList of type Boolean
     *           with one index per inbound CodeBlock.
     */
    public ArrayList<Boolean> getDiscovered(){
        if(discovered == null){
            resetDiscovered();
        }
        return discovered;
    }

    /**
     * Creates a new ArrayList of type
     * Boolean with one index per inbound CodeBlock
     * and all values set to false.
     */
    public void resetDiscovered(){
        discovered = new ArrayList<>();
        for (int i = 0; i < inboundCodeBlocks.size(); i++) {
            discovered.add(false);
        }
    }

    /**
     * Removes all connections from this CodeBlock and to other CodeBlocks.
     */
    public void removeAllConnections(){
        for (CodeBlock inboundCodeBlock : inboundCodeBlocks) {
            inboundCodeBlock.outboundCodeBlocks.remove(this);
        }
        for (CodeBlock outboundCodeBlock : outboundCodeBlocks) {
            outboundCodeBlock.inboundCodeBlocks.remove(this);
        }
        inboundCodeBlocks.clear();
        outboundCodeBlocks.clear();
        outboundCodeBlocksMarkings.clear();
    }

    /**
     * Removes all connections to and from this CodeBlock that match a given
     * reference of type CodeBlock.
     * @param codeBlock   The CodeBlock to remove outgoing references to from this
     *                    CodeBlock and to remove incoming connections to this CodeBlock
     *                    from.
     */
    public void removeConnection(CodeBlock codeBlock){
        ArrayList<Boolean> newOutboundMarkings = new ArrayList<>();
        for (CodeBlock inboundCodeBlock : inboundCodeBlocks) {
            if(inboundCodeBlock == codeBlock) {
                inboundCodeBlock.outboundCodeBlocks.remove(this);
            }
        }
        for(int i = 0; i < outboundCodeBlocks.size(); ++i){
            if(outboundCodeBlocks.get(i) == codeBlock) {
                outboundCodeBlocks.get(i).inboundCodeBlocks.remove(this);
            } else {
                newOutboundMarkings.add(outboundCodeBlocksMarkings.get(i));
            }
        }
        inboundCodeBlocks.remove(codeBlock);
        outboundCodeBlocks.remove(codeBlock);
        outboundCodeBlocksMarkings = newOutboundMarkings;
    }

    /**
     * Setter method to set the text of this CodeBlock.
     * @param text   The text to change the text of this
     *               CodeBlock to.
     */
    public void setText(String text){
        if(text == null){
            return;
        }
        Font font = new Font("Segoe", Font.PLAIN, 12);
        Canvas canvas = new Canvas();
        FontMetrics fontMetrics = canvas.getFontMetrics(font);

        int textWidth = fontMetrics.stringWidth(text);
        if(shape.getWidth() < textWidth + 5){
            shape.setWidth(textWidth + 5);
        }

        this.text = text;
    }

    /**
     * Setter method to set the text of this CodeBlock.
     */
    public String getText(){
        return text;
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
        if(maxInboundCount == INBOUND_INFINITE){
            return true;
        }
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
        if (canAddIn(block)) {
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
        if (canAddOut(block)) {
            outboundCodeBlocksMarkings.add(true);
            return outboundCodeBlocks.add(block);
        } else {
            return false;
        }
    }

    /**
     * Marks an outbound connection to a given CodeBlock as
     * true or false.
     * @param block   The CodeBlock of the outbound connection to mark.
     * @param marking   The marking (true or false) value to give to
     *                  the connection with the given CodeBlock.
     */
    public void setOutboundCodeBlocksMarking(CodeBlock block, Boolean marking){
        if(block == null){
            return;
        }
        int index = outboundCodeBlocks.indexOf(block);
        if(index != -1){
            outboundCodeBlocksMarkings.set(index, marking);
        }
    }

    /**
     * Gets the marking of an outbound connection to a given CodeBlock.
     * @param block   The outbound CodeBlock connection to get the marking for.
     * @return   A boolean representing the marking of the connection to the
     * given outbound CodeBlock.
     */
    public Boolean getOutBoundCodeBlockMarking(CodeBlock block){
        if(block == null){
            return null;
        }
        int index = outboundCodeBlocks.indexOf(block);
        if(index == -1) {
            return null;
        }
        return outboundCodeBlocksMarkings.get(index);
    }

    public ArrayList<Boolean> getOutBoundCodeBlockMarkings(){
        return outboundCodeBlocksMarkings;
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
        int textWidth = g.getFontMetrics().stringWidth(text);
        shape.draw(g);
        g.setColor(Color.BLACK);
        g.drawString(text,  (getXCenter() - textWidth / 2), getYCenter());
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

    /**
     * Returns the height of the CodeBlock's shape.
     * @return The height of the shape.
     */
    public int getWidth(){
        return shape.getWidth();
    }

    /**
     * Creates a deep copy of this Shape.
     * @return A deep copy of this Shape as a Shape.
     */
    public Shape copyShape(){
        return shape.copyShape();
    }

    /**
     * Returns the Shape object used by this CodeBlock.
     * @return The Shape object used by this Code
     */
    public Shape getShape(){
        return shape.copyShape();
    }

    /**
     * Creates a String representation of this CodeBlock's type.
     * @return a String representation of this CodeBlock's type.
     */
    @Override
    public String toString(){
        String str = getClass().toString();
        str = str.replace("class src.main.", "");
        str = str.replace("Block", "");
        return str;
    }

    /**
     * Returns if an inbound code block can be added.
     * @return True if an inbound code block can be added, false otherwise.
     */
    public boolean canAddIn(CodeBlock block) {
        if(maxInboundCount == INBOUND_INFINITE && !inboundCodeBlocks.contains(block)){
            return true;
        }
        return (inboundCodeBlocks.size() < maxInboundCount &&
                !inboundCodeBlocks.contains(block));
    }

    /**
     * Returns if an outbound code block can be added.
     * @return True if an outbout code block can be added, false otherwise.
     */
    public boolean canAddOut(CodeBlock block) {
        return (outboundCodeBlocks.size() < maxOutboundCount &&
                !outboundCodeBlocks.contains(block));
    }
}
