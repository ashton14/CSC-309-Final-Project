package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class TeacherView extends JPanel implements AppPage {
    private List<Course> courses;
    private TeachingApp app;

    public TeacherView(TeachingApp app) {
        this.app = app;
        this.courses = new ArrayList<>();
        this.setLayout(new GridBagLayout());
    }

    @Override
    public void showContents() {
        this.removeAll(); // remove existing components

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton createCourseButton = new JButton("Create Course");
        createCourseButton.setPreferredSize(new Dimension(120, 50)); // Set the preferred size of the button

        JTextField courseNameTextField = new JTextField();
        courseNameTextField.setPreferredSize(new Dimension(300, 50)); // Set the preferred size of the text field

        JPanel createCoursePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        createCoursePanel.add(courseNameTextField);
        createCoursePanel.add(createCourseButton);

        createCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = courseNameTextField.getText();
                if (!courseName.isEmpty()) {
                    // Create a new course with the provided course name
                    Course newCourse = new Course(courseName, "Test Teacher", "", new ArrayList<>());
                    // Add the new course to the list of courses
                    courses.add(0, newCourse);
                    // Refresh the view to display the new course
                    showContents();
                }
            }
        });

        // Create a panel to hold the courses
        JPanel coursesPanel = new JPanel();
        coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));

        for (Course course : this.courses) {
            JButton courseButton = new JButton(course.getCourseName());
            courseButton.setUI(new RoundedButtonUI(600, 100));
            courseButton.setBackground(new Color(100, 169, 209));
            courseButton.setPreferredSize(new Dimension(600, 100)); // Set the preferred size of the button

            String tooltipText = wrapTooltipText(course.getTeacher(), course.getDescription(), 30);
            courseButton.setToolTipText(tooltipText);

            courseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Navigate to the course editing view
                    TeacherCourseView courseEditingView = new TeacherCourseView(app, course);
                    app.pushPage(courseEditingView);
                }
            });

            // Create a JLabel to display the number of enrolled students
            JLabel enrolledLabel = new JLabel("Enrolled: " + course.getEnrolledStudents().size());
            enrolledLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Create a JPanel to hold the courseButton and enrolledLabel
            JPanel coursePanel = new JPanel(new BorderLayout());
            coursePanel.add(courseButton, BorderLayout.CENTER);
            coursePanel.add(enrolledLabel, BorderLayout.SOUTH);

            coursesPanel.add(coursePanel);
            coursesPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing between assignments
        }

        // Wrap the courses panel with a JScrollPane
        JScrollPane scrollPane = new JScrollPane(coursesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Set the preferred size of the scroll pane
        Dimension scrollPaneSize = new Dimension(600, 500);
        scrollPane.setPreferredSize(scrollPaneSize);

        // Add "Create Course" panel and the scroll pane to the main panel
        this.add(createCoursePanel, gbc);
        gbc.gridy++;
        this.add(scrollPane, gbc);

        this.revalidate();
        this.repaint();
    }

    @Override
    public String getHeaderInfo() {
        return "Teacher Dashboard";
    }

    private String wrapTooltipText(String teacher, String description, int maxLength) {
        StringBuilder toolTipText = new StringBuilder("<html>Teacher: " + teacher + "<br/><br/>");
        //... your existing wrapping code
        return toolTipText.toString();
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
