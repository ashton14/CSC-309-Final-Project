package Repo.main;
/**
 * @author Ashton Alonge
 * @author Connor Hickey
 * Repository class to handle the drawable data (Singleton)
 */
public class DataRepository implements Repository {
    private static DataRepository repository;
    private DrawableData repositoryData;

    /**
     * Constructor to initialize data
     */
    private DataRepository(){
        repositoryData = new DrawableData();
    }

    /**
     * getter for instance of repository, initializes if null
     * @return instance of repository
     */
    public static Repository getInstance(){
        if(repository == null) {
            repository = new DataRepository();
        }
        return repository;
    }
    public Object getData(){
        return repositoryData;
    }
}
