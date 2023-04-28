package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class SidePanelControlHandler implements ActionListener, MouseListener {

    private final ArrayList<JTextArea> jTextAreas;

    SidePanelControlHandler(ArrayList<JTextArea> jTextAreas){
        this.jTextAreas = jTextAreas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Submit")){
            Repository.getInstance().parseCode();

            //The following code is temporary in order to quickly test the main.UserExample class
            UserExample ex1 = UserExampleTests.getEx1();
            System.out.println("SUBMIT BUTTON PUSHED");

            //grade text
            System.out.println("user code at line 0: "+jTextAreas.get(0).getText());
            System.out.println("user code at line 1: "+jTextAreas.get(1).getText());

            //build ArrayList of strings
            ArrayList<String> usersCode = new ArrayList<>();
            for(int i = 0; i < jTextAreas.size(); i++) {
                if(!jTextAreas.get(i).getText().equals("")) {
                    usersCode.add(jTextAreas.get(i).getText());
                }
            }
            int mistakeIndex = ex1.gradeUserCode(usersCode);
            System.out.println("Code mistake at line: "+mistakeIndex);

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
