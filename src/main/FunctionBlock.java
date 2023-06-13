package src.main;

/**
 * @author Alex Banham, Aaron Bettencourt
 * Creates a FunctionBlock object
 */
public class FunctionBlock extends CodeBlock {

    /**
     * Constructs a new FunctionBlock with the given shape (wrapper).
     * @param wrapper The Shape of the FunctionBlock.
     */
    public FunctionBlock(Shape wrapper, String label) {
        super(wrapper,INBOUND_INFINITE,1, label);
    }

}
