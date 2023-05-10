package main;

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
    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }
}
