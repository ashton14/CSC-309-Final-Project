package main;

import java.awt.*;
import java.awt.event.*;

/**
 * WorkingAreaControlHandler responsible for controlling the functionality of the WorkingArea
 * Draws shapes and connections as selected
 * Controls dragging of shapes
 * @author Cameron Hardy
 * @author Connor Hickey
 */
public class WorkingAreaControlHandler implements MouseListener, MouseMotionListener {
    /**
     * @Field drawingLine - Boolean to keep track of when you have clicked on one CodeBlock
     * and are waiting to connect the line to another
     */
    private boolean drawingLine = false;
    /**
     * @Field firstClicked - CodeBlock first clicked when trying to draw a connection
     */
    private CodeBlock firstClicked = null;
    /**
     * @Field dragging - CodeBlock being dragged
     */
    private CodeBlock dragging = null;

    /**
     * @Field draggingOutline - Outline shape of CodeBLock being dragged
     */
    private Shape draggingShape = null;
    private String status;

    /**
     * Constructs the WorkingAreaControlHandler
     */
    public WorkingAreaControlHandler(){ }

    /**
     * Implemented for interface
     * @param e - MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * If mouse has been pressed, create corresponding block or connection
     * @param e - MouseEvent used for position
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Create blockFactory and get selected CodeBlock/Drawable type
        BlockFactory blockFactory = new BlockFactory();
        String blockType = Repository.getInstance().getSelectedCodeBlock();
        Repository.getInstance().updateStatusBar(blockType);


        // If we are drawing a connection
        if (blockType.equals("Connection")) { //Connection should no longer be an option, change to if a block is clicked
            // If the mouse is inside a CodeBlock
            for(CodeBlock block : Repository.getInstance().getCodeBlocks()) {
                if (block.isInBounds(e.getX(), e.getY())) {
                    // If we are already drawing a connection
                    if (drawingLine) {
                        // If adding a connection respects adding in/outbound connections, and you are not clicking on the same CodeBlock
                        if(block.canAddIn() && firstClicked.canAddOut() && !block.equals(firstClicked)) {
                            // Add a line to Repository, clear firstClicked and reset drawingLine
                            Repository.getInstance().addLine(new Line(firstClicked, block));
                            firstClicked = null;
                            drawingLine = false;
                            Repository.getInstance().repaintWorkingArea();
                            break;
                        }
                    }
                    // We are not already drawing a line... set CodeBlock to firstClicked, and set drawingLine to true
                    firstClicked = block;
                    drawingLine = true;
                    break;
                }
            }
        }
        else {
            boolean lineSelected = false;
            // If we click on a CodeBlock start dragging it
            for(CodeBlock block : Repository.getInstance().getCodeBlocks()) {
                if(block.isInBounds(e.getX(), e.getY())) {
                    dragging = block;
                    Repository.getInstance().setCurrentlySelectedCodeBlock(block);
                    draggingShape = Repository.getInstance().getCurrentlySelectedCodeBlockOutline();
                }
            }

            for(Line line : Repository.getInstance().getLines()) {
                if(line.pointDistanceFromLine(e.getX(), e.getY()) < 20) {
                    lineSelected = true;
                    Repository.getInstance().setCurrentlySelectedLine(line);
                    Repository.getInstance().getCurrentlySelectedLine().setColor(Color.RED);
                    Repository.getInstance().repaintWorkingArea();
                    System.out.print("Line selected");
                    break;
                }
            }
            // If we aren't dragging anything create corresponding CodeBlock
            if(dragging == null && !lineSelected) {
                Repository.getInstance().addCodeBlock(blockFactory.makeBlock(blockType, e.getX(), e.getY()));
            }
        }
        status = Repository.getInstance().getStatus();
    }

    /**
     * Release CodeBlock if we are dragging one
     * @param e - MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e) {

        dragging = null;
        draggingShape = null;
        Repository.getInstance().updateStatusBar(status);
    }
    /**
     * Implemented for interface
     * @param e - MouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    /**
     * Implemented for interface
     * @param e - MouseEvent
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
    /**
     * Handles mouse dragging CodeBlocks
     * @param e - MouseEvent used for position of CodeBlock dragging
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // If we are dragging a CodeBlock, set its position to the mouse position
        if(dragging != null) {
            dragging.setXCenter(e.getX());
            dragging.setYCenter(e.getY());
            draggingShape.setXCenter(e.getX());
            draggingShape.setYCenter(e.getY());
            Repository.getInstance().repaintWorkingArea();
        }

    }

    /**
     * Implemented for interface
     * @param e - MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e) {}
}
