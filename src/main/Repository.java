package src.main;
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
}