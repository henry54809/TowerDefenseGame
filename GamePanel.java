import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * GamePanel class. Creates the main panel for the TowerDefenseGame. This
 * contains all the crucial data of the game
 *
 * @author haolidu
 * @version 1.00
 */
public class GamePanel extends JPanel {

    private static final long serialVersionUID = -5083465984960174956L;
    private Timer timer;
    private int life; //
    private int money;
    private List<Enemy> enemies;
    private List<Tower> towers;
    private List<PathFragment> paths;
    private static final int WIDTH = TowerDefenseGame.WINDOW_WIDTH - 150;
    private static final int HEIGHT = TowerDefenseGame.WINDOW_HEIGHT;

    /**
     * Constructor for the GamePanel.
     */
    public GamePanel() {
        super();
        setBackground(Color.white);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Initialize variables
        paths = new ArrayList<PathFragment>(40);
        enemies = new LinkedList<Enemy>();
        towers = new ArrayList<Tower>();
        life = 100; // Game is over when it reaches 0.
        money = 500; // can be used to buy towers

        // Configure Path for the game. 1.03 makes the target length
        // (horizontal) of the path a bit longer to cover the width of the
        // panel.
        randomPath((int) (WIDTH * 1.03), null);
        addMouseListener(new ClickListener());
        timer = new Timer(80, new TimerListener());
        timer.start();
    }

    /**
     * Gets the list of enemies
     *
     * @return List<Enemy> a list that contains all the enemies
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Gets the life of the game
     *
     * @return int
     */
    public int getLife() {
        return life;
    }

    /**
     * Gets the money of the game
     *
     * @return int
     */
    public int getMoney() {
        return money;
    }

    /**
     * Gets the list of paths for the enemies to follow.
     *
     * @return List<PathFragment> a list that contains all the pathFragments of
     *         this game
     */
    public List<PathFragment> getPath() {
        return paths;
    }

    /**
     * Gets the timer of the game
     *
     * @return Timer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Gets the towers of the game
     *
     * @return List<Towers> a list of all the towers
     */
    public List<Tower> getTowers() {
        return towers;
    }

    /**
     * Paints all the components on the game map. Towers are painted first, then
     * the path, then all the enemies.
     *
     * @return none.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws all the towers
        for (Tower tower : towers) {
            tower.draw(g);
        }

        // Draws the path
        for (PathFragment fragment : paths) {
            fragment.draw(g);
        }

        // Draws all the enemies
        for (Enemy enermy : enemies) {
            enermy.draw(g);
        }
    }

    /**
     * Creates a new path for the game. Packages repaint() at the end
     *
     * @return none
     */
    protected void newPath() {
        paths = new ArrayList<PathFragment>(40);
        randomPath((int) (WIDTH * 1.03), null);
        repaint();
    }

    /**
     * Attacks all the enemies.
     *
     * @return none
     */
    private void attackEnemy() {
        for (Enemy enemy : enemies) {
            enemy.beAttacked();
        }
    }

    /**
     * Checks the status, out of bound, dead of all the enemies. Also checks if
     * the game is over.
     *
     * @return none.
     */
    private void checkStatus() {
        for (Enemy enemy : enemies) {
            if (enemy.isOutOfBound()) {
                life -= enemy.decreaseLife;
                if (life < 0) {
                    life = 0;
                }
                // Refreshes the control panel to reflect changes in numbers
                TowerDefenseGame.getControlPanel().refresh();
            } else if (enemy.isDead()) {
                money += enemy.increaseMoney;
                TowerDefenseGame.getControlPanel().refresh();
            }

            if (life <= 0) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over",
                        "Gave Over", JOptionPane.PLAIN_MESSAGE);
                System.exit(0);
            }
        }
    }

    /**
     * Helper method for path generation. Checks if the particular path fragment
     * is in bound.
     *
     * @param pt
     *            the end point of the path fragment.
     * @return true for in bound, false otherwise.
     */
    private boolean isInBound(double[] pt) {
        return (pt[0] >= 0 && pt[1] >= 0 && pt[1] <= HEIGHT);
    }

    /**
     * Moves all the enemies.
     *
     * @return none.
     */
    private void moveAll() {
        for (Enemy enermy : enemies) {
            enermy.move();
        }
    }

    /**
     * Helper method for generating random numbers. Generates a random number in
     * int between 0 and the limit given
     *
     * @param limit
     * @return int
     */
    private int random(int limit) {
        return (int) (Math.random() * limit);
    }

    /**
     * Helper method for generating the path for the game Generates a random
     * direction in int that are multiples of 45 degrees.
     *
     * @return int
     */
    private int randomDirection() {

        /*
         * Random directions between 270-360 and 0-90. This creates a path
         * that's visually appalling.
         *
         * if (Math.random() < 0.5) { return 270 + random(90); } else { return
         * random(120); }
         */

        // Generates angles that are multiples of 45 degrees.
        double random = Math.random();
        if (random < 0.125) {
            return 0;
        } else if (random < 0.25 && random >= 0.125) {
            return 45;
        } else if (random < 0.375 && random >= 0.25) {
            return 90;
        } else if (random < 0.5 && random >= 0.375) {
            return 135;
        } else if (random < 0.75 && random >= 0.625) {
            return 225;
        } else if (random < 0.825 && random >= 0.7) {
            return 270;
        } else {
            return 315;
        }
    }

    /**
     * Initialize the list of path fragments. It is able to randomly generate a
     * game path. It recursively calls itself to add new path fragment to the
     * game path until it is long enough to cover the width of the panel.
     *
     * @param int targetLength: the horizontal distance for the path to cover.
     *        PathFragment previousFragment, null if is the first path fragment
     * @return none.
     */
    private void randomPath(int targetLength, PathFragment previousFragment) {
        int width = 80 + (int) (Math.random() * 100); // A random width for each
                                                      // path fragment
        if (previousFragment == null) { // First path fragment
            // Randomly choose a starting point on the left side of the panel
            // and direction
            int y = random(TowerDefenseGame.WINDOW_HEIGHT);
            int direction = random(70);
            PathFragment newFragment = new PathFragment(0, y, width, direction);

            // Check if the new path is in bound
            while (!isInBound(newFragment.getPathEnd())) {
                direction = random(70);
                newFragment = new PathFragment(0, y, width, direction);
            }
            paths.add(newFragment);
            randomPath(targetLength - (int)
                    (Math.cos(Math.toRadians(direction)) * width), newFragment);
        } else if (targetLength > 0) {
            int direction = randomDirection();
            PathFragment newFragment;
            double[] pt = previousFragment.getPathEnd();
            newFragment = new PathFragment((int) pt[0],
                    (int) pt[1], width, direction);
            while (!isInBound(newFragment.getPathEnd())
                    || direction == previousFragment.getDirection()
                    || direction == previousFragment.getDirection() - 180
                    || direction == previousFragment.getDirection() + 180) {
                direction = randomDirection();
                newFragment = new PathFragment((int)
                        pt[0], (int) pt[1], width, direction);
            }
            paths.add(newFragment);
            randomPath(targetLength - (int)
                    (Math.cos(Math.toRadians(direction)) * width), newFragment);
        }
    }

    /**
     * Remove enemies that are either out of bound or dead. Also checks if this
     * wave is defeated. If it is, starts a new wave and adds 25 to the money
     *
     * @return none
     */
    private void remove() {
        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            if (enemy.isDead() || enemy.isOutOfBound()) {
                iterator.remove();
            }

            if (enemies.isEmpty()) {
                money += 25;
                TowerDefenseGame.getControlPanel().newWave();
            }
        }
    }

    /**
     * Resets number of enemies that the tower has attacked after each
     * iteration.
     *
     * @return none.
     */
    private void resetTower() {
        for (Tower tower : towers) {
            tower.resetAttacked();
        }
    }

    /**
     * Click listener. Responds to clicking on the game panel
     *
     * @author haolidu
     * @version 1.00
     */
    private class ClickListener extends MouseAdapter {

        /**
         * Instantiating a Tower given a String of the class name and the point
         * where it should be place.
         *
         * @param Point
         *            p is where the tower will be placed.
         * @param String
         *            className for the tower
         * @return Tower
         */
        public Tower instantiateTower(String className, Point p) {
            try {
                Class<?> cl = Class.forName(className);
                return (Tower) (cl.getDeclaredConstructor(int.class,
                        int.class).newInstance(p.x, p.y));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (InstantiationException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                System.exit(1);
            }
            return null;
        }

        /**
         * Responds to mouse clicking on the panel
         *
         * @return none
         */
        public void mousePressed(MouseEvent e) {
            // Get the Tower properties
            String towerType = TowerDefenseGame
                    .getControlPanel().getTowerType();
            Point p = e.getPoint();

            // Create the corresponding tower if there's enough money.
            Tower tower = instantiateTower(towerType, p);
            if (money >= tower.cost) {
                money -= tower.cost;
                towers.add(tower);
                TowerDefenseGame.getControlPanel().refresh();
            }
            repaint();
        }
    }

    /**
     * TimerListener. Responds to the timer.
     *
     * @author haolidu
     * @version 1.00
     */
    private class TimerListener implements ActionListener, Runnable {
        /**
         * Responds to timer trigger. Creates a new Thread each time timer
         * triggers.
         */
        public void actionPerformed(ActionEvent e) {
            (new Thread(new TimerListener())).start();
        }

        /**
         * Runs the thread. Contains the sequence of events that happens each
         * time timer triggers
         */
        @Override
        public void run() {
            synchronized (this) {
                moveAll();
                attackEnemy();
                checkStatus();
                remove();
                resetTower();
                repaint();
            }
        }
    }
}
