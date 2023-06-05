package src.main;

import java.util.List;

/**
 * @author Connor Hickey
 */
public class Course {
    private String courseName;
    private List<String> assignments;

    public Course(String courseName, List<String> assignments) {
        this.courseName = courseName;
        this.assignments = assignments;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public List<String> getAssignments() {
        return this.assignments;
    }
}
