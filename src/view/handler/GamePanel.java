package view.handler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GamePanel is the core panel of the game interface, responsible for organizing and laying out
 * various sub-panels in the game: player information panels (top and bottom groups),
 * game map panel, treasure panel, and flood panel.
 * Also maintains lists of player pawn buttons and player hand card buttons for game logic operations.
 */
public class GamePanel {
    /**
     * Stores all player pawn buttons for controlling pawn states and events during gameplay.
     * Order corresponds to players: Player 1-4's pawn buttons
     */
    public static ArrayList<JButton> playerPawnList;

    /**
     * List storing each player's hand card buttons. Each element is a list of hand card buttons for one player.
     * playerHandCards.get(i) represents the hand card button list for player i.
     */
    public static ArrayList<List<JButton>> playerHandCards;

    /**
     * Main game panel, holds all sub-panels
     */
    private final JPanel gamePanel;

    /**
     * Constructor: initializes all sub-panels and adds them to the main game panel, completing layout setup.
     */
    public GamePanel() {
        // Create top and bottom player panels, displaying information and operation buttons for two players each
        PlayerPanel playerPanelUp = new PlayerPanel();
        PlayerPanel playerPanelDown = new PlayerPanel();

        // Create game map panel (tile grid)
        TileGridPanel tileGridPanel = new TileGridPanel();

        // Create treasure display panel
        TreasurePanel treasurePanel = new TreasurePanel();

        // Create flood display panel
        FloodPanel floodPanel = new FloodPanel();

        // Create main panel with BorderLayout, 5-pixel gaps
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout(5, 5));

        // Add sub-panels according to BorderLayout positions
        gamePanel.add(playerPanelDown.getDuoPlayerPanel(), BorderLayout.SOUTH); // Bottom player panel
        gamePanel.add(playerPanelUp.getDuoPlayerPanel(), BorderLayout.NORTH);// Top player panel
        gamePanel.add(floodPanel.getFloodPanel(), BorderLayout.EAST);// Right flood panel
        gamePanel.add(treasurePanel.getTreasurePanel(), BorderLayout.WEST);// Left treasure panel
        gamePanel.add(tileGridPanel.getBoard(), BorderLayout.CENTER);// Center map panel

        // Initialize and collect all player pawn buttons for later operations
        playerPawnList = new ArrayList<>();
        playerPawnList.add(playerPanelDown.getP1Pawn());    // Bottom panel's player 1 pawn button
        playerPawnList.add(playerPanelDown.getP2Pawn());    // Bottom panel's player 2 pawn button
        playerPawnList.add(playerPanelUp.getP1Pawn());      // Top panel's player 1 pawn button
        playerPawnList.add(playerPanelUp.getP2Pawn());      // Top panel's player 2 pawn button

        // Get hand card button lists for each player, from both panels' 4 players
        List<JButton> p1HandCards = new ArrayList<>(playerPanelDown.getP1HandCards());
        List<JButton> p2HandCards = new ArrayList<>(playerPanelDown.getP2HandCards());
        List<JButton> p3HandCards = new ArrayList<>(playerPanelUp.getP1HandCards());
        List<JButton> p4HandCards = new ArrayList<>(playerPanelUp.getP2HandCards());

        // Consolidate all player hand card lists for unified management
        playerHandCards = new ArrayList<>();
        playerHandCards.add(p1HandCards);
        playerHandCards.add(p2HandCards);
        playerHandCards.add(p3HandCards);
        playerHandCards.add(p4HandCards);
    }

    /**
     * Get the main game panel for adding to top-level container or other panels.
     * @return returns the main game JPanel object
     */
    public JPanel getGamePanel() {
        return gamePanel;
    }
}