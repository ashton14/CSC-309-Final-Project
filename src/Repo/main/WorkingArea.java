package Repo.main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
        StateRepository stateRepository = StateRepository.getInstance();
        StateData stateData = (StateData) stateRepository.getData();

        Repository dataRepository = DataRepository.getInstance();
        DrawableData drawableData = (DrawableData) dataRepository.getData();
        drawableData.addObserver(this);

        ArrayList<CodeBlock> codeBlocks = drawableData.getCodeBlocks();
        CodeBlock currentlySelectedBlock = stateData.getCurrentlySelectedCodeBlock();
        ArrayList<Line> lines = drawableData.getLines();

        for (CodeBlock currentCodeBlock : codeBlocks) {
            if (currentlySelectedBlock == currentCodeBlock) {
                Shape codeBlockOutline = stateData.getCurrentlySelectedCodeBlockOutline();
                codeBlockOutline.draw(g);
            }
            currentCodeBlock.draw(g);
        }

        for (Line line : lines) {
            line.draw(g);
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
