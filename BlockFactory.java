import java.awt.*;

/**
 * A class to be instantiated to make CodeBlocks
 * @author Aaron Bettencourt, Alex Banham
 */
public class BlockFactory {

    /**
     * Creates a code block with a given type and x and y coordinates.
     *
     * @param type   The type of CodeBlock to make as a String.
     * @param xPosCenter   The x position of the center of the CodeBlock to make.
     * @param yPosCenter   The y position of the center of the CodeBlock to make.
     * @return A CodeBlock of the specified type with the given x and y coordinates.
     */
    public CodeBlock makeBlock(String type, int xPosCenter, int yPosCenter){
        if(type == null)
            return null;
        TextDecorator textDecorator;
        Shape shape;
        switch (type) {
            case "If" -> {
                shape = new Diamond(xPosCenter, yPosCenter, 50, 60, Color.ORANGE);
                textDecorator = new TextDecorator(shape, "IF");
                return new IfBlock(textDecorator);
            }
            case "Instruction" -> {
                shape = new Rectangle(xPosCenter, yPosCenter, 100, 40, Color.BLUE);
                textDecorator = new TextDecorator(shape, "INSTRUCTION");
                return new InstructionBlock(textDecorator);
            }
            case "Loop" -> {
                shape = new Diamond(xPosCenter, yPosCenter, 50, 60, Color.PINK);
                textDecorator = new TextDecorator(shape, "LOOP");
                return new LoopBlock(textDecorator);
            }
            case "Print" -> {
                shape = new Parallelogram(xPosCenter, yPosCenter, 100, 40, Color.ORANGE);
                textDecorator = new TextDecorator(shape, "PRINT");
                return new PrintBlock(textDecorator);
            }
            case "Start" -> {
                shape = new Circle(xPosCenter, yPosCenter, 50, Color.GREEN);
                return new StartBlock(shape);
            }
            case "End" -> {
                shape = new Circle(xPosCenter, yPosCenter, 50, Color.RED);
                DotDecorator dotDecorator = new DotDecorator(shape);
                return new StopBlock(dotDecorator);
            }
            case "Variable" -> {
                shape = new Rectangle(xPosCenter, yPosCenter, 100, 40, Color.decode("#f5f5dc"));
                PerpendicularLineDecorator pld = new PerpendicularLineDecorator(shape);
                textDecorator = new TextDecorator(pld, "VARIABLE");
                return new VariableBlock(textDecorator);
            }
            case "Function" -> {
                shape = new Rectangle(xPosCenter, yPosCenter, 100, 40, Color.decode("#f5f5dc"));
                ParallelLineDecorator pld = new ParallelLineDecorator(shape);
                textDecorator = new TextDecorator(pld, "FUNCTION");
                return new FunctionBlock(textDecorator);
            }
        }
        return null;
    }
}
