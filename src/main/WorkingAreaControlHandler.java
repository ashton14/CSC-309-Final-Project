package src.main;

import javax.swing.*;
import javax.xml.crypto.Data;
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
        stateRepository.setCurrentlySelectedDrawable(getTopCodeBlock(e.getX(), e.getY()));
        if(prevSelectedCodeBlock != null) {

            Line l = makeLine(prevSelectedCodeBlock);
            dataRepository.addDrawable(l);
        }
        Line l = getTopLine(e);
        if(l == null) {
            return;
        }
        if(e.getButton() == MouseEvent.BUTTON3) {
            labelLine(l, e);
        }
        else if(e.getClickCount() == 2) {
            l.split(e);
        }
        stateRepository.setCurrentlySelectedDrawable(l.getConnection());
        dataRepository.repaintWorkingArea();

    }
    /**
     * Labels the given line at the given Mouse position
     * @param l - given line
     * @param e - given MouseEvent
     */
    public void labelLine(Line l, MouseEvent e) {
        if(l.getClass() != Line.class ||
                (l.getStart().getClass() != IfBlock.class && l.getStart().getClass() != LoopBlock.class)) {
            System.out.println("This line can not be labeled");
            return;
        }
        String[] labels = new String[] {"True", "False"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an option", "Line Labeling",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, labels, labels[0]);
        l.label(labels[choice]);
    }
    /**
     * @param e - MouseEvent used to determine the line being pressed
     * @return Line
     */
    public Line getTopLine(MouseEvent e) {
        Line close = null;
        for(Line l : ((DataRepository) DataRepository.getInstance()).getLines()) {
            close = ((Link) l).selectLine(e);
            if(close != null) {
                break;
            }
        }
        return close;
    }

    /**
     * Returns the CodeBlock on the screen at the given x and y coordinates
     * @param x
     * @param y
     * @return
     */
    public CodeBlock getTopCodeBlock(int x, int y){
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
        ArrayList<CodeBlock> codeBlocks = dataRepository.getCodeBlocks();
        ArrayList<Line> links = dataRepository.getLines();

        for (CodeBlock codeBlock: codeBlocks) {
            if(codeBlock.isInBounds(x,y)){
                return codeBlock;
            }
        }
        for(Line l : links) {
            for(Node n : ((Link) l).getNodes()) {
                if(n.isInBounds(x, y)) {
                    return n;
                }
            }
        }
        return null;
    }
    /**
     * Tries to create a line between the selectedCodeBlock and the clicked CodeBlock
     * @param firstCodeBlock - clicked CodeBlock
     */
    private Line makeLine(CodeBlock firstCodeBlock) {
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();

        CodeBlock lastCodeBlock = stateRepository.getCurrentlySelectedCodeBlock();
        Link c = null;
        Line line = null;
        LineDecorator arrow = null;
        if(firstCodeBlock != lastCodeBlock &&
                lastCodeBlock != null &&
                firstCodeBlock.canAddOut(lastCodeBlock) &&
                lastCodeBlock.canAddIn(firstCodeBlock)) {

            c = new Link(firstCodeBlock, lastCodeBlock);
            line = new Line(firstCodeBlock, lastCodeBlock);
            arrow = new ArrowDecorator(line);

            arrow.setLink(c);
            c.addLine(arrow);

            stateRepository.setStatus("Established A Connection");
        } else {
            stateRepository.setCurrentlySelectedDrawable(null);
        }
        return c;
    }
    /**
     * Creates current CodeBlock at given x and y position
     * @param x - given Mouse x position
     * @param y - given Mouse y position
     */
    private CodeBlock makeCodeBlock(int x, int y) {
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        BlockFactory blockFactory = new BlockFactory();
        return blockFactory.makeBlock(stateRepository.getMenuBarCodeBlock(), x, y);

    }
    /**
     * Creates CodeBlock unless a CodeBlock is selected, or a Line is clicked, or if the
     * user is not allowed to edit the flowchart
     * @param e - given Mouse position for this action
     */
    @Override
    public void mousePressed(MouseEvent e) {
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();

        //check the current mode. Flowchart should not be editable in 'Translate Flowchart' mode
        if(stateRepository.getMode().equals("Translate Flowchart")) {
            System.out.println("Cannot edit flowchart in this mode!");
            return;
        }

        CodeBlock selectedCodeBlock = getTopCodeBlock(e.getX(),e.getY());

        if(selectedCodeBlock == null && getTopLine(e) == null) {
            selectedCodeBlock = makeCodeBlock(e.getX(), e.getY());
            dataRepository.addDrawable(selectedCodeBlock);
            stateRepository.setCurrentlySelectedDrawable(selectedCodeBlock);
            stateRepository.setStatus("Placing " + selectedCodeBlock.toString() + " Blocks.");
            return;
        }
        setOffset(selectedCodeBlock, e);
    }
    /**
     * Sets the offset for the selected CodeBlock and where the mouse
     * initially started relative to it
     * @param c - Given CodeBlock
     * @param e - given Mouse position
     */
    public void setOffset(CodeBlock c, MouseEvent e) {
        if(c != null) {
            xOffset =  c.getXCenter() - e.getX();
            yOffset = c.getYCenter() - e.getY();
            draggedCodeBlock = c;
        }
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
            stateRepository.setCurrentlySelectedDrawable(draggedCodeBlock);

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
