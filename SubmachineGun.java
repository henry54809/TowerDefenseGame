import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Submachine gun class. Submachine gun is a tower. It costs 450, can attack 3
 * enemy at a time and has a diameter of 150 px
 *
 * @author haolidu
 * @version 1.00
 */
public class SubmachineGun extends Tower {

    /**
     * Constructor for Submachine gun.
     *
     * @param x
     * @param y
     * @throws IOException
     */
    public SubmachineGun(int x, int y) throws IOException {
        super(x, y);
        setAttackRadius(150);
        setAttackPower(2);
        icon = ImageIO.read(getClass().getResource("submachine.png"));
        circle = new Circle(x - getAttackRadius() / 2, y - getAttackRadius()
                / 2, getAttackRadius());
        simulAttack = 3;
        cost = 450;
    }

    /**
     * Gets the String representation of the tower.
     *
     * @return String of the tower
     */
    @Override
    public String toString() {
        return "Submachine Gun Tower";
    }

}
