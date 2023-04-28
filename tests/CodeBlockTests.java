package tests;

import static org.junit.Assert.*;

import main.Rectangle;
import main.Circle;
import main.Diamond;
import main.Shape;
import main.*;
import org.junit.Test;
import java.awt.*;

public class CodeBlockTests {

    @Test
    public void testAddingAndRemovingConnections() {
        Shape rect = new Rectangle(100, 100, 50, 50, Color.BLACK);
        Shape circ = new Circle(100, 100, 50, Color.BLACK);
        Shape diam = new Diamond(100, 100, 50, 50, Color.BLACK);
        CodeBlock funcBlock = new FunctionBlock(rect, "");
        CodeBlock startBlock = new StartBlock(circ, "");
        CodeBlock loopBlock = new LoopBlock(diam, "");
        funcBlock.addToOutbound(loopBlock);
        funcBlock.addToInbound(startBlock);
        assertFalse(funcBlock.canAddIn());
        assertFalse(funcBlock.canAddOut());
        assertTrue(funcBlock.getOutboundCodeBlocks().contains(loopBlock));
        assertTrue(funcBlock.getInboundCodeBlocks().contains(startBlock));
        funcBlock.removeConnection(startBlock);
        assertTrue(funcBlock.canAddIn());
        loopBlock.addToOutbound(funcBlock);
        funcBlock.removeAllConnections();
        assertTrue(funcBlock.canAddIn());
        assertTrue(funcBlock.canAddOut());
    }

    @Test public void testIsInBounds() {
        Shape rect = new Rectangle(100, 100, 50, 50, Color.BLACK);
        CodeBlock funcBlock = new FunctionBlock(rect, "");
        assertTrue(funcBlock.isInBounds(75, 124));
        assertFalse(funcBlock.isInBounds(75, 125));
        assertFalse(funcBlock.isInBounds(74, 124));
    }


}
