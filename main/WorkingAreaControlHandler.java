package main;

import java.awt.*;
import java.awt.event.*;

/**
 * WorkingAreaControlHandler responsible for controlling the functionality of the WorkingArea
 * Draws shapes and connections as selected
 * Controls dragging of shapes
 * @author Cameron Hardy
 */
public class WorkingAreaControlHandler implements MouseListener, MouseMotionListener {
    private int lineSelectDistance  = 10;
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
     * @Field offset - Offset for codeblock being dragged
     */
    private Point offset = null;

    /**
     * @Field draggingOutline - Outline shape of CodeBLock being dragged
     */
    private Shape dragShape = null;
    private String status;

    /**
     * Constructs the WorkingAreaControlHandler
     */
    public WorkingAreaControlHandler() {}

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

        // If a block is selected we try to connect or split line
        // Otherwise Select or Place new block
        CodeBlock selected = Repository.getInstance().getCurrentlySelectedCodeBlock();
        if (selected != null) {
            for(CodeBlock block : Repository.getInstance().getCodeBlocks()) {
                // Try to connect CodeBlock, break if successful
                if(selected.equals(block)) {
                    // Click on selected code block
                    continue;
                }
                else if(tryConnectBlocks(selected, block, e)) {
                    Repository.getInstance().repaintWorkingArea();
                    Repository.getInstance().updateStatusBar("Drawing line...");//+  + " and " + "");
                    Repository.getInstance().setCurrentlySelectedCodeBlock(null);
                    break;
                }
            }
            Repository.getInstance().setCurrentlySelectedCodeBlock(null);
        }
        else {
            // If we click on a CodeBlock start dragging it
            if(!trySelect(e) &&
                    !trySplitLine(e)) {
                if(dragging == null) {
                    Repository.getInstance().addCodeBlock(blockFactory.makeBlock(blockType, e.getX(), e.getY()));
                }
            }
            // If we aren't dragging anything create corresponding CodeBlock
        }
        status = Repository.getInstance().getStatus();
    }
    public boolean trySplitLine(MouseEvent e) {
        for(Line l : Repository.getInstance().getLines()) {
            if(l.pointDistanceFromLine(e.getX(), e.getY()) <= lineSelectDistance) {
                l.split(e);
                return true;
            }
        }
        return false;
    }
    public boolean trySelect(MouseEvent e) {
        for(CodeBlock block : Repository.getInstance().getCodeBlocks()) {
            if(block.isInBounds(e.getX(), e.getY())) {
                dragging = block;
                Repository.getInstance().setCurrentlySelectedCodeBlock(block);
                offset = new Point(e.getX() - block.getXCenter(), e.getY() - block.getYCenter());
                return true;
            }
        }
        return false;
    }
    public boolean tryConnectBlocks(CodeBlock first, CodeBlock second, MouseEvent e) {
        if (second.isInBounds(e.getX(), e.getY()) && first.canAddOut() && second.canAddIn()) {
            Line connection = new Line(first, second);
            ArrowDecorator arrow = new ArrowDecorator(connection);
            Repository.getInstance().addLine(arrow);
            return true;
        }
        return false;
    }

    /**
     * Release CodeBlock if we are dragging one
     * @param e - MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e) {
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
        if(offset == null) {
            return;
        }
        dragging = Repository.getInstance().getCurrentlySelectedCodeBlock();
        dragShape = Repository.getInstance().getCurrentlySelectedCodeBlockOutline();
        if(dragShape != null) {
            dragShape.setXCenter((int) (e.getX() - offset.getX()));
            dragShape.setYCenter((int) (e.getY() - offset.getY()));
        }
        if(dragging != null) {
            dragging.setXCenter((int) (e.getX() - offset.getX()));
            dragging.setYCenter((int) (e.getY() - offset.getY()));
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
