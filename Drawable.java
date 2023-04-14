import java.awt.*;
import java.io.Serializable;

/**
 * Interface to signify that a Class supports
 * the ability to draw an object representation
 * of itself onto the screen.
 *
 * @author Aaron Bettencourt
 */
public interface Drawable {

    /**
     * Draws a representation of this Drawable onto Graphics g.
     * @param g   An instance of type Graphics to draw a representation
     *            of this drawable onto.
     */
    public void draw(Graphics g);
}
