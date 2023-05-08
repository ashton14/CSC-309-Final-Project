package Repo.main;

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
        Shape shape;
        switch (type) {
            case "If" -> {
                shape = new Diamond(xPosCenter, yPosCenter, 50, 60, Color.ORANGE);
                return new IfBlock(shape, "IF");
            }
            case "Instruction" -> {
                shape = new Rectangle(xPosCenter, yPosCenter, 100, 40, Color.BLUE);
                return new InstructionBlock(shape, "INSTRUCTION");
            }
            case "Loop" -> {
                shape = new Diamond(xPosCenter, yPosCenter, 50, 60, Color.PINK);
                return new LoopBlock(shape, "LOOP");
            }
            case "Print" -> {
                shape = new Parallelogram(xPosCenter, yPosCenter, 50, 60,Color.ORANGE);
                return new PrintBlock(shape, "PRINT");
            }
            case "Start" -> {
                shape = new Circle(xPosCenter, yPosCenter, 50, Color.GREEN);
                return new StartBlock(shape, "START");
            }
            case "Stop" -> {
                shape = new Circle(xPosCenter, yPosCenter, 50, Color.RED);
                DotDecorator dotDecorator = new DotDecorator(shape);
                return new StopBlock(dotDecorator, "STOP");
            }
            case "Variable" -> {
                shape = new Rectangle(xPosCenter, yPosCenter, 100, 40, Color.decode("#f5f5dc"));
                PerpendicularLineDecorator pld = new PerpendicularLineDecorator(shape);
                return new VariableBlock(pld, "VARIABLE");
            }
            case "Function" -> {
                shape = new Rectangle(xPosCenter, yPosCenter, 100, 40, Color.decode("#f5f5dc"));
                ParallelLineDecorator pld = new ParallelLineDecorator(shape);
                return new FunctionBlock(pld, "FUNCTION");
            }
        }
        return null;
    }
}
