package src.main;

/**
 * The AppPage interface represents a page in the application.
 * It provides methods to show the contents of the page and retrieve header information.
 *
 * @author Connor Hickey
 */
interface AppPage {
    /**
     * Shows the contents of the page.
     */
    void showContents();

    /**
     * Retrieves the header information of the page.
     *
     * @return the header information.
     */
    String getHeaderInfo();
}
