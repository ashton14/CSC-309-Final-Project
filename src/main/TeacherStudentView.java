package src.main;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class TeacherStudentView extends JPanel implements AppPage {
    private TeachingApp app;
    private Course course;
    private Student student;

    public TeacherStudentView(TeachingApp app, Course course, Student student) {
        this.app = app;
        this.course = course;
        this.student = student;
        this.setLayout(new BorderLayout());
    }

    @Override
    public void showContents() {
        this.removeAll();

        // Create a panel to display the student profile
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a circle with the text
        JLabel circleLabel = new JLabel("Profile Image");
        circleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        circleLabel.setPreferredSize(new Dimension(100, 100));
        circleLabel.setMaximumSize(new Dimension(100, 100));
        circleLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        profilePanel.add(circleLabel);

        // Add the student name
        JLabel studentNameLabel = new JLabel(student.getName());
        studentNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        studentNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePanel.add(studentNameLabel);

        // Calculate the course grade for the student
        String courseGrade = calculateCourseGrade();
        JLabel courseGradeLabel = new JLabel("Course Grade: " + courseGrade);
        courseGradeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        courseGradeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePanel.add(courseGradeLabel);

        this.add(profilePanel, BorderLayout.NORTH);

        // Create a panel to display the student's assignments
        JPanel assignmentsPanel = new JPanel();
        assignmentsPanel.setLayout(new BoxLayout(assignmentsPanel, BoxLayout.Y_AXIS));
        assignmentsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Get the assignments for the course
        List<String> assignments = course.getAssignments();

        // Iterate over the assignments and display the assignment details
        for (String assignment : assignments) {
            // Simulated assignment status and grade for demonstration purposes
            String assignmentStatus = "Completed";
            double grade = Math.random() * 100; // Random grade between 0 and 100
            String gradeFormatted = String.format("%.2f%%", grade); // Format grade as percentage

            // Create a panel to display the assignment details
            JPanel assignmentPanel = new JPanel(new BorderLayout());

            // Assignment name
            JLabel assignmentNameLabel = new JLabel(assignment);
            assignmentNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
            assignmentPanel.add(assignmentNameLabel, BorderLayout.WEST);

            // Assignment status and grade
            JLabel assignmentStatusLabel = new JLabel("Status: " + assignmentStatus + " | Grade: " + gradeFormatted);
            assignmentPanel.add(assignmentStatusLabel, BorderLayout.EAST);

            assignmentsPanel.add(assignmentPanel);
        }

        // Create a scroll pane for the assignments panel
        JScrollPane assignmentsScrollPane = new JScrollPane(assignmentsPanel);

        this.add(assignmentsScrollPane, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    @Override
    public String getHeaderInfo() {
        return "Student Profile";
    }

    private String calculateCourseGrade() {
        // Simulated calculation of course grade for demonstration purposes
        double totalGrade = 0;
        int numAssignments = course.getAssignments().size();
        for (int i = 0; i < numAssignments; i++) {
            double grade = Math.random() * 100; // Random grade between 0 and 100
            totalGrade += grade;
        }
        double averageGrade = totalGrade / numAssignments;
        return String.format("%.2f%%", averageGrade); // Format grade as percentage
    }

    public Student getStudent() {
        return this.student;
    }
}
