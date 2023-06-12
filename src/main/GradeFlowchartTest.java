package src.main;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class GradeFlowchartTest {

    private ArrayList<CodeBlock> mockSimpleChart(){
        StartBlock startBlock = Mockito.mock(StartBlock.class);
        StartBlock stopBlock = Mockito.mock(StartBlock.class);

        ArrayList<CodeBlock> startBlockInbound = new ArrayList<>();
        ArrayList<CodeBlock> startBlockOutbound = new ArrayList<>();
        ArrayList<Boolean> startDiscovered = new ArrayList<>();
        startDiscovered.add(false);

        startBlockOutbound.add(stopBlock);

        ArrayList<CodeBlock> stopBlockOutbound = new ArrayList<>();
        ArrayList<CodeBlock> stopBlockInbound = new ArrayList<>();
        ArrayList<Boolean> stopDiscovered = new ArrayList<>();
        stopBlockInbound.add(startBlock);

        when(startBlock.getOutboundCodeBlocks()).thenReturn(startBlockOutbound);
        when(startBlock.getInboundCodeBlocks()).thenReturn(startBlockInbound);
        when(startBlock.getDiscovered()).thenReturn(startDiscovered);

        when(stopBlock.getOutboundCodeBlocks()).thenReturn(stopBlockOutbound);
        when(stopBlock.getInboundCodeBlocks()).thenReturn(stopBlockInbound);
        when(stopBlock.getDiscovered()).thenReturn(stopDiscovered);

        ArrayList<CodeBlock> mockFlowchart = new ArrayList<>();
        mockFlowchart.add(startBlock);
        mockFlowchart.add(stopBlock);
        return mockFlowchart;
    }

    @Test
    public void testInstance(){
        ArrayList<CodeBlock> solutionChart = mockSimpleChart();
        ArrayList<CodeBlock> studentChart = mockSimpleChart();
        GradeFlowchart gradeFlowchart = new GradeFlowchart(studentChart, solutionChart, false);
        gradeFlowchart.grade();
    }

}
