package src.main;


import java.util.ArrayList;

/**
 * This class will hold an arraylist of problems for the user to solve
 * @author Patrick Whitlock
 */
public class Assignment {
    private ArrayList<UserExample> problems;
    private String assignmentName;

    public Assignment(ArrayList<UserExample> problems, String assignmentName) {
        this.problems = problems;
        this.assignmentName = assignmentName;
    }

    public ArrayList<UserExample> getAssignmentProblemSet() {
        return problems;
    }

    public UserExample getProblem(int index) {
        return this.problems.get(index);
    }

    public String getAssignmentName() {
        return assignmentName;
    }
}
