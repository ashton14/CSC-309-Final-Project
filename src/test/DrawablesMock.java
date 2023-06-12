package src.test;

import org.mockito.Mockito;
import src.main.*;
import src.main.Shape;

import java.awt.*;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class DrawablesMock {


    /**
     * Mocks a shape with support for getting all fields.
     * @return  A mock of a Shape object.
     */
    public static Shape mockShape(){
        Circle shape = Mockito.mock(Circle.class);
        Color color = Color.BLUE;
        Mockito.when(shape.getXCenter()).thenReturn(0);
        Mockito.when(shape.getYCenter()).thenReturn(0);
        Mockito.when(shape.getWidth()).thenReturn(50);
        Mockito.when(shape.getHeight()).thenReturn(50);
        Mockito.when(shape.getColor()).thenReturn(color);
        return shape;
    }

    /**
     * Mocks a simple flowchart with a StartBlock, EndBlock
     * and a line connecting them. This flowchart also supports
     * retrieving shapes from the mocked CodeBlocks and
     * removing connections.
     *
     * @return   An ArrayList of type Drawable containing
     * a mocked StartBlock in index 0, a mocked StopBlock
     * in index 1 and a mocked Line in index 2.
     */
    public static ArrayList<Drawable> mockFlowchartWithDrawables(){
        CodeBlock start = Mockito.mock(StartBlock.class);
        Shape startShape = mockShape();
        Shape startShapeCopy = mockShape();

        CodeBlock stop = Mockito.mock(StopBlock.class);
        Shape stopShape = mockShape();
        Shape stopShapeCopy = mockShape();

        ArrayList<CodeBlock> startOutbound = new ArrayList<>();
        startOutbound.add(stop);
        ArrayList<CodeBlock> stopInbound = new ArrayList<>();
        stopInbound.add(start);

        ArrayList<CodeBlock> startInbound = new ArrayList<>();
        ArrayList<CodeBlock> stopOutbound = new ArrayList<>();

        ArrayList<Boolean> startDiscovered = new ArrayList<>();
        ArrayList<Boolean> stopDiscovered = new ArrayList<>();
        startDiscovered.add(false);

        Mockito.when(start.getOutboundCodeBlocks()).thenReturn(startOutbound);
        Mockito.when(start.getInboundCodeBlocks()).thenReturn(startInbound);
        Mockito.when(start.copyShape()).thenReturn(startShapeCopy);
        Mockito.when(start.getShape()).thenReturn(startShape);
        Mockito.when(start.getDiscovered()).thenReturn(startDiscovered);
        Mockito.when(start.getText()).thenReturn("START");

        Mockito.when(stop.getOutboundCodeBlocks()).thenReturn(stopOutbound);
        Mockito.when(stop.getInboundCodeBlocks()).thenReturn(stopInbound);
        Mockito.when(stop.copyShape()).thenReturn(stopShapeCopy);
        Mockito.when(stop.getShape()).thenReturn(stopShape);
        Mockito.when(stop.getDiscovered()).thenReturn(stopDiscovered);
        Mockito.when(stop.getText()).thenReturn("STOP");

        start.addToOutbound(stop);
        stop.addToInbound(start);

        Line mockLine1 = Mockito.mock(Line.class);
        when(mockLine1.getStart()).thenReturn(start);
        when(mockLine1.getEnd()).thenReturn(stop);

        ArrayList<Drawable> mockFlowchart = new ArrayList<>();
        mockFlowchart.add(start);
        mockFlowchart.add(stop);
        mockFlowchart.add(mockLine1);

        Mockito.doAnswer(invocationOnMock -> {
            startOutbound.remove(0);
            stopInbound.remove(0);
            return true;
        }).when(start).removeConnection(stop);

        Mockito.doAnswer(invocationOnMock -> {
            startOutbound.remove(0);
            stopInbound.remove(0);
            return true;
        }).when(stop).removeConnection(start);

        return mockFlowchart;
    }

}
