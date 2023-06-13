package src.main;


import java.util.ArrayList;
import java.util.Observable;

/**
 * Repository to hold the problems for the user to solve
 * @author Patrick Whitlock
 */
public class ProblemRepository extends Observable implements Repository{
    private static ProblemRepository repository;
    private ArrayList<Assignment> assignments;
    private int assignmentIndex;
    private int problemIndex;

    /**
     * Constructor to initialize data
     */
    private ProblemRepository(){
        ArrayList<UserExample> probSet1 = new ArrayList<>();
        probSet1.add(UserExampleFactory.getFutureExercise("1-1",0));
        probSet1.add(UserExampleFactory.getFutureExercise("1-2",1));
        probSet1.add(UserExampleFactory.getFutureExercise("1-3",2));
        Assignment a1 = new Assignment(probSet1,"Assignment 1");
        ArrayList<UserExample> probSet2 = new ArrayList<>();
        probSet2.add(UserExampleFactory.getFutureExercise("2-1",0));
        probSet2.add(UserExampleFactory.getFutureExercise("2-2",1));
        probSet2.add(UserExampleFactory.getFutureExercise("2-3",2));
        probSet2.add(UserExampleFactory.getFutureExercise("2-4",1));
        probSet2.add(UserExampleFactory.getFutureExercise("2-5",0));
        Assignment a2 = new Assignment(probSet2,"Assignment 2");
        ArrayList<UserExample> probSet3 = new ArrayList<>();
        probSet3.add(UserExampleFactory.getFutureExercise("3-1",0));
        probSet3.add(UserExampleFactory.getFutureExercise("3-2",1));
        probSet3.add(UserExampleFactory.getFutureExercise("3-3",2));
        probSet3.add(UserExampleFactory.getFutureExercise("3-4",0));
        Assignment a3 = new Assignment(probSet3,"Assignment 3");

        assignments = new ArrayList<>();
        assignments.add(a1);
        assignments.add(a2);
        assignments.add(a3);

        assignmentIndex = 0;
        problemIndex = 0;
    }

    /**
     * getter for instance of repository, initializes if null
     * @return instance of repository
     */
    public static Repository getInstance(){
        if(repository == null) {
            repository = new ProblemRepository();
        }
        return repository;
    }

    public UserExample getCurrentProblem() {
        return this.assignments.get(this.assignmentIndex).getProblem(problemIndex);
    }

    public int getBestTimeForCurrentProblem() {
        return this.assignments.get(this.assignmentIndex).getBestTime(problemIndex).intValue();
    }

    public void addBestTimeToCurrentProblem(int bestTime) {
        this.assignments.get(this.assignmentIndex).setBestTime(problemIndex,bestTime);
    }



    public void setNextProblemIndex() {
        DataRepository dRepo = (DataRepository) DataRepository.getInstance();
        dRepo.clearFlowchart();
        if(this.problemIndex == this.assignments.get(assignmentIndex).getAssignmentProblemSet().size()-1) {
            this.problemIndex = 0;
        } else {
            this.problemIndex++;
        }
        System.out.println("current problem index: "+this.problemIndex);
        setChanged();
        notifyObservers();
    }
    public void setPrevProblemIndex() {
        DataRepository dRepo = (DataRepository) DataRepository.getInstance();
        dRepo.clearFlowchart();
        if(this.problemIndex == 0) {
            this.problemIndex = this.assignments.get(assignmentIndex).getAssignmentProblemSet().size()-1;
        } else {
            this.problemIndex--;
        }
        System.out.println("current problem index: "+this.problemIndex);
        setChanged();
        notifyObservers();
    }

    public void setCurrentProblem() {
        DataRepository dRepo = (DataRepository) DataRepository.getInstance();
        dRepo.clearFlowchart();
        dRepo.addAll(this.getCurrentProblem().getFlowChart());
    }
    public void setNextProblem() {
        DataRepository dRepo = (DataRepository) DataRepository.getInstance();
        this.setNextProblemIndex();
        dRepo.clearFlowchart();
        dRepo.addAll(this.getCurrentProblem().getFlowChart());
    }
    public void setPreviousProblem() {
        DataRepository dRepo = (DataRepository) DataRepository.getInstance();
        setPrevProblemIndex();
        dRepo.clearFlowchart();
        dRepo.addAll(this.getCurrentProblem().getFlowChart());
    }
    public void setAssignmentIndex(int assNum) {
        if(assNum < assignments.size()) {
            this.assignmentIndex = assNum;
            this.problemIndex = 0;
            setChanged();
            notifyObservers();
        }
        else System.out.println("could not find assignment at index "+assNum);
    }
    public int getNumAssignmentProblems(String assignmentName) {
        int assignmentIndex = Integer.parseInt(assignmentName.substring(11))-1;
        if(assignmentIndex < this.assignments.size()) return this.assignments.get(assignmentIndex).getAssignmentProblemSet().size();
        return 0;
    }

    public int getProblemIndex() {
        return problemIndex;
    }

    public void setAssignmentComplete(boolean complete) {
        this.assignments.get(this.assignmentIndex).setAssignmentComplete(complete);
    }
}
