import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * PrivateFirstClassSoldier Class. Extends Enemy class.
 *
 * @author haolidu
 * @version 1.00
 */
public class PrivateFirstClassSoldier extends Enemy {

    /**
     * Corporal Soldier constructor. It decreases the gamer's life by 3 if it
     * were to beat the towers. It increases the money by 15 if it is killed by
     * the towers. It has a health of 150. It has a speed of 6.
     *
     * @param bound
     * @throws IOException
     */
    public PrivateFirstClassSoldier(Rectangle bound) throws IOException {
        super(bound);
        icon = ImageIO.read(getClass().getResource("privateFirstClass.png"));
        decreaseLife = 3;
        increaseMoney = 15;
        health = 150;
        speed = 6;
    }
}
