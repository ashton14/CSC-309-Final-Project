package src.main;


import java.util.ArrayList;

/**
 * This class will hold an arraylist of problems for the user to solve
 * @author Patrick Whitlock
 */
public class Assignment {
    private ArrayList<UserExample> problems;
    private ArrayList<Boolean> correct;
    private ArrayList<Integer> bestTimes; //in seconds
    private String assignmentName;

    public Assignment(ArrayList<UserExample> problems, String assignmentName) {
        this.problems = problems;
        this.assignmentName = assignmentName;
        bestTimes = new ArrayList<>();
        for(UserExample problem : problems) { //initialize bestTimes to be the max time
            bestTimes.add(359999);
        }
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

    public Integer getBestTime(int index) {return this.bestTimes.get(index); }
    public void setBestTime(int index, int bestTimeSeconds) {
        System.out.println("attempting to set new best time! new: "+bestTimeSeconds+" old: "+this.bestTimes.get(index));
        if(this.bestTimes.get(index) > bestTimeSeconds && bestTimeSeconds > 0) {
            this.bestTimes.set(index, bestTimeSeconds);
            System.out.println("new best time set! new: "+bestTimeSeconds+" old: "+this.bestTimes.get(index));
        }
    }
}
