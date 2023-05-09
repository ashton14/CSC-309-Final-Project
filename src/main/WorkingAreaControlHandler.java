package src.main;

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
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        CodeBlock prevSelectedCodeBlock = stateRepository.getCurrentlySelectedCodeBlock();
        stateRepository.setCurrentlySelectedCodeBlock(getTopCodeBlock(e.getX(), e.getY()));
        if(prevSelectedCodeBlock != null) {
            System.out.println("Attempt to make a line");
            dataRepository.addDrawable(makeLine(prevSelectedCodeBlock));
        }
    }

    public CodeBlock getTopCodeBlock(int x, int y){
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
        ArrayList<CodeBlock> codeBlocks = dataRepository.getCodeBlocks();
        CodeBlock topCodeBlock = null;
        for (CodeBlock codeBlock: codeBlocks) {
            if(codeBlock.isInBounds(x,y)){
                topCodeBlock = codeBlock;
            }
        }
        return topCodeBlock;
    }

    private Line makeLine(CodeBlock firstCodeBlock){
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();

        CodeBlock lastCodeBlock = stateRepository.getCurrentlySelectedCodeBlock();
        Line line = null;

        if(firstCodeBlock != lastCodeBlock &&
                firstCodeBlock.canAddOut(lastCodeBlock) &&
                lastCodeBlock.canAddIn(firstCodeBlock)) {

            firstCodeBlock.addToOutbound(lastCodeBlock);
            lastCodeBlock.addToInbound(firstCodeBlock);
            line = new Line(firstCodeBlock, lastCodeBlock);
            System.out.println("Made a new line");
            stateRepository.setStatus("Established A Connection");
        } else {

        }
        return line;
    }

    private CodeBlock makeCodeBlock(int x, int y){
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        BlockFactory blockFactory = new BlockFactory();
        return blockFactory.makeBlock(stateRepository.getMenuBarCodeBlock(), x, y);

    }
    @Override
    public void mousePressed(MouseEvent e) {
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();

        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        CodeBlock selectedCodeBlock = getTopCodeBlock(e.getX(),e.getY());
        if(selectedCodeBlock == null) {
            selectedCodeBlock = makeCodeBlock(e.getX(), e.getY());
            dataRepository.addDrawable(selectedCodeBlock);
            stateRepository.setCurrentlySelectedCodeBlock(selectedCodeBlock);
            stateRepository.setStatus("Placing " + selectedCodeBlock.toString() + " Blocks.");
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
            StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
            stateRepository.setStatus("Placing " + stateRepository.getMenuBarCodeBlock() + " Blocks");
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
            StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
            stateRepository.setStatus("Dragging ");
            draggedCodeBlock.setXCenter(e.getX() + xOffset);
            draggedCodeBlock.setYCenter(e.getY() + yOffset);
            stateRepository.setCurrentlySelectedCodeBlock(draggedCodeBlock);

            DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
            dataRepository.modifiedDrawables();
        }
    }

    /**
     * Implemented for interface
     * @param e - MouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent e) {}
}
