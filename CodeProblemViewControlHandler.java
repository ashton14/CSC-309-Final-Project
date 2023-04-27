import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to be instantiated to handle the button presses for the
 * CodeProblemView instances.
 */
public class CodeProblemViewControlHandler implements ActionListener {

    /**
     * Handles the Next, Previous, Help and Submit button presses.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String commandString = e.getActionCommand();
        if(commandString.equals("Next")){
            // call to repo function to change code problem
        } else if(commandString.equals("Prev")){
            // call to repo function to change code problem
        } else if(commandString.equals("Help")){
            // call to repo function to make help
        } else if(commandString.equals("Submit")){
            // call to repo function to get feedback
        }
    }
}
