package com.forbidden.island.view.handler;

import com.forbidden.island.model.adventurer.Adventurer;
import com.forbidden.island.view.GamePanel;
import com.forbidden.island.view.AdventurerIcon;
import com.forbidden.island.controller.ForbiddenIslandGame;
import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.utils.ImageUtil;
import com.forbidden.island.utils.Constant;

import javax.swing.*;
import java.util.List;

/**
 * PlayerRendering implements IRendering interface,
 * responsible for updating the display and interaction states of player pawns
 * and hand cards in the game interface.
 */
public class PlayerRendering implements IRendering {

    /**
     * Constructor, initializes player pawn and hand card icons and states.
     * Iterates through all player slots, if slot has a player, displays their pawn icon
     * and hand card icons, and determines if hand cards are operable based on current player's turn.
     * If slot has no player, disables all controls for that slot.
     */
    public PlayerRendering() {
        // Iterate through all player pawn buttons in interface
        for (int i = 0; i < GamePanel.playerPawnList.size(); i++) {
            if (i < ForbiddenIslandGame.getNumOfPlayer()) {
                // Slot has player, set player pawn icon
                GamePanel.playerPawnList.get(i).setIcon(new ImageIcon(ImageUtil.getImage(ElementEngine.getAdventurers()[i].getPawnImg(),Constant.TREASURE_WIDTH,Constant.TREASURE_HEIGHT)));
                // Set hand card button states and icons for this player
                for (int j = 0; j < GamePanel.playerHandCards.get(i).size(); j++) {
                    if (j < ElementEngine.getAdventurers()[i].getHandCards().size()) {
                        // Current hand card exists, show corresponding card image
                        GamePanel.playerHandCards.get(i).get(j).setEnabled(i == ForbiddenIslandGame.getRoundNum());
                        GamePanel.playerHandCards.get(i).get(j).setIcon(new ImageIcon(ImageUtil.getImage("/TreasureCards/" + ElementEngine.getAdventurers()[i].getHandCards().get(j) + ".png", Constant.ADVENTURER_WIDTH, Constant.ADVENTURER_HEIGHT)));
                        GamePanel.playerHandCards.get(i).get(j).setAlignmentX(SwingConstants.CENTER);
                    } else {
                        // No card in this hand card position, disable button
                        GamePanel.playerHandCards.get(i).get(j).setEnabled(false);
                    }
                }
            } else {
                // Slot has no player, disable pawn and all hand card buttons
                GamePanel.playerPawnList.get(i).setEnabled(false);
                for (JButton handCard : GamePanel.playerHandCards.get(i)) {
                    handCard.setEnabled(false);
                }
            }
        }
    }

    /**
     * update method is called during gameplay to dynamically refresh the display
     * of captured figurines stacked on player pawns and hand cards.
     * - Each player pawn shows icons of captured figurines (can be stacked).
     * - Refreshes hand card button icons, enabled states, and visibility.
     */
    @Override
    public void update() {
        int numPlayers = ForbiddenIslandGame.getNumOfPlayer();
        Adventurer[] adventurers = ElementEngine.getAdventurers();

        // Iterate through all players
        for (int i = 0; i < numPlayers; i++) {
            // Stack captured figurine icons on player pawn button
            for (int k = 0; k < adventurers[i].getCapturedFigurines().size(); k++) {
                GamePanel.playerPawnList.get(i).setIcon(
                        new AdventurerIcon(
                                new ImageIcon(ImageUtil.getImage("/Figurines/"
                                        + adventurers[i].getCapturedFigurines().get(k).name() + ".png")),
                                GamePanel.playerPawnList.get(i).getIcon()));
            }

            // Update player hand card button icons and states, ensure showing latest hand cards
            for (int j = 0; j < adventurers[i].getHandCards().size(); j++) {
                GamePanel.playerHandCards.get(i).get(j).setIcon(new ImageIcon(
                        ImageUtil.getImage("/TreasureCards/" + adventurers[i].getHandCards().get(j) + ".png",
                                Constant.ADVENTURER_WIDTH, Constant.ADVENTURER_HEIGHT)));
                GamePanel.playerHandCards.get(i).get(j).setAlignmentX(SwingConstants.CENTER);
                // Only current turn player's hand card buttons are enabled
                GamePanel.playerHandCards.get(i).get(j).setEnabled(i == ForbiddenIslandGame.getRoundNum());
                GamePanel.playerHandCards.get(i).get(j).setVisible(true);
            }

            // Clear and disable excess hand card buttons, keep interface tidy
            for (int j = ElementEngine.getAdventurers()[i].getHandCards().size(); j < GamePanel.playerHandCards.get(i).size(); j++) {
                GamePanel.playerHandCards.get(i).get(j).setIcon(null);
                GamePanel.playerHandCards.get(i).get(j).setAlignmentX(SwingConstants.CENTER);
                GamePanel.playerHandCards.get(i).get(j).setEnabled(false);
                GamePanel.playerHandCards.get(i).get(j).setVisible(true);
            }
        }
    }

    /**
     * Called when game ends, disables all player pawn buttons and hand card buttons
     * to prevent further operations.
     */
    @Override
    public void finish() {
        // Disable all player pawn buttons
        for (JButton player : GamePanel.playerPawnList) {
            player.setEnabled(false);
        }

        // Disable all players' hand card buttons
        for (List<JButton> hands : GamePanel.playerHandCards) {
            for (JButton hand : hands) {
                hand.setEnabled(false);
            }
        }
    }
}
