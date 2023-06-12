package src.test;

import org.junit.Test;
import org.mockito.Mockito;
import src.main.*;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing Flowchart Grading.
 * 77% statement coverage, covers everything except
 * for one edgecase and verbose output verification.
 * Both of these were verified through manual testing.
 */
public class GradeFlowchartTest {

    /**
     * Helper method to create a connection between two mocked CodeBlocks.
     * @param mockBlock1   The CodeBlock at the start of the connection.
     * @param mockBlock2   The CodeBlock at the end of the connection.
     */
    private void establishConnection(CodeBlock mockBlock1, CodeBlock mockBlock2){
        mockBlock1.getOutboundCodeBlocks().add(mockBlock2);
        mockBlock1.getOutBoundCodeBlockMarkings().add(false);
        mockBlock1.getDiscovered().add(Boolean.FALSE);

        mockBlock2.getInboundCodeBlocks().add(mockBlock1);

        Mockito.when(mockBlock1.getOutBoundCodeBlockMarking(mockBlock2)).thenReturn(false);
    }

    /**
     * Creates a mocked CodeBlock with a given type and label.
     * @param type   The type to make the CodeBlock.
     * @param text   The text to label the CodeBlock.
     * @return   A mocked CodeBlock if the given type is valid,
     * otherwise a null object is given.
     */
    private CodeBlock mockBlock(String type, String text){
        CodeBlock mockBlock;
        if(type.equals("start")){
            mockBlock = Mockito.mock(StartBlock.class);
        } else if(type.equals("stop")){
            mockBlock = Mockito.mock(StopBlock.class);
        } else if(type.equals("variable")){
            mockBlock = Mockito.mock(VariableBlock.class);
        } else if(type.equals("if")){
            mockBlock = Mockito.mock(IfBlock.class);
        } else if(type.equals("loop")){
            mockBlock = Mockito.mock(LoopBlock.class);
        } else if(type.equals("print")){
            mockBlock = Mockito.mock(PrintBlock.class);
        } else if(type.equals("function")){
            mockBlock = Mockito.mock(FunctionBlock.class);
        } else {
            return null;
        }
        ArrayList<CodeBlock> outbound = new ArrayList<>();
        ArrayList<CodeBlock> inbound = new ArrayList<>();
        ArrayList<Boolean> discovered = new ArrayList<>();
        ArrayList<Boolean> markings = new ArrayList<>();

        Mockito.when(mockBlock.getDiscovered()).thenReturn(discovered);
        Mockito.when(mockBlock.getInboundCodeBlocks()).thenReturn(inbound);
        Mockito.when(mockBlock.getOutboundCodeBlocks()).thenReturn(outbound);
        Mockito.when(mockBlock.getText()).thenReturn(text);
        Mockito.when(mockBlock.getOutBoundCodeBlockMarkings()).thenReturn(markings);
        return mockBlock;
    }

    /**
     * Creates a mocked flowchart (ArrayList<CodeBlock>) with
     * a PrintBlock nested inside of two LoopBlocks and
     * a PrintBlock outside both LoopBlocks followed
     * by a StopBlock.
     *
     * @param missingFinalPrint   A boolean representing if the
     *                            type of the final PrintBlock
     *                            should be missing.
     * @param wrongTypePrint     A boolean representing if the
     *                           print statement inside the
     *                           LoopBlocks should be the wrong
     *                           type.
     * @return
     */
    private ArrayList<CodeBlock> mockElaborateFlowchart(boolean missingFinalPrint, boolean wrongTypePrint){
        ArrayList<CodeBlock> flowchart = new ArrayList<>();
        StartBlock startBlock = (StartBlock) mockBlock("start", "start");
        LoopBlock loopBlock_1 = (LoopBlock) mockBlock("loop", "for(int i = 0; i < 10; ++i)");
        LoopBlock loopBlock_2 = (LoopBlock) mockBlock("loop", "for(int i = 0; i < 10; ++i)");
        CodeBlock printBlock = (PrintBlock) mockBlock("print", "Another One!");
        PrintBlock finalPrintBlock = (PrintBlock) mockBlock("print", "fin");
        StopBlock stopBlock = (StopBlock) mockBlock("stop", "stop");

        if(wrongTypePrint){
            printBlock = mockBlock("variable", "Another One!");
        }

        if(missingFinalPrint){
            establishConnection(loopBlock_1, stopBlock);
        } else {
            flowchart.add(finalPrintBlock);
            establishConnection(finalPrintBlock, stopBlock);
            establishConnection(loopBlock_1, finalPrintBlock);
        }

        establishConnection(startBlock, loopBlock_1);
        establishConnection(loopBlock_1, loopBlock_2);
        establishConnection(loopBlock_2, printBlock);
        establishConnection(printBlock, loopBlock_2);
        establishConnection(loopBlock_2, loopBlock_1);

        flowchart.add(startBlock);
        flowchart.add(loopBlock_1);
        flowchart.add(loopBlock_2);
        flowchart.add(printBlock);
        flowchart.add(stopBlock);
        return flowchart;
    }

    /**
     * Creates an ArrayList of type CodeBlock containing one
     * StartBlock and one StopBlock that are connected to
     * one another and discoverable.
     * @return  A mock of an ArrayList of type CodeBlock.
     */
    private ArrayList<CodeBlock> mockSimpleFlowchart(){
        ArrayList<Drawable> drawables = DrawablesMock.mockFlowchartWithDrawables();
        ArrayList<CodeBlock> flowchart = new ArrayList<>();
        for(int i = 0; i < drawables.size(); ++i){
            if(drawables.get(i) instanceof CodeBlock){
                flowchart.add((CodeBlock) drawables.get(i));
            }
        }
        return flowchart;
    }

    /**
     * Ensures that two simple flowcharts (containing only one
     * StartBlock and StopBlock with a connection) are
     * determined to be the same.
     */
    @Test
    public void testSimpleFlowchart(){
        ArrayList<CodeBlock> flowchartSolution = mockSimpleFlowchart();
        ArrayList<CodeBlock> flowchartResponse = mockSimpleFlowchart();

        GradeFlowchart gradeFlowchart =
                new GradeFlowchart(flowchartSolution, flowchartResponse, false);
        assertTrue(gradeFlowchart.grade());
    }

    /**
     * Ensures that two simple flowcharts (containing only one
     * StartBlock and StopBlock with a connection) are
     * determined to be the same when verbose output is requested.
     */
    @Test
    public void testSimpleFlowchartVerbose(){
        ArrayList<CodeBlock> flowchartSolution = mockSimpleFlowchart();
        ArrayList<CodeBlock> flowchartResponse = mockSimpleFlowchart();

        GradeFlowchart gradeFlowchart =
                new GradeFlowchart(flowchartSolution, flowchartResponse, false);
        assertTrue(gradeFlowchart.grade());
    }

    /**
     * Ensures that two simple flowcharts (containing only one
     * StartBlock and StopBlock with a connection) are
     * determined to not be the same when the student
     * puts two StartBlocks in an otherwise correct submission.
     */
    @Test
    public void testSimpleFlowchart2StartVerbose(){
        ArrayList<CodeBlock> flowchartSolution = mockSimpleFlowchart();
        ArrayList<CodeBlock> flowchartResponse = mockSimpleFlowchart();
        flowchartResponse.add(Mockito.mock(StartBlock.class));

        GradeFlowchart gradeFlowchart =
                new GradeFlowchart(flowchartSolution, flowchartResponse, true);
        assertFalse(gradeFlowchart.grade());
    }

    /**
     * Ensures that two simple flowcharts (containing only one
     * StartBlock and StopBlock with a connection) are
     * determined to not be the same when the student
     * adds a disjoint (non-StartBlock) CodeBlock in
     * an otherwise correct submission.
     */
    @Test
    public void testSimpleFlowchartDisjointVerbose(){
        ArrayList<CodeBlock> flowchartSolution = mockSimpleFlowchart();
        ArrayList<CodeBlock> flowchartResponse = mockSimpleFlowchart();
        flowchartResponse.add(Mockito.mock(VariableBlock.class));

        GradeFlowchart gradeFlowchart =
                new GradeFlowchart(flowchartSolution, flowchartResponse, true);
        assertFalse(gradeFlowchart.grade());
    }

    /**
     * Ensures that two elaborate flowcharts are determined
     * to be the same. See mockElaborateFlowchart for
     * a definition of elaborate flowchart.
     */
    @Test
    public void testElaborateFlowchartVerbose(){
        ArrayList<CodeBlock> flowchartSolution = mockElaborateFlowchart(false, false);
        ArrayList<CodeBlock> flowchartResponse = mockElaborateFlowchart(false, false);

        GradeFlowchart gradeFlowchart =
                new GradeFlowchart(flowchartSolution, flowchartResponse, true);
        assertTrue(gradeFlowchart.grade());
    }

    /**
     * Ensures that two elaborate flowcharts are determined
     * to not be the same when the student submitted one
     * is missing the final print statement.
     */
    @Test
    public void testElaborateFlowchartMissingPrintVerbose(){
        ArrayList<CodeBlock> flowchartSolution = mockElaborateFlowchart(false, false);
        ArrayList<CodeBlock> flowchartResponse = mockElaborateFlowchart(true, false);

        GradeFlowchart gradeFlowchart =
                new GradeFlowchart(flowchartSolution, flowchartResponse, true);
        assertFalse(gradeFlowchart.grade());
    }


    /**
     * Ensures that two elaborate flowcharts are determined
     * to not be the same when the student submitted one
     * is has a non-PrintBlock inside the nested LoopBlocks.
     */
    @Test
    public void testElaborateFlowchartMissingPrintWrongTypeVerbose(){
        ArrayList<CodeBlock> flowchartSolution = mockElaborateFlowchart(false, false);
        ArrayList<CodeBlock> flowchartResponse = mockElaborateFlowchart(false, true);

        GradeFlowchart gradeFlowchart =
                new GradeFlowchart(flowchartSolution, flowchartResponse, true);
        assertFalse(gradeFlowchart.grade());
    }

    /**
     * Ensures that two elaborate flowcharts are determined
     * to not be the same when one of the CodeBlocks has
     * a small typo (<= 5 characters different).
     */
    @Test
    public void testElaborateFlowchartVerboseTypo(){
        ArrayList<CodeBlock> flowchartSolution = mockElaborateFlowchart(false, false);
        ArrayList<CodeBlock> flowchartResponse = mockElaborateFlowchart(false, false);
        CodeBlock block = flowchartResponse.get(3);
        String label = block.getText() + "A";
        StringBuilder stringBuilder = new StringBuilder(label);
        stringBuilder.deleteCharAt(3);
        label = stringBuilder.toString();

        Mockito.when(block.getText()).thenReturn(label);

        GradeFlowchart gradeFlowchart =
                new GradeFlowchart(flowchartSolution, flowchartResponse, true);
        assertFalse(gradeFlowchart.grade());
    }

    /**
     * Ensures that two elaborate flowcharts are determined
     * to not be the same when one of the CodeBlocks has
     * is misspelled (> 5 characters different).
     */
    @Test
    public void testElaborateFlowchartMisspelledWrongType(){
        ArrayList<CodeBlock> flowchartSolution = mockElaborateFlowchart(false, false);
        ArrayList<CodeBlock> flowchartResponse = mockElaborateFlowchart(false, true);
        CodeBlock block = flowchartResponse.get(3);
        String label = "ijaofej988fe 8q989uf 98qf8";

        Mockito.when(block.getText()).thenReturn(label);

        GradeFlowchart gradeFlowchart =
                new GradeFlowchart(flowchartSolution, flowchartResponse, true);
        assertFalse(gradeFlowchart.grade());
    }
}
