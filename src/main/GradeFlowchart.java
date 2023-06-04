package src.main;

import java.util.*;
import java.util.function.Predicate;

/**
 * A class to compare a flowchart solution to another
 * flowchart. Verbose feedback is given through the FeedBack Repository.
 * Flowcharts are to be given in the form of an ArrayList of type CodeBlock
 * for ease of use and increased efficiency of select operations.
 * These flowcharts are evaluated as graphs starting with
 * the StartBlock, which there should exist only one of.
 */
public class GradeFlowchart {

    private Map<CodeBlock, CodeBlock> solutionToStudentBlock;
    private  ArrayList<CodeBlock> solutionChart;
    private ArrayList<CodeBlock> studentChart;
    private CodeBlock previousCodeBlock;

    private boolean gaveHint;
    private boolean isVerbose;
    private boolean isCorrect;

    /**
     * A constructor to create objects of type GradeFlowchart. These objects are to be
     * used to grade a flowchart.
     * @param solutionChart  The flowchart to be used to grade the student's flowchart.
     * @param studentChart   The student submitted flowchart.
     * @param isVerbose   A boolean representing if verbose feedback should be sent
     *                    to the FeedBackRepository (more than if the diagram
     *                    is correct or not).
     */
    public GradeFlowchart(ArrayList<CodeBlock> solutionChart, ArrayList<CodeBlock> studentChart,
                          boolean isVerbose){
        if(solutionChart == null && studentChart == null){
            throw new NullPointerException("Both parameters are null.");
        }
        if(solutionChart == null){
            throw new NullPointerException("The solutionChart parameter is null.");
        }
        if(studentChart == null){
            throw new NullPointerException("The studentChart parameter is null.");
        }
        this.solutionChart = solutionChart;
        this.studentChart = studentChart;
        previousCodeBlock = null;
        solutionToStudentBlock = new HashMap<>();
        this.isVerbose = isVerbose;
        isCorrect = true;
        gaveHint = false;
    }

    /**
     * Helper method to give feedback that the student was wrong
     * depending on if verbose or non-verbose feedback is expected.
     * @param feedback   The verbose feedback as a String to
     *                   potentially send to the FeedbackRepository.
     */
    private void sendNegativeFeedback(String feedback){
        FeedbackRepository hintRepository = (FeedbackRepository) FeedbackRepository.getInstance();
        isCorrect = false;
        if(gaveHint){
            return;
        }
        gaveHint = true;
        if(isVerbose) {
            hintRepository.setFeedback(feedback);
        } else {
            hintRepository.setFeedback("The provided diagram is not correct.");
        }
    }

    /**
     * Helper method to give feedback that the student was correct
     * depending on if verbose or non-verbose feedback is expected.
     * @param feedback   The verbose feedback as a String to
     *                   potentially send to the FeedbackRepository.
     */
    public void sendPositiveFeedback(String feedback){
        FeedbackRepository hintRepository = (FeedbackRepository) FeedbackRepository.getInstance();
        if(isVerbose) {
            hintRepository.setFeedback(feedback);
        } else {
            hintRepository.setFeedback("The provided diagram is correct.");
        }
    }


    /**
     * Finds the first instance of a StartBlock within an
     * ArrayList of type CodeBlock.
     * @param flowchart   An ArrayList of type CodeBlock
     *                    to search.
     * @return The first instance of a StartBlock within
     * the given flowchart or null if it does not exist.
     */
    private StartBlock findStart(ArrayList<CodeBlock> flowchart){
        for (CodeBlock codeBlock : flowchart) {
            if (codeBlock.getClass() == StartBlock.class) {
                return (StartBlock) codeBlock;
            }
        }
        return null;
    }

    /**
     * Determines if a given flowchart has only one instance
     * of a StartBlock.
     * @param flowchart  An ArrayList of type CodeBlock to search.
     * @return  A boolean representing if the flowchart has
     * one instance of a StartBlock.
     */
    private boolean hasOneStart(ArrayList<CodeBlock> flowchart){
        int startCount = 0;
        for (CodeBlock codeBlock : flowchart) {
            if (codeBlock.getClass() == StartBlock.class) {
                ++startCount;
            }
        }
        return startCount == 1;
    }

    /**
     * Helper method for the distance function. Finds
     * the minimum value of three integers.
     * @param int1   The first potential minimum integer.
     * @param int2   The second potential minimum integer.
     * @param int3   The third potential minimum integer.
     * @return   The minimum integer of three given values.
     */
    public static int min(int int1, int int2, int int3){
        int min = Math.min(int1, int2);
        min = Math.min(min, int3);
        return min;
    }

    /**
     * Recursive function to find the Levenshtein distance between two strings of characters given as StringBuilders.
     * Gives up on finding the distance if it is greater than the tolerance value given.
     * @param solution   The StringBuilder representing the expected string of characters.
     * @param student   The StringBuilder representing the string of characters provided by a student.
     * @param distance   The "current" distance between the two StringBuilders within this step of recursion
     *                   or -1 to indicate that this is the first time this function is being called.
     * @param index   The index of the student StringBuilder that is being evaluated to match the solution StringBuilder.
     * @param tolerance   The maximum amount of changes to search for to match the two given strings of characters.
     * @return The Levenshtein distance between two StringBuilders or the given tolerance value if the computed distance
     * exceeds it.
     */
    public int distance(StringBuilder solution, StringBuilder student, int distance, int index, int tolerance){
        if(solution == null || student == null)
            return 0;
        if(distance == -1)
            distance = 0;
        if(distance > tolerance){
            return distance;
        }
        for(int i = index; i < solution.length(); ++i) {
            if (i < student.length() && solution.charAt(i) != student.charAt(i)){
                    distance += 1;
                    StringBuilder delete = new StringBuilder(student);
                    delete.deleteCharAt(i);

                    StringBuilder replace = new StringBuilder(student);
                    replace.replace(i, i +1, String.valueOf(solution.charAt(i)));

                    StringBuilder add = new StringBuilder(student);
                    add.insert(i, solution.charAt(i));
                    return min(distance(solution, add, distance, i, tolerance),
                            distance(solution, replace, distance, i, tolerance),
                            distance(solution, delete, distance, i, tolerance));
                }
        }
        return distance;
    }

    /**
     * Wrapper function to call the distance function for the first time. This function is to be used
     * to the distance of an expected String to an input String up to a given distance (tolerance).
     * @param solution   The expected input as a String.
     * @param student   The student's input as a String.
     * @param tolerance   The maximum amount of differences to search for.
     * @return  The distance between the solution and student Strings or the given tolerance value,
     * whichever is lower.
     */
    public int distanceWrapper(String solution, String student, int tolerance){
        int distance = distance(new StringBuilder(solution), new StringBuilder(student), -1,0, tolerance);
        if(solution.length() > student.length()){
            distance += solution.length() - student.length();
        }
        return distance;
    }

    /**
     * Returns a boolean representing if two CodeBlocks
     * are equivalent (not null, the same class and have the equal text ignoring whitespace).
     * @param codeBlock1   The first CodeBlock to check.
     * @param codeBlock2   The second CodeBlock to check.
     * @return    True if the two CodeBlocks are equivalent, otherwise
     * false.
     */
    public  boolean isCodeBlockEquivalent(CodeBlock codeBlock1, CodeBlock codeBlock2){
        if(codeBlock1 == null || codeBlock2 == null){
            return false;
        }
        String codeBlock1Text = codeBlock1.getText().replaceAll(" ", "");
        String codeBlock2Text = codeBlock2.getText().replaceAll(" ", "");
        if(codeBlock1.getClass() == codeBlock2.getClass() && codeBlock1Text.equals(codeBlock2Text)){
            return true;
        }
        return false;
    }

    /**
     * Returns a boolean representing if two CodeBlocks are equivalent with the
     * exception that they contain unequal text.
     * @param codeBlock1   The first CodeBlock to check.
     * @param codeBlock2   The second CodeBlock to check.
     * @return   True if the two CodeBlocks are equivalent with the exception
     * that they contain unequal text, otherwise false.
     */
    public  boolean isCodeBlockMisspelled(CodeBlock codeBlock1, CodeBlock codeBlock2){
        return (codeBlock1 != null && codeBlock2 != null
                && codeBlock1.getClass() == codeBlock2.getClass()
                && !codeBlock1.getText().equals(codeBlock2.getText()));
    }

    /**
     * Returns a boolean representing if two CodeBlocks are not null
     * and have unequal text.
     * @param codeBlock1   The first CodeBlock to check.
     * @param codeBlock2   The second CodeBlock to check.
     * @return  True if both CodeBlocks are not null and unequal text,
     * otherwise false.
     */
    public  boolean isCodeBlockMisspelledIgnoreType(CodeBlock codeBlock1, CodeBlock codeBlock2){
        return (codeBlock1 != null && codeBlock2 != null
                && !codeBlock1.getText().equals(codeBlock2.getText()));
    }

    /**
     * Returns a boolean representing if two CodeBlocks are not null,
     * contain equal text and are different classes.
     * @param codeBlock1   The first CodeBlock to check.
     * @param codeBlock2   The second CodeBlock to check.
     * @return   True if both CodeBlocks are not null, contain equal text
     * and are different classes, otherwise false.
     */
    public boolean isCodeBlockWrongType(CodeBlock codeBlock1, CodeBlock codeBlock2){
        return (codeBlock1 != null && codeBlock2 != null
                && codeBlock1.getClass() != codeBlock2.getClass()
                && codeBlock1.getText().equals(codeBlock2.getText()));
    }

    /**
     * Finds and returns the first CodeBlock in the nextStudentBlocks ArrayList that
     * has not yet been discovered and is determined to be equivalent to the given
     * nextSolutionBlock of type CodeBlock.
     * @param nextStudentBlocks   The ArrayList of type CodeBlock to search for a match.
     * @param nextSolutionBlock   The CodeBlock to search for.
     * @return The first CodeBlock in the nextStudentBlocks ArrayList that
     *         has not yet been discovered and is determined to be equivalent to the given
     *         nextSolutionBlock of type CodeBlock if it exists, otherwise null.
     */
    public CodeBlock findEquivalent(ArrayList<CodeBlock> nextStudentBlocks, CodeBlock nextSolutionBlock){
        for(int i = 0; i < nextStudentBlocks.size(); ++i){
            if(isCodeBlockEquivalent(nextSolutionBlock, nextStudentBlocks.get(i))) {
                for (int j = 0; j < nextStudentBlocks.get(i).getDiscovered().size(); ++j) {
                    if(!nextStudentBlocks.get(i).getDiscovered().get(j)){
                        nextStudentBlocks.get(i).getDiscovered().set(j, true);
                        nextSolutionBlock.getDiscovered().set(j, true);
                        solutionToStudentBlock.put(nextSolutionBlock, nextStudentBlocks.get(i));
                        return nextStudentBlocks.get(i);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Finds and returns the first CodeBlock in the nextStudentBlocks ArrayList that
     * has not yet been discovered and is determined to be the same, but be a different class
     * compared to the given nextSolutionBlock of type CodeBlock.
     * @param nextStudentBlocks   The ArrayList of type CodeBlock to search for a match.
     * @param nextSolutionBlock   The CodeBlock to search for.
     * @return The first CodeBlock in the nextStudentBlocks ArrayList that
     *         has not yet been discovered and is determined to be the same, but be a different class
     *         compared to the given nextSolutionBlock of type CodeBlock, otherwise null.
     */
    public CodeBlock findWrongType(ArrayList<CodeBlock> nextStudentBlocks, CodeBlock nextSolutionBlock){
        for(int i = 0; i < nextStudentBlocks.size(); ++i){
            if(isCodeBlockWrongType(nextSolutionBlock, nextStudentBlocks.get(i))) {
                for (int j = 0; j < nextStudentBlocks.get(i).getDiscovered().size(); ++j) {
                    if(!nextStudentBlocks.get(i).getDiscovered().get(j)){
                        nextStudentBlocks.get(i).getDiscovered().set(j, true);
                        nextSolutionBlock.getDiscovered().set(j, true);
                        solutionToStudentBlock.put(nextSolutionBlock, nextStudentBlocks.get(i));
                        return nextStudentBlocks.get(i);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Finds and returns the first CodeBlock in the nextStudentBlocks ArrayList that
     * has not yet been discovered and is determined to be the same, but contains
     * unequal text compared to the nextSolutionBlock of type CodeBlock.
     * @param nextStudentBlocks   The ArrayList of type CodeBlock to search for a match.
     * @param nextSolutionBlock   The CodeBlock to search for.
     * @return The first CodeBlock in the nextStudentBlocks ArrayList that
     *         has not yet been discovered and is determined to be the same, but contains
     *         unequal text compared to the nextSolutionBlock of type CodeBlock, otherwise null.
     */
    public CodeBlock findMisspelled(ArrayList<CodeBlock> nextStudentBlocks, CodeBlock nextSolutionBlock, int tolerance){
        CodeBlock closestSpelledBlock = null;
        int closestDistance = tolerance - 1;
        for(int i = 0; i < nextStudentBlocks.size(); ++i){
            if(isCodeBlockMisspelled(nextSolutionBlock, nextStudentBlocks.get(i))) {
                for (int j = 0; j < nextStudentBlocks.get(i).getDiscovered().size(); ++j) {
                    if (!nextStudentBlocks.get(i).getDiscovered().get(j)) {
                        int tempClosestDistance = distanceWrapper(nextSolutionBlock.getText(),
                                nextStudentBlocks.get(i).getText(), tolerance);
                        if (closestDistance > tempClosestDistance) {
                            closestSpelledBlock = nextStudentBlocks.get(i);
                            closestDistance = tempClosestDistance;
                        }
                    }
                }
            }
        }
        return closestSpelledBlock;
    }


    /**
     * Finds and returns the first CodeBlock in the nextStudentBlocks ArrayList that
     * has not yet been discovered and is determined to be the same, but contains
     * unequal text compared to the nextSolutionBlock of type CodeBlock.
     * @param nextStudentBlocks   The ArrayList of type CodeBlock to search for a match.
     * @param nextSolutionBlock   The CodeBlock to search for.
     * @return The first CodeBlock in the nextStudentBlocks ArrayList that
     *         has not yet been discovered and is determined to be the same, but contains
     *         unequal text compared to the nextSolutionBlock of type CodeBlock, otherwise null.
     */
    public CodeBlock findMisspelledIgnoreType(ArrayList<CodeBlock> nextStudentBlocks, CodeBlock nextSolutionBlock, int tolerance){
        CodeBlock closestSpelledBlock = null;
        int closestDistance = tolerance - 1;
        for(int i = 0; i < nextStudentBlocks.size(); ++i){
            if(isCodeBlockMisspelledIgnoreType(nextSolutionBlock, nextStudentBlocks.get(i))) {
                for (int j = 0; j < nextStudentBlocks.get(i).getDiscovered().size(); ++j) {
                    if (!nextStudentBlocks.get(i).getDiscovered().get(j)) {
                        int tempClosestDistance = distanceWrapper(nextSolutionBlock.getText(),
                                nextStudentBlocks.get(i).getText(), tolerance);
                        if (closestDistance > tempClosestDistance) {
                            closestSpelledBlock = nextStudentBlocks.get(i);
                            closestDistance = tempClosestDistance;
                        }
                    }
                }
            }
        }
        return closestSpelledBlock;
    }

    /**
     * Determines if a CodeBlock has more CodeBlocks that
     * are accessible from it that have not yet been discovered.
     * @param codeBlock  The CodeBlock instance to check
     *                   if there are more CodeBlocks
     *                   that are accessible from it.
     * @return   True if there is at least one CodeBlock
     * that has not yet been discovered that is accessible
     * from the given CodeBlock, otherwise false.
     */
    private boolean discoverable(CodeBlock codeBlock){
        if(codeBlock.getDiscovered().size() == 0)
            return false;
        for(int i = 0; i < codeBlock.getDiscovered().size(); ++i){
            if(!codeBlock.getDiscovered().get(i)){
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method for discover to retrieve a conditional CodeBlock (LoopBlock or IfBlocK)
     * that has an inbound connection to the given CodeBlock and is not the same instance
     * of the given CodeBlock.
     * @param codeBlock   The CodeBlock to search for a conditional CodeBlock parent of.
     * @return   A conditional CodeBlock that has an inbound connection into the
     * given CodeBlock or null if one does not exist.
     */
    private CodeBlock getPreviousConditional(CodeBlock codeBlock){
        ArrayList<CodeBlock> inboundCodeBlocks = codeBlock.getInboundCodeBlocks();
        for(int i = 0; i < inboundCodeBlocks.size(); ++i){
            if( (inboundCodeBlocks.get(i).getClass() == IfBlock.class ||
            inboundCodeBlocks.get(i).getClass() == LoopBlock.class) &&
            inboundCodeBlocks.get(i) != codeBlock){
                return inboundCodeBlocks.get(i);
            }
        }
        return null;
    }

    private void unequivalentCodeBlockSuggestion(CodeBlock nextSolutionBlock, ArrayList<CodeBlock> nextStudentBlocks){
        if(solutionToStudentBlock.get(nextSolutionBlock) != null){
            sendNegativeFeedback("A " + nextSolutionBlock.toString() + " Block labeled: \""
                    + nextSolutionBlock.getText() + "\" is missing a connection. It should have an incoming " +
                    " connection from the "
                    + previousCodeBlock.toString() + "Block labeled: \"" + previousCodeBlock.getText() + "\".");
            return;
        }
        CodeBlock nextStudentWrongType = findWrongType(nextStudentBlocks, nextSolutionBlock);
        if(nextStudentWrongType != null){
            sendNegativeFeedback("The " + nextStudentWrongType.toString() + " Block labeled: \""
                    + nextStudentWrongType.getText() + "\" might be the wrong type. Did you mean to make it a" +
                    " \"" + nextSolutionBlock.toString() + "\"?");
            return;
        }


        CodeBlock nextStudentBlockTypo = findMisspelled(nextStudentBlocks, nextSolutionBlock, 5);
        if(nextStudentBlockTypo != null){
            sendNegativeFeedback("A " + nextSolutionBlock.toString() + " Block labeled: \""
                    + nextStudentBlockTypo.getText() + "\" might be mislabeled. Did you mean to label it" +
                    " \"" + nextSolutionBlock.getText() + "\"?");
            return;
        }

        CodeBlock nextStudentBlockTypoTypeIgnored = findMisspelledIgnoreType(nextStudentBlocks, nextSolutionBlock,5);
        if(nextStudentBlockTypoTypeIgnored != null){
            sendNegativeFeedback("A " + nextSolutionBlock.toString() + " Block labeled: \""
                    + nextStudentBlockTypoTypeIgnored.getText() + "\" might be mislabeled and of the wrong type. " +
                    "Did you mean to label it:" +
                    " \"" + nextSolutionBlock.getText() + "\" and make it a " + nextSolutionBlock.toString() + "Block ?");
            return;
        }
        sendNegativeFeedback("A " + nextSolutionBlock.toString() + " labeled: \""
                + nextSolutionBlock.getText() + "\" is missing or not properly connected. " +
                "This Code Block should be executed after the " + previousCodeBlock.toString() + " Block labeled: \"" +
                previousCodeBlock.getText() + "\".");
    }

    /**
     * Discovers if the two graphs starting at the given CodeBlocks of the solution
     * and the student's response are equivalent. The checking stops once the first
     * error is found and feedback is reported to the FeedbackRepository.
     * @param solutionBlock   The root of the solution graph of CodeBlocks to check.
     * @param studentBlock   The root of the student's graph of CodeBlocks to check.
     */
    public void discover(CodeBlock solutionBlock, CodeBlock studentBlock) {
        previousCodeBlock = solutionBlock;
        ArrayList<CodeBlock> nextSolutionBlocks = solutionBlock.getOutboundCodeBlocks();
        ArrayList<Boolean> nextSolutionBlocksMarkings = solutionBlock.getOutBoundCodeBlockMarkings();
        ArrayList<CodeBlock> nextStudentBlocks = studentBlock.getOutboundCodeBlocks();
        ArrayList<Boolean> nextStudentBlocksMarkings = studentBlock.getOutBoundCodeBlockMarkings();

        for(int i = 0; i < nextSolutionBlocks.size(); ++i){
            CodeBlock nextSolutionBlock = nextSolutionBlocks.get(i);

            if(!discoverable(nextSolutionBlock)){
                return;
            }

            if(previousCodeBlock.getClass() == StopBlock.class){
                CodeBlock tempConditional = getPreviousConditional(nextSolutionBlock);
                if(tempConditional != null) {
                    previousCodeBlock = tempConditional;
                }
            }

            CodeBlock nextStudentBlock = findEquivalent(nextStudentBlocks, nextSolutionBlock);
            if (nextStudentBlock == null) {
               unequivalentCodeBlockSuggestion(nextSolutionBlock, nextStudentBlocks);
               return;
            }
            if(!nextSolutionBlocksMarkings.get(i).equals(nextStudentBlocksMarkings.get(i))){
                sendNegativeFeedback("A connection from a" + previousCodeBlock + " Block to a " + nextStudentBlock.toString() + " Block" +
                        " labeled: " + nextStudentBlock.getText() + " should be marked as " + nextSolutionBlocksMarkings.get(i)
                        + " instead of " + nextStudentBlocksMarkings.get(i));
                return;
            }
            discover(nextSolutionBlock, nextStudentBlock);
        }
    }

    /**
     * Determines if any of the CodeBlocks within a flowchart
     * are disjoint.
     * @param codeBlocks   The flowchart to check for disjoint CodeBlocks.
     * @return   A reference to the first disjoint CodeBlock if it exists,
     * otherwise null.
     */
    private CodeBlock findDisjoint(ArrayList<CodeBlock> codeBlocks){
        for(int i = 0; i < codeBlocks.size(); ++i){
            if(codeBlocks.get(i).getInboundCodeBlocks().size() == 0 &&
            codeBlocks.get(i).getClass() != StartBlock.class){
                return codeBlocks.get(i);
            }
        }
        return null;
    }

    /**
     * Grades the student's flowchart given through the instantiation of this GradeFlowChart
     * by comparing it to the solution flowchart. Feedback is given through the FeedBack
     * Repository.
     *
     * @return   A boolean value of true if the student's flowchart is correct, otherwise false.
     */
    public boolean grade(){
        CodeBlock solutionStart = findStart(solutionChart);
        CodeBlock studentStart = findStart(studentChart);
        if(studentStart == null){
            sendNegativeFeedback("It looks like a Start Block is missing. This block" +
                    " is important because it tells the program where to start.");
            return isCorrect;
        }

        if(!hasOneStart(studentChart)){
            sendNegativeFeedback("It looks like there are more than one Start Blocks in the program. The program " +
                    " in this exercise can only start from one place. This means that there should be only one Start Block.");
            return isCorrect;
        }


        CodeBlock disjointBlock = findDisjoint(studentChart);

        if(disjointBlock != null){
            sendNegativeFeedback("The " + disjointBlock.toString() + " labeled: \"" +
                    disjointBlock.getText() + "\" has no connections entering it. Without" +
                    " an incoming connection this Code Block cannot be executed.");
            return isCorrect;
        }

        for(int i = 0; i < solutionChart.size(); ++i){
            solutionChart.get(i).resetDiscovered();
        }
        for(int i = 0; i < studentChart.size(); ++i){
            studentChart.get(i).resetDiscovered();
        }
        discover(solutionStart, studentStart);
        if(isCorrect){
          sendPositiveFeedback("Good job, this diagram is correct!");
        }
        return isCorrect;
    }
}
