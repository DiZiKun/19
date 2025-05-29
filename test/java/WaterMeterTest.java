import com.forbidden.island.view.WaterMeter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for WaterMeter
 */
public class WaterMeterTest {
    
    private WaterMeter waterMeter;
    private static final int MAX_LEVEL = 10;
    
    @Before
    public void setUp() {
        // Initialize water meter at level 1 (easiest)
        waterMeter = new WaterMeter(1);
    }
    
    @Test
    public void testInitialization() {
        // Test initial state
        assertEquals("Initial water meter image should be correct",
                    "/WaterMeter/1.png", waterMeter.getImg());
        assertEquals("Initial flood card count should be 2",
                    2, waterMeter.getFloodCardCount());
    }
    
    @Test
    public void testWaterRise() {
        // Test water rise from level 1 to 2 (same flood card count)
        waterMeter.WaterRise();
        assertEquals("Water meter image should update",
                    "/WaterMeter/2.png", waterMeter.getImg());
        assertEquals("Flood card count should remain 2",
                    2, waterMeter.getFloodCardCount());
        
        // Test water rise to level 3 (increased flood card count)
        waterMeter.WaterRise();
        assertEquals("Water meter image should update",
                    "/WaterMeter/3.png", waterMeter.getImg());
        assertEquals("Flood card count should increase to 3",
                    3, waterMeter.getFloodCardCount());
    }
    
    @Test
    public void testFloodCardCountRanges() {
        // Test level 1-2: 2 cards
        assertEquals("Levels 1-2 should draw 2 cards", 2, waterMeter.getFloodCardCount());
        
        // Test level 3-5: 3 cards
        waterMeter.WaterRise();
        waterMeter.WaterRise();  // Level 3
        assertEquals("Levels 3-5 should draw 3 cards", 3, waterMeter.getFloodCardCount());
        
        // Test level 6-7: 4 cards
        waterMeter.WaterRise();
        waterMeter.WaterRise();
        waterMeter.WaterRise();  // Level 6
        assertEquals("Levels 6-7 should draw 4 cards", 4, waterMeter.getFloodCardCount());
        
        // Test level 8-9: 5 cards
        waterMeter.WaterRise();
        waterMeter.WaterRise();  // Level 8
        assertEquals("Levels 8-9 should draw 5 cards", 5, waterMeter.getFloodCardCount());
    }
    
    @Test
    public void testGameEndCondition() {
        int currentLevel = 1;
        
        // Test water rise up to level 9
        while (currentLevel < MAX_LEVEL - 1) {
            waterMeter.WaterRise();
            currentLevel++;
            assertEquals("Water meter should show correct level",
                        "/WaterMeter/" + currentLevel + ".png", waterMeter.getImg());
        }
        
        // Test final rise to level 10
        waterMeter.WaterRise();
        currentLevel++;
        assertEquals("Water meter should show final level",
                    "/WaterMeter/" + currentLevel + ".png", waterMeter.getImg());
    }
} 