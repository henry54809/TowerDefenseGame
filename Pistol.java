import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Pistol class. Pistol is a tower. It costs 100, can attack 1 enemy at a time
 * and has a diameter of 50 px
 *
 * @author haolidu
 * @version 1.00
 */
public class Pistol extends Tower {

    /**
     * Constructor for slingshot.
     *
     * @param x
     * @param y
     * @throws IOException
     */
    public Pistol(int x, int y) throws IOException {
        super(x, y);
        setAttackRadius(100);
        setAttackPower(2);
        icon = ImageIO.read(getClass().getResource("pistol.png"));
        circle = new Circle(x - getAttackRadius() / 2, y - getAttackRadius()
                / 2, getAttackRadius());
        simulAttack = 1;
        cost = 100;
    }

    /**
     * Gets the String representation of the tower.
     *
     * @return String of the tower
     */
    @Override
    public String toString() {
        return "Pistol Tower";
    }

}
