package src.main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * @author Connor Hickey
 */
public class RoundedButtonUI extends BasicButtonUI {
    private Color shadowColor = new Color(0, 0, 0, 50); // semi-transparent black for drop shadow
    private int width;
    private int height;

    public RoundedButtonUI(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

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

    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        // don't paint the default button pressed state
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(this.width, this.height);
    }
}
