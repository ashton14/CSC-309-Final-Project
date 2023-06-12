package src.main;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Repository to hold feedback to send to the user.
 */
public class FeedbackRepository extends Observable implements Repository {

    public static final int REQUEST_COMPLEXITY = 0;
    public static final int RESET_METRICS_FIELDS = 1;
    public static final int REQUEST_CLEAR = 1;
    private static FeedbackRepository repository;
    private int feedbackNumber;
    private int errorIndex;
    private String cyclomaticComplexity;

    /**
     * Private constructor to enforce singleton.
     */
    private FeedbackRepository(){
        this.errorIndex = -1;
        feedbackNumber = 0;
        cyclomaticComplexity = "";
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
     * Notifies observers giving textual feedback
     * that a new set of feedback is being given.
     */
    public void newFeedback(){
        ++feedbackNumber;
        setChanged();
        notifyObservers("\nFeedback Number: " + feedbackNumber  + "\n");
    }

    /**
     * Sets the feedback String and notifies observers.
     * @param feedback   The feedback String to send to
     *                   the user.
     */
    public void setFeedback(String feedback){
        if(feedback == null)
            return;
        setChanged();
        notifyObservers("Casey: " + feedback + "\n");
    }


    /**
     * Stores a String representing cyclomatic complexity in
     * this FeedbackRepository to be used for the purpose
     * of giving feedback.
     * @param cyclomaticComplexity   The cyclomaticComplexity of this
     *                               FeedBackRepository as a String.
     */
    public void setCyclomaticComplexity(String cyclomaticComplexity){
        this.cyclomaticComplexity = cyclomaticComplexity;
    }

    /**
     * Gets the cyclomatic complexity in this FeedbackRepository as
     * a String.
     * @return   A String representation of the cyclomatic complexity
     * stored in this FeedbackRepository as a String.
     */
    public String getCyclomaticComplexity(){
        return cyclomaticComplexity;
    }

    /**
     * Sends a request to fetch metrics from an observer.
     * @param request   A request corresponding to a static
     *                  constant within this FeedbackRepository.
     */
    public void metricsPromptRequest(int request){
        setChanged();
        notifyObservers(request);
    }

    /**
     * Sends a request to the MetricsPrompt to change the
     * Color of one of its JTextFields. Makes the request
     * in the form of an ArrayList of type Integer.
     *
     * @param index   The index of the JTextField to modify
     *                in the observer given as a Static
     *                constant within MetricsPrompt.
     * @param isCorrect  An integer representing if the
     *                   answer was correct (0 = false, else true).
     */
    public void setColorTextField(int index, boolean isCorrect){
        ArrayList<Integer> indexGrade = new ArrayList<>();
        int isCorrectInt = isCorrect ? 1 : 0;
        indexGrade.add(index);
        indexGrade.add(isCorrectInt);
        setChanged();
        notifyObservers(indexGrade);
    }

    public void setErrorIndex(int index) {
        this.errorIndex = index;
        setChanged();
        notifyObservers();
    }
    public int getErrorIndex() {
        return this.errorIndex;
    }
}
