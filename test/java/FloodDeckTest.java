import com.forbidden.island.model.cards.FloodDeck;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Test class for FloodDeck
 * Based on FloodDeck class implementation:
 * 1. Manages flood cards
 * 2. Provides operations for drawing, discarding, and managing flood cards
 * 3. Tracks initialization state and number of cards
 */
public class FloodDeckTest {
    private FloodDeck floodDeck;

    @Before
    public void setUp() {
        // Create FloodDeck, initially isInit is true and Num is 6
        floodDeck = new FloodDeck();
    }

    @Test
    public void testGetCards() {
        // Test getCards method, should return displayedCards
        ArrayList<Integer> cards = floodDeck.getCards();
        assertNotNull(cards);
    }

    @Test
    public void testDiscard() {
        floodDeck.discard();
        ArrayList<Integer> cards = floodDeck.getCards();
        assertNotNull("Should be able to get cards after discard", cards);
    }

    @Test
    public void testPutBack2Top() {
        floodDeck.putBack2Top();
        ArrayList<Integer> cards = floodDeck.getCards();
        assertNotNull("Should be able to get cards after putting back", cards);
    }

    @Test
    public void testRemoveFloodCard() {
        ArrayList<Integer> cards = floodDeck.getCards();
        if (!cards.isEmpty()) {
            floodDeck.removeFloodCard(cards.get(0));
        }
    }
} 
