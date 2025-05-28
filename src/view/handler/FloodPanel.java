package view.handler;

import com.forbidden.island.utils.Constant;
import com.forbidden.island.utils.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * FloodPanel class is responsible for creating and managing the graphical interface components
 * of the flood card area in the game, including displaying the flood discard pile,
 * flood deck back image, and current flood card buttons.
 */
public class FloodPanel {

    /**
     * Static list storing currently displayed flood card buttons,
     * used to show, hide, or update corresponding flood cards during gameplay.
     */
    public static ArrayList<JButton> floodCards;

    /**
     * JPanel container for holding all flood-related components (labels, buttons, etc.).
     */
    private final JPanel floodPanel;

    /**
     * FloodPanel constructor, initializes interface components:
     * Creates an 8x1 grid layout panel, adds discard pile and deck back labels,
     * and creates 6 initially invisible flood card buttons for dynamic display and interaction.
     */
    public FloodPanel() {
        // Create a grid layout panel, 8 rows, 1 column, with 1 horizontal and 3 vertical spacing
        floodPanel = new JPanel(new GridLayout(8, 1, 1, 3));

        // Create label for "Flood Discard Pile"
        JLabel pile = new JLabel();
        // Set label size using predefined constant width and height
        Dimension floodCardSize = new Dimension(Constant.FLOOD_WIDTH, Constant.FLOOD_HEIGHT);
        pile.setPreferredSize(floodCardSize);
        // Set label icon using ImageUtil to load and resize image
        pile.setIcon(new ImageIcon(ImageUtil.getImage("/Back/Flood Discard.png", Constant.FLOOD_WIDTH, Constant.FLOOD_HEIGHT)));
        floodPanel.add(pile); // Add label to panel

        // Create label for "Flood Deck Back"
        JLabel back = new JLabel();
        back.setPreferredSize(floodCardSize);
        // Load back image and rotate 90 degrees (parameter 90d indicates rotation angle)
        back.setIcon(new ImageIcon(Objects.requireNonNull(
                ImageUtil.getImage("/Back/Flood Deck.png", Constant.FLOOD_WIDTH, Constant.FLOOD_HEIGHT, 90d))));
        floodPanel.add(back); // Add to panel

        // Initialize list for flood card buttons
        floodCards = new ArrayList<>();

        // Create 6 flood card buttons for displaying current flood cards
        for (int i = 0; i < 6; i++) {
            JButton button = new JButton();
            button.setPreferredSize(floodCardSize); // Set button size
            button.setVisible(false); // Initially hidden, waiting for game logic to activate display
            floodCards.add(button);
            floodPanel.add(button); // Add button to panel
        }
    }

    /**
     * Get the FloodPanel's JPanel container for external use and addition to main interface.
     * @return floodPanel component container
     */
    public JPanel getFloodPanel() {
        return floodPanel;
    }
}
