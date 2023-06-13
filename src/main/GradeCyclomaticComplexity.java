package src.main;

import src.main.CodeBlock;
import src.main.FeedbackRepository;
import src.main.IfBlock;
import src.main.LoopBlock;

import java.util.ArrayList;

/**
 * A class designed to grade the cyclomatic complexity
 * of a flowchart by comparing its contents against
 * a user submitted answer.
 *
 * @author Aaron Bettencourt
 */
public class GradeCyclomaticComplexity {

    private ArrayList<CodeBlock> flowchart;
    private boolean isVerbose;

    /**
     * Creates an instance of GradeCyclomaticComplexity.
     * @param flowchart   The flowchart to compare as
     *                    an ArrayList<CodeBlock>.
     * @param isVerbose   A boolean representing on if
     *                    verbose or minimal feedback
     *                    should be given to the Feedback
     *                    Repository.
     * @throws NullPointerException if the given ArrayList of type CodeBlock is null.
     */
    public GradeCyclomaticComplexity(ArrayList<CodeBlock> flowchart, boolean isVerbose){
        this.isVerbose = isVerbose;
        this.flowchart = flowchart;
        if(flowchart == null){
            throw new NullPointerException("GradeCyclomaticComplexity: flowchart is null");
        }
    }

    /**
     * Converts a String to an Integer. The Integer
     * will contain a null if this operation fails.
     * @param str   The String to convert to an Integer.
     * @return  An Integer representing String.
     */
    private Integer stringToInt(String str){
        try{
            return Integer.parseInt(str);
        } catch (NumberFormatException e){
            return null;
        }
    }

    /**
     * Tests if the user submitted answer as a String represents
     * the cyclomatic complexity of the flowchart used to construct
     * this GradeCyclomaticComplexity.
     * @param userAnswerStr   The answer the user gave as a String.
     * @return   A boolean representing if the user submitted
     * cyclomatic complexity is correct.
     */
    public boolean grade(String userAnswerStr){
        Integer userAnswerNum = stringToInt(userAnswerStr);
        if(userAnswerNum == null){
            ((FeedbackRepository) FeedbackRepository.getInstance()).setFeedback("No " +
                    "cyclomatic complexity was given as an integer. ");
            return false;
        }

        int expectedCyclomaticComplexity = 1;

        for(int i = 0; i < flowchart.size(); ++i){
            if(flowchart.get(i).getClass() == IfBlock.class||
               flowchart.get(i).getClass() == LoopBlock.class){
                expectedCyclomaticComplexity++;
            }
        }
        if(expectedCyclomaticComplexity != userAnswerNum && isVerbose) {
            String reminder = "The cyclomatic complexity is the amount of paths the program can take" +
                    "(1 + the number of loops + the number of if statements). ";
            if(expectedCyclomaticComplexity < userAnswerNum) {
                ((FeedbackRepository) FeedbackRepository.getInstance()).setFeedback("The expected " +
                        "cyclomatic complexity is lower than " + userAnswerNum + ". " + reminder);
            } else {
                ((FeedbackRepository) FeedbackRepository.getInstance()).setFeedback("The expected " +
                        "cyclomatic complexity is greater than " + userAnswerNum + ". " + reminder);
            }
        } else if (expectedCyclomaticComplexity != userAnswerNum && !isVerbose) {
            ((FeedbackRepository) FeedbackRepository.getInstance()).setFeedback("The " +
                    "cyclomatic complexity is not correct.");
        } else if(expectedCyclomaticComplexity == userAnswerNum) {
            ((FeedbackRepository) FeedbackRepository.getInstance()).setFeedback("The " +
                    "cyclomatic complexity is correct!");
        }
        return expectedCyclomaticComplexity == userAnswerNum;
    }

}
