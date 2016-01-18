import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

/**
 * PathFragment Class. A path fragment is a section in the entire path generated
 * by the game panel. Each path fragment contains a rectangle and a direction.
 *
 * @author haolidu
 * @version 1.00
 */
public class PathFragment {
    private int direction;
    protected Rectangle path;
    public static final int HEIGHT = 10;

    /**
     * PathFragment constructor. The height of all the path are defined by the
     * class variable.
     *
     * @param x
     * @param y
     * @param width
     * @param direction
     */
    public PathFragment(int x, int y, int width, int direction) {
        this.direction = direction;
        this.path = new Rectangle(x, y, width, HEIGHT);
    }

    /**
     * Draws this path fragment. Rotations are made with the left side midpoint
     * to be the anchor point.
     *
     * @param g
     */
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g.setColor(Color.RED);
        double radians = Math.toRadians(direction);
        g2D.rotate(radians, path.x, path.y + path.getHeight() / 2);
        g2D.fill(path);
        g2D.rotate(-radians, path.x, path.y + path.getHeight() / 2);
    }

    /**
     * Gets the direction of this path fragment.
     *
     * @return int direction
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Gets the path end point of this path fragment
     *
     * @return double[] containing the top right corner of this path fragment.
     */
    public double[] getPathEnd() {
        double[] pt = new double[2];
        pt[0] = path.getMaxX();
        pt[1] = path.getMinY();

        AffineTransform.getRotateInstance(Math.toRadians(direction), path.x,
                path.y).transform(pt, 0, pt, 0, 1);
        return pt;
    }

    /**
     * Gets the path start point of this path fragment
     *
     * @return double[] containing the top left corner of this path fragment.
     */
    public double[] getPathStart() {
        double[] pt = new double[2];
        pt[0] = path.getMinX();
        pt[1] = path.getMinY();

        return pt;
    }

    /**
     * Gets the width of this path fragment.
     *
     * @return the width in integer
     */
    public int getWidth() {
        return (int) path.getWidth();
    }
}
