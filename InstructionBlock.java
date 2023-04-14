
/**
 * Represents an InstructionBlock which extends CodeBlock.
 *
 * @author Connor Hickey
 */
public class InstructionBlock extends CodeBlock {

    /**
     * Constructs a new InstructionBlock with the given shape (wrapper).
     * @param wrapper The Shape of the InstructionBlock.
     */
    public InstructionBlock(Shape wrapper) {
        super(wrapper,1,1);
    }
}
