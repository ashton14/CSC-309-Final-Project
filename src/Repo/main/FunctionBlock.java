package Repo.main;

/**
 * @author Alex Banham
 * Creates a FunctionBlock object
 */
public class FunctionBlock extends CodeBlock {

    /**
     * Constructs a new FunctionBlock with the given shape (wrapper).
     * @param wrapper The Shape of the FunctionBlock.
     */
    public FunctionBlock(Shape wrapper, String label) {
        super(wrapper,1,1, label);
    }

}
