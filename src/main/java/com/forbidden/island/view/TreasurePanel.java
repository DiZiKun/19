package com.forbidden.island.view;

import com.forbidden.island.utils.ImageUtil;
import com.forbidden.island.utils.Constant;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * TreasurePanel class is used to display treasure cards and water meter panel in the game.
 */
public class TreasurePanel {
    // Static label: used to display water meter (accessible externally)
    public static JLabel waterMeter = new JLabel();

    // Static button list: used to display currently drawn treasure cards (maximum 2)
    public static ArrayList<JButton> treasureCards;

    // Main panel, encapsulating all UI components
    private final JPanel treasurePanel;

    /**
     * Constructor, initializes panel layout and UI components.
     * Includes two treasure card slots, a discard pile icon, a draw pile icon, and water meter.
     */
    public TreasurePanel() {
        // Set main panel to BorderLayout: cards pile at top, water meter in center
        treasurePanel = new JPanel(new BorderLayout(1, 3));

        // Create treasure card pile panel, vertical layout (4 rows 1 column): discard pile, draw pile, two hand cards
        JPanel treasureCardPile = new JPanel(new GridLayout(4, 1, 1, 3));
        treasureCards = new ArrayList<>();

        // Create discard pile icon label
        JLabel pile = new JLabel();
        Dimension treasureCardSize = new Dimension(Constant.TREASURE_WIDTH, Constant.TREASURE_HEIGHT);
        pile.setPreferredSize(treasureCardSize);

        // Set discard pile image
        pile.setIcon(new ImageIcon(ImageUtil.getImage("/Back/Treasure Discard.png", Constant.TREASURE_WIDTH, Constant.TREASURE_HEIGHT)));
        treasureCardPile.add(pile); // Add discard pile icon to panel

        // Create draw pile icon label
        JLabel back = new JLabel();
        back.setPreferredSize(treasureCardSize);
        // Set draw pile image, rotated 90 degrees
        back.setIcon(new ImageIcon(Objects.requireNonNull(ImageUtil.getImage("/Back/Treasure Deck.png", Constant.TREASURE_WIDTH, Constant.TREASURE_HEIGHT, 90d))));
        treasureCardPile.add(back); // Add draw pile icon to panel

        // Initialize and add two card slots (buttons, initially disabled)
        for (int i = 0; i < 2; i++) {
            treasureCards.add(new JButton());
            treasureCards.get(i).setPreferredSize(treasureCardSize);
            treasureCards.get(i).setEnabled(false);
            treasureCardPile.add(treasureCards.get(i));
        }

        // Add treasure card pile panel to main panel top
        treasurePanel.add(treasureCardPile, BorderLayout.NORTH);

        // Add water meter label to main panel center area
        treasurePanel.add(waterMeter, BorderLayout.CENTER);
    }

    /**
     * Get the constructed treasure panel for embedding in other containers.
     *
     * @return Main panel containing treasure card pile and water meter
     */
    public JPanel getTreasurePanel() {
        return treasurePanel;
    }
}
