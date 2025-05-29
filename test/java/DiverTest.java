import com.forbidden.island.model.adventurer.Diver;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Diver
 * Based on Diver class implementation:
 * 1. Inherits from Adventurer class
 * 2. Fixed id of 0
 * 3. Fixed name "Diver"
 */
public class DiverTest {
    private Diver diver;

    @Before
    public void setUp() {
        // Create Diver instance (order=4)
        diver = new Diver(4);
    }

    @Test
    public void testInitialization() {
        // Test initialization state
        assertEquals("ID should be 0", 0, diver.getId());
        assertEquals("Order should be 4", 4, diver.getOrder());
        assertEquals("Name should be Diver", "Diver", diver.getName());
        assertEquals("Pawn image path should be correct", "/Pawns/Diver.png", diver.getPawnImg());
    }
} 
