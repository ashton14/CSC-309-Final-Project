package Repo.main;
/**
 * Defines the StopBlock class, which is to
 * be used to be instantiated to make objects
 * of type StopBlock to represent the stopping
 * point of a flowchart.
 *
 * @author Aaron Bettencourt
 */
public class StopBlock extends CodeBlock {

    /**
     * Constructs a CodeBlock with at most 1 inbound line
     * and 0 outbound lines that is representable by a shape.
     *
     * @param wrapper   A Shape representation of this StopBlock
     *                  that may contain more Shapes inside.
     * @param label A label to label this CodeBlock with.
     */
    public StopBlock(Shape wrapper, String label){
        super(wrapper, 1, 0, label);
    }

}