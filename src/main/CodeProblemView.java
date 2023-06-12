package src.main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * A Class to display a problem for the user to translate Code to a Flowchart.
 */
public class CodeProblemView extends JPanel implements Observer {

    private ArrayList<JButton> buttons;
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
        CodeProblemViewControlHandler codeProblemViewControlHandler =
                new CodeProblemViewControlHandler(options[2], options[3]);
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
     * Creates the tutorFeedback JTextArea and
     * encapsulates it inside a JScrollPane
     * to mimic a tutor talking to a student.
     * @return  A JScrollPane representation
     * of a tutor.
     */
    private JScrollPane makeTutorFeedback(){
        tutorFeedback = new JTextArea();
        JScrollPane chatScrollPane = new JScrollPane(tutorFeedback);
        tutorFeedback.setWrapStyleWord(true);
        tutorFeedback.setLineWrap(true);
        ((DefaultCaret)tutorFeedback.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        tutorFeedback.setText("Casey: Hello its nice to meet you!\n" +
                "Casey: I am your tutor today.\n" +
                "Casey: I can check if your work is correct and give hints when requested.\n");
        tutorFeedback.setEnabled(false);
        return chatScrollPane;
    }

    /**
     * Fetches the String representation of a problem from
     * the ProblemRepository.
     */
    private void updateCodeText() {
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        this.problemTitle.setText(pRepo.getCurrentProblem().getProblemName());
        this.codeProblemLabel.setText(pRepo.getCurrentProblem().getHtml_code());
    }

    /**
     * Constructs objects of type CodeProblemView, which is a
     * view representing a problem with code that the user
     * needs to translate into a flowchart.
     * @param metricsPrompt  The MetricsPrompt instance to
     *                       display inside this CodeProblemView.
     * @throws NullPointerException if the metricsPrompt is null.
     */
    public CodeProblemView(MetricsPrompt metricsPrompt){
        if(metricsPrompt == null){
            throw new NullPointerException("CodeProblemView: metricsPrompt can't be null");
        }

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        this.problemTitle = new JLabel();
        this.problemTitle.setBorder(new EmptyBorder(10, 10, 10,10));
        this.codeProblemLabel = makeCodeProblemLabel();
        JScrollPane tutorPane = makeTutorFeedback();

        metricsPrompt.setAlignmentX(Component.LEFT_ALIGNMENT);
        updateCodeText();

        JPanel problemPanel = new JPanel();
        problemPanel.setLayout(new BoxLayout(problemPanel, BoxLayout.Y_AXIS));
        problemPanel.add(problemTitle);
        problemPanel.add(codeProblemLabel);
        problemPanel.add(metricsPrompt);

        JPanel buttonPanel = makeButtonPanel();
        add(problemPanel, BorderLayout.NORTH);
        add(tutorPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
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
        if(o != FeedbackRepository.getInstance()){
            updateCodeText();
            repaint();
            return;
        }

        if(arg.getClass() == String.class) {
            String string = (String) arg;
            tutorFeedback.append(string);
        }
        repaint();
    }
}