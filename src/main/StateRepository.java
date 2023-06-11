package src.main;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * @author Aaron Bettencourt (most recent)
 * @author Ashton Alonge
 * @author Connor Hickey
 * Repository class to handle data for the state the program is in (Singleton).
 * State data includes items that are currently selected and system status.
 */
public class StateRepository extends Observable implements Repository {
    private String menuBarCodeBlock;
    private String selectedMenuItem;
    private Drawable currentlySelectedDrawable;
    private String status;
    private String mode;

    private static StateRepository repository;

    /**
     * Constructor to initialize data
     */
    private StateRepository(){
        mode = "Sandbox";
        menuBarCodeBlock = "Start";
        selectedMenuItem = null;
        currentlySelectedDrawable = null;
        status = "Placing Start Blocks";
    }

    /**
     * getter for instance of repository, initializes if null
     * @return instance of repository
     */
    public static Repository getInstance(){
        if(repository == null) {
            repository = new StateRepository();
        }
        return repository;
    }

    /**
     * Function used to reset this StateRepository
     * to constructor defaults. Exists
     * only for the purposes of unit testing
     */
    public void reset(){
        mode = "Sandbox";
        menuBarCodeBlock = "Start";
        selectedMenuItem = null;
        currentlySelectedDrawable = null;
        status = "Placing Start Blocks";
    }


    /**
     * setter for selected element as a Drawable
     * @param drawable - The drawable to be selected.
     */
    public void setCurrentlySelectedDrawable(Drawable drawable) {
        String message;
        if(drawable == null) {
            message = "Element Unselected";
        } else {
            message = "Element Selected";
        }
        System.out.println("before " + currentlySelectedDrawable + " after " + drawable);
        if(currentlySelectedDrawable instanceof Link && currentlySelectedDrawable != drawable) {
            ((Link) currentlySelectedDrawable).deSelect();
        }
        currentlySelectedDrawable = drawable;
        setChanged();
        notifyObservers(message);
    }

    /**
     * getter for selected Drawable
     * @return currently selected Drawable
     */
    public Drawable getCurrentlySelectedDrawable() {
        return currentlySelectedDrawable;
    }

    /**
     * Returns the currently selected Drawable as a Line
     * if possible, otherwise null.
     * @return The currently selected Drawable as a Line
     * if possible, otherwise null.
     */
    public Line getCurrentlySelectedLine(){
        if(currentlySelectedDrawable instanceof Line){
            return (Line) currentlySelectedDrawable;
        }
        return null;
    }

    /**
     * Returns the currently selected Drawable as a CodeBlock
     * if possible, otherwise null.
     * @return The currently selected Drawable as a CodeBlock
     * if possible, otherwise null.
     */
    public CodeBlock getCurrentlySelectedCodeBlock(){
        if(currentlySelectedDrawable instanceof CodeBlock){
            return (CodeBlock) currentlySelectedDrawable;
        }
        return null;
    }

    /**
     * Returns the outline for the currently selected
     * Drawable if it is a CodeBlock instance as a Shape.
     * @return The outline for the currently selected
     * Drawable if it is a CodeBlock instance as a Shape.
     */
    public Shape getCurrentlySelectedCodeBlockOutline(){
        if(currentlySelectedDrawable instanceof CodeBlock) {
            CodeBlock codeBlock = ((CodeBlock) currentlySelectedDrawable);
            Shape shapeOutline = codeBlock.copyShape();
            Shape innerShape = codeBlock.getShape();
            innerShape.addObserver(shapeOutline);
            shapeOutline.setColor(Color.YELLOW);
            int width = shapeOutline.getWidth();
            int height = shapeOutline.getHeight();
            shapeOutline.setWidth(width + 5);
            shapeOutline.setHeight(height + 5);
            return shapeOutline;
        }
        return null;
    }

    /**
     * Returns the outline for the currently selected
     * Drawable if it is a Line instance as a Line.
     * @return The outline for the currently selected
     * Drawable if it is a Line instance as a Line.
     */
    public Line getCurrentlySelectedLineOutline(){
        if(currentlySelectedDrawable == null)
            return null;
        if (currentlySelectedDrawable.getClass() == Line.class
        || currentlySelectedDrawable instanceof Line) {
            Line line = (Line) currentlySelectedDrawable;
            line.setColor(Color.YELLOW);
            Line lineOutline = new Line(line.getStart(), line.getEnd());
            lineOutline.setStrokeWidth(5);
            return lineOutline;
        }
        return null;
    }

    /**
     * Getter for the currently selected drawable outline as a Drawable.
     * @return The currently selected drawable outline as a Drawable.
     */
    public Drawable getCurrentlySelectedDrawableOutline() {
        Drawable result = getCurrentlySelectedCodeBlockOutline();
        if(result == null){
            result = getCurrentlySelectedLineOutline();
        }
        return result;
    }

    /**
     * Sets the currently selected code block from menu
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
     * Sets selected menu item from menu bar
     * @param s
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
     * the DataRepository.
     */
    public void deleteSelectedItem(){
        DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
        dataRepository.removeDrawable(currentlySelectedDrawable);
        if(currentlySelectedDrawable instanceof Link) {
            ((Link) currentlySelectedDrawable).disconnect();
        }
        currentlySelectedDrawable = null;
        setChanged();
        notifyObservers("Selected element deleted");
    }

    /**
     * Changes the mode of the DiagramApp
     * (Sandbox, Code to Flowchart, Flowchart to Code.
     * @param mode   The mode to change the DiagramApp to as
     *               a String.
     */
    public void changeMode(String mode){
        if(this.mode.equals(mode)){
            return;
        }
        this.mode = mode;
        setChanged();
        notifyObservers(mode);
    }
    public String getMode() {
        return this.mode;
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

    /**
     * Returns the status of this StateRepository as a String.
     * @return The status of this StateRepository as a String.
     */
    public String getStatus(){
        return status;
    }

}
