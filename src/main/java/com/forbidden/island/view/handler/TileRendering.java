package com.forbidden.island.view.handler;

import com.forbidden.island.model.adventurer.Adventurer;
import com.forbidden.island.utils.Constant;
import com.forbidden.island.utils.ImageUtil;
import com.forbidden.island.utils.Map;
import com.forbidden.island.view.AdventurerIcon;
import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.view.Tile;
import com.forbidden.island.view.TileGridPanel;

import javax.swing.*;
import java.util.ArrayList;

/**
 * TileRendering implements the IRendering interface,
 * responsible for updating the state and display of each tile on the main game panel (TileGridPanel) during gameplay.
 * Includes: tile image loading, tile visibility and enabling, character icon overlays, and disabling interaction at game end.
 */
public class TileRendering implements IRendering {

    /**
     * Constructor called at game startup, initializes tile graphics and character initial position images.
     * Gets map IDs and character position information from ElementEngine to set up initial display.
     */
    public TileRendering() {
        // Get initialized tile ID sequence
        ArrayList<Integer> tiles = new ArrayList<>(ElementEngine.getTilesArray());

        // Iterate through all tile card components and set images, visibility, and enabled state
        for (int i = 0; i < TileGridPanel.tileCards.size(); i++) {
            JButton tileButton = TileGridPanel.tileCards.get(i);
            tileButton.setEnabled(true); // Enable interaction
            tileButton.setVisible(true); // Set visible

            // Set tile image
            tileButton.setBorderPainted(false);
            tileButton.setContentAreaFilled(false);
            tileButton.setFocusPainted(false);

            tileButton.setIcon(new ImageIcon(
                    ImageUtil.getImage("/Tiles/" + tiles.get(i) + ".png", Constant.TILE_WIDTH, Constant.TILE_HEIGHT)));
        }

        // Set initial position icons for all adventurers (overlaid on tiles)
        for (int i = 0; i < ElementEngine.getAdventurers().length; i++) {
            Adventurer adv = ElementEngine.getAdventurers()[i];
            int posIndex = adv.getPos();

            // Get overlay icon (character + tile)
            AdventurerIcon icon = new AdventurerIcon(
                    new ImageIcon(ImageUtil.getImage(
                            adv.getPawnImg())),
                    TileGridPanel.tileCards.get(tiles.indexOf(adv.getId() + 9)).getIcon());

            TileGridPanel.tileCards.get(posIndex).setIcon(icon);
        }
    }

    /**
     * Called whenever game state updates (e.g., tile floods, character moves).
     * Updates tile states (visible/invisible) and character overlay icons.
     */
    @Override
    public void update() {
        int idx = 0;

        // Iterate through each tile in the game map and refresh display based on state
        for (Tile[] tiles : ElementEngine.getBoard().getTileMap()) {
            for (Tile tile : tiles) {
                if (tile.isExist()) {
                    // Tile exists: show normal image
                    JButton tileButton = TileGridPanel.tileCards.get(idx);
                    tileButton.setBorderPainted(false);
                    tileButton.setContentAreaFilled(false);
                    tileButton.setFocusPainted(false);
                    tileButton.setIcon(new ImageIcon(
                            ImageUtil.getImage(tile.getImg(), Constant.TILE_WIDTH, Constant.TILE_HEIGHT)));
                } else if (!tile.isExist() && (tile.getTileId() != -1)) {
                    // Tile has sunk but once existed: set invisible and disabled
                    TileGridPanel.tileCards.get(idx).setVisible(false);
                    TileGridPanel.tileCards.get(idx).setEnabled(false);
                } else {
                    // Empty tile (tileId is -1): skip current index (idx-- to backtrack)
                    idx--;
                }
                idx++;
            }
        }

        // Iterate through all adventurers, update their overlay icons on tiles
        for (int i = 0; i < ElementEngine.getAdventurers().length; i++) {
            Adventurer adv = ElementEngine.getAdventurers()[i];
            Tile tile = ElementEngine.getBoard().getTile(adv.getX(), adv.getY());

            for (int j = 0; j < tile.getPlayerOnBoard().size(); j++) {
                String pawnName = Map.adventurerMatcher.get(tile.getPlayerOnBoard().get(j));
                AdventurerIcon advIcon = new AdventurerIcon(
                        new ImageIcon(ImageUtil.getImage(
                                "/Pawns/" + pawnName + ".png",
                                Constant.TILE_WIDTH,
                                Constant.TILE_HEIGHT)),
                        TileGridPanel.tileCards.get(adv.getPos()).getIcon(), j);    // Control multiple character icon position offset
                TileGridPanel.tileCards.get(adv.getPos()).setIcon(advIcon);
            }
        }
    }

    /**
     * Called at game end, disables all tile cards to prevent further player interaction.
     */
    @Override
    public void finish() {
        for (JButton tile : TileGridPanel.tileCards) {
            tile.setEnabled(false); // Disable interaction
        }
    }
}

