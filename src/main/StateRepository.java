package main;

/**
 * @author Ashton Alonge
 * @author Connor Hickey
 * Repository class to handle data (Singleton)
 */
public class StateRepository implements Repository {
    private static StateRepository repository;
    private StateData data;

    /**
     * Constructor to initialize data
     */
    private StateRepository(){
        data = new StateData();
    }

    /**
     * getter for instance of repository, initializes if null
     * @return instance of repository
     */
    public static StateRepository getInstance(){
        if(repository == null) {
            repository = new StateRepository();
        }
        return repository;
    }

    public Object getData(){
        return data;
    }
}
