package src.main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FlowchartProblemViewControlHandler implements ActionListener, MouseListener {

    private static ArrayList<JTextField> jTextAreas = null;
    private JButton prev;
    private JButton next;
    private int curProblemNumber = 1;
    private int numProblemsCompleted = 0;

    private int numProblemsInCurrentAssignment = ((ProblemRepository) ProblemRepository.getInstance())
            .getNumAssignmentProblems(AssignmentsView.getCurrentAssignment().substring(0,11)+
                    (Character.toLowerCase(AssignmentsView.getCurrentAssignment().charAt(11)) - 'a' + 1));

    /*private int numProblemsInCurrentAssignment = ((ProblemRepository) ProblemRepository.getInstance())
            .getNumAssignmentProblems(CoursesPage.getCurrentAssignment().substring(0,11)+
                    (Character.toLowerCase(CoursesPage.getCurrentAssignment().charAt(11)) - 'a' + 1));*/

    FlowchartProblemViewControlHandler(ArrayList<JTextField> jTextAreas, JButton prev, JButton next){
        this.jTextAreas = jTextAreas;
        this.prev = prev;
        this.next = next;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ProblemRepository pRepo = (ProblemRepository)ProblemRepository.getInstance();
        System.out.println("a button was pushed: "+e.getActionCommand());
        if(e.getActionCommand().equals("Submit")){
            StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
            stateRepository.setStatus("Parsed code");
            gradeCode(false);
        }
        if(e.getActionCommand().equals("Help")){
            gradeCode(true);
        }
        if(e.getActionCommand().equals("Next")) {
            FeedbackRepository fRepo = (FeedbackRepository) FeedbackRepository.getInstance();
            fRepo.setErrorIndex(-2);
            clearCode();
            System.out.println("setting next problem");
            curProblemNumber ++;
            pRepo.setNextProblem();
            if(curProblemNumber > numProblemsCompleted || curProblemNumber == numProblemsInCurrentAssignment)
                this.next.setEnabled(false);
            this.prev.setEnabled(true);

        }
        if(e.getActionCommand().equals("Previous")) {
            FeedbackRepository fRepo = (FeedbackRepository) FeedbackRepository.getInstance();
            fRepo.setErrorIndex(-2);
            clearCode();
            next.setEnabled(true);
            pRepo.setPreviousProblem();
            curProblemNumber --;
            if(curProblemNumber == 1)
                this.prev.setEnabled(false);
        }

    }
    public void gradeCode(boolean help) {

        ProblemRepository pRepo = (ProblemRepository)ProblemRepository.getInstance();
        UserExample currentProblem = pRepo.getCurrentProblem();

        ArrayList<String> usersCode = new ArrayList<>();
        for(int i = 0; i < jTextAreas.size(); i++) {
            if(!jTextAreas.get(i).getText().equals("")) {
                usersCode.add(jTextAreas.get(i).getText());
            }
        }
        int mistakeIndex = currentProblem.gradeUserCode(usersCode,help);
        FeedbackRepository fRepo = (FeedbackRepository) FeedbackRepository.getInstance();
        fRepo.setErrorIndex(mistakeIndex);

        if (mistakeIndex == -1) {
            DecimalFormat df = new DecimalFormat("#0");
                JOptionPane.showMessageDialog(null,
                    "Correct! Nice Work!\n"+df.format((double)(curProblemNumber)/
                            (double)numProblemsInCurrentAssignment*100) +"% complete.",
                        "Success",
                    JOptionPane.INFORMATION_MESSAGE, CodeProblemViewControlHandler.createIcon());
            if(curProblemNumber == numProblemsCompleted + 1) {
                numProblemsCompleted++;
            }
            if(numProblemsCompleted < numProblemsInCurrentAssignment ) {
                this.next.setEnabled(true);
            }
            else {
                this.next.setEnabled(false);
                CoursesPage.numAssignmentsCompleted++;
                CoursesPage.updateCourseProgress();
            }

            if(curProblemNumber+1 == numProblemsInCurrentAssignment) {
                pRepo.setAssignmentComplete(true);
            }
        }
    }

    public static void clearCode(){
        if(jTextAreas != null){
            for(int i = 0; i < jTextAreas.size(); i++) {
                jTextAreas.get(i).setText("");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
        stateRepository.setStatus("Coding...");
    }

    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}

