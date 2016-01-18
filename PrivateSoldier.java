import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * PrivateSoldier Class. Extends Enemy class.
 *
 * @author haolidu
 * @version 1.00
 */
public class PrivateSoldier extends Enemy {

    /**
     * Corporal Soldier constructor. It decreases the gamer's life by 1 if it
     * were to beat the towers. It increases the money by 5 if it is killed by
     * the towers. It has a default health. It has a default speed.
     *
     * @param bound
     * @throws IOException
     */
    public PrivateSoldier(Rectangle bound) throws IOException {
        super(bound);
        icon = ImageIO.read(getClass().getResource("private.png"));
        decreaseLife = 1;
        increaseMoney = 5;
    }

}
