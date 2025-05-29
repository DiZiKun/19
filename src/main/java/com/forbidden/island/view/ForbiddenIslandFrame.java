package com.forbidden.island.view;

import com.forbidden.island.controller.ForbiddenIslandGame;
import com.forbidden.island.controller.GameController;
import com.forbidden.island.controller.GameListener;
import com.forbidden.island.utils.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ForbiddenIslandFrame extends JFrame and serves as the top-level container for the game's main window.
 * Responsible for initializing window properties, laying out game panels and operation panels,
 * and displaying the settings dialog when the window opens.
 */
public class ForbiddenIslandFrame extends JFrame {

    /**
     * Constructor sets the window title and initializes window size, position, and layout.
     * @param title Window title
     * @throws HeadlessException Thrown when running in a headless environment
     */
    public ForbiddenIslandFrame(String title) throws HeadlessException {
        super(title);   // Call parent constructor to set title

        // Get screen dimensions for centering the window
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int screenWidth = toolkit.getScreenSize().width;
        int screenHeight = toolkit.getScreenSize().height;
        this.setResizable(false);

        // Set window layout to BorderLayout with 5-pixel horizontal and vertical gaps
        this.setLayout(new BorderLayout(5, 5)); // Component orientation

        // Set window initial position and size for center display
        this.setBounds((screenWidth - Constant.FRAME_WIDTH) / 2,
                (screenHeight - Constant.FRAME_HEIGHT) / 2-20,
                Constant.FRAME_WIDTH,
                Constant.FRAME_HEIGHT);

        // Call initialization method to add panels and listeners
        init();
        setVisible(true);
    }

    /**
     * Initialize window contents:
     * Creates main panel with vertical layout, adds game panel and operation panel,
     * and sets window open listener to display game settings dialog.
     */
    private void init() {
        // 1. Create main container (using vertical Box layout or GridBagLayout)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Vertical arrangement

        // 2. Get game panel and operation panel
        JPanel gamePanel = new GamePanel().getGamePanel();
        Box operateBox = new OperatePanel().getBox();

        // 3. Set panel alignment
        gamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        operateBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 4. Add panels to main container
        mainPanel.add(gamePanel);
        mainPanel.add(operateBox);

        // 5. Add main container to JFrame's center area
        this.add(mainPanel, BorderLayout.CENTER);

        // 6. Add window event listener to show settings dialog when window opens
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                showSettingsDialog();   // Display game settings dialog
            }
        });
    }

    /**
     * Display game settings dialog allowing users to select player count and difficulty.
     * Initializes game or exits program based on user selection.
     */
    private void showSettingsDialog() {
        SettingsDialog dialog = new SettingsDialog(this);
        dialog.setVisible(true);    // Show modal dialog, blocks until closed

        if (dialog.isConfirmed()) {
            // If user confirms settings, get player count and difficulty level
            int players = dialog.getPlayerCount();
            int difficulty = dialog.getDifficulty();

            // Initialize game core logic with player count and water level difficulty
            ForbiddenIslandGame.init(players, difficulty);

            // Initialize controller and listener, set up game interaction logic
            GameController.getInstance();
            new GameListener();
        } else {
            // User cancelled settings, close program
            System.exit(0);
        }
    }
}
