import com.forbidden.island.model.adventurer.Pilot;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Pilot
 * Based on Pilot class implementation:
 * 1. Inherits from Adventurer class
 * 2. Fixed id of 5
 * 3. Fixed name "Pilot"
 */
public class PilotTest {
    private Pilot pilot;

    @Before
    public void setUp() {
        // Create Pilot instance (order=5)
        pilot = new Pilot(5);
    }

    @Test
    public void testInitialization() {
        // Test initialization state
        assertEquals("ID should be 5", 5, pilot.getId());
        assertEquals("Order should be 5", 5, pilot.getOrder());
        assertEquals("Name should be Pilot", "Pilot", pilot.getName());
        assertEquals("Pawn image path should be correct", "/Pawns/Pilot.png", pilot.getPawnImg());
    }
} 
