import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.forbidden.island.view.handler.PlayerRendering;
import com.forbidden.island.view.handler.IRendering;
import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.view.GamePanel;
import com.forbidden.island.controller.ForbiddenIslandGame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;

/**
 * Test class for PlayerRendering
 */
public class PlayerRenderingTest {
    
    private PlayerRendering playerRendering;
    
    @Before
    public void setUp() {
        // Initialize ElementEngine
        ElementEngine.init(2, 1);
        
        // Mock player pawns list
        GamePanel.playerPawnList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            GamePanel.playerPawnList.add(new JButton());
        }
        
        // Mock player hand cards
        GamePanel.playerHandCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List<JButton> playerCards = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                playerCards.add(new JButton());
            }
            GamePanel.playerHandCards.add(playerCards);
        }
        
        // Initialize PlayerRendering
        playerRendering = new PlayerRendering();
    }
    
    @Test
    public void testInitialization() {
        assertNotNull("PlayerRendering should be properly initialized", playerRendering);
        assertTrue("PlayerRendering should implement IRendering", 
                  playerRendering instanceof IRendering);
        
        // Verify player pawns initialization
        for (int i = 0; i < GamePanel.playerPawnList.size(); i++) {
            if (i < ForbiddenIslandGame.getNumOfPlayer()) {
                assertNotNull("Player pawns should have an icon", GamePanel.playerPawnList.get(i).getIcon());
            } else {
                assertFalse("Unused player pawns should be disabled", GamePanel.playerPawnList.get(i).isEnabled());
            }
        }
    }
    
    @Test
    public void testUpdate() {
        // Test update method
        playerRendering.update();
        
        // Verify current player's hand cards state
        int currentPlayer = ForbiddenIslandGame.getRoundNum();
        for (int i = 0; i < ForbiddenIslandGame.getNumOfPlayer(); i++) {
            List<JButton> handCards = GamePanel.playerHandCards.get(i);
            for (JButton card : handCards) {
                if (i == currentPlayer) {
                    assertTrue("Current player's hand cards should be enabled", card.isEnabled());
                } else {
                    assertFalse("Other players' hand cards should be disabled", card.isEnabled());
                }
                assertTrue("All hand cards should be visible", card.isVisible());
            }
        }
    }
    
    @Test
    public void testFinish() {
        // Test finish method
        playerRendering.finish();
        
        // Verify all pawns and hand cards are disabled
        for (JButton pawn : GamePanel.playerPawnList) {
            assertFalse("Player pawns should be disabled", pawn.isEnabled());
        }
        
        for (List<JButton> handCards : GamePanel.playerHandCards) {
            for (JButton card : handCards) {
                assertFalse("Hand cards should be disabled", card.isEnabled());
            }
        }
    }
} 
