package main;

import javax.swing.*;
import java.awt.*;

/**
 * Class responsible for holding the code snippet, and perhaps
 * other tutor-related items
 * @author Patrick Whitlock
 */
public class SidePanel extends JPanel {
    JTextArea codeSection;
    /**
     * Creates a SidePanel object
     */
    SidePanel() {

        SidePanelControlHandler sideController = new SidePanelControlHandler(codeSection);

        this.setPreferredSize(new Dimension(300,400));
        codeSection = new JTextArea("//Code section");
        codeSection.setPreferredSize(new Dimension(280,300));
        this.add(codeSection);
        codeSection.addMouseListener(sideController);

        JButton submit = new JButton("Submit");
        submit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(submit);

        submit.addActionListener(sideController);
    }

    /**
     * Draws the panel on the screen
     * @param g Graphics object to draw with
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
