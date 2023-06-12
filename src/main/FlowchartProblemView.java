package src.main;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
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
    private ArrayList<JTextField> codeSections;
    private ArrayList<JButton> buttons;

    private JTextArea tutorFeedback;
    private JLabel problemTitle;
    /**
     * Creates a SidePanel object
     */
    FlowchartProblemView() {
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        codeSections = new ArrayList<>();

        JPanel codePanel = new JPanel();
        codePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 15;

        JLabel empty = new JLabel("");
        empty.setPreferredSize(new Dimension(15,20));
        codePanel.add(empty,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 200;

        this.problemTitle = new JLabel("problumo uno");
        this.problemTitle.setPreferredSize(new Dimension(250,20));
        codePanel.add(this.problemTitle,gbc);

        JButton[] options = {new JButton("Submit"), new JButton("Help"),
                new JButton("Previous"), new JButton("Next")};

        FlowchartProblemViewControlHandler sideController =
                                    new FlowchartProblemViewControlHandler(codeSections, options[2], options[3]);

        for(int i = 0; i < 12; i++) {
            gbc.gridx = 0;
            gbc.gridy = 1+i;
            gbc.weightx = 15;
            codePanel.add(new JLabel(String.valueOf(i+1)),gbc);
            JTextField codeSection = new JTextField("");
            codeSection.setPreferredSize(new Dimension(280,20));
            this.codeSections.add(codeSection);

            gbc.gridx = 1;
            gbc.gridy = 1+i;
            gbc.weightx = 200;
            codePanel.add(codeSection,gbc);
            codeSection.addMouseListener(sideController);
        }
        this.add(codePanel,BorderLayout.NORTH);

        //thanks Aaron
        buttons = new ArrayList<>();
        JPanel buttonPanel = new JPanel();
        GridLayout buttonLayout = new GridLayout(1,4);
        buttonPanel.setLayout(buttonLayout);


        for (JButton jButton : options) {
            jButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            jButton.addActionListener(sideController);
            buttonPanel.add(jButton);
            if(jButton.getText().equals("Next") || jButton.getText().equals("Previous"))
                jButton.setEnabled(false);
            buttons.add(jButton);
        }

        tutorFeedback = new JTextArea();
        JScrollPane chatScrollPane = new JScrollPane(tutorFeedback);
        tutorFeedback.setWrapStyleWord(true);
        tutorFeedback.setLineWrap(true);
        ((DefaultCaret)tutorFeedback.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        tutorFeedback.setText("Casey: Hello its nice to meet you!\n" +
                "Casey: I am your tutor today.\n" +
                "Casey: I can check if your work is correct and give hints when requested.\n");
        tutorFeedback.setEnabled(false);

        ProblemTimer problemTimer = new ProblemTimer();
        FeedbackRepository fRepo = (FeedbackRepository) FeedbackRepository.getInstance();
        fRepo.addObserver(problemTimer);
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        pRepo.addObserver(problemTimer);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2,1));
        southPanel.add(buttonPanel);
        southPanel.add(problemTimer);

        this.add(chatScrollPane, BorderLayout.CENTER);
        this.add(southPanel,BorderLayout.SOUTH);
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
            try {
                if(arg instanceof ArrayList) {
                    ArrayList<String> stringList = (ArrayList<String>) arg;
                    for (String string : stringList) {
                        tutorFeedback.append(string);
                    }
                } else if(arg instanceof Integer) {
                    tutorFeedback.append(arg.toString());
                } else {
                    String string = (String) arg;
                    tutorFeedback.append(string);

                }
            } catch(Exception e){
                System.out.println("an error occured: "+e.getMessage());
            }
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
