import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * TowerDefenseGame Class. Contains the public static game panel and control
 * panel.
 *
 * @author haolidu
 * @version 1.00
 */
public class TowerDefenseGame extends JFrame {

    private static final long serialVersionUID = 7802547810493971167L;
    public static final int WINDOW_WIDTH = 800 + 150, WINDOW_HEIGHT = 600;
    private static GamePanel gamePanel;
    private static ControlPanel cPanel;

    /**
     * Returns the control panel
     *
     * @return ControlPanel
     */
    public static ControlPanel getControlPanel() {
        return cPanel;
    }

    /**
     * Returns the game panel
     *
     * @return the game panel
     */
    public static GamePanel getGamePanel() {
        return gamePanel;
    }

    public static void main(String[] args) {
        new TowerDefenseGame("Tower Defense Game");
    }

    /**
     * TowerDefenseGame Constructor. Sets up the properties of the frame.
     */
    public TowerDefenseGame(String name) {
        super(name);

        // Adds menu bar.
        setJMenuBar(setupMenuBar());

        // Set Window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new GamePanel();
        add(gamePanel);
        cPanel = new ControlPanel();
        add(cPanel, BorderLayout.WEST);
        pack();
        setVisible(true);
    }

    /**
     * Setups the menu bar for the frame.
     *
     * @return JMenuBar
     */
    private JMenuBar setupMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu system = new JMenu("System");
        // item 1:
        JMenuItem path = new JMenuItem("New Path");

        path.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.newPath();
            }
        });

        JMenuItem fastF = new JMenuItem("Fast Forward x2");
        fastF.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.getTimer().setDelay(40);
            }
        });

        JMenuItem fastF2 = new JMenuItem("Fast Forward x4");
        fastF2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.getTimer().setDelay(20);
            }
        });

        JMenuItem regularV = new JMenuItem("Regular Speed");
        regularV.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.getTimer().setDelay(80);
            }
        });

        system.add(path);
        system.add(fastF);
        system.add(fastF2);
        system.add(regularV);
        menuBar.add(system);
        return menuBar;
    }
}
