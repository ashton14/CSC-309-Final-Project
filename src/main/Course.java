package src.main;

import java.util.ArrayList;
import java.util.List;

/**
 * The Course class represents a course with its properties and assignments.
 * It provides methods to get and set the course information.
 * It also provides methods to retrieve the course completion status and assignments.
 *
 * The course completion status is calculated based on the number of completed assignments
 * and the total number of assignments.
 *
 * The assignments are stored as a list of strings.
 *
 * @author Connor Hickey
 */
public class Course {
    private String courseName;
    private String description;
    private String teacher;
    private int numAssignmentsCompleted;

    private List<Student> enrolledStudents;  // The list of students enrolled in the course

    private List<String> assignments;

    /**
     * Constructs a Course object with the specified course name, teacher, description, and assignments.
     * The initial number of completed assignments is set to 0.
     *
     * @param courseName the name of the course.
     * @param teacher    the teacher of the course.
     * @param description the description of the course.
     * @param assignments the assignments of the course.
     */
    public Course(String courseName, String teacher, String description, List<String> assignments) {
        this.courseName = courseName;
        this.teacher = teacher;
        this.description = description;
        this.assignments = assignments;
        this.numAssignmentsCompleted = 0;
        this.enrolledStudents = new ArrayList<>();  // Initialize with no enrolled students
    }

    /**
     * Sets the number of completed assignments.
     *
     * @param completed the number of completed assignments.
     */
    public void setNumAssignmentsCompleted(int completed) {
        this.numAssignmentsCompleted = completed;
    }

    /**
     * Retrieves the course completion status as a string.
     * The completion status is calculated by dividing the number of completed assignments
     * by the total number of assignments and returning it as a string.
     *
     * @return the course completion status string.
     */
    public String getCourseCompletionString() {
        return this.numAssignmentsCompleted + "/" + this.assignments.size();
    }

    /**
     * Retrieves the description of the course.
     *
     * @return the description of the course.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the teacher of the course.
     *
     * @return the teacher of the course.
     */
    public String getTeacher() {
        return this.teacher;
    }

    /**
     * Retrieves the name of the course.
     *
     * @return the name of the course.
     */
    public String getCourseName() {
        return this.courseName;
    }

    /**
     * Retrieves the assignments of the course.
     *
     * @return the assignments of the course.
     */
    public List<String> getAssignments() {
        return this.assignments;
    }

    public void addAssignment(String assignmentName) {
        this.assignments.add(assignmentName);
    }

    public List<Student> getEnrolledStudents() {
        return this.enrolledStudents;
    }

    public void enrollStudent(Student student) {
        this.enrolledStudents.add(student);
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
