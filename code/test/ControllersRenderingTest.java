import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.forbidden.island.view.handler.ControllersRendering;

import java.util.ArrayList;
import javax.swing.JButton;

/**
 * Test class for ControllersRendering
 */
public class ControllersRenderingTest {
    
    private ControllersRendering controllersRendering;
    
    @Before
    public void setUp() {
        // Initialize ElementEngine
        ElementEngine.init(2, 1);
        
        // Mock operation panel buttons
        OperatePanel.opButtons = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            OperatePanel.opButtons.add(new JButton());
        }
        
        // Reset game state
        ForbiddenIslandGame.setInFakeRound(false);
        ForbiddenIslandGame.setNeed2save(false);
        
        // Initialize ControllersRendering
        controllersRendering = new ControllersRendering();
    }
    
    @Test
    public void testInitialization() {
        assertNotNull("ControllersRendering should be properly initialized", controllersRendering);
        assertTrue("ControllersRendering should implement IRendering", 
                  controllersRendering instanceof IRendering);
    }
    
    @Test
    public void testUpdateInNormalRound() {
        // First set to normal round with no need to save
        ForbiddenIslandGame.setInFakeRound(false);
        ForbiddenIslandGame.setNeed2save(false);
        
        // Update and verify all buttons are enabled
        controllersRendering.update();
        for (JButton button : OperatePanel.opButtons) {
            assertTrue("Buttons should be enabled when no save is needed", button.isEnabled());
        }
        
        // Then set need to save
        ForbiddenIslandGame.setNeed2save(true);
        
        // Update and verify all buttons are disabled
        controllersRendering.update();
        for (JButton button : OperatePanel.opButtons) {
            assertFalse("Buttons should be disabled when save is needed", button.isEnabled());
        }
    }
    
    @Test
    public void testFinish() {
        // Test finish method
        controllersRendering.finish();
        
        // Verify all buttons are disabled
        for (JButton button : OperatePanel.opButtons) {
            assertFalse("Buttons should be disabled after finish", button.isEnabled());
        }
    }
    
    @Test
    public void testUpdateWithDifferentGameStates() {
        // Test different game state combinations
        
        // 1. Fake round + No need to save
        ForbiddenIslandGame.setNeed2save(false);
        ForbiddenIslandGame.setInFakeRound(true);
        controllersRendering.update();
        assertTrue("All buttons should be enabled in fake round with no save needed", 
                  OperatePanel.opButtons.get(0).isEnabled());
        
        // 2. Fake round + Need to save
        ForbiddenIslandGame.setNeed2save(true);
        controllersRendering.update();
        assertTrue("Save button should be enabled in fake round when save is needed", 
                  OperatePanel.opButtons.get(1).isEnabled());
        assertFalse("Other buttons should be disabled in fake round when save is needed", 
                   OperatePanel.opButtons.get(0).isEnabled());
        
        // 3. Normal round + No need to save
        ForbiddenIslandGame.setNeed2save(false);
        ForbiddenIslandGame.setInFakeRound(false);
        controllersRendering.update();
        assertTrue("Buttons should be enabled in normal round with no save needed", 
                  OperatePanel.opButtons.get(0).isEnabled());
        
        // 4. Normal round + Need to save
        ForbiddenIslandGame.setNeed2save(true);
        controllersRendering.update();
        assertFalse("Buttons should be disabled in normal round when save is needed", 
                   OperatePanel.opButtons.get(0).isEnabled());
    }
} 