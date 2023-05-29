package src.main;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class FlowchartProblemViewControlHandler implements ActionListener, MouseListener {

    private final ArrayList<JTextField> jTextAreas;
    private JButton prev;
    private JButton next;
    private int curProblemNumber = 0;
    private int numProblemsCompleted = 0;

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
            System.out.println("setting next problem");
            curProblemNumber ++;
            pRepo.setNextProblem();
            if(curProblemNumber == numProblemsCompleted)
                this.next.setEnabled(false);
            this.prev.setEnabled(true);
        }
        if(e.getActionCommand().equals("Previous")) {
            next.setEnabled(true);
            pRepo.setPreviousProblem();
            curProblemNumber --;
            if(curProblemNumber == 0)
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
            JOptionPane.showMessageDialog(null, "Correct! Nice Work!", "Success",
                    JOptionPane.INFORMATION_MESSAGE, CodeProblemViewControlHandler.createIcon());
            this.next.setEnabled(true);
            if(curProblemNumber == numProblemsCompleted)
                numProblemsCompleted ++;
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

