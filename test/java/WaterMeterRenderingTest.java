import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.forbidden.island.view.handler.WaterMeterRendering;
import com.forbidden.island.view.handler.IRendering;
import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.view.TreasurePanel;
import com.forbidden.island.utils.ImageUtil;
import com.forbidden.island.utils.Constant;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * Test class for WaterMeterRendering
 */
public class WaterMeterRenderingTest {
    
    private WaterMeterRendering waterMeterRendering;
    
    @Before
    public void setUp() {
        // Initialize ElementEngine
        ElementEngine.init(2, 1);
        
        // Mock water meter label
        TreasurePanel.waterMeter = new JLabel();
        
        // Initialize WaterMeterRendering
        waterMeterRendering = new WaterMeterRendering();
    }
    
    @Test
    public void testInitialization() {
        assertNotNull("WaterMeterRendering should be properly initialized", waterMeterRendering);
        assertTrue("WaterMeterRendering should implement IRendering", 
                  waterMeterRendering instanceof IRendering);
        
        // Verify water meter initialization
        assertNotNull("Water meter should have an icon", TreasurePanel.waterMeter.getIcon());
    }
    
    @Test
    public void testUpdate() {
        // Test update method
        waterMeterRendering.update();
        
        // Verify water meter icon is updated
        assertNotNull("Water meter should have an icon", TreasurePanel.waterMeter.getIcon());
        
        // Create expected icon
        ImageIcon expectedIcon = new ImageIcon(
            ImageUtil.getImage(ElementEngine.getWaterMeterImg(),
                             Constant.WATER_METER_WIDTH,
                             Constant.WATER_METER_HEIGHT));
        ImageIcon actualIcon = (ImageIcon) TreasurePanel.waterMeter.getIcon();
        
        // Compare icon image data
        Image expectedImage = expectedIcon.getImage();
        Image actualImage = actualIcon.getImage();
        
        // Verify image dimensions
        assertEquals("Water meter icon width should match", 
                    expectedImage.getWidth(null), 
                    actualImage.getWidth(null));
        assertEquals("Water meter icon height should match", 
                    expectedImage.getHeight(null), 
                    actualImage.getHeight(null));
    }
    
    @Test
    public void testFinish() {
        // Test finish method
        waterMeterRendering.finish();
        
        // Verify water meter is hidden
        assertFalse("Water meter should be hidden", TreasurePanel.waterMeter.isVisible());
    }
} 
