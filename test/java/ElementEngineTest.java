import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.view.WaterMeter;
import com.forbidden.island.model.adventurer.Adventurer;
import com.forbidden.island.model.cards.FloodDeck;
import com.forbidden.island.model.cards.TreasureDeck;
import com.forbidden.island.view.TileBoard;

public class ElementEngineTest {
    
    @Before
    public void setUp() {
        // Initialize game with 2 players and water level 1
        ElementEngine.init(2, 1);
    }
    
    @Test
    public void testInitialization() {
        // Test basic component initialization
        assertNotNull("Water meter should be initialized", ElementEngine.getWaterMeter());
        assertNotNull("Flood deck should be initialized", ElementEngine.getFloodDeck());
        assertNotNull("Treasure deck should be initialized", ElementEngine.getTreasureDeck());
        assertNotNull("Board should be initialized", ElementEngine.getBoard());
        assertNotNull("Adventurers should be initialized", ElementEngine.getAdventurers());
        
        // Test player count
        assertEquals("Should have 2 adventurers", 2, ElementEngine.getAdventurers().length);
        
        // Test initial water level
        assertEquals("Initial water meter image should be correct", 
                    "/WaterMeter/1.png", ElementEngine.getWaterMeterImg());
    }
    
    @Test
    public void testSelectPawn() {
        // Test pawn selection
        ElementEngine.selectPawn(0);
        assertEquals("Selected pawn should be 0", 0, ElementEngine.getSelectedPawn());
        
        // Test selection reset
        ElementEngine.selectPawn(-1);
        assertEquals("Selected pawn should be reset to -1", -1, ElementEngine.getSelectedPawn());
        assertTrue("Cards in round should be empty after reset", 
                  ElementEngine.getCardsInRound().isEmpty());
        assertTrue("Selected pawns should be empty after reset", 
                  ElementEngine.getSelectedPawns().isEmpty());
    }
    
    @Test
    public void testSpecialActionTile() {
        // Test special action tile setting and resetting
        int[] coords = {2, 3};
        ElementEngine.nextTile(coords);
        
        int[] specialTile = ElementEngine.getSpecialActionTile();
        assertEquals("Special action tile X coordinate should match", 2, specialTile[0]);
        assertEquals("Special action tile Y coordinate should match", 3, specialTile[1]);
        
        ElementEngine.resetSpecialActionTile();
        specialTile = ElementEngine.getSpecialActionTile();
        assertEquals("Special action tile should be reset to -1", -1, specialTile[0]);
        assertEquals("Special action tile should be reset to -1", -1, specialTile[1]);
    }
    
    @Test
    public void testDisplayedTreasureCards() {
        // Test displayed treasure cards
        assertTrue("Initially displayed treasure cards should be empty", 
                  ElementEngine.getDisplayedTreasureCard().isEmpty());
    }
    
    @Test
    public void testCardsInRound() {
        // Test cards in current round
        assertTrue("Initially cards in round should be empty", 
                  ElementEngine.getCardsInRound().isEmpty());
        
        // Test round cards reset
        ElementEngine.resetCardsInRound();
        assertTrue("Cards in round should be empty after reset", 
                  ElementEngine.getCardsInRound().isEmpty());
    }
} 
