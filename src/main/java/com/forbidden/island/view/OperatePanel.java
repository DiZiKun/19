package com.forbidden.island.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * OperatePanel is a control panel class that displays various game operation buttons.
 * Uses a vertical Box layout container with a nested horizontal GridLayout containing the buttons.
 */
public class OperatePanel {
    // Static button list for global access (e.g., for controllers to add button listeners)
    public static ArrayList<JButton> opButtons = new ArrayList<>();

    // Vertical Box container serving as the root container for this panel
    private final Box box = Box.createVerticalBox();

    /**
     * Constructor: Initializes buttons and builds panel layout
     */
    public OperatePanel() {

        // 0. Initialize all buttons (added to list in order)
        opButtons.add(createStyledButton("Start", new Color(52, 73, 94), new Color(44, 62, 80)));       // index 0
        opButtons.add(createStyledButton("Move", new Color(52, 152, 219), new Color(41, 128, 185)));     // 1
        opButtons.add(createStyledButton("Shore", new Color(230, 126, 34), new Color(211, 84, 0)));      // 2
        opButtons.add(createStyledButton("Pass", new Color(155, 89, 182), new Color(142, 68, 173)));     // 3
        opButtons.add(createStyledButton("Capture", new Color(46, 204, 113), new Color(39, 174, 96)));   // 4
        opButtons.add(createStyledButton("Lift Off", new Color(241, 196, 15), new Color(243, 156, 18))); // 5
        opButtons.add(createStyledButton("Special", new Color(149, 165, 166), new Color(127, 140, 141))); // 6
        opButtons.add(createStyledButton("Next", new Color(44, 62, 80), new Color(52, 73, 94)));         // 7
        opButtons.add(createStyledButton("Discard", new Color(231, 76, 60), new Color(192, 57, 43)));    // 8
        opButtons.add(createStyledButton("Reset", new Color(52, 152, 219), new Color(41, 128, 185)));    // 9

        // 1. Create a 1-row, 9-column grid layout (for button arrangement)
        GridLayout gridLayout = new GridLayout(1, 9, 0, 2);
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(gridLayout);
        // 2. Add all buttons except "Start" (index 0) to actionPanel
        for (int i = 1; i <= 9; i++) {
            actionPanel.add(opButtons.get(i));
        }

        // Set preferred size for button panel (width controlled by layout, height set to 30)
        actionPanel.setPreferredSize(new Dimension(50, 30));

        // 3. Add actionPanel to box layout
        // Add vertical spacing
        box.add(Box.createVerticalStrut(1));      // Top spacing
        box.add(Box.createVerticalStrut(5));      // Second layer spacing (can be consolidated)
        box.add(actionPanel);                     // Main operation button area
        box.add(Box.createVerticalGlue());        // Bottom elastic filling, automatically fills remaining space
    }

    /**
     * Get the constructed Box container for external component nesting.
     * @return Box Panel containing operation buttons
     */
    public Box getBox() {
        return box;
    }

    /**
     * Creates a styled button with custom colors and hover effects.
     *
     * @param text Button text
     * @param baseColor Default button color
     * @param hoverColor Color when mouse hovers over button
     * @return Styled JButton instance
     */
    private JButton createStyledButton(String text, Color baseColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(baseColor);
            }
        });

        return button;
    }
}
