import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Sniper class. Sniper is a tower. It costs 1000, can attack 1 enemy at a
 * time and has a diameter of 200 px
 *
 * @author haolidu
 * @version 1.00
 */
public class Sniper extends Tower {

    /**
     * Constructor for Sniper.
     *
     * @param x
     * @param y
     * @throws IOException
     */
    public Sniper(int x, int y) throws IOException {
        super(x, y);
        setAttackRadius(200);
        setAttackPower(3);
        icon = ImageIO.read(getClass().getResource("sniper.png"));
        circle = new Circle(x - getAttackRadius()
                / 2, y - getAttackRadius() / 2, getAttackRadius());
        simulAttack = 1;
        cost = 1000;
    }

    /**
     * Gets the String representation of the tower.
     *
     * @return String of the tower
     */
    @Override
    public String toString() {
        return "Sniper Tower";
    }

}
