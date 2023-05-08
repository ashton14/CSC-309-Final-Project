package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 * WorkingAreaControlHandler responsible for controlling the functionality of the WorkingArea
 * Draws shapes and connections as selected
 * Controls dragging of shapes
 * @author Cameron Hardy
 */
public class WorkingAreaControlHandler implements MouseListener, MouseMotionListener {

    /**
     * @Field dragging - CodeBlock being dragged
     */
    private CodeBlock draggedCodeBlock = null;
    private int xOffset = 0;
    private int yOffset = 0;
    /**
     * Constructs the WorkingAreaControlHandler
     */
    public WorkingAreaControlHandler(){ }

    /**
     * Implemented for interface
     * @param e - MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Repository dataRepository = DataRepository.getInstance();
        DrawableData drawableData = (DrawableData) dataRepository.getData();
        StateRepository stateRepository = StateRepository.getInstance();
        StateData stateData = (StateData) stateRepository.getData();
        if(stateData.getMode().equals("Translate Flowchart")){
            stateData.setStatus("Cannot edit flowchart in this mode");
            return;
        }
        CodeBlock prevSelectedCodeBlock = stateData.getCurrentlySelectedCodeBlock();
        stateData.setCurrentlySelectedCodeBlock(getTopCodeBlock(e.getX(), e.getY()));
        if(prevSelectedCodeBlock != null) {
           drawableData.addDrawable(makeLine(prevSelectedCodeBlock));
        }
    }

    public CodeBlock getTopCodeBlock(int x, int y){
        Repository dataRepository = DataRepository.getInstance();
        DrawableData drawableData = (DrawableData) dataRepository.getData();
        ArrayList<CodeBlock> codeBlocks = drawableData.getCodeBlocks();
        CodeBlock topCodeBlock = null;
        for (CodeBlock codeBlock: codeBlocks) {
            if(codeBlock.isInBounds(x,y)){
                topCodeBlock = codeBlock;
            }
        }
        return topCodeBlock;
    }

    private Line makeLine(CodeBlock firstCodeBlock){
        Repository dataRepository = DataRepository.getInstance();
        DrawableData drawableData = (DrawableData) dataRepository.getData();
        StateRepository stateRepository = StateRepository.getInstance();
        StateData stateData = (StateData) stateRepository.getData();

        CodeBlock lastCodeBlock = stateData.getCurrentlySelectedCodeBlock();
        Line line = null;

        if(firstCodeBlock != lastCodeBlock &&
                firstCodeBlock.canAddOut(lastCodeBlock) &&
                lastCodeBlock.canAddIn(firstCodeBlock)) {

            firstCodeBlock.addToOutbound(lastCodeBlock);
            lastCodeBlock.addToInbound(firstCodeBlock);
            line = new Line(firstCodeBlock, lastCodeBlock);
            System.out.println("Made a new line");
            stateData.setStatus("Established A Connection");
        } else {

        }
        return line;
    }

    private CodeBlock makeCodeBlock(int x, int y){
        StateRepository stateRepository = StateRepository.getInstance();
        StateData stateData = (StateData) stateRepository.getData();
        BlockFactory blockFactory = new BlockFactory();
        return blockFactory.makeBlock(stateData.getMenuBarCodeBlock(), x, y);

    }
    @Override
    public void mousePressed(MouseEvent e) {
        Repository dataRepository = DataRepository.getInstance();
        DrawableData drawableData = (DrawableData) dataRepository.getData();

        StateRepository stateRepository = StateRepository.getInstance();
        StateData stateData = (StateData) stateRepository.getData();
        if(stateData.getMode().equals("Translate Flowchart")){
            stateData.setStatus("Cannot edit flowchart in this mode");
            return;
        }

        CodeBlock selectedCodeBlock = getTopCodeBlock(e.getX(),e.getY());
        if(selectedCodeBlock == null) {
            selectedCodeBlock = makeCodeBlock(e.getX(), e.getY());
            drawableData.addDrawable(selectedCodeBlock);
            stateData.setCurrentlySelectedCodeBlock(selectedCodeBlock);
            return;
        }
        xOffset =  selectedCodeBlock.getXCenter() - e.getX();
        yOffset = selectedCodeBlock.getYCenter() - e.getY();
        draggedCodeBlock = selectedCodeBlock;
    }

    /**
     * Release CodeBlock if we are dragging one
     * @param e - MouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        StateRepository stateRepository = StateRepository.getInstance();
        StateData stateData = (StateData) stateRepository.getData();
        if(stateData.getMode().equals("Translate Flowchart")){
            stateData.setStatus("Cannot edit flowchart in this mode");
            return;
        }

        if(draggedCodeBlock != null){
            stateData.setStatus("Placing " + stateData.getMenuBarCodeBlock() + " Blocks");
        }
        draggedCodeBlock = null;
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
        StateRepository stateRepository = StateRepository.getInstance();
        StateData stateData = (StateData) stateRepository.getData();
        if(stateData.getMode().equals("Translate Flowchart")){
            stateData.setStatus("Cannot edit flowchart in this mode");
            return;
        }

        if(draggedCodeBlock != null) {
            draggedCodeBlock.setXCenter(e.getX() + xOffset);
            draggedCodeBlock.setYCenter(e.getY() + yOffset);
            stateData.setCurrentlySelectedCodeBlock(draggedCodeBlock);
            ((DrawableData) DataRepository.getInstance().getData()).modifiedDrawables();
            stateData.setStatus("Dragging " + stateData.getCurrentlySelectedCodeBlock().toString());
        }
    }

    /**
     * Implemented for interface
     * @param e - MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e) {}
}
