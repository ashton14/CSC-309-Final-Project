package src.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        String commandString = e.getActionCommand();
        if(commandString.equals("Next")){
            // call to repo function to change code problem
            pRepo.setNextProblemIndex();
        } else if(commandString.equals("Previous")){
            // call to repo function to change code problem
            pRepo.setPrevProblemIndex();
        } else if(commandString.equals("Help")){
            UserExample solution = pRepo.getCurrentProblem();
            ArrayList<CodeBlock> studentAnswerBlocks =
                    ((DataRepository)(DataRepository.getInstance())).getCodeBlocks();
            GradeFlowchart evaluate = new GradeFlowchart(solution.getCodeBlocks(), studentAnswerBlocks, true);
            evaluate.grade();

        } else if(commandString.equals("Submit")){
            UserExample solution = pRepo.getCurrentProblem();
            ArrayList<CodeBlock> studentAnswerBlocks =
                    ((DataRepository)(DataRepository.getInstance())).getCodeBlocks();
            GradeFlowchart evaluate = new GradeFlowchart(solution.getCodeBlocks(), studentAnswerBlocks, false);
            evaluate.grade();
            // call to repo function to get feedback

            //temporary code for testing purposes
            /*UserExample ex1 = pRepo.getCurrentProblem();
            System.out.println("SUBMIT BUTTON PUSHED");
            System.out.println(ex1.getFlowChart().size());
            DataRepository dataRepository = (DataRepository) DataRepository.getInstance();
            int mistakeIndex = ex1.gradeUserDiagram(dataRepository.getCodeBlocks());
            System.out.println("mistake at CodeBlock index: "+mistakeIndex);*/
        }
    }
}
