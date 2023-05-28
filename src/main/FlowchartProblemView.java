package src.main;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Class responsible for holding the code snippet, and perhaps
 * other tutor-related items
 * @author Patrick Whitlock
 */
public class FlowchartProblemView extends JPanel implements Observer {
    private ArrayList<JTextArea> codeSections;
    private ArrayList<JButton> buttons;

    private JTextArea tutorFeedback;
    private JLabel problemTitle;
    /**
     * Creates a SidePanel object
     */
    FlowchartProblemView() {
        this.problemTitle = new JLabel("problumo uno");
        this.problemTitle.setHorizontalAlignment(SwingConstants.LEFT);
        this.problemTitle.setBorder(new EmptyBorder(10, 10, 10,10));

        codeSections = new ArrayList<>();
        SidePanelControlHandler sideController = new SidePanelControlHandler(codeSections);

        this.add(problemTitle);
        for(int i = 0; i < 12; i++) {
            JTextArea codeSection = new JTextArea("");
            codeSection.setPreferredSize(new Dimension(280,20));
            this.codeSections.add(codeSection);
            this.add(codeSection);
            codeSection.addMouseListener(sideController);
        }

        //thanks Aaron
        buttons = new ArrayList<>();
        JPanel buttonPanel = new JPanel();
        GridLayout buttonLayout = new GridLayout(1,4);
        buttonPanel.setLayout(buttonLayout);
        String[] buttonStrings = {"Submit", "Help", "Previous", "Next"};
        for (String buttonString : buttonStrings) {
            JButton jButton = new JButton(buttonString);
            jButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            jButton.addActionListener(sideController);
            buttonPanel.add(jButton);
            buttons.add(jButton);
        }

        tutorFeedback = new JTextArea();
        JScrollPane chatScrollPane = new JScrollPane(tutorFeedback);
        tutorFeedback.setWrapStyleWord(true);
        tutorFeedback.setLineWrap(true);
        ((DefaultCaret)tutorFeedback.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        tutorFeedback.setText("Jimbo: Hello its nice to meet you!\n" +
                "Jimbo: I am your tutor today.\n" +
                "Jimbo: I can check if your work is correct and give hints when requested.\n");
        tutorFeedback.setEnabled(false);
        chatScrollPane.setPreferredSize(new Dimension(300,500));

        this.add(buttonPanel,BorderLayout.SOUTH);
        this.add(chatScrollPane, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(300,400));
        this.updateProblemTitle();
    }
    public void updateProblemTitle() {
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        this.problemTitle.setText(pRepo.getCurrentProblem().getProblemName());
    }

    /**
     * Draws the panel on the screen
     * @param g Graphics object to draw with
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void update(Observable o, Object arg) {
        updateProblemTitle();
        if(o == FeedbackRepository.getInstance()) {
            String string = (String) arg;
            tutorFeedback.append(string);
        }

        //make error'd line of code red
        this.clearRedCode();
        FeedbackRepository fRepo = (FeedbackRepository) FeedbackRepository.getInstance();
        System.out.println("view update: err index = "+fRepo.getErrorIndex());
        if(fRepo.getErrorIndex() >= 0) {
            this.codeSections.get(fRepo.getErrorIndex()).setBackground(Color.RED);
        }
    }
    private void clearRedCode() {
        for(int i = 0; i < this.codeSections.size(); i++) {
            this.codeSections.get(i).setBackground(Color.WHITE);
        }
    }
}
