import com.forbidden.island.model.adventurer.Explorer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for Explorer
 * Based on Explorer class implementation:
 * 1. Inherits from Adventurer class
 * 2. Fixed id of 2
 * 3. Fixed name "Explorer"
 */
public class ExplorerTest {
    private Explorer explorer;

    @Before
    public void setUp() {
        // Create Explorer instance (order=6)
        explorer = new Explorer(6);
    }

    @Test
    public void testInitialization() {
        // Test initialization state
        assertEquals("ID should be 2", 2, explorer.getId());
        assertEquals("Order should be 6", 6, explorer.getOrder());
        assertEquals("Name should be Explorer", "Explorer", explorer.getName());
        assertEquals("Pawn image path should be correct", "/Pawns/Explorer.png", explorer.getPawnImg());
    }
} 