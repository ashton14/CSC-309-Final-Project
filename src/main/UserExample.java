package src.main;

import javax.swing.*;
import java.util.ArrayList;

/**
 *  This class will hold a list of CodeBlocks (a flowchart) as well as
 *  a list of Strings that will represent code statements.
 *
 *  This class will also include methods to compare a provided list of CodeBlocks or
 *  Strings to the correct values and return where there is a mistake or if
 *  everything is correct
 *
 * @author Patrick Whitlock
 */
public class UserExample {
    private ArrayList<String> codeStatements;
    private String html_code;
    private ArrayList<CodeBlock> codeBlocks;
    private ArrayList<Line> lines;
    private String exampleName;
    private boolean completed;

    /**
     * Creates a new UserExample.
     * @param codeStatements ArrayList of strings where each string is a code statement
     * @param codeBlocks ArrayList of CodeBlocks that represent the flowchart of this exercise
     * @param exampleName String representing the name of this exercise
     */
    public UserExample(ArrayList<String> codeStatements,String html_code, ArrayList<CodeBlock> codeBlocks, ArrayList<Line> lines, String exampleName) {
        this.codeStatements = codeStatements;
        this.html_code = html_code;
        this.codeBlocks = codeBlocks;
        this.lines = lines;
        this.exampleName = exampleName;
    }

    /**
     * Compares a user-drawn flowchart to the correct flowchart in this exercise
     * @param userCodeBlocks ArrayList of CodeBlocks, to be extracted from the user drawn flowchart
     * @return the index where the first mistake is, or -1 if there are no mistakes
     */
    public int gradeUserDiagram(ArrayList<CodeBlock> userCodeBlocks) {
        int maxIndex = this.codeBlocks.size();
        int sizeProblem = 0;

        //check for empty arraylist
        if(userCodeBlocks.size() == 0) {
            return 0;
        }

        //check for incomplete diagram
        if(userCodeBlocks.size() < this.codeBlocks.size()) {
            maxIndex = userCodeBlocks.size();
            sizeProblem = 1;
        }
        if(userCodeBlocks.size() > this.codeBlocks.size()) {
            maxIndex = this.codeBlocks.size();
            sizeProblem = 1;
        }
        for(int i = 0; i < maxIndex; i++) {
            System.out.println("i = "+i);
            //check if we reached the final 'valid' codeblock (but still incomplete)
            if(i == maxIndex-1 && sizeProblem == 1) return i;

            //check this codeblock is the same
            if(this.codeBlocks.get(i).getClass() !=
                    userCodeBlocks.get(i).getClass()) {
                return i;
            }
            System.out.println("class name match: "+this.codeBlocks.get(i).getClass().getName());

            //check inbound CodeBlocks
            if(this.codeBlocks.get(i).getInboundCodeBlocks().size() !=
                userCodeBlocks.get(i).getInboundCodeBlocks().size()) {
                return i;
            }
            System.out.println("inbound code blocks match: "+this.codeBlocks.get(i).getInboundCodeBlocks().size());

            for(int j = 0; j < this.codeBlocks.get(i).getInboundCodeBlocks().size(); j++) {
                if(this.codeBlocks.get(i).getInboundCodeBlocks().get(j).getClass() !=
                    userCodeBlocks.get(i).getInboundCodeBlocks().get(j).getClass()) {
                    return i;
                }
                System.out.println("inbound classes match: "+userCodeBlocks.get(i).getInboundCodeBlocks().get(j).getClass());
            }

            //check outbound CodeBlocks
            if(this.codeBlocks.get(i).getOutboundCodeBlocks().size() !=
                userCodeBlocks.get(i).getOutboundCodeBlocks().size()) {
                return i;
            }
            System.out.println("outbound code blocks match: "+userCodeBlocks.get(i).getOutboundCodeBlocks().size());
            for(int j = 0; j < this.codeBlocks.get(i).getOutboundCodeBlocks().size(); j++) {
                if(this.codeBlocks.get(i).getOutboundCodeBlocks().get(j).getClass() !=
                        userCodeBlocks.get(i).getOutboundCodeBlocks().get(j).getClass()) {
                    return i;
                }
                System.out.println("outbound classes match: "+userCodeBlocks.get(i).getOutboundCodeBlocks().get(j).getClass()+" j="+j);
            }
            System.out.println("this code block looks good, i = "+i);
        }
        return -1;
    }

    /**
     * Compares a user-written code snippet to the correct code snippet in this exercise
     * @param userCodeStatements ArrayList of strings, to be extracted from the code snippet panel
     * @return the index of the first mistake, or -1 for no mistakes
     */
    public int gradeUserCode(ArrayList<String> userCodeStatements,boolean verbose){
        FeedbackRepository fRepo = (FeedbackRepository) FeedbackRepository.getInstance();
        fRepo.setErrorIndex(0);

        //check if userCode is smaller than correct answer
        int maxIndex = this.codeStatements.size();
        int sizeProblem = 0;

        if(userCodeStatements.size() == 0) {
            if(!verbose)fRepo.setFeedback("Where's the code?");
            if(verbose) fRepo.setFeedback("This should get you started: "+this.codeStatements.get(0));
            return 0;
        }
        if(userCodeStatements.size() < this.codeStatements.size()) {
            maxIndex = userCodeStatements.size();
            sizeProblem = 1;
            if(!verbose) fRepo.setFeedback("The code has fewer lines than expected.");
        }
        if(userCodeStatements.size() > this.codeStatements.size()) {
            maxIndex = this.codeStatements.size();
            sizeProblem = 1;
            if(!verbose) fRepo.setFeedback("The code has more lines than expected.");
        }

        for(int i = 0; i < maxIndex; i++) {
            if(!this.codeStatements.get(i).equals(userCodeStatements.get(i))) {
                int lineNumber = i + 1;
                fRepo.setFeedback("There is a mistake on line "+lineNumber);
                if(verbose) fRepo.setFeedback("The correct code for this line is: "+this.codeStatements.get(i));
                return i;
            }
            if(i == maxIndex-1 && sizeProblem == 1) {
                if(verbose) fRepo.setFeedback("The correct code for this line is: "+this.codeStatements.get(i+1));
                return i+1;
            }
        }
        fRepo.setFeedback("The code is correct! Great job!");
        return -1;
    }

    public ArrayList<String> getCodeStatements() {
        return codeStatements;
    }

    public ArrayList<Drawable> getFlowChart() {
        ArrayList<Drawable> drawables = new ArrayList<>();
        drawables.addAll(codeBlocks);
        drawables.addAll(lines);
        return drawables;
    }

    /**
     * Returns the CodeBlocks from this UserExample
     * in an ArrayList of type CodeBlock.
     * @return   The CodeBlocks from this UserExample
     *           in an ArrayList of type CodeBlock.
     */
    public ArrayList<CodeBlock> getCodeBlocks(){
        return codeBlocks;
    }
    public String getHtml_code() {
        return this.html_code;
    }
    public String getProblemName() {
        return this.exampleName;
    }

    public void setExampleName(String exampleName) {
        this.exampleName = exampleName;
    }

    public void setCompleted(){
        this.completed = true;
    }

    public boolean isCompleted() {
        return this.completed;
    }
}
