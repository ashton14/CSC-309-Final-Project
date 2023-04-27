package main;

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
