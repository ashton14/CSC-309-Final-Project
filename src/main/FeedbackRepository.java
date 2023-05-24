package src.main;
import java.util.Observable;

/**
 * Repository to hold feedback to send to the user.
 */
public class FeedbackRepository extends Observable implements Repository {

    private static FeedbackRepository repository;
    private String feedback;

    /**
     * Private constructor to enforce singleton.
     */
    private FeedbackRepository(){
    }

    /**
     * getter for instance of repository, initializes if null
     * @return instance of repository
     */
    public static Repository getInstance(){
        if(repository == null) {
            repository = new FeedbackRepository();
        }
        return repository;
    }

    /**
     * Sets the feedback String and notifies observers.
     * @param feedback   The feedback String to send to
     *                   the user.
     */
    public void setFeedback(String feedback){
        if(feedback == null)
            return;
        this.feedback = feedback;
        setChanged();
        notifyObservers("Jimbo: " + feedback + "\n");
    }


}