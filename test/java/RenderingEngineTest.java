import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import com.forbidden.island.view.handler.*;
import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.view.GamePanel;
import com.forbidden.island.view.TileGridPanel;
import com.forbidden.island.view.TreasurePanel;
import com.forbidden.island.view.FloodPanel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;

/**
 * Test class for RenderingEngine
 */
public class RenderingEngineTest {
    
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
        
        // Initialize RenderingEngine
        RenderingEngine.init();
    }
    
    @Test
    public void testInitialization() {
        assertNotNull("TileRendering should be initialized", RenderingEngine.getBoardRendering());
        assertNotNull("FloodRendering should be initialized", RenderingEngine.getFloodRendering());
        assertNotNull("PlayerRendering should be initialized", RenderingEngine.getPlayerRendering());
        assertNotNull("TreasureRendering should be initialized", RenderingEngine.getTreasureRendering());
        assertNotNull("WaterMeterRendering should be initialized", RenderingEngine.getWaterMeterRendering());
        assertNotNull("ControllersRendering should be initialized", RenderingEngine.getControllersRendering());
    }
    
    @Test
    public void testRenderingComponents() {
        // Test that each component is an instance of IRendering
        assertTrue("TileRendering should implement IRendering", 
                  RenderingEngine.getBoardRendering() instanceof IRendering);
        assertTrue("FloodRendering should implement IRendering", 
                  RenderingEngine.getFloodRendering() instanceof IRendering);
        assertTrue("PlayerRendering should implement IRendering", 
                  RenderingEngine.getPlayerRendering() instanceof IRendering);
        assertTrue("TreasureRendering should implement IRendering", 
                  RenderingEngine.getTreasureRendering() instanceof IRendering);
        assertTrue("WaterMeterRendering should implement IRendering", 
                  RenderingEngine.getWaterMeterRendering() instanceof IRendering);
        assertTrue("ControllersRendering should implement IRendering", 
                  RenderingEngine.getControllersRendering() instanceof IRendering);
    }
    
    @Test
    public void testComponentsUpdate() {
        // Test that update() can be called on each component
        RenderingEngine.getBoardRendering().update();
        RenderingEngine.getFloodRendering().update();
        RenderingEngine.getPlayerRendering().update();
        RenderingEngine.getTreasureRendering().update();
        RenderingEngine.getWaterMeterRendering().update();
        RenderingEngine.getControllersRendering().update();
    }
    
    @Test
    public void testComponentsFinish() {
        // Test that finish() can be called on each component
        RenderingEngine.getBoardRendering().finish();
        RenderingEngine.getFloodRendering().finish();
        RenderingEngine.getPlayerRendering().finish();
        RenderingEngine.getTreasureRendering().finish();
        RenderingEngine.getWaterMeterRendering().finish();
        RenderingEngine.getControllersRendering().finish();
    }
} 
