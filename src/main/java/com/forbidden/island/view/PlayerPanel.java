package com.forbidden.island.view;

import com.forbidden.island.utils.Constant;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * PlayerPanel displays the interface for two players' pawns and hand cards.
 * Each player has a button representing their character (Pawn) and five hand card buttons.
 */
public class PlayerPanel {

    // Player instances
    private final Player player1;
    private final Player player2;

    // Unified size for pawns and hand cards
    private final Dimension adventurerSize = new Dimension(Constant.ADVENTURER_WIDTH, Constant.ADVENTURER_HEIGHT);

    // Horizontal Box container for displaying two players
    private final Box duoPlayerPanel = Box.createHorizontalBox();

    /**
     * Constructor: initializes two players and their interface components, and adds them to parent container
     */
    public PlayerPanel() {
        // Initialize two players
        player1 = new Player();
        player2 = new Player();

        // Add player1's panel
        duoPlayerPanel.add(player1.playerPanel);

        // Add spacing between two players
        duoPlayerPanel.add(Box.createHorizontalStrut(10));

        // Add player2's panel
        duoPlayerPanel.add(player2.playerPanel);
    }

    public JButton getP1Pawn() {
        return player1.pawn;
    }

    public ArrayList<JButton> getP1HandCards() {
        return player1.handCards;
    }

    public JButton getP2Pawn() {
        return player2.pawn;
    }

    public ArrayList<JButton> getP2HandCards() {
        return player2.handCards;
    }

    public Box getDuoPlayerPanel() {
        return duoPlayerPanel;
    }

    /**
     * Player class represents a single player, including pawn and hand card components.
     */
    public class Player {
        // Player panel (main container for pawn and hand cards)
        private final JPanel playerPanel = new JPanel(new BorderLayout(3, 0));

        // Pawn button (represents player character)
        private final JButton pawn;

        // Hand card button list (fixed at 5 cards)
        private final ArrayList<JButton> handCards = new ArrayList<>();

        /**
         * Constructor: initializes player's pawn and 5 hand cards, and adds them to layout
         */
        public Player() {
            // Initialize pawn button
            pawn = new JButton();
            pawn.setPreferredSize(adventurerSize);
            playerPanel.add(pawn, BorderLayout.WEST);   // Pawn placed on left side

            // Create hand card panel (5 cards arranged horizontally)
            JPanel handCardPanel = new JPanel(new GridLayout(1, 5, 1, 1));
            for (int i = 0; i < 5; i++) {
                handCards.add(new JButton());
                handCards.get(i).setPreferredSize(adventurerSize);
                handCards.get(i).setEnabled(false); // Initially disabled, waiting for game logic to enable
                handCardPanel.add(handCards.get(i));
            }

            // Add hand card panel to player panel's center area
            playerPanel.add(handCardPanel, BorderLayout.CENTER);

            // Set overall panel's preferred size
            playerPanel.setPreferredSize(new Dimension(440, Constant.ADVENTURER_HEIGHT+10));
        }
    }
}
