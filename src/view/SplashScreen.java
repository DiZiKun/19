package view;

import com.forbidden.island.view.handler.WelcomeFrame;

import javax.swing.*;

/**
 * SplashScreen class displays a startup screen with fade-out effect.
 * Used to show a logo or image during program startup, with automatic transition to the main window.
 */
public class SplashScreen extends JWindow {

    // Current opacity (initially fully opaque)
    private float opacity = 1.0f;

    // Timer for controlling fade animation
    private Timer fadeTimer;

    /**
     * Constructor: Creates and displays the startup image, then begins fade-out.
     *
     * @param imagePath Path to the startup image
     */
    public SplashScreen(String imagePath) {
        // Use JLabel to display the image
        JLabel imageLabel = new JLabel(new ImageIcon(imagePath));
        getContentPane().add(imageLabel); // Add image to window content pane

        pack(); // Automatically adjust window size to fit image
        setLocationRelativeTo(null); // Center the window

        setOpacity(opacity); // Set initial opacity
        setVisible(true); // Show window

        startFadeOut(); // Start fade-out animation
    }

    /**
     * Start fade-out animation: Decrease opacity every 50ms until invisible, then close.
     */
    private void startFadeOut() {
        fadeTimer = new Timer(50, e -> {
            opacity -= 0.05f;
            if (opacity <= 0f) {
                fadeTimer.stop();
                dispose();
                SwingUtilities.invokeLater(() -> new WelcomeFrame().setVisible(true));
            } else {
                setOpacity(opacity);
            }
        });

        fadeTimer.start();  // Start the timer
    }

    /**
     * Launch the main window (ForbiddenIslandFrame).
     * Called after splash screen fade-out completes.
     */
    private void showMainWindow() {
        ForbiddenIslandFrame frame = new ForbiddenIslandFrame("Forbidden Island");
        frame.setVisible(true); // Show main interface
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close behavior
    }
}
