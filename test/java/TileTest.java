import com.forbidden.island.model.adventurer.Engineer;
import com.forbidden.island.model.enums.TileStatus;
import com.forbidden.island.view.Tile;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.forbidden.island.model.adventurer.Adventurer;
import com.forbidden.island.model.adventurer.Diver;

/**
 * Test class for Tile
 * Based on Tile class implementation:
 * 1. Basic properties:
 *    - tileId: terrain ID
 *    - status: tile status (Normal, Flooded, Sunk)
 *    - img: image path
 *    - isExist: whether the tile exists
 * 2. Player related:
 *    - adventurersOnBoard: list of player IDs on this tile
 *    - moveOn/moveOff: player movement
 *    - CanPass: check if two players are on the same tile
 * 3. Tile status changes:
 *    - shoreUp: reinforce (from Flooded to Normal)
 *    - sinkTile: sink (Normal->Flooded->Sunk)
 * 4. Treasure related:
 *    - isCaptured: whether the treasure is obtained
 *    - setCaptured: set treasure as obtained
 */
public class TileTest {
    private Tile normalTile;
    private Tile playerTile;
    private Tile emptyTile;
    private Adventurer player1;
    private Adventurer player2;

    @Before
    public void setUp() {
        // Create test tiles
        normalTile = new Tile(1, true);  // Normal tile with ID 1
        playerTile = new Tile(2, 0, true);  // Tile with player 0
        emptyTile = new Tile(false);  // Non-existent tile
        
        // Create test players
        player1 = new Diver(0);  // Player 0
        player2 = new Diver(1);  // Player 1
    }

    @Test
    public void testInitialization() {
        // Test normal tile initialization
        assertEquals("Tile ID should be 1", 1, normalTile.getTileId());
        assertEquals("Initial status should be Normal", TileStatus.Normal, normalTile.getStatus());
        assertTrue("Tile should exist", normalTile.isExist());
        
        // Test player tile initialization
        assertEquals("Tile ID should be 2", 2, playerTile.getTileId());
        assertTrue("Player 0 should be on tile", playerTile.getPlayerOnBoard().contains(0));
        
        // Test empty tile initialization
        assertFalse("Empty tile should not exist", emptyTile.isExist());
    }

    @Test
    public void testPlayerMovement() {
        // Test moving player onto tile
        normalTile.moveOn(player1.getId());
        assertTrue("Player should be on tile", normalTile.getPlayerOnBoard().contains(player1.getId()));
        
        // Test moving player off tile
        normalTile.moveOff(player1);
        assertFalse("Player should not be on tile", normalTile.getPlayerOnBoard().contains(player1.getId()));
    }

    @Test
    public void testTileFlooding() {
        // Test flooding sequence
        assertFalse("First flood should not sink tile", normalTile.sinkTile());
        assertEquals("Status should be Flooded", TileStatus.Flooded, normalTile.getStatus());
        
        // Test shore up on flooded tile
        normalTile.shoreUp();
        assertEquals("Status should return to Normal", TileStatus.Normal, normalTile.getStatus());
        
        // Test complete sinking
        normalTile.sinkTile();  // First flood
        assertTrue("Second flood should sink tile", normalTile.sinkTile());  // Second flood
        assertEquals("Status should be Sunk", TileStatus.Sunk, normalTile.getStatus());
        assertFalse("Tile should no longer exist", normalTile.isExist());
    }

    @Test
    public void testTreasureCapture() {
        // Test initial state
        assertTrue("Tile should start uncaptured", normalTile.isUnCaptured());
        
        // Test capture
        normalTile.setCaptured();
        assertFalse("Tile should be captured", normalTile.isUnCaptured());
        assertTrue("Image path should be updated", 
                  normalTile.getImg().contains(String.valueOf(normalTile.getTileId() + 24)));
    }
} 