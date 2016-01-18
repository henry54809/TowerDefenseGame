import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Enemy Class.Specifies an enemy. Enemy contains the path fragment that it's
 * currently on.
 *
 * @author haolidu
 * @version 1.00
 */
public abstract class Enemy {

    protected double speed;
    protected double direction;
    protected int x, y;
    protected boolean outOfBound;
    protected boolean dead;
    protected PathFragment currentPathFragment;
    protected Rectangle bound;
    protected BufferedImage icon;
    protected int timeToNext;
    protected int decreaseLife;
    protected int health;
    protected int increaseMoney;

    /**
     * Enemy constructor.
     *
     * @param bound
     */
    public Enemy(Rectangle bound) {
        this.bound = bound;
        currentPathFragment = TowerDefenseGame.getGamePanel().getPath().get(0);

        // Sets the initial parameters of the enemy
        double[] startPt = currentPathFragment.getPathStart();
        x = (int) startPt[0];
        y = (int) startPt[1];
        speed = 5;
        direction = currentPathFragment.getDirection();
        timeToNext = (int) ((currentPathFragment.getWidth()) / speed);
        dead = false;
        outOfBound = false;
        health = 100;
    }

    /**
     * Be attacked. Iterates through all the towers to see if any of the towers
     * and intersecting this enemy.
     *
     * @return none.
     */
    public void beAttacked() {
        List<Tower> towers = TowerDefenseGame.getGamePanel().getTowers();
        for (Tower tower : towers) {
            if (tower.isIntersecting(this)) {
                health -= tower.getAttackPower();
            }
        }
    }

    /**
     * Draws the Enemy and the health status of the enemy.
     *
     * @param g
     */
    public void draw(Graphics g) {
        if (icon != null) {
            Graphics2D g2D = (Graphics2D) g;
            g.setColor(Color.RED);
            double radians = Math.toRadians(direction);

            // The y position is adjusted so that the enemy is aligned with the
            // track
            int adjY = y - icon.getHeight() * 3 / 4;
            int adjX = x - icon.getWidth() * 1 / 4;
            g2D.rotate(radians, x, y);
            g2D.drawImage(icon, adjX, adjY, null);
            g2D.rotate(-radians, x, y);
            g2D.setColor(Color.BLACK);
            g2D.drawString("Health: " + health, x, y - 30);
            /*
             * // For debug purposes. Draws the lines that surrounds determines
             * if the enenmy is intersecting towers. for (Line2D
             * line:getAllLines()){ g2D.draw(line); }
             */
        } else {
            System.exit(0);
        }
    }

    /**
     * Gets the lines that bounds the enemy.
     *
     * @return ArrayList<Line2D.Double> containing 4 lines.
     */
    public ArrayList<Line2D.Double> getAllLines() {
        double[][] pts = getAllCorners();
        ArrayList<Line2D.Double> lines = new ArrayList<Line2D.Double>(4);

        // Line1 pt1-->pt2
       lines.add(new Line2D.Double(pts[0][0], pts[0][1], pts[1][0], pts[1][1]));

        // Line2 pt1-->pt3
       lines.add(new Line2D.Double(pts[0][0], pts[0][1], pts[2][0], pts[2][1]));

        // Line3 pt2-->pt4
       lines.add(new Line2D.Double(pts[1][0], pts[1][1], pts[3][0], pts[3][1]));

        // Line4 pt3-->pt4
       lines.add(new Line2D.Double(pts[2][0], pts[2][1], pts[3][0], pts[3][1]));

        return lines;
    }

    /**
     * Return if the enemy is dead.
     *
     * @return true if the health is lower than 0
     */
    public boolean isDead() {
        return (health <= 0);
    }

    /**
     * Checks if the position is in boundary
     *
     * @param x
     * @param y
     * @return true if the position is in boundary
     */
    public boolean isInBoundary(int x, int y) {
        double maxX = bound.getMaxX() - this.icon.getWidth();
        double maxY = bound.getMaxY() - this.icon.getHeight();
        double minX = bound.getMinX();
        double minY = bound.getMinY();
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    /**
     * Return if the enemy is out of bound.
     *
     * @return true if the enemy is out of bound
     */
    public boolean isOutOfBound() {
        return outOfBound;
    }

    /**
     * Move the enemy.
     *
     * @return none
     */
    public void move() {
        if (timeToNext > 0) {
            double directionRad = Math.toRadians(direction);
            x = x + (int) (Math.cos(directionRad) * speed);
            y = y + (int) (Math.sin(directionRad) * speed);
            timeToNext--;
        } else {
            List<PathFragment> paths = TowerDefenseGame
                    .getGamePanel().getPath();
            int index = paths.indexOf(currentPathFragment) + 1;
            if (paths.size() > index) {
                currentPathFragment = paths.get(index);
                reset();
            } else {
                outOfBound = true; // If it is out of bound
            }
        }
    }

    /**
     * Helper method for getting the lines that bound the enemy. Gets all the
     * corners of the enemies.
     *
     * @return double[4][2] that contains all 4 corners.
     */
    private double[][] getAllCorners() {
        double[][] pts = new double[4][2];

        // Top left corner
        pts[0][0] = x;
        pts[0][1] = y;

        // Bottom left corner
        double[] pt = {x, y + icon.getHeight() / 2};
        pt = rotatePoint(pt);
        pts[1][0] = pt[0];
        pts[1][1] = pt[1];

        // Top right corner
        double[] pt2 = {x + icon.getWidth(), y};
        pt = rotatePoint(pt2);
        pts[2][0] = pt2[0];
        pts[2][1] = pt2[1];

        // Bottom right corner
        double[] pt3 = {x + icon.getWidth(), y + icon.getHeight() / 2};
        pt = rotatePoint(pt3);
        pts[3][0] = pt3[0];
        pts[3][1] = pt3[1];

        return pts;
    }

    /**
     * Resets the enemie's position after it has reached the end of its current
     * path fragment.
     *
     * @return none
     */
    private void reset() {
        timeToNext = (int) (currentPathFragment.getWidth() / speed);
        direction = currentPathFragment.getDirection();
        double[] startPt = currentPathFragment.getPathStart();
        x = (int) startPt[0];
        y = (int) startPt[1];
    }

    /**
     * Helper method. Rotates a given point by this path's direction.
     *
     * @param pt
     *            double[] a 2D point. X position specified in the first
     *            position.
     * @return double[] with the rotated point
     */
    private double[] rotatePoint(double[] pt) {
        AffineTransform.getRotateInstance(Math.toRadians(direction), x, y)
                .transform(pt, 0, pt, 0, 1);
        return pt;
    }
}
