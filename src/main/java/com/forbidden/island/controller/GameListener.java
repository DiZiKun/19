package com.forbidden.island.controller;

import com.forbidden.island.utils.Map;
import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.view.GamePanel;
import com.forbidden.island.view.TileGridPanel;
import com.forbidden.island.view.TreasurePanel;

import javax.swing.*;
import java.util.List;

/**
 * GameListener class is responsible for setting up click event listeners for various game buttons
 * (board tiles, character pawns, hand cards, treasure cards) to ensure user interactions
 * trigger the correct game logic.
 */
public class GameListener {

    /**
     * Constructor
     * Initializes by calling all listener setup methods to bind event listeners to all relevant buttons.
     */
    public GameListener() {
        boardListener();     // Bind board tile button listeners
        PawnListener();      // Bind character pawn button listeners
        handCardListener();  // Bind player hand card button listeners
        treasureListener();  // Bind treasure card button listeners
    }

    /**
     * BoardListener method
     * Binds click events to each board tile button, notifying ElementEngine to process
     * the next operation based on the corresponding coordinates when clicked.
     */
    private void boardListener() {
        for (JButton tile : TileGridPanel.tileCards) {
            // Get tile's index in tileCards list to map to coordinates
            tile.addActionListener(e -> ElementEngine.nextTile(
                    Map.coordinatesMatcher.get(TileGridPanel.tileCards.indexOf(tile)))
            );
        }
    }

    /**
     * TreasureListener method
     * Binds click events to each treasure card button, notifying ElementEngine to select
     * the corresponding treasure card (non-player hand cards) when clicked.
     */
    private void treasureListener() {
        for (JButton treasure : TreasurePanel.treasureCards) {
            treasure.addActionListener(e ->
                    ElementEngine.selectTreasureCard(false, TreasurePanel.treasureCards.indexOf(treasure))
            );
        }
    }

    /**
     * HandCardListener method
     * Binds click events to each player's hand card buttons, notifying ElementEngine to select
     * the corresponding player hand card when clicked.
     */
    private void handCardListener() {
        for (List<JButton> individualPlayerCards : GamePanel.playerHandCards) {
            for (JButton handCard : individualPlayerCards) {
                // Get index of this hand card button in player's hand cards
                handCard.addActionListener(e ->
                        ElementEngine.selectTreasureCard(true, individualPlayerCards.indexOf(handCard))
                );
            }
        }
    }

    /**
     * PawnListener method
     * Binds click events to each player character pawn button, notifying ElementEngine to select
     * the corresponding character pawn when clicked.
     */
    private void PawnListener() {
        for (JButton pawn : GamePanel.playerPawnList) {
            // Use index to identify the selected character pawn
            pawn.addActionListener(e ->
                    ElementEngine.selectPawn(GamePanel.playerPawnList.indexOf(pawn))
            );
        }
    }
}
