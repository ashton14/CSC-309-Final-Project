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
        this.setPreferredSize(new Dimension(300,400));
        codeSection = new JTextArea("//Code section");
        codeSection.setPreferredSize(new Dimension(280,300));
        this.add(codeSection);
    }

    /**
     * Draws the panel on the screen
     * @param g Graphics object to draw with
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
