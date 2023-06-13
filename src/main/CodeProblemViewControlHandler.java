package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Class to be instantiated to handle the button presses for the
 * CodeProblemView instances.
 * @author Aaron Bettencourt, Ashton Alonge
 */
public class CodeProblemViewControlHandler implements ActionListener {

    private JButton prev;
    private JButton next;
    private int currentProblemIndex = 0;
    private int numProblemsInCurrentAssignment = ((ProblemRepository) ProblemRepository.getInstance())
            .getNumAssignmentProblems(AssignmentsView.getCurrentAssignment().substring(0,11)+
                    (Character.toLowerCase(AssignmentsView.getCurrentAssignment().charAt(11)) - 'a' + 1));
    /*private int numProblemsInCurrentAssignment = ((ProblemRepository) ProblemRepository.getInstance())
            .getNumAssignmentProblems(CoursesPage.getCurrentAssignment().substring(0,11)+
                    (Character.toLowerCase(CoursesPage.getCurrentAssignment().charAt(11)) - 'a' + 1));
*/
    public CodeProblemViewControlHandler(JButton prev, JButton next){
        this.prev = prev;
        this.next = next;
        next.setEnabled(false);
        prev.setEnabled(false);
    }

    /**
     * Helper function to display a prompt that the problem is correct.
     */
    private void displayCorrectPrompt() {
            DecimalFormat df = new DecimalFormat("#0");
            JOptionPane.showMessageDialog(null,
                    "Correct! Nice Work!\n" + df.format((double) (currentProblemIndex + 1) /
                            (double) numProblemsInCurrentAssignment * 100) + "% complete.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE, CodeProblemViewControlHandler.createIcon());
            if (numProblemsInCurrentAssignment > currentProblemIndex + 1) {
                next.setEnabled(true);
            }
            if(currentProblemIndex+1 == numProblemsInCurrentAssignment) {
                ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
                pRepo.setAssignmentComplete(true);
            }
    }

    /**
     * Grades the current problem and sends verbose or minimal feedback to the user.
     * @param isVerbose   A boolean representing if verbose or minimal feedback
     *                    should be given to the user.
     * @return  A boolean representing if the problem was correct.
     */
    private boolean gradeProblem(boolean isVerbose){
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        UserExample solution = pRepo.getCurrentProblem();
        ArrayList<CodeBlock> studentAnswerBlocks =
                ((DataRepository)(DataRepository.getInstance())).getCodeBlocks();

        GradeFlowchart gradeFlowchart = new GradeFlowchart(solution.getCodeBlocks(), studentAnswerBlocks, isVerbose);
        GradeCyclomaticComplexity gradeCyclomaticComplexity = new GradeCyclomaticComplexity(solution.getCodeBlocks(), isVerbose);
        boolean correctFlowchart = gradeFlowchart.grade();

        ((FeedbackRepository)FeedbackRepository.getInstance()).metricsPromptRequest(FeedbackRepository.REQUEST_COMPLEXITY);
        String complexityStr = ((FeedbackRepository)FeedbackRepository.getInstance()).getCyclomaticComplexity();
        boolean correctComplexity = gradeCyclomaticComplexity.grade(complexityStr);
        ((FeedbackRepository)FeedbackRepository.getInstance()).setColorTextField(FeedbackRepository.REQUEST_COMPLEXITY, correctComplexity);

        return correctComplexity && correctFlowchart;
    }

    /**
     * Handles the Next, Previous, Help and Submit button presses.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        String commandString = e.getActionCommand();
        if(commandString.equals("Next")){
            FeedbackRepository fRepo = (FeedbackRepository) FeedbackRepository.getInstance();
            fRepo.setErrorIndex(-2);
            currentProblemIndex++;

            pRepo.setNextProblemIndex();
            next.setEnabled(false);
            prev.setEnabled(true);
            ((FeedbackRepository)FeedbackRepository.getInstance()).
                    metricsPromptRequest(FeedbackRepository.REQUEST_CLEAR);

        } else if(commandString.equals("Previous")){
            FeedbackRepository fRepo = (FeedbackRepository) FeedbackRepository.getInstance();
            fRepo.setErrorIndex(-2);
            currentProblemIndex--;
            next.setEnabled(true);
            pRepo.setPreviousProblem();
            if(currentProblemIndex == 0){
                prev.setEnabled(false);
            } else {
                prev.setEnabled(true);
            }
            ((FeedbackRepository)FeedbackRepository.getInstance()).
                    metricsPromptRequest(FeedbackRepository.REQUEST_CLEAR);

        } else if(commandString.equals("Help")){
            gradeProblem(true);

        } else if(commandString.equals("Submit")) {

            boolean isCorrect = gradeProblem(false);
            /*testing
            boolean isCorrect = true;*/
            if (isCorrect) {
                FeedbackRepository fRepo = (FeedbackRepository) FeedbackRepository.getInstance();
                fRepo.setErrorIndex(-1);
                displayCorrectPrompt();
                if (numProblemsInCurrentAssignment > currentProblemIndex + 1) {
                    next.setEnabled(true);
                } else {
                    next.setEnabled(false);
                    // Update the completion status in ProblemRepository
                    pRepo.setAssignmentComplete(true); // Mark current assignment as completed
                }
            }
        }
    }

    public static Icon createIcon() {
        // Create a custom icon image with a green check mark
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(7, 16, 12, 22);
        g2d.drawLine(12, 22, 24, 8);
        g2d.dispose();

        // Create an ImageIcon from the custom icon image
        return new ImageIcon(image);
    }
}
