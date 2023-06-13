package src.main;
/**
 * Print Code Block
 * @author Patrick Whitlock, Aaron Bettencourt
 */
public class PrintBlock extends CodeBlock {
    /**
     * Creates a PrintBlock object
     * @param wrapper   The Shape or ShapeDecorator to represent
     *                  this PrintBlock.
     * @param label A label to label this CodeBlock with.
     */
    public PrintBlock(Shape wrapper, String label) {
        super(wrapper, INBOUND_INFINITE, 1, label);
    }
}
