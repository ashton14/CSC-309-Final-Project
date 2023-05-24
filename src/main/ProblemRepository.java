package src.main;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Repository to hold the problems for the user to solve
 * @author Patrick Whitlock
 */
public class ProblemRepository extends Observable implements Repository{
    private static ProblemRepository repository;
    private ArrayList<UserExample> problemSet;
    private int problemSetIndex;

    /**
     * Constructor to initialize data
     */
    private ProblemRepository(){
        problemSet = new ArrayList<>();
        problemSet.add(UserExampleFactory.getEx0());
        problemSet.add(UserExampleFactory.getEx1());
        problemSet.add(UserExampleFactory.getEx2());
        problemSetIndex = 0;
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
        return this.problemSet.get(this.problemSetIndex);
    }

    public void setNextProblemIndex() {
        DataRepository dRepo = (DataRepository) DataRepository.getInstance();
        dRepo.clear();
        if(this.problemSetIndex == this.problemSet.size()-1) {
            this.problemSetIndex = 0;
        } else {
            this.problemSetIndex++;
        }
        System.out.println("current problem index: "+this.problemSetIndex);
        setChanged();
        notifyObservers();
    }
    public void setPrevProblemIndex() {
        DataRepository dRepo = (DataRepository) DataRepository.getInstance();
        dRepo.clear();
        if(this.problemSetIndex == 0) {
            this.problemSetIndex = this.problemSet.size()-1;
        } else {
            this.problemSetIndex--;
        }
        System.out.println("current problem index: "+this.problemSetIndex);
        setChanged();
        notifyObservers();
    }

    public void setCurrentProblem() {
        DataRepository dRepo = (DataRepository) DataRepository.getInstance();
        dRepo.clear();
        dRepo.addAll(this.getCurrentProblem().getFlowChart());
    }
    public void setNextProblem() {
        DataRepository dRepo = (DataRepository) DataRepository.getInstance();
        this.setNextProblemIndex();
        dRepo.clear();
        dRepo.addAll(this.getCurrentProblem().getFlowChart());
    }
    public void setPreviousProblem() {
        DataRepository dRepo = (DataRepository) DataRepository.getInstance();
        setPrevProblemIndex();
        dRepo.clear();
        dRepo.addAll( this.getCurrentProblem().getFlowChart());
        dRepo.modifiedDrawables();
    }
}
