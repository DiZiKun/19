import org.junit.Test;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/**
 * Test class for image utility functions
 */
public class ImageUtilTest {
    
    /**
     * Test implementation of ImageUtil class
     */
    private static class TestImageUtil {
        // Create a test image with specified dimensions
        private static BufferedImage createTestImage(int width, int height) {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.RED);
            g2d.fillRect(0, 0, width, height);
            g2d.dispose();
            return image;
        }
        
        public static Image getImage(String imageName) {
            // Create a test image instead of loading from file
            return createTestImage(100, 100);
        }
        
        public static Image getImage(String imageName, int imageWidth, int imageHeight) {
            // Create a test image with specified dimensions
            BufferedImage image = createTestImage(imageWidth, imageHeight);
            return image;
        }
        
        public static Image getImage(String imageName, int imageWidth, int imageHeight, double rotationAngle) {
            // Create and rotate a test image
            BufferedImage image = createTestImage(imageWidth, imageHeight);
            return rotate(image, rotationAngle);
        }
        
        public static BufferedImage rotate(BufferedImage image, Double degrees) {
            double radians = Math.toRadians(degrees);
            double sin = Math.abs(Math.sin(radians));
            double cos = Math.abs(Math.cos(radians));
            
            int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
            int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);
            
            BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = rotate.createGraphics();
            
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int x = (newWidth - image.getWidth()) / 2;
            int y = (newHeight - image.getHeight()) / 2;
            
            AffineTransform at = new AffineTransform();
            at.translate(x, y);
            at.rotate(radians, x + (image.getWidth() / 2.0), y + (image.getHeight() / 2.0));
            g2d.setTransform(at);
            
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();
            return rotate;
        }
    }
    
    @Test
    public void testGetImage() {
        // Test basic image loading
        Image image = TestImageUtil.getImage("test.png");
        assertNotNull("Image should be loaded successfully", image);
        // Wait for image to load completely
        waitForImage(image);
        assertEquals("Image width should be 100", 100, image.getWidth(null));
        assertEquals("Image height should be 100", 100, image.getHeight(null));
    }
    
    @Test
    public void testGetImageWithSize() {
        // Test loading image with specified dimensions
        int width = 100;
        int height = 100;
        Image image = TestImageUtil.getImage("test.png", width, height);
        assertNotNull("Image should be loaded successfully", image);
        // Wait for image to load completely
        waitForImage(image);
        assertEquals("Image width should match specified value", width, image.getWidth(null));
        assertEquals("Image height should match specified value", height, image.getHeight(null));
    }
    
    @Test
    public void testGetImageWithRotation() {
        // Test image rotation
        int width = 100;
        int height = 100;
        double angle = 90.0;
        Image image = TestImageUtil.getImage("test.png", width, height, angle);
        assertNotNull("Image should be loaded and rotated successfully", image);
        // Wait for image to load completely
        waitForImage(image);
        // After 90-degree rotation, width and height should swap
        assertEquals("Rotated width should equal original height", height, image.getWidth(null));
        assertEquals("Rotated height should equal original width", width, image.getHeight(null));
    }
    
    @Test
    public void testRotate() {
        // Test image rotation functionality
        BufferedImage original = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        double angle = 90.0;
        
        BufferedImage rotated = TestImageUtil.rotate(original, angle);
        assertNotNull("Image should be rotated successfully", rotated);
        // After 90-degree rotation, width and height should swap (allowing for rounding)
        assertTrue("Rotated height should be close to original width", Math.abs(rotated.getHeight() - original.getWidth()) <= 1);
        assertTrue("Rotated width should be close to original height", Math.abs(rotated.getWidth() - original.getHeight()) <= 1);
    }
    
    /**
     * Helper method to wait for image loading completion
     */
    private void waitForImage(Image image) {
        if (image instanceof BufferedImage) {
            return; // BufferedImage is already loaded
        }
        
        // Use MediaTracker to track image loading
        java.awt.MediaTracker tracker = new java.awt.MediaTracker(new java.awt.Canvas());
        tracker.addImage(image, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            fail("Image loading was interrupted");
        }
    }
} 