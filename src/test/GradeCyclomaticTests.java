package src.test;
import org.junit.Test;
import org.mockito.Mockito;
import src.main.*;
import src.main.GradeCyclomaticComplexity;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Class to test the Cyclomatic Complexity
 * expected cyclomatic complexity of a given flowchart.
 * Since cyclomatic complexity is independent of connections
 * between CodeBlocks, no connections need to be made.
 *
 * 74% statement coverage, only tests for if a solution
 * is right or wrong, not the verbose feedback.
 * @author Aaron Bettencourt
 */
public class GradeCyclomaticTests {


    /**
     * Tests the GradeCyclomaticComplexity.grade method to ensure
     * that a String that can't be converted to an integer results
     * in a false value being returned also that the program doesn't crash.
     */
    @Test
    public void testCyclomaticComplexityNonInteger(){
        ArrayList<CodeBlock> flowchart = new ArrayList<>();
        flowchart.add(Mockito.mock(StartBlock.class));
        flowchart.add(Mockito.mock(StopBlock.class));
        GradeCyclomaticComplexity grade = new GradeCyclomaticComplexity(flowchart, false);
        assertFalse(grade.grade("abcdefg"));
    }

    /**
     * Tests the GradeCyclomaticComplexity.grade method to ensure
     * a flowchart with no conditional (Loop or If) CodeBlocks
     * is 1.
     */
    @Test
    public void testCyclomaticComplexity1(){
        ArrayList<CodeBlock> flowchart = new ArrayList<>();
        flowchart.add(Mockito.mock(StartBlock.class));
        flowchart.add(Mockito.mock(StopBlock.class));
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
        flowchart.add(Mockito.mock(StartBlock.class));
        flowchart.add(Mockito.mock(IfBlock.class));
        flowchart.add(Mockito.mock(StopBlock.class));
        GradeCyclomaticComplexity grade = new GradeCyclomaticComplexity(flowchart, false);
        assertTrue(grade.grade("2"));
    }

    /**
     * Tests the GradeCyclomaticComplexity.grade method to ensure
     * a flowchart with 1 IfBlock is 2 when Verbose is true.
     */
    @Test
    public void testCyclomaticComplexity2IfVerbose(){
        ArrayList<CodeBlock> flowchart = new ArrayList<>();
        flowchart.add(Mockito.mock(StartBlock.class));
        flowchart.add(Mockito.mock(IfBlock.class));
        flowchart.add(Mockito.mock(StopBlock.class));
        GradeCyclomaticComplexity grade = new GradeCyclomaticComplexity(flowchart, true);
        assertTrue(grade.grade("2"));
    }

    /**
     * Tests the GradeCyclomaticComplexity.grade method to ensure
     * a flowchart with 1 LoopBlock is 2.
     */
    @Test
    public void testCyclomaticComplexity2Loop(){
        ArrayList<CodeBlock> flowchart = new ArrayList<>();
        flowchart.add(Mockito.mock(StartBlock.class));
        flowchart.add(Mockito.mock(LoopBlock.class));
        flowchart.add(Mockito.mock(StopBlock.class));
        GradeCyclomaticComplexity grade = new GradeCyclomaticComplexity(flowchart, false);
        assertTrue(grade.grade("2"));
    }

    /**
     * Tests the GradeCyclomaticComplexity.grade method to ensure
     * a flowchart with 1 LoopBlock is 2 when verbose is true.
     */
    @Test
    public void testCyclomaticComplexity2LoopVerbose(){
        ArrayList<CodeBlock> flowchart = new ArrayList<>();
        flowchart.add(Mockito.mock(StartBlock.class));
        flowchart.add(Mockito.mock(LoopBlock.class));
        flowchart.add(Mockito.mock(StopBlock.class));
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
        flowchart.add(Mockito.mock(StartBlock.class));
        for(int i = 0; i < 8; ++i){
            flowchart.add(Mockito.mock(LoopBlock.class));
            flowchart.add(Mockito.mock(IfBlock.class));
        }
        flowchart.add(Mockito.mock(StopBlock.class));
        GradeCyclomaticComplexity grade = new GradeCyclomaticComplexity(flowchart, false);
        assertTrue(grade.grade("17"));
    }
}
