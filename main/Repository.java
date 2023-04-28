package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * @author Ashton Alonge
 * Repository class to handle data (Singleton)
 */
public class Repository extends Observable {
    private static Repository repository;
    private String selectedCodeBlock;
    private String selectedMenuItem;
    private CodeBlock currentlySelectedCodeBlock;
    private Shape currentlySelectedCodeBlockOutline;
    private ArrayList<CodeBlock> codeBlocks;
    private ArrayList<Line> lines;
    private ArrayList<Drawable> drawables;
    private String status;

    private String mode;

    /**
     * Constructor to initialize data
     */
    private Repository(){
        mode = "Sandbox";
        selectedCodeBlock = "Start";
        codeBlocks = new ArrayList<>();
        lines = new ArrayList<>();
        drawables = new ArrayList<>();
        currentlySelectedCodeBlock = null;
        currentlySelectedCodeBlockOutline = new main.Rectangle(0, 0, 0, 0, Color.white);
    }

    /**
     * getter for instance of repository, initializes if null
     * @return instance of repository
     */
    public static Repository getInstance(){
        if(repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    /**
     * Sets the clicked code block as the currently selected code block, along with populating the outlineShape field
     * with the correct shape
     * @param block code block to be set as currently selected
     */
    public void setCurrentlySelectedCodeBlock(CodeBlock block) {
        Circle circOutline = new Circle(block.getXCenter(), block.getYCenter(), 60, Color.decode("#E93D2D"));
        Rectangle rectOutline = new Rectangle(block.getXCenter(), block.getYCenter(), 110,
                50, Color.decode("#E93D2D"));
        Diamond diamOutline =  new Diamond(block.getXCenter(), block.getYCenter(), 61,
                77, Color.decode("#E93D2D"));
        Parallelogram prlgrmOutline =  new Parallelogram(block.getXCenter(), block.getYCenter(), 110,
                50, Color.decode("#E93D2D"));
        String curBlockName = "";
        if (block.getClass().equals(FunctionBlock.class)) {
            currentlySelectedCodeBlockOutline = rectOutline;
            curBlockName = "Function";
        }
        else if (block.getClass().equals(IfBlock.class)) {
            currentlySelectedCodeBlockOutline = diamOutline;
            curBlockName = "If";
        }
        else if (block.getClass().equals(InstructionBlock.class)) {
            currentlySelectedCodeBlockOutline = rectOutline;
            curBlockName = "Instruction";
        }
        else if (block.getClass().equals(LoopBlock.class)) {
            currentlySelectedCodeBlockOutline = diamOutline;
            curBlockName = "Loop";
        }
        else if (block.getClass().equals(StartBlock.class)) {
            currentlySelectedCodeBlockOutline = circOutline;
            curBlockName = "Start";
        }
        else if (block.getClass().equals(StopBlock.class)) {
            currentlySelectedCodeBlockOutline = circOutline;
            curBlockName = "Stop";
        }
        else if (block.getClass().equals(VariableBlock.class)) {
            currentlySelectedCodeBlockOutline = rectOutline;
            curBlockName = "Variable";
        }
        else if (block.getClass().equals(PrintBlock.class)) {
            currentlySelectedCodeBlockOutline = prlgrmOutline;
            curBlockName = "Print";
        }
        currentlySelectedCodeBlock = block;
        setChanged();
        notifyObservers(curBlockName+" Block selected.");
    }

    /**
     * getter for currently selected code block
     * @return the Code Block object that was last clicked
     */
    public CodeBlock getCurrentlySelectedCodeBlock() {
        return currentlySelectedCodeBlock;
    }

    /**
     * getter for currently selected code block outline Shape
     * @return the Shape object that is the correct outline for the currently selected shape
     */
    public Shape getCurrentlySelectedCodeBlockOutline() {
        return currentlySelectedCodeBlockOutline;
    }

    /**
     * Sets the currently selected code block from menu
     * @param s code block as a string
     */
    public void setSelectedCodeBlock(String s){
        selectedCodeBlock = s;
        setChanged();
        notifyObservers(selectedCodeBlock);
    }

    /**
     * getter for currently selected code block
     * @return currently selected code block
     */
    public String getSelectedCodeBlock() {
        return selectedCodeBlock;
    }

    /**
     * Sets selected menu item from menu bar
     * @param s
     */
    public void setSelectedMenuItem(String s){
        selectedMenuItem = s;
        setChanged();
        notifyObservers(selectedMenuItem);
    }

    public String getSelectedMenuItem(){ return selectedMenuItem; }

    /**
     * Sets list of code blocks
     * @param codeBlocks list of code blocks
     */
    public void setCodeBlocks(ArrayList<CodeBlock> codeBlocks) {
        this.codeBlocks = codeBlocks;
    }

    /**
     * getter for data
     * @return list of code blocks
     */
    public ArrayList<CodeBlock> getCodeBlocks() {
        if(codeBlocks == null){
            codeBlocks = new ArrayList<>();
        }
        return codeBlocks;
    }

    /**
     * getter for data
     * @return list of drawables
     */
    public ArrayList<Drawable> getDrawables() {
        if(drawables == null){
            drawables = new ArrayList<>();
        }
        return drawables;
    }

    /**
     * adds code block to list
     * @param codeBlock code block
     */
    public void addCodeBlock(CodeBlock codeBlock) {
        if(codeBlocks == null){
            codeBlocks = new ArrayList<>();
        }
        codeBlocks.add(codeBlock);
        drawables.add(codeBlock);
        setChanged();
        notifyObservers();
    }

    /**
     * sets list of lines
     * @param lines list of lines
     */
    public void setLines(ArrayList<Line> lines) { this.lines = lines; }

    /**
     * getter for list of lines
     * @return list of lines
     */
    public ArrayList<Line> getLines() {
        if (lines == null) {
            return new ArrayList<>();
        }
        return lines;
    }

    /**
     * adds line to list of lines, initializes if null
     * @param l line to be added
     */
    public void addLine(Line l) {
        if(lines == null) {
            lines = new ArrayList<>();
        }
        lines.add(l);
        drawables.add(l);
    }


    /**
     * undo last drawn code block
     */
    public void undo() {
        if (drawables != null && drawables.size() > 0) {
            if (drawables.get(drawables.size() - 1).getClass().equals(Line.class)) {
                if (lines != null && lines.size() > 0) {
                    Line line = lines.get((lines.size() - 1));
                    line.getStart().removeConnection(line.getEnd());
                    lines.remove(lines.size() - 1);
                    drawables.remove(drawables.size()-1);

                }
            }
            else {
                if (codeBlocks != null && codeBlocks.size() > 0) {
                    codeBlocks.remove(codeBlocks.size() - 1);
                    drawables.remove(drawables.size()-1);
                }
            }
            setChanged();
            notifyObservers("Action undone.");
        }
    }

    /**
     * Clear the list of code blocks, notifies observer to clear drawing area
     */
    public void clear(){
        if(!codeBlocks.isEmpty()) codeBlocks.clear();
        if(!lines.isEmpty()) lines.clear();
        if(!drawables.isEmpty()) drawables.clear();

        setChanged();
        notifyObservers("Board Cleared.");
    }

    public void deleteSelectedItem(){
        codeBlocks.remove(currentlySelectedCodeBlock);
        currentlySelectedCodeBlockOutline = null;
        if(currentlySelectedCodeBlock != null) {
            setChanged();
            notifyObservers(selectedCodeBlock + " Block deleted.");
        }
        currentlySelectedCodeBlock = null;
    }

    public void parseCode(){
        //parse code
        setChanged();
        notifyObservers("Code submitted.");
    }

    public void updateStatusBar(String s){
        setChanged();
        notifyObservers(s);
    }

    /**
     * notifies observer to clear drawing area
     */
    public void repaintWorkingArea() {
        setChanged();
        notifyObservers("Dragging "+selectedCodeBlock+" Block...");
    }

    /**
     * Changes the mode of the DiagramApp
     * (Sandbox, Code to Flowchart, Flowchart to Code.
     * @param mode
     */
    public void changeMode(String mode){
        if(this.mode.equals(mode)){
            return;
        }
        this.mode = mode;
        setChanged();
        notifyObservers(mode);
    }

    public void setStatus(String s){
        status = s;
    }

    public String getStatus(){
        return this.status;
    }

}
