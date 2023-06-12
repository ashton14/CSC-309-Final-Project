package src.main;

import java.util.List;

/**
 * @author Connor Hickey
 */
public class Course {
    private String courseName;
    private String description;
    private String teacher;
    private int numAssignmentsCompleted;
    private List<String> assignments;

    public Course(String courseName, String teacher, String description, List<String> assignments) {
        this.courseName = courseName;
        this.teacher = teacher;
        this.description = description;
        this.assignments = assignments;
        this.numAssignmentsCompleted = 0;
    }

    public void setNumAssignmentsCompleted(int completed) {
        this.numAssignmentsCompleted = completed;
    }

    public String getCourseCompletionString() {
        return this.numAssignmentsCompleted+"/"+this.assignments.size();
    }

    public String getDescription() {
        return description;
    }

    public String getTeacher() {
        return this.teacher;
    }


    public String getCourseName() {
        return this.courseName;
    }

    public List<String> getAssignments() {
        return this.assignments;
    }
}
