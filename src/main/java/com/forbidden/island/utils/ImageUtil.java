package com.forbidden.island.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ImageUtil {

    /**
     * Load an image (original size)
     *
     * @param imageName image filename, relative to resource path
     * @return Image object, returns null if loading fails
     */
    public static Image getImage(String imageName) {
        try {
            InputStream is = ImageUtil.class.getResourceAsStream("/image" + imageName);
            return ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("Failed to load image: " + imageName);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Load an image with specified dimensions (will be scaled)
     *
     * @param imageName   image filename
     * @param imageWidth  target width
     * @param imageHeight target height
     * @return scaled Image object
     */
    public static Image getImage(String imageName, int imageWidth, int imageHeight) {
        Image image = getImage(imageName);
        if (image == null) {
            return null;
        }
        return image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
    }

    /**
     * Load an image, rotate and scale it
     *
     * @param imageName     image filename
     * @param imageWidth    scaled width
     * @param imageHeight   scaled height
     * @param rotationAngle clockwise rotation angle (in degrees)
     * @return processed Image object (rotated then scaled)
     */
    public static Image getImage(String imageName, int imageWidth, int imageHeight, double rotationAngle) {
        Image image = getImage(imageName);
        if (image == null) {
            return null;
        }

        // Convert to BufferedImage if needed
        BufferedImage bufferedImage;
        if (image instanceof BufferedImage) {
            bufferedImage = (BufferedImage) image;
        } else {
            bufferedImage = new BufferedImage(
                image.getWidth(null),
                image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
            );
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();
        }

        // Rotate the image
        bufferedImage = rotate(bufferedImage, rotationAngle);

        // Scale the rotated image
        return bufferedImage.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
    }

    /**
     * Rotate a BufferedImage
     *
     * @param image   original image
     * @param degrees clockwise rotation angle (in degrees)
     * @return rotated BufferedImage
     */
    public static BufferedImage rotate(BufferedImage image, Double degrees) {
        // Convert degrees to radians
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));

        // Calculate new dimensions after rotation
        int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);

        // Create a new blank image (supports transparency)
        BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotate.createGraphics();

        // Anti-aliasing and quality optimization
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Calculate rotation center point (center of original image)
        int x = (newWidth - image.getWidth()) / 2;
        int y = (newHeight - image.getHeight()) / 2;

        // Build transformation object
        AffineTransform at = new AffineTransform();
        at.translate(x, y); // Move image to center position
        at.setToRotation(radians, x + (image.getWidth() / 2.0), y + (image.getHeight() / 2.0)); // Rotate around image center
        g2d.setTransform(at);

        // Set transformation and draw original image
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotate;
    }
}
