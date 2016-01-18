import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * MachineGun class. sling shot is a tower. It costs 900, can attack 5 enemy at
 * a time and has a diameter of 180 px
 *
 * @author haolidu
 * @version 1.00
 */
public class MachineGun extends Tower {

    /**
     * Constructor for Machine guns.
     *
     * @param x
     * @param y
     * @throws IOException
     */
    public MachineGun(int x, int y) throws IOException {
        super(x, y);
        setAttackRadius(180);
        setAttackPower(3);
        icon = ImageIO.read(getClass().getResource("machine.png"));
        circle = new Circle(x - getAttackRadius() / 2, y - getAttackRadius()
                / 2, getAttackRadius());
        simulAttack = 5;
        cost = 900;
    }

    /**
     * Gets the String representation of the tower.
     *
     * @return String of the tower
     */
    @Override
    public String toString() {
        return "Machine Gun";
    }

}
