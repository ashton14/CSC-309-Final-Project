
/**
 * Represents a LoopBlock, which is a specialized type of CodeBlock for loops.
 * @author Connor Hickey
 */
 public class LoopBlock extends CodeBlock {

    /**
     * Constructs a new LoopBlock with the given shape (drawable).
     * @param drawable The Shape of the LoopBlock.
     */
    public LoopBlock(Shape drawable) {
        super(drawable, 2, 2);
    }

}
