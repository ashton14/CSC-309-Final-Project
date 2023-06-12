package src.main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * The RoundedButtonUI class extends BasicButtonUI and provides a custom UI for rounded buttons.
 * It sets the button to be opaque, creates an empty border, and paints the button with rounded corners.
 * The button is painted with a drop shadow effect when pressed.
 * The size of the button can be customized.
 *
 * The RoundedButtonUI is associated with the AssignmentsView and CourseView classes.
 *
 * @author
 */
public class RoundedButtonUI extends BasicButtonUI {
    private Color shadowColor = new Color(0, 0, 0, 50); // semi-transparent black for drop shadow
    private int width;
    private int height;

    /**
     * Constructs a RoundedButtonUI object with the specified width and height.
     *
     * @param width the width of the button.
     * @param height the height of the button.
     */
    public RoundedButtonUI(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Installs the UI for the button.
     * It sets the button to be opaque and creates an empty border.
     *
     * @param c the button component.
     */
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    /**
     * Paints the button component with rounded corners.
     * The button is painted with a drop shadow effect when pressed.
     *
     * @param g the Graphics object.
     * @param c the button component.
     */
    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x = 0;
        int y = 0;
        int w = b.getWidth();
        int h = b.getHeight();
        int arc = h / 2;
        if (b.getModel().isPressed()) {
            g2.setColor(shadowColor);
            g2.fill(new RoundRectangle2D.Double(x + 2, y + 2, w - 4, h - 4, arc, arc));
            g2.setColor(b.getBackground());
            g2.fill(new RoundRectangle2D.Double(x, y, w - 4, h - 4, arc, arc));
        } else {
            g2.setColor(shadowColor);
            g2.fill(new RoundRectangle2D.Double(x + 2, y + 2, w - 4, h - 4, arc, arc));
            g2.setColor(b.getBackground());
            g2.fill(new RoundRectangle2D.Double(x, y, w - 4, h - 4, arc, arc));
        }
        super.paint(g2, c);
    }

    /**
     * Overrides the paintButtonPressed method to not paint the default button pressed state.
     *
     * @param g the Graphics object.
     * @param b the AbstractButton.
     */
    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        // don't paint the default button pressed state
    }

    /**
     * Returns the preferred size of the button component.
     *
     * @param c the button component.
     * @return the preferred size of the button.
     */
    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(this.width, this.height);
    }
}
