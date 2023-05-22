package src.main;

import java.util.ArrayList;
import java.util.Observable;

public class FeedbackRepository extends Observable implements Repository {

    private static FeedbackRepository repository;
    private String feedback;

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

    public void setFeedback(String feedback){
        if(feedback == null)
            return;
        this.feedback = feedback;
        setChanged();
        notifyObservers("Jimbo: " + feedback + "\n");
    }


}
