import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SidePanelControlHandler implements ActionListener, MouseListener {

    private final JTextArea jTextArea;

    SidePanelControlHandler(JTextArea jTextArea){
        this.jTextArea = jTextArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Submit")){
            Repository.getInstance().parseCode();

            //Temporary code to test the UserExample class
            UserExample ex0 = UserExampleTests.getEx0();
            System.out.println("SUBMIT BUTTON PUSHED");
            System.out.println(Repository.getInstance().getCodeBlocks().size());
            System.out.println(ex0.getCodeBlocks().size());
            int mistakeIndex = ex0.gradeUserDiagram(Repository.getInstance().getCodeBlocks());
            System.out.println("mistake at CodeBlock index: "+mistakeIndex);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Repository.getInstance().updateStatusBar("Coding...");
    }

    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}
