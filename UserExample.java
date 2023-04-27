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
    ArrayList<String> codeStatements;
    ArrayList<CodeBlock> codeBlocks;

    String exampleName;

    /**
     * Creates a new UserExample.
     * @param codeStatements ArrayList of strings where each string is a code statement
     * @param codeBlocks ArrayList of CodeBlocks that represent the flowchart of this exercise
     * @param exampleName String representing the name of this exercise
     */
    public UserExample(ArrayList<String> codeStatements, ArrayList<CodeBlock> codeBlocks, String exampleName) {
        this.codeStatements = codeStatements;
        this.codeBlocks = codeBlocks;
        this.exampleName = exampleName;
    }

    /**
     * Compares a user-drawn flowchart to the correct flowchart in this exercise
     * @param userCodeBlocks ArrayList of CodeBlocks, extracted from the user drawn flowchart
     * @return the index where the first mistake is, or -1 if there are no mistakes
     */
    public int compareDiagram(ArrayList<CodeBlock> userCodeBlocks) {
        for(int i = 0; i < this.codeBlocks.size(); i++) {
            //check this codeblock is the same
            if(this.codeBlocks.get(i).getClass() !=
                    userCodeBlocks.get(i).getClass()) {
                return i;
            }

            //check inbound CodeBlocks
            for(int j = 0; j < this.codeBlocks.get(i).getInboundCodeBlocks().size(); i++) {
                if(this.codeBlocks.get(i).getInboundCodeBlocks().get(j).getClass() !=
                    userCodeBlocks.get(i).getInboundCodeBlocks().get(j).getClass()) {
                    return i;
                }
            }

            //check outbound CodeBlocks
            for(int j = 0; j < this.codeBlocks.get(i).getOutboundCodeBlocks().size(); i++) {
                if(this.codeBlocks.get(i).getOutboundCodeBlocks().get(j).getClass() !=
                        userCodeBlocks.get(i).getOutboundCodeBlocks().get(j).getClass()) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Compares a user-written code snippet to the correct code snippet in this exercise
     * @param userCodeStatements ArrayList of strings, extracted from the code snippet panel
     * @return the index of the first mistake, or -1 for no mistakes
     */
    public int compareCode(ArrayList<String> userCodeStatements){
        for(int i = 0; i < this.codeStatements.size(); i++) {
            if(this.codeStatements.get(i).equals(userCodeStatements.get(i))) {
                //continue
            } else {
                return i;
            }
        }
        return -1;
    }
}
