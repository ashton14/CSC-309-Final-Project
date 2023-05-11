package src.main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class responsible for holding the code snippet, and perhaps
 * other tutor-related items
 * @author Patrick Whitlock
 */
public class SidePanel extends JPanel {
    private ArrayList<JTextArea> codeSections;
    private ArrayList<JButton> buttons;
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

        //thanks Aaron
        buttons = new ArrayList<>();
        JPanel buttonPanel = new JPanel();
        GridLayout buttonLayout = new GridLayout(1,4);
        buttonPanel.setLayout(buttonLayout);
        String[] buttonStrings = {"Submit", "Help", "Previous", "Next"};
        for (String buttonString : buttonStrings) {
            JButton jButton = new JButton(buttonString);
            jButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            jButton.addActionListener(sideController);
            buttonPanel.add(jButton);
            buttons.add(jButton);
        }
        this.add(buttonPanel,BorderLayout.SOUTH);
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
