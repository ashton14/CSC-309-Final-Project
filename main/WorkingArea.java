package main;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Class responsible for diagram drawing area
 * @author Cameron Hardy
 */
public class WorkingArea extends JPanel implements Observer {
    /**
     * Construct a WorkingArea object
     */
    public WorkingArea() {}

    /**
     * Paints the CodeBlocks and lines to the screen
     * @param g - Graphics object used for drawing
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Repository.getInstance().getCurrentlySelectedCodeBlockOutline().draw(g);
        for(CodeBlock codeBlock: Repository.getInstance().getCodeBlocks()) {
            codeBlock.draw(g);
        }
        for(Line l : Repository.getInstance().getLines()) {
            l.draw(g);
        }
    }

    /**
     * Repaints the WorkingArea whenever notified
     * @param o - Object being observed
     * @param arg - Argument passed through when notified
     */
    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
