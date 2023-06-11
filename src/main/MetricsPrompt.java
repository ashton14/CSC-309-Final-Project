package src.main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A view to prompt for Code Metrics.
 * This class is intended to support
 * the addition of more metrics once the
 * algorithms to grade them are implemented.
 */
public class MetricsPrompt extends JPanel implements Observer {

    private ArrayList<JTextField> metricsPrompts;

    /**
     * Constructor for a MetricsPrompt object. Currently
     * contains one prompt for cyclomatic complexity and
     * a JTextField.
     */
    public MetricsPrompt(){
        GridLayout gridLayout = new GridLayout(1, 2);
        metricsPrompts = new ArrayList<>();

        this.add(new JLabel("Cyclomatic Complexity: "));
        JTextField cyclomaticField = new JTextField();
        metricsPrompts.add(cyclomaticField);
        this.add(cyclomaticField);

        this.setLayout(gridLayout);
        this.setBorder(new EmptyBorder(10,10,10,10));
    }

    /**
     * Makes all JTextFields within this View
     * have a white background and no text.
     */
    public void reset(){
        for(int i = 0; i < metricsPrompts.size(); ++i){
            metricsPrompts.get(i).setBackground(Color.WHITE);
            metricsPrompts.get(i).setText("");
        }
    }

    /**
     * Gives visual feedback for a given metric textArea by turning
     * it green if contains a correct answer and red if it has an
     * incorrect answer.
     * @param promptIndex   The index of the textArea to modify. Corresponds
     *                      to request constants.
     * @param isCorrect     A boolean representing if the answer is correct.
     */
    private void visualFeedback(int promptIndex, boolean isCorrect){
        Color feedback;
        if(promptIndex > metricsPrompts.size() || promptIndex < 0){
            return;
        }
        if(isCorrect){
            feedback = Color.GREEN;
        } else {
            feedback = Color.RED;
        }
        metricsPrompts.get(promptIndex).setBackground(feedback);
    }

    /**
     * Updates color if possible. The object received needs
     * to be in the format of an ArrayList of type Integer
     * where the first index represents the index of the
     * TextField to modify and the second index is a value
     * representing if it contains a correct answer
     * (0 = false, otherwise true).
     *
     * @param arg  The object to interpret.
     * @return  A boolean representing if updating the color
     * was successful.
     */
    private boolean updateColor(Object arg){
        if( !(arg instanceof ArrayList)) {
            return false;
        }
        ArrayList<?> data = (ArrayList<?>) arg;
        if(!data.isEmpty() && data.get(0) instanceof Integer){
            ArrayList<Integer> intData = (ArrayList<Integer>) data;
            boolean isCorrect = intData.get(1) != 0;
            visualFeedback(intData.get(0), isCorrect);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Processes an Integer request code sent to this observer. If
     * it matches one of the defined constants in the FeedbackRepository
     * for requests.
     * @param requestId   The request being made.
     */
    private void processRequest(Integer requestId){
        if(requestId == null || requestId.intValue() < 0
                || requestId.intValue() >= metricsPrompts.size()){
            return;
        }
        if(requestId == FeedbackRepository.REQUEST_COMPLEXITY){
            String complexityStr = metricsPrompts.get(FeedbackRepository.REQUEST_COMPLEXITY).getText();
            ((FeedbackRepository) (FeedbackRepository.getInstance())).
                    setCyclomaticComplexity(complexityStr);

        } else if(requestId == FeedbackRepository.RESET_METRICS_FIELDS){
            reset();
        }
    }

    /**
     * Handles input sent to this Observer. Supports
     * updating the color and contents of this MetricsPrompt's
     * TextFields and enables the retrieval of data from
     * TextFields.
     *
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Integer){
            int requestId = (Integer) arg;
            processRequest(requestId);
        } else {
            updateColor(arg);
        }
    }
}
