package com.forbidden.island.view.handler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Welcome screen frame for the Forbidden Island game.
 * Displays the game title screen with interactive buttons for:
 * - Starting a new game
 * - Viewing game rules
 * - Accessing help
 * - Exiting the game
 */
public class WelcomeFrame extends JFrame {

    /**
     * Constructor initializes and configures the welcome screen.
     * Sets up the background image and creates styled buttons with hover effects.
     * Each button is configured with its respective action:
     * - Start Game: Launches the main game window
     * - Rule: Opens the rules dialog
     * - Help: Shows the help dialog
     * - Exit: Closes the application
     */
    public WelcomeFrame() {
        setTitle("Welcome to Forbidden Island");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Background image path
        ImageIcon bgIcon = new ImageIcon("/image/TitleScreen.png");
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Button definitions
        JButton startBtn = createStyledButton("Start Game", new Color(46, 204, 113), new Color(39, 174, 96));
        JButton ruleBtn = createStyledButton("Rule", new Color(52, 152, 219), new Color(41, 128, 185));
        JButton helpBtn = createStyledButton("Help", new Color(52, 152, 219), new Color(41, 128, 185));
        JButton exitBtn = createStyledButton("Exit", new Color(231, 76, 60), new Color(192, 57, 43));

        // Button action bindings
        startBtn.addActionListener(e -> {
            new ForbiddenIslandFrame("Forbidden Island");
            dispose();
        });

        ruleBtn.addActionListener(e -> new RuleDialog());

        helpBtn.addActionListener(e -> {
            new HelpDialog(); // Show help window
        });

        exitBtn.addActionListener(e -> System.exit(0));

        // Button area layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        buttonPanel.add(startBtn);
        buttonPanel.add(ruleBtn);
        buttonPanel.add(helpBtn);
        buttonPanel.add(exitBtn);

        background.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates a styled button with specified text and colors.
     * Configures the button appearance with:
     * - Custom font and size
     * - Normal and hover background colors
     * - White text
     * - Custom padding
     * - Mouse hover effects
     *
     * @param text Button display text
     * @param normalColor Default background color
     * @param hoverColor Background color when mouse hovers
     * @return Styled JButton instance
     */
    private JButton createStyledButton(String text, Color normalColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(normalColor);

        button.setFocusPainted(false);
        button.setContentAreaFilled(true);  //  Show background color
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });

        return button;
    }
}
