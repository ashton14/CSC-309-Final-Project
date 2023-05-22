package src.main;

/**
 * @author Alex Banham
 * Creates a VariableBlock object
 */
public class VariableBlock extends CodeBlock {

    /**
     * Constructs a new VariableBlock with the given shape (wrapper).
     * @param wrapper The Shape of the VariableBlock.
     * @param label A label to label this CodeBlock with.
     */
    public VariableBlock(Shape wrapper, String label) {
        super(wrapper,1,1, label);
    }
}
