package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Class to be instantiated to handle the button presses for the
 * CodeProblemView instances.
 */
public class CodeProblemViewControlHandler implements ActionListener {

    private JButton prev;
    private JButton next;
    private int curProblemNumber = 0;
    private int numProblemsCompleted = 0;

    public CodeProblemViewControlHandler(JButton prev, JButton next){
        this.prev = prev;
        this.next = next;
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
            curProblemNumber ++;
            // call to repo function to change code problem
            pRepo.setNextProblemIndex();
            if(curProblemNumber == numProblemsCompleted)
                this.next.setEnabled(false);
            this.prev.setEnabled(true);

        } else if(commandString.equals("Previous")){
            next.setEnabled(true);
            // call to repo function to change code problem
            pRepo.setPrevProblemIndex();
            curProblemNumber --;
            if(curProblemNumber == 0)
                this.prev.setEnabled(false);
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
            if(evaluate.grade()) {
                JOptionPane.showMessageDialog(null, "Correct! Nice Work!", "Success",
                                            JOptionPane.INFORMATION_MESSAGE, createIcon());

                this.next.setEnabled(true);
                if(curProblemNumber == numProblemsCompleted)
                    numProblemsCompleted ++;
            }

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
