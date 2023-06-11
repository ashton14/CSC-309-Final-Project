package src.main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A Class to display a problem for the user to translate Code to a Flowchart.
 */
public class CodeProblemView extends JPanel implements Observer {

    ArrayList<JButton> buttons;

    private JTextArea tutorFeedback;
    private JLabel codeProblemLabel;
    private JLabel problemTitle;

    /**
     * A helper method to make the JLabel for the constructor of the CodeProblemView
     * class that contains the code problem.
     * @return A JLabel formatted and with the code problem text given through the
     * parameter.
     */
    private JLabel makeCodeProblemLabel(){
        JLabel codeProblem = new JLabel();
        codeProblem.setSize(100, getHeight());
        codeProblem.setBorder(new EmptyBorder(10, 10, 10,10));
        codeProblem.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(codeProblem, BorderLayout.CENTER);
        return codeProblem;
    }

    /**
     * Helper for the constructor of CodeProblemView to make a JPanel
     * with Submit, Help, Previous and Next buttons.
     * @return A JPanel containing Submit, Help, Previous and Next Buttons.
     */
    private JPanel makeButtonPanel(){
        buttons = new ArrayList<>();
        JPanel buttonPanel = new JPanel();
        GridLayout buttonLayout = new GridLayout(1,4);
        buttonPanel.setLayout(buttonLayout);
        JButton[] options = {new JButton("Submit"), new JButton("Help"),
                        new JButton("Previous"), new JButton("Next")};
        CodeProblemViewControlHandler codeProblemViewControlHandler = new CodeProblemViewControlHandler(options[2], options[3]);
        for (JButton jButton : options) {
            jButton.addActionListener(codeProblemViewControlHandler);
            if(jButton.getText().equals("Next") || jButton.getText().equals("Previous"))
                jButton.setEnabled(false);
            buttonPanel.add(jButton);
            buttons.add(jButton);
        }
        return buttonPanel;
    }

    /**
     * Constructs objects of type CodeProblemView, which is a
     * view representing a problem with code that the user
     * needs to translate into a flowchart.
     */
    public CodeProblemView(){
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        this.problemTitle = new JLabel();
        this.problemTitle.setBorder(new EmptyBorder(10, 10, 10,10));
        this.codeProblemLabel = makeCodeProblemLabel();
        this.updateCodeText();

        JPanel problemPanel = new JPanel();
        problemPanel.setLayout(new BoxLayout(problemPanel, BoxLayout.PAGE_AXIS));
        problemPanel.add(problemTitle);
        problemPanel.add(codeProblemLabel);
        tutorFeedback = new JTextArea();
        JScrollPane chatScrollPane = new JScrollPane(tutorFeedback);
        tutorFeedback.setWrapStyleWord(true);
        tutorFeedback.setLineWrap(true);
        ((DefaultCaret)tutorFeedback.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        tutorFeedback.setText("Casey: Hello its nice to meet you!\n" +
                "Casey: I am your tutor today.\n" +
                "Casey: I can check if your work is correct and give hints when requested.\n");
        tutorFeedback.setEnabled(false);

        JPanel buttonPanel = makeButtonPanel();
        ProblemTimer problemTimer = new ProblemTimer();

        FeedbackRepository fRepo = (FeedbackRepository) FeedbackRepository.getInstance();
        fRepo.addObserver(problemTimer);
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        pRepo.addObserver(problemTimer);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2,1));
        southPanel.add(buttonPanel);
        southPanel.add(problemTimer);

        add(problemPanel, BorderLayout.NORTH);
        add(chatScrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * Adds an action listener to all buttons within this JPanel.
     * @param actionListener  The actionListener to add to all
     *                        buttons within this JPanel.
     */
    public void addActionListener(ActionListener actionListener){
        if(actionListener == null)
            return;
        for (JButton button: buttons) {
            button.addActionListener(actionListener);
        }
    }

    public void updateCodeText() {
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        this.problemTitle.setText(pRepo.getCurrentProblem().getProblemName());
        this.codeProblemLabel.setText(pRepo.getCurrentProblem().getHtml_code());
    }

    /**
     * A method to change the problem number and associated code
     * when the next and previous buttons are pressed.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if(o == FeedbackRepository.getInstance()) {
            String string = (String) arg;
            tutorFeedback.append(string);
        } else {
            updateCodeText();
        }
        repaint();
    }
}
