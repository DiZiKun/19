package com.forbidden.island.view;

import com.forbidden.island.utils.Constant;
import com.forbidden.island.utils.Map;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * TileGridPanel class is responsible for creating and managing the main game board interface.
 * The board consists of a 6x6 grid, where some tiles are inaccessible (blankLayout) and others are playable.
 * Each playable tile is represented by a JButton and added to the static tileCards list for game logic control
 * (e.g., visibility, click events).
 * The board uses Arena.jpg as background and arranges tiles using GridLayout.
 *
 * Main purposes:
 * - Initialize and render the game map interface
 * - Provide access to tile buttons
 * - Manage background image and layout
 *
 * Dependencies:
 * - Constant: Contains tile dimensions and resource path constants
 * - Map.blankLayout: Marks which positions are blank/unusable
 * - ImagePanel: A custom panel class with background image support
 */
public class TileGridPanel {
    // Static list storing all interactive tile buttons (JButtons for non-blank areas)
    public static ArrayList<JButton> tileCards = new ArrayList<>();

    // Game board image panel containing 6x6 grid
    private final ImagePanel board;

    /**
     * Constructor: Creates and initializes the board interface, including background image and all tile buttons.
     * Adds playable tile buttons to tileCards for game control use.
     */
    public TileGridPanel() {
        // Set individual tile dimensions
        Dimension tileSize = new Dimension(Constant.TILE_WIDTH, Constant.TILE_HEIGHT);

        // Set overall board panel dimensions
        Dimension boardSize = new Dimension(Constant.BOARD_WIDTH, Constant.BOARD_HEIGHT);

        // Create panel with map image background and 6x6 grid layout
        board = new ImagePanel(Constant.RESOURCES_PATH + "/Map/Arena.jpg", new GridLayout(6, 6, 0, 0));

        // Initialize 36 buttons (corresponding to 6x6 board tiles)
        for (int i = 0; i < 36; i++) {
            JButton tileCard = new JButton();
            tileCard.setPreferredSize(tileSize);    // Set button size
            tileCard.setMargin(new Insets(-0,-0,-0,-0));

            // Check if tile is in blank area (unusable tile)
            if (Map.blankLayout.contains(i)) {
                tileCard.setVisible(false); // Blank tiles are invisible
            } else {
                tileCard.setVisible(false); // Initially hidden, can be shown through game logic
                tileCards.add(tileCard);    // Add to list of operable tiles
            }
            // Add button to board background panel
            board.add(tileCard);
        }
        // Set board panel size
        board.setPreferredSize(boardSize);
    }

    /**
     * Get the board image panel (containing all buttons and background)
     * @return board Panel composed of game board background and buttons
     */
    public ImagePanel getBoard() {
        return board;
    }
}
