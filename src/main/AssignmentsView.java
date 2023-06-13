package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The AssignmentsView class represents a view that displays the assignments for a specific course.
 * It implements the AppPage interface.
 * It provides methods to show the contents of the view and retrieve header information.
 * It also handles the assignment button clicks.
 *
 * The assignments are retrieved from the Course object.
 * The view is displayed as a vertical list of assignment buttons.
 * Clicking on an assignment button opens the DiagramApp and sets the mode and assignment index accordingly.
 *
 * @author Connor Hickey
 */
class AssignmentsView extends JPanel implements AppPage {

    private List<String> assignments;
    private TeachingApp app;
    private Course course;
    private static String currentAssignment;

    /**
     * Constructs an AssignmentsView object with the specified TeachingApp and Course.
     *
     * @param app    the TeachingApp instance.
     * @param course the Course instance.
     */
    public AssignmentsView(TeachingApp app, Course course) {
        this.app = app;
        this.course = course;
        this.assignments = course.getAssignments();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Shows the contents of the AssignmentsView by displaying the assignment buttons.
     */
    @Override
    public void showContents() {
        this.removeAll();
        // For the course name
        JLabel courseLabel = new JLabel(course.getCourseName());
        courseLabel.setFont(new Font("Arial", Font.BOLD, 24));
        courseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        courseLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        this.add(courseLabel);

        // For each assignment
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        for (String assignment : this.assignments) {
            JButton assignmentButton = new JButton(assignment + " (" + pRepo.getNumAssignmentProblems(assignment) + " problems)");
            assignmentButton.setForeground(Color.WHITE);
            assignmentButton.setBackground(new Color(40, 237, 152));
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
                    handleAssignmentButtonClick(assignment);
                }
            });
            assignmentButton.setUI(new RoundedButtonUI(200, 200));
            this.add(assignmentButton);

            // Add the assignment completion status
            JLabel completionLabel = new JLabel();
            int assignmentIndex = Integer.parseInt(assignment.substring(11))-1;
            if (pRepo.getAssignments().get(assignmentIndex).getAssignmentComplete()) {
                completionLabel.setText("Completed");
                completionLabel.setForeground(new Color(0, 128, 0)); // green color
            } else {
                completionLabel.setText("Incomplete");
                completionLabel.setForeground(Color.RED); // red color
            }
            completionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(completionLabel);

            this.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        this.revalidate();
        this.repaint();
    }


    /**
     * Retrieves the header information of the AssignmentsView.
     * The header information consists of the current course name followed by "Assignments".
     *
     * @return the header information.
     */
    @Override
    public String getHeaderInfo() {
        String currentCourse = course.getCourseName();
        return currentCourse + " Assignments";
    }

    /**
     * Handles the assignment button click events.
     * Opens the DiagramApp and sets the mode and assignment index based on the clicked assignment.
     *
     * @param assignment the clicked assignment.
     */
    private void handleAssignmentButtonClick(String assignment) {
        System.out.println("Assignment button clicked! " + assignment);
        setCurrentAssignment(assignment);
        StateRepository sRepo = (StateRepository) StateRepository.getInstance();
        ProblemRepository pRepo = (ProblemRepository) ProblemRepository.getInstance();
        DiagramApp diagramApp = new DiagramApp(app);
        diagramApp.setVisible(true);
        diagramApp.setSize(900, 900);
        diagramApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(false);

        switch (assignment) {
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

    /**
     * Retrieves the list of assignments.
     *
     * @return the list of assignments.
     */
    public List<String> getAssignments() {
        return this.assignments;
    }

    /**
     * Sets the list of assignments.
     *
     * @param assignments the list of assignments.
     */
    public void setAssignments(List<String> assignments) {
        this.assignments = assignments;
    }

    public void setCurrentAssignment(String assignment){
        this.currentAssignment = "Assignment "+assignment;
    }

    public static String getCurrentAssignment(){
        return currentAssignment;
    }

}
