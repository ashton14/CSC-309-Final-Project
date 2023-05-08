package Repo.main;

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
        CodeBlock prevSelectedCodeBlock = stateData.getCurrentlySelectedCodeBlock();
        stateData.setCurrentlySelectedCodeBlock(getTopCodeBlock(e.getX(), e.getY()));
        System.out.println(getTopCodeBlock(e.getX(), e.getY()));
        if(prevSelectedCodeBlock != null) {
            System.out.println("Attempt to make a line");
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

        System.out.println(firstCodeBlock.getInboundCodeBlocks().size());
        System.out.println(firstCodeBlock.getOutboundCodeBlocks().size());
        System.out.println(lastCodeBlock.getInboundCodeBlocks().size());
        System.out.println(lastCodeBlock.getOutboundCodeBlocks().size());
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
        CodeBlock selectedCodeBlock = getTopCodeBlock(e.getX(),e.getY());
        if(selectedCodeBlock == null) {
            selectedCodeBlock = makeCodeBlock(e.getX(), e.getY());
            drawableData.addDrawable(selectedCodeBlock);
            stateData.setCurrentlySelectedCodeBlock(selectedCodeBlock);
            stateData.setStatus("Placing " + selectedCodeBlock.toString() + " Blocks.");
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
        if(draggedCodeBlock != null){
            StateRepository stateRepository = StateRepository.getInstance();
            StateData stateData = (StateData) stateRepository.getData();
            stateData.setStatus("Placing: " + stateData.getMenuBarCodeBlock() + " Blocks");
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
        if(draggedCodeBlock != null) {
            StateData stateData = (StateData) StateRepository.getInstance().getData();
            stateData.setStatus("Dragging ");
            draggedCodeBlock.setXCenter(e.getX() + xOffset);
            draggedCodeBlock.setYCenter(e.getY() + yOffset);
            stateData.setCurrentlySelectedCodeBlock(draggedCodeBlock);
            ((DrawableData) DataRepository.getInstance().getData()).modifiedDrawables();
        }
    }

    /**
     * Implemented for interface
     * @param e - MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e) {}
}
