import com.forbidden.island.model.adventurer.Messenger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Messenger
 * Based on Messenger class implementation:
 * 1. Inherits from Adventurer class
 * 2. Fixed id of 3
 * 3. Fixed name "Messenger"
 */
public class MessengerTest {
    private Messenger messenger;

    @Before
    public void setUp() {
        // Create Messenger instance (order=3)
        messenger = new Messenger(3);
    }

    @Test
    public void testInitialization() {
        // Test initialization state
        assertEquals("ID should be 3", 3, messenger.getId());
        assertEquals("Order should be 3", 3, messenger.getOrder());
        assertEquals("Name should be Messenger", "Messenger", messenger.getName());
        assertEquals("Pawn image path should be correct", "/Pawns/Messenger.png", messenger.getPawnImg());
    }
} 