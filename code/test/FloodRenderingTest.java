import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.forbidden.island.view.handler.FloodRendering;

import java.util.ArrayList;
import javax.swing.JButton;

/**
 * Test class for FloodRendering
 */
public class FloodRenderingTest {

    private FloodRendering floodRendering;

    @Before
    public void setUp() {
        // Initialize ElementEngine
        ElementEngine.init(2, 1);

        // Mock FloodPanel cards
        FloodPanel.floodCards = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            FloodPanel.floodCards.add(new JButton());
        }

        // Initialize FloodRendering
        floodRendering = new FloodRendering();
    }

    @Test
    public void testInitialization() {
        assertNotNull("FloodRendering should be properly initialized", floodRendering);
        assertTrue("FloodRendering should implement IRendering interface",
                  floodRendering instanceof IRendering);
    }

    @Test
    public void testUpdate() {
        // Test update method
        floodRendering.update();

        // Verify card states
        for (int i = 0; i < FloodPanel.floodCards.size(); i++) {
            JButton card = FloodPanel.floodCards.get(i);
            if (i < ElementEngine.getFloodDeck().getCards().size()) {
                assertTrue("Valid flood cards should be visible", card.isVisible());
                assertTrue("Valid flood cards should be enabled", card.isEnabled());
                assertNotNull("Valid flood cards should have an icon", card.getIcon());
            } else {
                assertFalse("Invalid flood cards should not be visible", card.isVisible());
                assertFalse("Invalid flood cards should be disabled", card.isEnabled());
            }
        }
    }

    @Test
    public void testFinish() {
        // Test finish method
        floodRendering.finish();

        // Verify all cards are disabled
        for (JButton card : FloodPanel.floodCards) {
            assertFalse("Cards should be disabled", card.isEnabled());
        }
    }
} 