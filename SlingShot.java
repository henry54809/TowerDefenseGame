import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * SlingShot class. sling shot is a tower. It costs 50, can attack 1 enemy at a
 * time and has a diameter of 60 px
 *
 * @author haolidu
 * @version 1.00
 */
public class SlingShot extends Tower {

    /**
     * Constructor for slingshot.
     *
     * @param x
     * @param y
     * @throws IOException
     */
    public SlingShot(int x, int y) throws IOException {
        super(x, y);
        setAttackRadius(60);
        setAttackPower(1);
        icon = ImageIO.read(getClass().getResource("slingshot.png"));
        circle = new Circle(x - getAttackRadius() / 2, y - getAttackRadius()
                / 2, getAttackRadius());
        simulAttack = 1;
        cost = 50;
    }

    /**
     * Gets the String representation of the tower.
     *
     * @return String of the tower
     */
    @Override
    public String toString() {
        return "Sling Shot Tower";
    }
}
