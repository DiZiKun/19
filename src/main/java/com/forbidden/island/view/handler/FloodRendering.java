package com.forbidden.island.view.handler;

import com.forbidden.island.utils.Constant;
import com.forbidden.island.utils.ImageUtil;
import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.view.FloodPanel;

import javax.swing.*;
import java.util.ArrayList;

/**
 * FloodRendering implements IRendering interface,
 * responsible for updating and managing the display and interaction states
 * of Flood Cards panel in the game.
 */
public class FloodRendering implements IRendering {

    /**
     * Updates the display and enabled states of flood cards.
     * Iterates through all flood card buttons in FloodPanel, determines whether to show
     * and enable corresponding buttons based on remaining cards in the flood deck,
     * and sets appropriate card icons based on card numbers.
     */
    @Override
    public void update() {
        // Get current available cards count and number list from flood deck
        ArrayList<Integer> floodCardsInDeck = ElementEngine.getFloodDeck().getCards();

        // Iterate through flood card buttons in interface
        for (int i = 0; i < FloodPanel.floodCards.size(); i++) {
            if (i < floodCardsInDeck.size()) {
                // Current button index is less than deck card count, indicating card exists
                // Enable button, allow user interaction
                FloodPanel.floodCards.get(i).setEnabled(true);

                // Set button visible
                FloodPanel.floodCards.get(i).setVisible(true);

                // Load corresponding card icon based on card number and set to button
                String imgPath = "/Flood/" + floodCardsInDeck.get(i) + ".png";
                FloodPanel.floodCards.get(i).setIcon(new ImageIcon(
                        ImageUtil.getImage(imgPath, Constant.FLOOD_WIDTH, Constant.FLOOD_HEIGHT)
                ));
            } else {
                // Current button index exceeds deck card count, indicating no corresponding card

                // Disable button to prevent user interaction
                FloodPanel.floodCards.get(i).setEnabled(false);
                // Hide button from interface
                FloodPanel.floodCards.get(i).setVisible(false);
            }
        }
    }

    /**
     * Called when game ends, disables all flood card buttons to prevent further operations.
     */
    @Override
    public void finish() {
        // Iterate through all flood card buttons, disable them all
        for (JButton floodCard : FloodPanel.floodCards) {
            floodCard.setEnabled(false);
        }
    }
}
