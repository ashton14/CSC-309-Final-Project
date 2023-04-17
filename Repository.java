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
    private ArrayList<CodeBlock> codeBlocks;
    private ArrayList<Line> lines;
    private ArrayList<Object> drawables;


    /**
     * Constructor to initialize data
     */
    private Repository(){
        selectedCodeBlock = "Start";
        codeBlocks = new ArrayList<>();
        lines = new ArrayList<>();
        drawables = new ArrayList<>();
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
     * Sets the currently selected code block from menu
     * @param s code block as a string
     */
    public void setSelectedCodeBlock(String s){
        selectedCodeBlock = s;
        setChanged();
        notifyObservers(selectedCodeBlock);
    }

    /**
     * Sets selected menu item from menu bar
     * @param s
     */
    public void selectMenuItem(String s){
        selectedMenuItem = s;
        setChanged();
        notifyObservers(selectedMenuItem);
    }

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
     * getter for currently selected code block
     * @return currently selected code block
     */
    public String getSelectedCodeBlock() {
        return selectedCodeBlock;
    }


    /**
     * undo last drawn code block
     */
    public void undo() {
        if (drawables != null && drawables.size() > 0) {
            if (drawables.get(drawables.size() - 1).getClass().equals(Line.class)) {
                if (lines != null && lines.size() > 0) {
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

    /**
     * notifies observer to clear drawing area
     */
    public void repaintWorkingArea() {
        setChanged();
        notifyObservers();
    }
}
