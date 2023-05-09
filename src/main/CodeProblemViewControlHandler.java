package src.main;

import src.test.UserExampleTests;

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

            //temporary code for testing purposes
            UserExample ex1 = UserExampleTests.getEx1();
            System.out.println("SUBMIT BUTTON PUSHED");
            //System.out.println(Repository.getInstance().g.size());
            System.out.println(ex1.getCodeBlocks().size());
            DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
            int mistakeIndex = ex1.gradeUserDiagram(dataRepository.getCodeBlocks());
            System.out.println("mistake at CodeBlock index: "+mistakeIndex);
        }
    }
}
