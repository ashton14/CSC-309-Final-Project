package src.test;
import src.main.*;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Class to test the Cyclomatic Complexity
 * expected cyclomatic complexity of a given flowchart.
 * Since cyclomatic complexity is independent of connections
 * between CodeBlocks, no connections need to be made.
 */
public class GradeCyclomaticTests {

    /**
     * Helper method to create a Circle to construct a CodeBlock.
     * @return   A Circle to place inside a CodeBlock.
     */
    private Circle makeCircle(){
        return new Circle(0,0, 0, Color.BLACK);
    }

    /**
     * Helper method to create a Diamond to construct a CodeBlock.
     * @return   A Diamond to place inside a CodeBlock.
     */
    private Diamond makeDiamond(){
        return new Diamond(0,0, 0, 0, Color.BLACK);
    }

    /**
     * Tests the GradeCyclomaticComplexity.grade method to ensure
     * a flowchart with no conditional (Loop or If) CodeBlocks
     * is 1.
     */
    @Test
    public void testCyclomaticComplexity1(){
        ArrayList<CodeBlock> flowchart = new ArrayList<>();
        flowchart.add(new StartBlock(makeCircle(), ""));
        flowchart.add(new StopBlock(makeCircle(), ""));
        GradeCyclomaticComplexity grade = new GradeCyclomaticComplexity(flowchart, false);
        assertTrue(grade.grade("1"));
    }

    /**
     * Tests the GradeCyclomaticComplexity.grade method to ensure
     * a flowchart with 1 IfBlock is 2.
     */
    @Test
    public void testCyclomaticComplexity2If(){
        ArrayList<CodeBlock> flowchart = new ArrayList<>();
        flowchart.add(new StartBlock(makeCircle(), ""));
        flowchart.add(new IfBlock(makeDiamond(), ""));
        flowchart.add(new StopBlock(makeCircle(), ""));
        GradeCyclomaticComplexity grade = new GradeCyclomaticComplexity(flowchart, false);
        assertTrue(grade.grade("2"));
    }

    /**
     * Tests the GradeCyclomaticComplexity.grade method to ensure
     * a flowchart with 1 LoopBlock is 2.
     */
    @Test
    public void testCyclomaticComplexity2Loop(){
        ArrayList<CodeBlock> flowchart = new ArrayList<>();
        flowchart.add(new StartBlock(makeCircle(), ""));
        flowchart.add(new LoopBlock(makeDiamond(), ""));
        flowchart.add(new StopBlock(makeCircle(), ""));
        GradeCyclomaticComplexity grade = new GradeCyclomaticComplexity(flowchart, false);
        assertTrue(grade.grade("2"));
    }

    /**
     * Tests the GradeCyclomaticComplexity.grade method to ensure
     * a flowchart with 8 LoopBlocks and 8 IfBlocks is 17.
     */
    @Test
    public void testCyclomaticComplexityCombo17(){
        ArrayList<CodeBlock> flowchart = new ArrayList<>();
        flowchart.add(new StartBlock(makeCircle(), ""));
        for(int i = 0; i < 8; ++i){
            flowchart.add(new LoopBlock(makeDiamond(), ""));
            flowchart.add(new IfBlock(makeDiamond(), ""));
        }
        flowchart.add(new StopBlock(makeCircle(), ""));
        GradeCyclomaticComplexity grade = new GradeCyclomaticComplexity(flowchart, false);
        assertTrue(grade.grade("17"));
    }
}
