import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * ControlPanel class. contains all the buttons for game controls.
 *
 * @author haolidu
 * @version 1.00
 */
public class ControlPanel extends JPanel {

    public static final int PRIVATE_SOLDIER = 0;
    public static final int PRIVATE_FIRST_CLASS_SOLDIER = 1;
    public static final int CORPORAL_SOLDIER = 2;

    private static final long serialVersionUID = 3503469091375541801L;
    private JLabel money, life, waveL, towerInfo;
    private JButton startPause, slingShot, pistol, sniper, submachine, machine;
    private String towerType;
    private boolean initialized;
    private int wave;

    /**
     * ControlPanel constructor. Sets up, and place the buttons in the right
     * order.
     */
    public ControlPanel() {
        super();
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(150, TowerDefenseGame.WINDOW_HEIGHT));
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        initializeButtons();

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.4;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 30;
        c.ipady = 10;
        add(slingShot, c);

        c.gridy++;
        add(pistol, c);

        c.gridy++;
        add(sniper, c);

        c.gridy++;
        add(submachine, c);

        c.gridy++;
        add(machine, c);

        // Labels
        towerInfo = new JLabel("", JLabel.CENTER);
        towerType = "SlingShot";

        money = new JLabel("Money: " + TowerDefenseGame
                .getGamePanel().getMoney(), JLabel.CENTER);
        life = new JLabel("Lives: " + TowerDefenseGame.getGamePanel().getLife(),
                JLabel.CENTER);
        wave = 1;
        waveL = new JLabel("Wave: " + wave, JLabel.CENTER);
        Font font = new Font("Verdana", Font.BOLD, 20);
        waveL.setFont(font);

        c.gridy++;
        add(towerInfo, c);

        // Place holder
        for (int i = 0; i < 20; i++) {
            c.gridy++;
            add(new JLabel(), c);
        }

        c.gridy++;
        add(waveL, c);

        c.gridy++;
        add(money, c);

        c.gridy++;
        add(life, c);

        // Start Buttons
        startPause = new JButton("Start");
        startPause.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!initialized) {
                    initialized = true;
                    startPause.setText("Pause");
                    waveStart(wave);
                } else {
                    if (startPause.getText().equals("Pause")) {
                        TowerDefenseGame.getGamePanel().getTimer().stop();
                        startPause.setText("Resume");
                    } else {
                        TowerDefenseGame.getGamePanel().getTimer().start();
                        if (startPause.getText().equals("New Wave")) {
                            waveStart(wave);
                        }
                        startPause.setText("Pause");
                    }
                }
            }
        });
        c.gridy++;
        c.insets = new Insets(20, 0, 0, 0);
        c.fill = GridBagConstraints.VERTICAL;
        add(startPause, c);
    }

    /**
     * Helper method to initialize all the buttons.
     *
     * @return none.
     */
    private void initializeButtons() {
        // Towers
        slingShot = new JButton("Sling Shot");
        slingShot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                towerType = "SlingShot";
                try {
                    setTowerInfo();
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        pistol = new JButton("Pistol");
        pistol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                towerType = "Pistol";
                try {
                    setTowerInfo();
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        sniper = new JButton("Sniper");
        sniper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                towerType = "Sniper";
                try {
                    setTowerInfo();
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        submachine = new JButton("Submachine Gun");
        submachine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                towerType = "SubmachineGun";
                try {
                    setTowerInfo();
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        machine = new JButton("Machine Gun");
        machine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                towerType = "MachineGun";
                try {
                    setTowerInfo();
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
    }

    /**
     * Helper method to sets up and show the tower information. Creates an
     * instance of the particular tower and gets the tower's information.
     *
     * @throws ClassNotFoundException
     * @return none.
     */
    private void setTowerInfo() throws ClassNotFoundException {
        Class<?> cl = Class.forName(towerType);
        try {
            Tower tower = (Tower) (cl.getDeclaredConstructor(int.class,
                    int.class).newInstance(0, 0));
            towerInfo.setText("Cost: " + tower.cost + " Power: "
                    + tower.getAttackPower());
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Defines the properties of a new wave. Creates a new thread for enemy
     * addition for each wave.
     *
     * @param wave
     *            in integer
     * @return none
     */
    public void waveStart(int wave) {
        switch (wave) {
        case 1:
            (new Thread(new EnemyAddition())).start();
            break;
        case 2:
            (new Thread(new EnemyAddition(1000, wave * 5))).start();
            break;
        case 3:
            (new Thread(new EnemyAddition(900, wave * 5, wave * 2))).start();
            break;
        case 4:
            (new Thread(new EnemyAddition(800, wave * 6, wave * 2))).start();
            break;
        case 5:
            (new Thread(new EnemyAddition(800, wave * wave, wave * 2))).start();
            break;
        case 6:
           (new Thread(new EnemyAddition(700, wave * wave, wave * 5, wave * 2)))
                    .start();
            break;
        case 7:
            JOptionPane.showMessageDialog(this, "A new path is given!");
            TowerDefenseGame.getGamePanel().newPath();
            (new Thread(new EnemyAddition(700, wave * wave, wave * 5))).start();
            break;
        case 8:
            (new Thread(
                    new EnemyAddition(600, wave * wave, wave * 10, wave * 2)))
                    .start();
            break;
        default:
            (new Thread(
                    new EnemyAddition(300, wave * wave, wave * 10, wave * 5)))
                    .start();
        }
    }

    /**
     * Returns the tower type that the user have clicked on. Default is
     * slingshot.
     *
     * @return String towerType
     */
    public String getTowerType() {
        return towerType;
    }

    /**
     * Refreshes the game properties(Money and lives).
     *
     * @return none.
     */
    public void refresh() {
        money.setText("Money: " + TowerDefenseGame.getGamePanel().getMoney());
        life.setText("Lives: " + TowerDefenseGame.getGamePanel().getLife());
    }

    /**
     * Setup for a new wave. This stops the timer.
     *
     * @return none.
     */
    public void newWave() {
        TowerDefenseGame.getGamePanel().getTimer().stop();
        startPause.setText("New Wave");
        wave++;
        waveL.setText("Wave: " + wave);
        refresh();
    }

    /**
     * Adds an enemy to the enemy array. The type of enemy added is specified by
     * the parameter int.
     *
     * @param int enemytype
     * @return none
     */
    public void addEnemy(int i) {
        List<Enemy> enemies = TowerDefenseGame.getGamePanel().getEnemies();
        try {
            switch (i) {
            case 0:
                enemies.add(new PrivateSoldier(TowerDefenseGame.getGamePanel()
                        .getBounds()));
                break;
            case 1:
                enemies.add(new PrivateFirstClassSoldier(
                        TowerDefenseGame.getGamePanel().getBounds()));
                break;
            case 2:
                enemies.add(new CorporalSoldier(TowerDefenseGame.getGamePanel()
                        .getBounds()));
                break;
            default:
                enemies.add(new PrivateSoldier(TowerDefenseGame.getGamePanel()
                        .getBounds()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * EnemyAddition Class: A runnable class that dynamically adds the enemies
     * during the wave. The type of enemies and the type of enemies and the
     * interval of addition can be specified. Default enemy is 10 privateSoldier
     * added at an interval of 1000ms.
     *
     * @author haolidu
     * @version 1.00
     */
    private class EnemyAddition implements Runnable {
        private int interval;
        private int[] numEnemyWave;

        /**
         * EnemyAddition default constructor. 10 privateSoldiers and 1000ms
         * interval.
         */
        public EnemyAddition() {
            interval = 1000;
            numEnemyWave = new int[2];
            numEnemyWave[0] = 2;
        }

        /**
         * EnemyAddition constructor. numEnemyWave can be any number of paired
         * integers. The first number in the pair is the enenmyType and the
         * second number is the number of enemies of that type.
         *
         * @param interval
         * @param int... numEnemyWave
         */
        public EnemyAddition(int interval, int... numEnemyWave) {
            this.interval = interval;
            this.numEnemyWave = numEnemyWave;
        }

        /**
         * Starts the thread for adding enemies.
         *
         * @return void
         */
        @Override
        public void run() {
         for (int enemyType = numEnemyWave.length; enemyType > 0; enemyType--) {
                for (int j = 0; j < numEnemyWave[enemyType - 1]; j++) {
                    // Only allow one thread to access the enemies list
                    synchronized (this) {
                        addEnemy(enemyType - 1);
                    }
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
