import com.forbidden.island.model.adventurer.Engineer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Engineer
 * Based on Engineer class implementation:
 * 1. Inherits from Adventurer class
 * 2. Special property shoreUpCount (number of shore up actions)
 * 3. Fixed id of 1
 * 4. Provides special operations:
 *    - ShoreUp (perform shore up, decrease count by 1)
 *    - resetShoreUpCount (reset shore up count to 1)
 */
public class EngineerTest {
    private Engineer engineer;

    @Before
    public void setUp() {
        // Create Engineer instance (order=1)
        engineer = new Engineer(1);
    }

    @Test
    public void testInitialization() {
        // Test initialization state
        assertEquals("ID should be 1", 1, engineer.getId());
        assertEquals("Initial shore up count should be 1", 1, engineer.getShoreUpCount());
    }

    @Test
    public void testShoreUp() {
        // Test shore up functionality
        engineer.ShoreUp();
        assertEquals("Count should be 0 after shore up", 0, engineer.getShoreUpCount());
    }

    @Test
    public void testResetShoreUpCount() {
        // Test reset shore up count
        engineer.ShoreUp();  // Use once first
        engineer.resetShoreUpCount();  // Reset
        assertEquals("Count should be 1 after reset", 1, engineer.getShoreUpCount());
    }
} 