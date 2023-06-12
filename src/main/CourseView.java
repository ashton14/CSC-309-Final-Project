package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Hickey
 */
class CourseView extends JPanel implements AppPage {
    private List<Course> courses;
    private TeachingApp app;

    public CourseView(TeachingApp app) {
        this.app = app;
        this.courses = new ArrayList<>();
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20)); // spacing of 20 pixels
    }

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

    @Override
    public String getHeaderInfo() {
        return "Dashboard";
    }


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



    public List<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}