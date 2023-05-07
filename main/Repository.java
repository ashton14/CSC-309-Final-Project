package main;

/**
 * An interface to be implemented for
 * the creation of multiple Singleton
 * Repository classes.
 */
public interface Repository {

    /**
     * An interface method to be overridden
     * to get an instance of this Repository.
     * @return An instance of this Repository.
     */
    public static Repository getInstance() {
        return null;
    }
    public void removeCodeBlockSelection() {
        currentlySelectedCodeBlock = null;
        currentlySelectedCodeBlockOutline = new main.Rectangle(0, 0, 0, 0, Color.white);
    }

    /**
     * An interface method to be overridden
     * to get the data stored within this
     * Repository.
     * @return The data Object within this
     * Repository.
     */

    public Object getData();
}