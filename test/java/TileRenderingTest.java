import com.forbidden.island.view.*;
import com.forbidden.island.view.handler.IRendering;
import com.forbidden.island.view.handler.TileRendering;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for TileRendering
 */
public class TileRenderingTest {
    
    private TileRendering tileRendering;
    
    @Before
    public void setUp() {
        // Initialize ElementEngine with 2 players and water level 1
        ElementEngine.init(2, 1);
        
        // Mock TileGridPanel cards
        TileGridPanel.tileCards = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            TileGridPanel.tileCards.add(new JButton());
        }
        
        // Mock GamePanel player pawn list
        GamePanel.playerPawnList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            GamePanel.playerPawnList.add(new JButton());
        }
        
        // Mock GamePanel player hand cards
        GamePanel.playerHandCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List<JButton> playerCards = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                playerCards.add(new JButton());
            }
            GamePanel.playerHandCards.add(playerCards);
        }
        
        // Mock TreasurePanel treasure cards
        TreasurePanel.treasureCards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            TreasurePanel.treasureCards.add(new JButton());
        }
        
        // Mock FloodPanel flood cards
        FloodPanel.floodCards = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            FloodPanel.floodCards.add(new JButton());
        }
        
        // Initialize TileRendering
        tileRendering = new TileRendering();
    }
    
    @Test
    public void testInitialization() {
        assertNotNull("TileRendering should be initialized", tileRendering);
        assertTrue("TileRendering should implement IRendering", tileRendering instanceof IRendering);
    }
    
    @Test
    public void testUpdate() {
        // Test the update method
        tileRendering.update();
        // Note: Additional assertions would depend on the specific implementation
        // of how the update affects the tile state
    }
    
    @Test
    public void testFinish() {
        // Test the finish method
        tileRendering.finish();
        // Verify that all tiles are disabled
        for (JButton tile : TileGridPanel.tileCards) {
            assertFalse("Tile should be disabled after finish", tile.isEnabled());
        }
    }
} 