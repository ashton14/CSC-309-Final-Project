package src.main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Connor Hickey
 */

public class CoursesPage {
    private JFrame frame;
    public JPanel coursePanel;
    public JPanel assignmentPanel;
    private JButton backButton;
    private static JLabel courseProgress;
    public Map<String, List<String>> courseAssignments;
    private static String currentAssignment;
    private Color shadowColor = new Color(0, 0, 0, 50);

    public static int numAssignmentsCompleted = 0;

    public Color generateRandomColor() {
        float hue = (float) Math.random();
        float saturation = (float) (Math.random() * 0.5f + 0.5f);
        float brightness = (float) (Math.random() * 0.5f + 0.5f);
        return Color.getHSBColor(hue, saturation, brightness);
    }


    public CoursesPage() {
        frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(generateRandomColor());
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 70));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);

        JPanel dashboardPanel = new JPanel(new GridLayout(2, 1));
        dashboardPanel.setBackground(generateRandomColor());
        dashboardPanel.setPreferredSize(new Dimension(100, frame.getHeight()));


        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        coursePanel = new JPanel();
        coursePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        addCourse("Course 1");
        addCourse("Course 2");
        addCourse("Course 3");

        JButton sandboxButton = new JButton("Sandbox");
        sandboxButton.setForeground(Color.WHITE);
        sandboxButton.setBackground(generateRandomColor());
        sandboxButton.setBorderPainted(false);
        sandboxButton.setFocusPainted(false);
        sandboxButton.setContentAreaFilled(true);
        sandboxButton.setOpaque(true);
        sandboxButton.setFont(new Font("Arial", Font.BOLD, 13));
        sandboxButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sandboxButton.addActionListener(e -> showSandbox());
        sandboxButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sandboxButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(shadowColor, 4),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sandboxButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            }
        });
        sandboxButton.setUI(new BasicButtonUI());
        dashboardPanel.add(sandboxButton);
        JButton cloudCoursesButton = new JButton("Cloud");
        cloudCoursesButton.setForeground(Color.WHITE);
        cloudCoursesButton.setBackground(generateRandomColor());
        cloudCoursesButton.setBorderPainted(false);
        cloudCoursesButton.setFocusPainted(false);
        cloudCoursesButton.setContentAreaFilled(false);
        cloudCoursesButton.setOpaque(true);
        cloudCoursesButton.setFont(new Font("Arial", Font.BOLD, 13));
        cloudCoursesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cloudCoursesButton.addActionListener(e -> showFlowchartsFromDb());
        cloudCoursesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cloudCoursesButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(shadowColor, 4),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cloudCoursesButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            }
        });
        cloudCoursesButton.setUI(new BasicButtonUI());
        dashboardPanel.add(cloudCoursesButton);
        assignmentPanel = new JPanel();
        assignmentPanel.setLayout(new BoxLayout(assignmentPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(assignmentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.add(coursePanel, BorderLayout.WEST);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
        frame.getContentPane().add(dashboardPanel, BorderLayout.WEST);
        frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        courseAssignments = new HashMap<>();
        courseAssignments.put("Course 1", List.of("Assignment 1", "Assignment 2", "Assignment 3"));
        courseAssignments.put("Course 2", List.of("Assignment 4", "Assignment 5", "Assignment 6"));
        courseAssignments.put("Course 3", List.of("Assignment 7", "Assignment 8", "Assignment 9"));
    }

    private void showFlowchartsFromDb() {
        frame.setVisible(false);
        CloudDataPage cdp = new CloudDataPage(SqlControlHandler.getFlowchartFileNames());
        cdp.setVisible(true);
    }

    public void addCourse(String courseName) {
        JButton courseButton = new JButton(courseName);
        courseButton.setForeground(Color.WHITE);
        courseButton.setBackground(generateRandomColor());
        courseButton.setBorderPainted(false);
        courseButton.setFocusPainted(false);
        courseButton.setContentAreaFilled(false);
        courseButton.setOpaque(true);
        courseButton.setPreferredSize(new Dimension(200, 200));
        courseButton.setMaximumSize(new Dimension(200, 200));
        courseButton.setMinimumSize(new Dimension(200, 200));
        courseButton.setFont(new Font("Arial", Font.BOLD, 18));
        courseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        courseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAssignmentsForCourse(courseName);
            }
        });
        courseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                courseButton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(shadowColor, 4),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                courseButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            }
        });
        courseButton.setUI(new RoundedButtonUI(200, 200));
        coursePanel.add(courseButton);
    }

    public void showAssignmentsForCourse(String courseName) {
        coursePanel.setVisible(false);
        assignmentPanel.removeAll();
        assignmentPanel.revalidate();
        assignmentPanel.repaint();

        JLabel courseLabel = new JLabel(courseName);
        courseLabel.setFont(new Font("Arial", Font.BOLD, 24));
        courseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        courseLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        assignmentPanel.add(courseLabel);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        courseProgress = new JLabel("Progress: "+ numAssignmentsCompleted+"/3");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCourseList();
            }
        });
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        assignmentPanel.add(backButton);
        assignmentPanel.add(courseProgress);
        assignmentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        List<String> assignments = courseAssignments.get(courseName);
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        for (String assignment : assignments) {
            JButton assignmentButton = new JButton(assignment+" ("+pRepo.getNumAssignmentProblems(assignment)+" problems)");
            assignmentButton.setForeground(Color.WHITE);
            assignmentButton.setBackground(generateRandomColor());
            assignmentButton.setBorderPainted(false);
            assignmentButton.setFocusPainted(false);
            assignmentButton.setContentAreaFilled(false);
            assignmentButton.setOpaque(true);
            assignmentButton.setPreferredSize(new Dimension(400, 75));
            assignmentButton.setMaximumSize(new Dimension(400, 75));
            assignmentButton.setMinimumSize(new Dimension(400, 75));
            assignmentButton.setFont(new Font("Arial", Font.BOLD, 18));
            assignmentButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            assignmentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // handle assignment button click
                    System.out.println("Assignment button clicked! "+e.getActionCommand());
                    setCurrentAssignment(((JButton)e.getSource()).getText());
                    StateRepository sRepo = (StateRepository) StateRepository.getInstance();
                    ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
                    showSandbox();

                    switch(e.getActionCommand().substring(0,12)) {
                        case "Assignment 1":
                            sRepo.changeMode("Translate Code");
                            pRepo.setAssignmentIndex(0);
                            break;
                        case "Assignment 2":
                            sRepo.changeMode("Translate Flowchart");
                            pRepo.setAssignmentIndex(1);
                            pRepo.setCurrentProblem();
                            break;
                        case "Assignment 3":
                            sRepo.changeMode("Translate Code");
                            pRepo.setAssignmentIndex(2);
                            break;
                    }

                }
            });
            assignmentButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    assignmentButton.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(shadowColor, 4),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    assignmentButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                }
            });
            assignmentButton.setUI(new RoundedButtonUI(200, 200));
            assignmentPanel.add(assignmentButton);
            assignmentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JTextArea messageProfessor = new JTextArea("Message your professor...");
        assignmentPanel.add(messageProfessor);
        messageProfessor.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                messageProfessor.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {}
        });

        JButton sendMessage = new JButton("Send");
        sendMessage.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Message sent to professor: "+messageProfessor.getText());
                messageProfessor.setText("");
            }
        });
        assignmentPanel.add(sendMessage);

        frame.revalidate();
        frame.repaint();
    }


    public void showCourseList() {
        coursePanel.setVisible(true);
        assignmentPanel.removeAll();
        assignmentPanel.revalidate();
        assignmentPanel.repaint();
        frame.revalidate();
        frame.repaint();
    }

    private void showSandbox() {
        frame.setVisible(false);
        DiagramApp app = new DiagramApp(new TeachingApp(false));
        app.setVisible(true);
        app.setSize(700,700);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void setCurrentAssignment(String assignment){
        this.currentAssignment = "Assignment "+assignment;
    }

    public static String getCurrentAssignment(){
        return currentAssignment;
    }

    public static void updateCourseProgress(){
        courseProgress.setText("Progress: "+ numAssignmentsCompleted+"/3");
    }

}

