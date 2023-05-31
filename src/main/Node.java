package src.main;
import java.awt.*;

public class Node extends CodeBlock {

    /**
     * Constructs a new CodeBlock with the given shape, max inbound count, and max outbound count.
     *
     * @param shape            The Shape of the CodeBlock.
     */
    public Node(Shape shape) {
        super(shape, 1, 1, "");
    }
    /**
     * Draw this node
     * @param g - The graphics object being drawn to
     */
    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}
