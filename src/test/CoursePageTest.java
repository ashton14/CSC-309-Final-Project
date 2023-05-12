package src.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import src.main.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoursesPageTest {
    private CoursesPage coursesPage;

    @BeforeEach
    void setUp() {
        coursesPage = new CoursesPage();
    }

    @Test
    void testAddCourse() {
        JPanel coursePanel = new JPanel();
        coursesPage.coursePanel = coursePanel;

        coursesPage.addCourse("Test Course");

        JButton courseButton = (JButton) coursePanel.getComponent(0);
        assertEquals("Test Course", courseButton.getText());
        assertEquals(Color.WHITE, courseButton.getForeground());
        assertEquals(new Dimension(200, 200), courseButton.getPreferredSize());
        assertEquals(new Dimension(200, 200), courseButton.getMaximumSize());
        assertEquals(new Dimension(200, 200), courseButton.getMinimumSize());
        assertEquals(new Font("Arial", Font.BOLD, 18), courseButton.getFont());
    }

    @Test
    void testShowAssignmentsForCourse() {
        String courseName = "Test Course";
        List<String> assignments = Arrays.asList("Assignment 1", "Assignment 2");
        Map<String, List<String>> courseAssignments = new HashMap<>();
        courseAssignments.put(courseName, assignments);
        coursesPage.courseAssignments = courseAssignments;

        JPanel assignmentPanel = new JPanel();
        coursesPage.assignmentPanel = assignmentPanel;

        coursesPage.showAssignmentsForCourse(courseName);

        assertEquals(7, assignmentPanel.getComponentCount());

        JLabel courseLabel = (JLabel) assignmentPanel.getComponent(0);
        assertEquals(courseName, courseLabel.getText());
        assertEquals(new Font("Arial", Font.BOLD, 24), courseLabel.getFont());

        JButton backButton = (JButton) assignmentPanel.getComponent(1);
        assertEquals("Back", backButton.getText());
        assertEquals(new Font("Arial", Font.PLAIN, 16), backButton.getFont());

        Object thirdComponent = assignmentPanel.getComponent(2);
        if (thirdComponent instanceof JButton) {
            JButton assignmentButton = (JButton) thirdComponent;
            assertEquals(assignments.get(0), assignmentButton.getText());
            assertEquals(Color.WHITE, assignmentButton.getForeground());
            assertEquals(new Dimension(400, 75), assignmentButton.getPreferredSize());
            assertEquals(new Dimension(400, 75), assignmentButton.getMaximumSize());
            assertEquals(new Dimension(400, 75), assignmentButton.getMinimumSize());
            assertEquals(new Font("Arial", Font.BOLD, 18), assignmentButton.getFont());
        } else if (!(thirdComponent instanceof Box.Filler)) {
            throw new AssertionError("Unexpected component type: " + thirdComponent.getClass());
        }
    }

    @Test
    void testShowCourseList() {
        JPanel coursePanel = new JPanel();
        coursesPage.coursePanel = coursePanel;

        JPanel assignmentPanel = new JPanel();
        coursesPage.assignmentPanel = assignmentPanel;

        coursesPage.showCourseList();

        assertEquals(true, coursePanel.isVisible());
        assertEquals(0, assignmentPanel.getComponentCount());
    }

    @Test
    void testGenerateRandomColor() {
        Color color1 = coursesPage.generateRandomColor();
        Color color2 = coursesPage.generateRandomColor();

        // Since it's a random color, we can only check if it's not null
        // and not the same color (very unlikely)
        assertEquals(false, color1 == null);
        assertEquals(false, color2 == null);
        assertEquals(false, color1.equals(color2));
    }
}
