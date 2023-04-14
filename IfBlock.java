/**
 * Represents an if statement with at most 1 inbound line,
 * at most 2 outbound lines and a shape to represent it.
 *
 * @author Aaron Bettencourt
 */
public class IfBlock extends CodeBlock {

    /**
     * Creates an instance of type IfBlock using a shape
     * to represent it.
     *
     * @param wrapper   The Shape or ShapeDecorator to represent
     *                  this IfBlock.
     */
    public IfBlock(Shape wrapper){
        super(wrapper, 1, 2);
    }
}
