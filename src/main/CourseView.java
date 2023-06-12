package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The CourseView class represents the view for displaying courses.
 * It extends JPanel and implements the AppPage interface.
 * It provides methods to show the contents of the course view and retrieve the header information.
 *
 * The course view displays a list of courses as buttons.
 * Each button represents a course and can be clicked to navigate to the assignments view for that course.
 * The course name is displayed on the button, and the teacher and description are shown as tooltip text.
 * The number of completed assignments is also displayed below each course button.
 *
 * The course view uses a FlowLayout with spacing for arranging the course buttons.
 *
 * The courses are stored as a list of Course objects.
 *
 * The course view is associated with the TeachingApp.
 *
 * @author Connor Hickey
 */
class CourseView extends JPanel implements AppPage {
    private List<Course> courses;
    private TeachingApp app;

    /**
     * Constructs a CourseView object associated with the TeachingApp.
     * It initializes the courses list and sets the layout to a FlowLayout with left alignment and spacing.
     *
     * @param app the TeachingApp instance.
     */
    public CourseView(TeachingApp app) {
        this.app = app;
        this.courses = new ArrayList<>();
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
    }

    /**
     * Shows the contents of the course view.
     * It removes existing components, creates and adds course buttons with completed assignment labels.
     * Each course button is associated with an ActionListener to navigate to the assignments view for that course.
     */
    @Override
    public void showContents() {
        this.removeAll(); // remove existing components
        for (Course course : this.courses) {
            JButton courseButton = new JButton(course.getCourseName());
            courseButton.setUI(new RoundedButtonUI(300, 300));
            courseButton.setBackground(new Color(100, 169, 209));

            String tooltipText = wrapTooltipText(course.getTeacher(), course.getDescription(), 30);
            courseButton.setToolTipText(tooltipText);

            courseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AssignmentsView assignmentsView = new AssignmentsView(app, course);
                    app.pushPage(assignmentsView);
                }
            });

            // Create a JLabel to display the number of completed assignments
            JLabel completedLabel = new JLabel("Completed: " + course.getCourseCompletionString());
            completedLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Add a JPanel to hold the courseButton and completedLabel
            JPanel coursePanel = new JPanel();
            coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
            coursePanel.add(courseButton);
            coursePanel.add(completedLabel);

            this.add(coursePanel);
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Retrieves the header information for the course view.
     *
     * @return the header information as a string.
     */
    @Override
    public String getHeaderInfo() {
        return "Dashboard";
    }

    /**
     * Wraps the tooltip text by splitting the description into lines based on the maximum length.
     * The tooltip text includes the teacher and description.
     *
     * @param teacher     the teacher of the course.
     * @param description the description of the course.
     * @param maxLength   the maximum length of each line.
     * @return the wrapped tooltip text as a string.
     */
    private String wrapTooltipText(String teacher, String description, int maxLength) {
        StringBuilder toolTipText = new StringBuilder("<html>Teacher: " + teacher + "<br/><br/>");
        String[] words = description.split(" ");
        StringBuilder line = new StringBuilder();
        int numLines = 1; // start with 1 because of the "Teacher" line

        for (String word : words) {
            if (line.length() + word.length() < maxLength) {
                line.append(word).append(" ");
            } else {
                if (numLines == 6) {
                    toolTipText.append(line).append("...<br/></html>");
                    break;
                }
                toolTipText.append(line).append("<br/>");
                line = new StringBuilder(word).append(" ");
                numLines++;
            }
        }
        if (numLines < 6) {
            toolTipText.append(line).append("</html>");
        }

        // Convert StringBuilder to String and return.
        return toolTipText.toString();
    }

    /**
     * Retrieves the list of courses.
     *
     * @return the list of courses.
     */
    public List<Course> getCourses() {
        return this.courses;
    }

    /**
     * Sets the list of courses.
     *
     * @param courses the list of courses to set.
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
