package com.forbidden.island.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * ImagePanel is a custom JPanel that can draw an image in its background.
 * Can be used to set custom background images (e.g., main menu, game background, etc.).
 */
public class ImagePanel extends JPanel {

    // Stores the background image
    private Image backgroundImage;

    /**
     * Constructor: creates a panel with background image and specifies the panel's layout manager.
     * @param fileName image file path (relative to project root or absolute path)
     * @param layout JPanel's layout manager
     */
    public ImagePanel(String fileName, LayoutManager layout) {
        super(layout);  // Set JPanel's layout
        try {
            // Attempt to load image file as Image object
            backgroundImage = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Override paintComponent method to implement background image drawing.
     * Called by Swing each time the component needs to be redrawn.
     * @param g Graphics object for drawing
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);    // Maintain normal Swing drawing mechanism, avoid redraw errors

        // Draw background image
        // Parameters: (image, x, y, width, height, observer)
        // Here draws the image at position (-150, -150), enlarged to 1100x1100
        // Used to center or extend image across entire background area
        g.drawImage(backgroundImage, -150, -150, 1100, 1100, this);
    }
}
