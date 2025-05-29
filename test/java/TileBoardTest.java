import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.view.Tile;
import com.forbidden.island.view.TileBoard;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TileBoardTest {
    private TileBoard board;
    private ArrayList<Integer> players;
    private ArrayList<Integer> tiles;
    
    @Before
    public void setUp() {
        // First initialize ElementEngine
        ElementEngine.init(2, 1);
        
        // Initialize player list (2 players)
        players = new ArrayList<>();
        players.add(0); // Player 1
        players.add(1); // Player 2
        
        // Initialize terrain tile list
        tiles = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            tiles.add(i);
        }
        
        // Create game board
        board = new TileBoard(players, tiles);
    }
    
    @Test
    public void testBoardInitialization() {
        // Test board dimensions
        assertEquals("Board should be 6x6", 6, board.getTileMap().length);
        assertEquals("Board should be 6x6", 6, board.getTileMap()[0].length);
        
        // Test each tile is properly initialized
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                assertNotNull("Tile should not be null", board.getTile(i, j));
            }
        }
    }
    
    @Test
    public void testMovementFlags() {
        // Test movement flag setting and getting
        board.setCanMove(true);
        assertTrue("Should be able to move", board.isCanMove());
        
        board.setCanMove(false);
        assertFalse("Should not be able to move", board.isCanMove());
    }
    
    @Test
    public void testShoreUpFlags() {
        // Test shore up flag setting and getting
        board.setCanShoreUp(true);
        assertTrue("Should be able to shore up", board.isCanShoreUp());
        
        board.setCanShoreUp(false);
        assertFalse("Should not be able to shore up", board.isCanShoreUp());
    }
    
    @Test
    public void testTileAccess() {
        // Test accessing specific tile
        Tile tile = board.getTile(0, 0);
        assertNotNull("Should be able to access tile at (0,0)", tile);
        
        // Test getting entire tile map
        Tile[][] tileMap = board.getTileMap();
        assertNotNull("Should be able to get tile map", tileMap);
        assertEquals("Tile map should be 6x6", 6, tileMap.length);
        assertEquals("Tile map should be 6x6", 6, tileMap[0].length);
    }
} 