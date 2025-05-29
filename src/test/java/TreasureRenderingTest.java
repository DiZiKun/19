import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.view.TreasurePanel;
import com.forbidden.island.view.handler.IRendering;
import com.forbidden.island.view.handler.TreasureRendering;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test class for TreasureRendering
 */
public class TreasureRenderingTest {
    
    private TreasureRendering treasureRendering;
    
    @Before
    public void setUp() {
        // Initialize ElementEngine
        ElementEngine.init(2, 1);
        
        // Mock treasure cards
        TreasurePanel.treasureCards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            TreasurePanel.treasureCards.add(new JButton());
        }
        
        // Initialize TreasureRendering
        treasureRendering = new TreasureRendering();
    }
    
    @Test
    public void testInitialization() {
        assertNotNull("TreasureRendering should be properly initialized", treasureRendering);
        assertTrue("TreasureRendering should implement IRendering", 
                  treasureRendering instanceof IRendering);
        
        // Verify all treasure cards are initially disabled
        for (JButton card : TreasurePanel.treasureCards) {
            assertFalse("Treasure cards should be initially disabled", card.isEnabled());
        }
    }
    
    @Test
    public void testUpdate() {
        // Test update method
        treasureRendering.update();
        
        // Verify card states
        for (int i = 0; i < TreasurePanel.treasureCards.size(); i++) {
            JButton card = TreasurePanel.treasureCards.get(i);
            if (i < ElementEngine.getDisplayedTreasureCard().size()) {
                assertTrue("Displayed treasure cards should be enabled", card.isEnabled());
                assertNotNull("Displayed treasure cards should have an icon", card.getIcon());
            } else {
                assertFalse("Hidden treasure cards should be disabled", card.isEnabled());
                assertNull("Hidden treasure cards should not have an icon", card.getIcon());
            }
        }
    }
    
    @Test
    public void testFinish() {
        // Test finish method
        treasureRendering.finish();
        
        // Verify all cards are disabled
        for (JButton card : TreasurePanel.treasureCards) {
            assertFalse("Treasure cards should be disabled", card.isEnabled());
        }
    }
} 