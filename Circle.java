import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Line2D;

/**
 * Circle class. Extends the Arc2D.Double class. Has the new method of checking
 * if a line is intersecting a circle.
 *
 * @author haolidu
 * @version 1.00
 */
public class Circle extends Double {

    private static final long serialVersionUID = 5075382594155069106L;

    /**
     * Circle constructor.
     *
     * @param x
     *            double position in x-axis
     * @param y
     *            double position in y-axis
     * @param d
     *            double diameter
     */
    public Circle(double x, double y, double d) {
        super(x, y, d, d, 0, 360, Arc2D.CHORD);
    }

    /**
     * Checks if the given line is intersecting this circle.
     *
     * @param line
     *            Line2D object
     * @return ture if the circle is intersecting the line, false otherwise.
     */
    public boolean intersects(Line2D line) {
        double r = width / 2;
        if (line.ptSegDist(x + r, y + r) <= r) {
            return true;
        }
        return false;
    }
}
