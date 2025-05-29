import com.forbidden.island.model.adventurer.Navigator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Navigator
 * Based on Navigator class implementation:
 * 1. Inherits from Adventurer class
 * 2. Fixed id of 4
 * 3. Fixed name "Navigator"
 */
public class NavigatorTest {
    private Navigator navigator;

    @Before
    public void setUp() {
        // Create Navigator instance (order=2)
        navigator = new Navigator(2);
    }

    @Test
    public void testInitialization() {
        // Test initialization state
        assertEquals("ID should be 4", 4, navigator.getId());
        assertEquals("Order should be 2", 2, navigator.getOrder());
        assertEquals("Name should be Navigator", "Navigator", navigator.getName());
        assertEquals("Pawn image path should be correct", "/Pawns/Navigator.png", navigator.getPawnImg());
    }
} 