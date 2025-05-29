package com.forbidden.island.view;

import javax.swing.*;
import java.awt.*;

/**
 * AdventurerIcon is a custom icon class that implements javax.swing.Icon interface.
 * This class combines two icons (top and bottom) for overlapping display on a single component.
 * It also supports horizontal scaling or displacement effects for the top icon through scaledX.
 */
public class AdventurerIcon implements Icon {
    // Top icon (overlaid on bottom icon)
    private final Icon top;
    // Bottom icon (drawn first, at the bottom layer)
    private final Icon bottom;

    // Horizontal scaling or displacement factor, defaults to 1. Multiplied by 6 in constructor to enhance displacement effect.
    private int scaledX = 1;

    /**
     * Constructor for creating an overlapping icon object (without scaling displacement).
     *
     * @param top top icon
     * @param bottom bottom icon
     */
    public AdventurerIcon(Icon top, Icon bottom) {
        this.top = top;
        this.bottom = bottom;
    }

    /**
     * Constructor for creating an overlapping icon object with horizontal scaling displacement.
     *
     * @param top top icon
     * @param bottom bottom icon
     * @param scaledX horizontal scaling or offset factor (multiplied by 6)
     */
    public AdventurerIcon(Icon top, Icon bottom, int scaledX) {
        this.top = top;
        this.bottom = bottom;
        this.scaledX = scaledX * 6; // Multiplied by 6 to enhance offset effect
    }

    @Override
    public int getIconHeight() {
        return Math.max(top.getIconHeight(), bottom.getIconHeight());
    }

    @Override
    public int getIconWidth() {
        return Math.max(top.getIconWidth(), bottom.getIconWidth());
    }

    /**
     * Draw the icon on the specified component.
     * First draws the bottom icon, then draws the top icon with x-coordinate multiplied by scaledX for displacement.
     *
     * @param c component to draw the icon on
     * @param g graphics context
     * @param x top-left x coordinate
     * @param y top-left y coordinate
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // Draw bottom icon first
        bottom.paintIcon(c, g, x, y);

        // Then draw top icon, with x coordinate scaled or displaced by scaledX
        top.paintIcon(c, g, x * scaledX, y);
    }
}

