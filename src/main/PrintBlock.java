package src.main;
/**
 * Print Code Block
 * @author Patrick Whitlock
 */
public class PrintBlock extends CodeBlock {
    /**
     * Creates a PrintBlock object
     * @param xPosCenter x location
     * @param yPosCenter y location
     * @param label A label to label this CodeBlock with.
     */
    public PrintBlock(Shape wrapper, String label) {
        super(wrapper, INBOUND_INFINITE, 1, label);
    }
}
