package com.forbidden.island.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * SettingsDialog is a modal dialog for setting the number of players and difficulty level.
 */
public class SettingsDialog extends JDialog {
    // Number of players (default 2)
    private int playerCount = 2;

    // Difficulty level (default 1)
    private int difficulty = 1;

    // Whether the confirm button was clicked
    private boolean confirmed = false;

    /**
     * Constructor: creates a modal dialog for users to select settings.
     *
     * @param parent parent window (for centering dialog)
     */
    public SettingsDialog(JFrame parent) {
        super(parent, "Setting", true); // true indicates modal window, blocks parent window input

        // Set window size
        setSize(300, 200);

        // Center window relative to parent
        setLocationRelativeTo(parent);

        // Set layout to 3 rows 2 columns, with 10-pixel gaps between components
        setLayout(new GridLayout(3, 2, 10, 10));

        // ------------------- Player Count Selection -------------------
        JLabel lblPlayers = new JLabel("Players:");
        JComboBox<Integer> cbPlayers = new JComboBox<>(new Integer[]{2, 3, 4});

        // ------------------- Difficulty Level Selection -------------------
        JLabel lblDifficulty = new JLabel("Levels:");
        JComboBox<Integer> cbDifficulty = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});

        // ------------------- Confirm Button -------------------
        JButton btnConfirm = new JButton("Start");
        btnConfirm.addActionListener((ActionEvent e) -> {
            // Get user-selected player count and difficulty
            playerCount = (int) cbPlayers.getSelectedItem();
            difficulty = (int) cbDifficulty.getSelectedItem();
            confirmed = true;
            dispose();
        });

        // ------------------- Add Components to Window -------------------
        add(lblPlayers);
        add(cbPlayers);
        add(lblDifficulty);
        add(cbDifficulty);
        add(new JLabel());     // Placeholder label for layout alignment
        add(btnConfirm);       // Place confirm button in last grid cell

        // Set close operation to release resources (close window)
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getDifficulty() {
        return difficulty;
    }
}