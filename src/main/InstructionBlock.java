package src.main;
/**
 * Represents an InstructionBlock which extends CodeBlock.
 *
 * @author Connor Hickey
 */
public class InstructionBlock extends CodeBlock {

    /**
     * Constructs a new InstructionBlock with the given shape (wrapper).
     * @param wrapper The Shape of the InstructionBlock.
     * @param label A label to label this CodeBlock with.
     */
    public InstructionBlock(Shape wrapper, String label) {
        super(wrapper,INBOUND_INFINITE,1, label);
    }
}
