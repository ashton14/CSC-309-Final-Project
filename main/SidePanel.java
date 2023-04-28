package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class responsible for holding the code snippet, and perhaps
 * other tutor-related items
 * @author Patrick Whitlock
 */
public class SidePanel extends JPanel {
    ArrayList<JTextArea> codeSections;
    /**
     * Creates a SidePanel object
     */
    SidePanel() {
        codeSections = new ArrayList<>();
        SidePanelControlHandler sideController = new SidePanelControlHandler(codeSections);
        for(int i = 0; i < 12; i++) {
            JTextArea codeSection = new JTextArea("");
            codeSection.setPreferredSize(new Dimension(280,20));
            this.codeSections.add(codeSection);
            this.add(codeSection);
            codeSection.addMouseListener(sideController);
        }



        JButton submit = new JButton("Submit");
        submit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(submit);

        submit.addActionListener(sideController);
        this.setPreferredSize(new Dimension(300,400));
    }

    /**
     * Draws the panel on the screen
     * @param g Graphics object to draw with
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
