package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Random rand = new Random();
        for (Course course : this.courses) {
            JButton courseButton = new JButton(course.getCourseName());
            courseButton.setUI(new RoundedButtonUI(300, 300));
            courseButton.setBackground(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            courseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AssignmentsView assignmentsView = new AssignmentsView();
                    assignmentsView.setAssignments(course.getAssignments());
                    app.pushPage(assignmentsView);
                }
            });
            this.add(courseButton);
        }
        this.revalidate();
        this.repaint();
    }

    public List<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
