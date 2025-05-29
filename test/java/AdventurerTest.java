import com.forbidden.island.model.adventurer.Engineer;
import com.forbidden.island.model.enums.TreasureFigurines;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Test class for Adventurer
 * Based on Adventurer abstract class implementation:
 * 1. Contains basic properties: id, order, x, y, targetX, targetY, shoreUpX, shoreUpY, name, pawnImg
 * 2. Contains two ArrayLists: handCards (cards in hand) and capturedFigurines (collected treasures)
 * 3. Provides basic operations: move, set position, add cards, collect treasures, etc.
 * 4. Uses Map.numberMatcher for coordinate conversion
 * 
 * Note: Since Adventurer is an abstract class, we use Engineer subclass for testing
 */
public class AdventurerTest {
    private Engineer adventurer;  // Using Engineer as concrete implementation for testing

    @Before
    public void setUp() {
        // Create an Engineer instance (order=1)
        adventurer = new Engineer(1);
    }

    @Test
    public void testSetPosition() {
        // Test position setting
        adventurer.setPosition(2, 3);
        assertEquals("X coordinate should be 2", 2, adventurer.getX());
        assertEquals("Y coordinate should be 3", 3, adventurer.getY());
    }

    @Test
    public void testMove() {
        // Test movement functionality
        adventurer.setMoveTarget(4, 5);
        adventurer.Move();
        assertEquals("X coordinate after move should be 4", 4, adventurer.getX());
        assertEquals("Y coordinate after move should be 5", 5, adventurer.getY());
    }

    @Test
    public void testHandCards() {
        // Test hand cards management
        ArrayList<Integer> newCards = new ArrayList<>();
        newCards.add(1);
        newCards.add(2);
        adventurer.setHandCards(newCards);
        assertEquals("Should have 2 cards in hand", 2, adventurer.getHandCards().size());
    }

    @Test
    public void testCapturedFigurines() {
        // Test treasure collection
        adventurer.addCapturedFigurine(TreasureFigurines.Earth);
        assertEquals("Should have 1 treasure", 1, adventurer.getCapturedFigurines().size());
    }

    @Test
    public void testBasicProperties() {
        // Test basic properties
        assertEquals("Order should be 1", 1, adventurer.getOrder());
        assertEquals("Name should be Engineer", "Engineer", adventurer.getName());
        assertEquals("Pawn image path should be correct", "/Pawns/Engineer.png", adventurer.getPawnImg());
    }
} 
