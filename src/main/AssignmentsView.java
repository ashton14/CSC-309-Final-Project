package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

/**
 * @author Connor Hickey
 */
class AssignmentsView extends JPanel implements AppPage {

    private List<String> assignments;

    private TeachingApp app;

    private Course course;

    public AssignmentsView(TeachingApp app, Course course) {
        this.app = app;
        this.course = course;
        this.assignments = course.getAssignments();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // set layout to BoxLayout for vertical arrangement
    }

    @Override
    public void showContents() {
        this.removeAll();
        Random rand = new Random();

        for (String assignment : this.assignments) {
            JButton assignmentButton = new JButton(assignment);
            assignmentButton.setUI(new RoundedButtonUI(800, 80));
            assignmentButton.setBackground(new Color(40, 237, 152));
            assignmentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleAssignmentButtonClick(assignment);
                }
            });
            this.add(assignmentButton);
            this.add(Box.createRigidArea(new Dimension(0, 15))); // add spacing between buttons
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public String getHeaderInfo() {
        String currentCourse = course.getCourseName(); // Assuming there's a method that gets the current course
        return currentCourse + " Assignments";
    }


    private void handleAssignmentButtonClick(String assignment) {
        System.out.println("Assignment button clicked! " + assignment);
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

    public List<String> getAssignments() {
        return this.assignments;
    }

    public void setAssignments(List<String> assignments) {
        this.assignments = assignments;
    }
}
