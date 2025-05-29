import com.forbidden.island.model.cards.TreasureDeck;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Test class for TreasureDeck
 * Based on TreasureDeck class implementation:
 * 1. Manages treasure cards
 * 2. Provides operations for drawing and discarding cards
 * 3. Handles special "Waters Rise" cards separately
 */
public class TreasureDeckTest {
    private TreasureDeck treasureDeck;

    @Before
    public void setUp() {
        // Create TreasureDeck, initially Num is 2, contains cards 0-27
        treasureDeck = new TreasureDeck();
    }

    @Test
    public void testGetCards() {
        // Test getCards method, should return NTreasureCards
        ArrayList<Integer> cards = treasureDeck.getCards();
        assertNotNull(cards);
    }

    @Test
    public void testGetNoRiseCards() {
        ArrayList<Integer> cards = treasureDeck.getNoRiseCards();
        assertNotNull("Should be able to get cards", cards);
    }

    @Test
    public void testDiscard() {
        ArrayList<Integer> cards = treasureDeck.getCards();
        if (!cards.isEmpty()) {
            treasureDeck.discard(cards.get(0));
        }
    }
} 
