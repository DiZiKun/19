package com.forbidden.island.view.handler;

import com.forbidden.island.utils.Constant;
import com.forbidden.island.utils.ImageUtil;
import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.view.TreasurePanel;

import javax.swing.*;
import java.util.Objects;

/**
 * TreasureRendering class implements IRendering interface,
 * specifically responsible for rendering and updating treasure cards
 * in the game interface.
 */
public class TreasureRendering implements IRendering {

    /**
     * Constructor
     * During initialization, disables all treasure card buttons to prevent
     * accidental operations before they are ready.
     */
    public TreasureRendering() {
        // Iterate through all treasure card buttons in TreasurePanel, disable them
        for (JButton treasureCard : TreasurePanel.treasureCards) {
            treasureCard.setEnabled(false);
        }
    }

    /**
     * Update method, refreshes treasure card display.
     * This method dynamically sets button icons and states based on
     * the current list of displayed treasure cards.
     */
    @Override
    public void update() {
        // Iterate through currently displayed treasure cards
        for (int i = 0; i < ElementEngine.getDisplayedTreasureCard().size(); i++) {
            // Enable corresponding button, indicating card is clickable
            TreasurePanel.treasureCards.get(i).setEnabled(true);

            // Set button icon using treasure card's corresponding image, with fixed size and rotation angle
            TreasurePanel.treasureCards.get(i).setIcon(new ImageIcon((
                    Objects.requireNonNull(
                            ImageUtil.getImage("/TreasureCards/"
                                            + ElementEngine.getDisplayedTreasureCard().get(i) + ".png",
                                    Constant.TREASURE_WIDTH,
                                    Constant.TREASURE_HEIGHT,
                                    270d)))));  // Image rotation angle in degrees
        }

        // Handle excess buttons (buttons exceeding current displayed card count)
        for (int i = ElementEngine.getDisplayedTreasureCard().size(); i < TreasurePanel.treasureCards.size(); i++) {
            // Clear icon, hide excess buttons
            TreasurePanel.treasureCards.get(i).setIcon(null);
            // Disable buttons to prevent clicking
            TreasurePanel.treasureCards.get(i).setEnabled(false);
        }
    }

    /**
     * Finish method, called during completion or cleanup.
     * Disables all treasure card buttons to prevent operations after game ends.
     */
    @Override
    public void finish() {
        // Disable all treasure card buttons
        for (JButton treasureCard : TreasurePanel.treasureCards) {
            treasureCard.setEnabled(false);
        }
    }
}
