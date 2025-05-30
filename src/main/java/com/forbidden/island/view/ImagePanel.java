package com.forbidden.island.view;

import com.forbidden.island.utils.ImageUtil;
import javax.swing.*;
import java.awt.*;

/**
 * ImagePanel is a custom JPanel that can draw an image in its background.
 * Can be used to set custom background images (e.g., main menu, game background, etc.).
 */
public class ImagePanel extends JPanel {

    // Stores the background image
    private Image backgroundImage;

    /**
     * Constructor: creates a panel with background image and specifies the panel's layout manager.
     * @param imagePath image path relative to /image directory in resources
     * @param layout JPanel's layout manager
     */
    public ImagePanel(String imagePath, LayoutManager layout) {
        super(layout);  // Set JPanel's layout
        backgroundImage = ImageUtil.getImage(imagePath);
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
