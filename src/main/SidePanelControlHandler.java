package src.main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class SidePanelControlHandler implements ActionListener, MouseListener {

    private final ArrayList<JTextArea> jTextAreas;

    SidePanelControlHandler(ArrayList<JTextArea> jTextAreas){
        this.jTextAreas = jTextAreas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ProblemRepository pRepo = (ProblemRepository)ProblemRepository.getInstance();
        System.out.println("a button was pushed: "+e.getActionCommand());
        if(e.getActionCommand().equals("Submit")){
            StateRepository stateRepository = (StateRepository) StateRepository.getInstance();
            stateRepository.setStatus("Parsed code");

            //The following code is temporary in order to quickly test the UserExample class
            UserExample ex1 = pRepo.getCurrentProblem();
            System.out.println("SUBMIT BUTTON PUSHED");

            //build ArrayList of strings
            ArrayList<String> usersCode = new ArrayList<>();
            for(int i = 0; i < jTextAreas.size(); i++) {
                if(!jTextAreas.get(i).getText().equals("")) {
                    usersCode.add(jTextAreas.get(i).getText());
                }
            }
            int mistakeIndex = ex1.gradeUserCode(usersCode);
            System.out.println("Code mistake at line: "+mistakeIndex);
            FeedbackRepository fRepo = (FeedbackRepository) FeedbackRepository.getInstance();
            fRepo.setErrorIndex(mistakeIndex);
        }
        if(e.getActionCommand().equals("Next")) {
            System.out.println("setting next problem");
            pRepo.setNextProblem();
        }
        if(e.getActionCommand().equals("Previous")) {
            pRepo.setPreviousProblem();
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
