package src.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * @author Connor Hickey
 */
public class TeachingApp {

    private Stack<AppPage> pages;
    private JFrame frame;
    private JButton backButton; // make backButton a class field

    public TeachingApp() {
        this.pages = new Stack<>();
        this.frame = new JFrame("Teaching App");
        this.frame.setSize(1000, 1600);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backButton = new JButton("< Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(backButton);
        this.frame.getContentPane().add(topPanel, BorderLayout.PAGE_START);
    }

    public void pushPage(AppPage page) {
        this.pages.push(page);
        showCurrentPage();
        this.frame.getContentPane().add((Component) this.pages.peek(), BorderLayout.CENTER);
        this.frame.validate();
        this.frame.repaint();
        this.frame.setVisible(true);
    }




    public void goBack() {
        if (!this.pages.isEmpty()) {
            this.pages.pop();
            showCurrentPage();
        }
    }

    private void showCurrentPage() {
        Component[] components = this.frame.getContentPane().getComponents();
        for (Component component : components) {
            if (BorderLayout.CENTER.equals(((BorderLayout) this.frame.getContentPane().getLayout()).getConstraints(component))) {
                this.frame.getContentPane().remove(component);
                break;
            }
        }

        if (!this.pages.isEmpty()) {
            this.pages.peek().showContents();
            this.frame.getContentPane().add((Component) this.pages.peek(), BorderLayout.CENTER);
            backButton.setVisible(pages.size() > 1);  // hide back button if there's only one page left
        }

        this.frame.validate();
        this.frame.repaint();
        this.frame.setVisible(true);
    }


    public static void main(String[] args) {
        TeachingApp app = new TeachingApp();

        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Course 1", Arrays.asList("Assignment 1", "Assignment 2", "Assignment 3")));
        courses.add(new Course("Course 2", Arrays.asList("Assignment 4", "Assignment 5", "Assignment 6")));
        courses.add(new Course("Course 3", Arrays.asList("Assignment 7", "Assignment 8", "Assignment 9")));
        courses.add(new Course("Course 4", Arrays.asList("Assignment 10", "Assignment 11", "Assignment 12")));

        CourseView courseView = new CourseView(app);
        courseView.setCourses(courses);

        app.pushPage(courseView);
    }


}
