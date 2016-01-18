import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * CorporalSoldier Class. Extends Enemy class.
 *
 * @author haolidu
 * @version 1.00
 */
public class CorporalSoldier extends Enemy {

    /**
     * Corporal Soldier constructor. It decreases the gamer's life by 10 if it
     * were to beat the towers. It increases the money by 30 if it is killed by
     * the towers. It has a health of 300. It has a speed of 7.
     *
     * @param bound
     * @throws IOException
     */
    public CorporalSoldier(Rectangle bound) throws IOException {
        super(bound);
        icon = ImageIO.read(getClass().getResource("corporal.png"));
        decreaseLife = 10;
        increaseMoney = 30;
        health = 300;
        speed = 7;
    }
}
