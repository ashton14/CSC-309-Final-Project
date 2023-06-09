package src.main;

/**
 * Represents a LoopBlock, which is a specialized type of CodeBlock for loops.
 * @author Connor Hickey, Aaron Bettencourt
 */
 public class LoopBlock extends CodeBlock {

    /**
     * Constructs a new LoopBlock with the given shape (drawable).
     * @param drawable The Shape of the LoopBlock.
     * @param label A label to label this CodeBlock with.
     */
    public LoopBlock(Shape drawable, String label) {
        super(drawable, INBOUND_INFINITE, 2, label);
    }

}
