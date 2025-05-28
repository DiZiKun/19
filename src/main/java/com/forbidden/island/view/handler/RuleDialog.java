package com.forbidden.island.view.handler;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog window that displays the game rules using a series of image pages.
 * Shows the official rulebook as a scrollable sequence of PNG images.
 */
public class RuleDialog extends JDialog {
    /**
     * Constructor initializes and configures the rules dialog window.
     * Creates a scrollable panel containing sequential rulebook pages as images.
     * Images are loaded from the /Rules directory and displayed vertically.
     */
    public RuleDialog() {
        setTitle("Game Help");
        setSize(900, 1100); // Adjustable initial window size
        setLocationRelativeTo(null);
        setModal(true);

        // Content panel uses BoxLayout (vertical arrangement)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.DARK_GRAY);

        for (int i = 0; i <= 7; i++) {
            // Path format must match exactly
            String path = "/Rules/forbidden-island-rulebook_0" + i + ".png";

            ImageIcon icon = new ImageIcon(getClass().getResource(path));

            JLabel imageLabel = new JLabel(icon);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

            contentPanel.add(Box.createVerticalStrut(20)); // Spacing
            contentPanel.add(imageLabel);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll speed
        add(scrollPane);

        setVisible(true);
    }
}
