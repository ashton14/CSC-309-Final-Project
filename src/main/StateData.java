package main;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * @author Ashton Alonge
 * @author Connor Hickey
 * Repository class to handle data for the state the program is in (Singleton).
 * State data includes items that are currently selected and system status.
 */
public class StateData extends Observable {
    private String menuBarCodeBlock;
    private String selectedMenuItem;
    private Line currentlySelectedLine;
    private CodeBlock currentlySelectedCodeBlock;
    private String status;
    private String mode;

    /**
     * Constructor to initialize state data
     */
    public StateData(){
        mode = "Sandbox";
        menuBarCodeBlock = "Start";
        selectedMenuItem = null;
        currentlySelectedLine = null;
        currentlySelectedCodeBlock = null;
        status = "Placing Start Blocks";
    }


    /**
     * Setter for selected line and notifies observers.
     * @param line - line to be set as selected.
     */
    public void setCurrentlySelectedLine(Line line) {
        currentlySelectedLine = line;
        setChanged();
        notifyObservers( "Line Selected");
    }

    /**
     * getter for selected line.
     * @return currently selected line.
     */
    public Line getCurrentlySelectedLine() {
        return currentlySelectedLine;
    }

    /**
     * Sets the clicked code block as the currently selected code block,
     * along with populating the outlineShape field
     * with the correct shape. Notifies observers.
     * @param block CodeBlock to be set as currently selected.
     */
    public void setCurrentlySelectedCodeBlock(CodeBlock block) {
        if(block == null){
            return;
        }
        currentlySelectedCodeBlock = block;
        setChanged();
        notifyObservers( "Selected " + currentlySelectedCodeBlock.toString() + " Block");
    }

    /**
     * getter for currently selected code block.
     * @return the CodeBlock object that was last clicked.
     */
    public CodeBlock getCurrentlySelectedCodeBlock() {
        return currentlySelectedCodeBlock;
    }

    /**
     * getter for currently selected code block outline Shape.
     * @return the Shape object that is the correct outline for the currently selected shape.
     */
    public main.Shape getCurrentlySelectedCodeBlockOutline() {
        Shape shapeOutline = currentlySelectedCodeBlock.copyShape();
        shapeOutline.setColor(Color.YELLOW);
        shapeOutline.setWidth(shapeOutline.getWidth() + 5);
        shapeOutline.setHeight(shapeOutline.getHeight() + 5);
        return shapeOutline;
    }

    /**
     * Sets the currently selected code block from menu.
     * Notifies observers.
     * @param s code block as a string
     */
    public void setMenuBarCodeBlock(String s){
        menuBarCodeBlock = s;
        setChanged();
        notifyObservers(menuBarCodeBlock);
    }

    /**
     * getter for currently selected code block
     * @return currently selected code block
     */
    public String getMenuBarCodeBlock() {
        return menuBarCodeBlock;
    }

    /**
     * Sets selected menu item from menu bar and
     * notifies observers.
     * @param s The String representation of the
     *          selected menu item.
     */
    public void setSelectedMenuItem(String s){
        selectedMenuItem = s;
        setChanged();
        notifyObservers(selectedMenuItem);
    }

    /**
     * Gets the selected menu item as a String.
     * @return A String representation of the selected menu item.
     */
    public String getSelectedMenuItem(){ return selectedMenuItem; }

    /**
     * Removes the selected Drawable from the DrawableData in
     * the DataRepository and notifies observers.
     */
    public void deleteSelectedItem(){
        Repository dataRepository = DataRepository.getInstance();
        DrawableData drawableData = (DrawableData) dataRepository.getData();
        ArrayList<Drawable> drawables = drawableData.getDrawables();
        ArrayList<Drawable> copy = new ArrayList<>();

        if(currentlySelectedLine != null){
            drawables.remove(currentlySelectedLine);
            currentlySelectedLine.getStart().removeConnection(currentlySelectedLine.getEnd());
            drawableData.getDrawables().remove(currentlySelectedLine);
        }
        if(currentlySelectedCodeBlock != null) {
            for (Drawable drawable : drawables) {
                if (drawable.getClass() == Line.class) {
                    Line line = (Line) drawable;
                    if (line.getStart() != currentlySelectedCodeBlock &&
                            line.getEnd() != currentlySelectedCodeBlock) {
                        copy.add(drawable);
                    }
                } else {
                    if (drawable != currentlySelectedCodeBlock) {
                        copy.add(drawable);
                    }
                }
            }
            currentlySelectedCodeBlock.removeAllConnections();
            drawableData.getDrawables().remove(currentlySelectedCodeBlock);
            drawableData.clear();
            drawableData.addAll(copy);
        }
        currentlySelectedCodeBlock = null;
        setChanged();
        notifyObservers("Selected element deleted");
    }

    /**
     * Changes the mode of the DiagramApp
     * (Sandbox, Code to Flowchart, Flowchart to Code.
     * @param mode   The mode to change the DiagramApp to as
     *               a String.
     */
    public void setMode(String mode){
        if(this.mode.equals(mode)){
            return;
        }
        this.mode = mode;
        setChanged();
        notifyObservers(mode);
    }


    /**
     * Returns the mode.
     * @return The mode as a String.
     */
    public String getMode(){
        return mode;
    }

    /**
     * Notifies observers with the new status of this StateData.
     * @param s  A String representation of the new status.
     */
    public void setStatus(String s){
        if(status.equals(s)){
            return;
        }
        status = s;
        setChanged();
        notifyObservers(s);
    }

}
