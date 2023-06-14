package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class TeacherCourseView extends JPanel implements AppPage {
    private TeachingApp app;
    private Course course;
    private List<String> assignments;
    private List<JTextField> assignmentTextFields;

    public TeacherCourseView(TeachingApp app, Course course) {
        this.app = app;
        this.course = course;
        this.assignments = course.getAssignments();
        this.assignmentTextFields = new ArrayList<>();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public void showContents() {
        this.removeAll();
        this.assignmentTextFields.clear();

        // Show the list of students enrolled in the course in the side panel
        String[] studentNames = course.getEnrolledStudents().stream()
                .map(Student::getName)
                .toArray(String[]::new);

        // Create a label for "Enrolled Students" text
        JLabel enrolledLabel = new JLabel("Enrolled Students");
        enrolledLabel.setFont(new Font("Arial", Font.BOLD, 18));
        enrolledLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        enrolledLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JList<String> studentList = new JList<>(studentNames);
        studentList.setFont(new Font("Arial", Font.BOLD, 18)); // Set bigger font size for student names
        studentList.setVisibleRowCount(course.getEnrolledStudents().size()); // Display all students
        JScrollPane studentListScrollPane = new JScrollPane(studentList);

        // Set a fixed width for the scroll pane
        Dimension scrollPaneSize = new Dimension(200, 600);
        studentListScrollPane.setPreferredSize(scrollPaneSize);

        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(200, 600)); // Set a slightly wider side panel
        sidePanel.setLayout(new BorderLayout()); // Use BorderLayout for proper alignment
        sidePanel.add(enrolledLabel, BorderLayout.NORTH); // Add the enrolledLabel at the top
        sidePanel.add(studentListScrollPane, BorderLayout.CENTER); // Add the scroll pane in the center
        app.setSidePanel(sidePanel);




        // Show the course name
        JLabel courseLabel = new JLabel(course.getCourseName());
        courseLabel.setFont(new Font("Arial", Font.BOLD, 24));
        courseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        courseLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        this.add(courseLabel);

        // Show the course description
        JTextArea descriptionArea = new JTextArea(course.getDescription());
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 18));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(true);

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setPreferredSize(new Dimension(600, 150));
        descriptionScrollPane.setMaximumSize(new Dimension(600, 150));

        this.add(descriptionScrollPane);
        this.add(Box.createRigidArea(new Dimension(0, 30)));

        // Show the assignments
// Create a scroll pane for the assignments
        JPanel assignmentsPanel = new JPanel();
        assignmentsPanel.setLayout(new BoxLayout(assignmentsPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < assignments.size(); i++) {
            JTextField assignmentTextField = new JTextField(assignments.get(i));
            assignmentTextField.setPreferredSize(new Dimension(400, 50));
            assignmentTextField.setMaximumSize(new Dimension(400, 50));
            this.assignmentTextFields.add(assignmentTextField);

            JPanel assignmentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            assignmentPanel.add(assignmentTextField);

            JButton editButton = new JButton("Edit Task");
            JButton deleteButton = new JButton("Delete Assignment");
            int index = i; // Store the index of the assignment

            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Code to handle assignment editing
                }
            });

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Remove the assignment from the course's list
                    course.getAssignments().remove(index);
                    // Refresh the contents of the page to reflect the changes
                    showContents();
                }
            });

            assignmentPanel.add(editButton);
            assignmentPanel.add(deleteButton);
            assignmentsPanel.add(assignmentPanel);
            assignmentsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between assignments
        }

        JScrollPane assignmentsScrollPane = new JScrollPane(assignmentsPanel);
        assignmentsScrollPane.setPreferredSize(new Dimension(600, 300));

        this.add(assignmentsScrollPane);

        // Show the "Create Assignment" button
        JButton createAssignmentButton = new JButton("Create Assignment");
        createAssignmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new assignment and add it to the course's list
                course.addAssignment("New Assignment");
                // Refresh the contents of the page to show the new assignment
                showContents();
            }
        });

        this.add(createAssignmentButton);

        // Show the "Save Changes" button
        JButton saveChangesButton = new JButton("Save Changes");
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the assignments with the text field values and save them back to the course
                for (int i = 0; i < assignmentTextFields.size(); i++) {
                    String assignment = assignmentTextFields.get(i).getText();
                    course.getAssignments().set(i, assignment);
                }

                // Update the description with the text field value and save it back to the course
                String description = descriptionArea.getText();
                course.setDescription(description);
            }
        });
        this.add(saveChangesButton);

        this.revalidate();
        this.repaint();
    }

    @Override
    public String getHeaderInfo() {
        return "Course View for Teachers";
    }
}
