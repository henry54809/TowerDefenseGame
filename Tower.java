import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Tower Class. Specifies the things a tower can do.
 *
 * @author haolidu
 * @version 1.00
 */
public abstract class Tower {

    private int attackPower;
    private int attackRadius;
    private int x, y;
    protected int simulAttack;
    protected int attacked;
    protected int cost;
    protected BufferedImage icon;
    protected Circle circle;

    /**
     * Tower constructor.
     *
     * @param x
     *            int position in x-axis
     * @param y
     *            int position in y-axis
     */
    public Tower(int x, int y) {
        this.x = x;
        this.y = y;
        attacked = 0;
    }

    /**
     * Gets the attack power
     *
     * @return int attack power
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Returns the string representation of the tower.
     *
     * @return String.
     */
    public abstract String toString();

    /**
     * Gets the attack radius of the tower.
     *
     * @return attackRadius.
     */
    public int getAttackRadius() {
        return attackRadius;
    }

    /**
     * Reset the number of attacked by the tower.
     *
     * @return none
     */
    protected void resetAttacked() {
        attacked = 0;
    }

    /**
     * Sets the attack power of the tower.
     *
     * @param attackPower
     * @return none
     */
    protected void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    /**
     * Sets the attack radius of the tower
     *
     * @param attackRadius
     * @return none
     */
    protected void setAttackRadius(int attackRadius) {
        this.attackRadius = attackRadius;
    }

    /**
     * Checks if the tower is intersecting with the given enemy
     *
     * @param enemy
     * @return true if this tower is intersecting the enemy.
     */
    public boolean isIntersecting(Enemy enemy) {
        ArrayList<Line2D.Double> lines = enemy.getAllLines();
        for (Line2D line : lines) {
            if (circle.intersects(line) && attacked < simulAttack) {
                attacked++;
                return true;
            }
        }
        return false;
    }

    /**
     * Draws the tower and its attack radius
     *
     * @param g
     *            Graphics object.
     */
    public void draw(Graphics g) {
        if (icon != null) {
            // Paints the circle
            Graphics2D g2D = (Graphics2D) g;
            Color color = new Color(119, 136, 153, 50);
            g2D.setColor(color);
            g2D.fill(circle);

            // Paints rim
            color = new Color(49, 79, 79, 60);
            g2D.setColor(color);
            g2D.setStroke(new BasicStroke(5));
            Circle c = new Circle(circle.x, circle.y, circle.width + 1);
            g2D.draw(c);

            // Paints the icon.
            g2D.drawImage(icon, x - icon.getWidth()
                    / 2, y - icon.getHeight() / 2, null);
        } else {
            System.exit(0);
        }
    }
}
