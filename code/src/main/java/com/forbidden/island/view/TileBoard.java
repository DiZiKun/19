package com.forbidden.island.view;

import java.util.ArrayList;

/**
 * TileBoard class represents the game map data structure, composed of Tiles
 */
public class TileBoard {
    // 6x6 game map (tile) array
    private final Tile[][] tileMap;
    // List of all tile IDs (in order consistent with map layout)
    private final ArrayList<Integer> tiles;
    // Whether current player is allowed to move
    private boolean canMove;
    // Whether current player is allowed to shore up tiles
    private boolean canShoreUp;

    /**
     * Constructor, initializes the game map
     * @param players list of player IDs
     * @param tiles list of tile IDs (shuffled)
     */
    public TileBoard(ArrayList<Integer> players, ArrayList<Integer> tiles) {
        this.tiles = tiles;
        tileMap = new Tile[6][6]; // Initialize 6x6 map grid
        int tileIdx = 0; // Current tile index being processed

        // Traverse map cells
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                int flatIndex = i * 6 + j;

                // If this is an empty cell (no tile placement)
                if (Map.blankLayout.contains(flatIndex)) {
                    tileMap[i][j] = new Tile(false); // Create empty Tile
                } else {
                    int tileId = this.tiles.get(tileIdx);

                    // If there's a player initially placed on this tile
                    if (players.contains(tileId - 9)) {
                        int playerID = tileId - 9;
                        tileMap[i][j] = new Tile(tileId, playerID, true); // Initialize tile with player
                        // Set player's initial position
                        ElementEngine.getAdventurers()[players.indexOf(playerID)].setPosition(i, j);
                    } else {
                        tileMap[i][j] = new Tile(tileId, true); // Normal tile
                    }
                    tileIdx++; // Move to next tile
                }
            }
        }
    }

    /**
     * Sink a group of tiles
     * @param sinkTiles list of tile IDs to sink
     */
    public void sinkTiles(ArrayList<Integer> sinkTiles) {
        for (int sinkTile : sinkTiles) {
            // Get coordinates for this tile
            int[] coords = Map.coordinatesMatcher.get(this.tiles.indexOf(sinkTile));

            // If tile is completely removed (sunk twice)
            if (tileMap[coords[0]][coords[1]].sinkTile()) {
                // Remove this card from flood deck
                ElementEngine.getFloodDeck().removeFloodCard(sinkTile);

                // If there are still players on this tile
                if (!tileMap[coords[0]][coords[1]].getPlayerOnBoard().isEmpty()) {
                    for (int player : tileMap[coords[0]][coords[1]].getPlayerOnBoard()) {
                        LogUtil.console(Map.adventurerMatcher.get(player) + " Has Fallen Into Sea");
                    }

                    // Mark need for rescue
                    ForbiddenIslandGame.setNeed2save(true);
                    ForbiddenIslandGame.setPlayerIDinWater(tileMap[coords[0]][coords[1]].getPlayerOnBoard());

                    // Clear all players from tile
                    tileMap[coords[0]][coords[1]].getPlayerOnBoard().clear();
                }
            }
        }

        // If players have fallen into water, trigger fake round for rescue logic
        if (ForbiddenIslandGame.isNeed2save()) {
            ForbiddenIslandGame.setInFakeRound(true); // Start fake round
            ForbiddenIslandGame.setFakeRoundNum(ForbiddenIslandGame.getRoundNum()); // Save current round number
            ForbiddenIslandGame.SavePlayersRound(); // Save player states
        }

        // Re-render game map
        RenderingEngine.getBoardRendering().update();
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public void setCanShoreUp(boolean canShoreUp) {
        this.canShoreUp = canShoreUp;
    }

    /**
     * Check if any shrine (treasure location) is completely flooded.
     * Each treasure has two shrine locations (tiles), and if both tiles for a treasure
     * are sunk before the treasure is captured, the game is lost.
     *
     * @return true if any treasure's both shrine locations are sunk and uncaptured
     */
    public boolean isShrinesFlooded() {
        // Array to track shrine pairs, one element per treasure type (4 treasures total)
        // Initial value 1 means both shrines are safe
        // Value 0 means one shrine is sunk
        // Value -1 means both shrines are sunk
        int[] isShrinesFlooded = {1, 1, 1, 1};

        // Check all tiles on the board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                switch (tileMap[i][j].getTileId()) {
                    case 1:
                    case 2:  // First treasure shrine pair (tiles 1 and 2)
                        if (!tileMap[i][j].isExist() && tileMap[i][j].isUnCaptured()) {
                            isShrinesFlooded[0]--;  // Decrement counter for first treasure
                        }
                        break;
                    case 3:
                    case 4:  // Second treasure shrine pair (tiles 3 and 4)
                        if (!tileMap[i][j].isExist() && tileMap[i][j].isUnCaptured()) {
                            isShrinesFlooded[1]--;  // Decrement counter for second treasure
                        }
                        break;
                    case 5:
                    case 6:  // Third treasure shrine pair (tiles 5 and 6)
                        if (!tileMap[i][j].isExist() && tileMap[i][j].isUnCaptured()) {
                            isShrinesFlooded[2]--;  // Decrement counter for third treasure
                        }
                        break;
                    case 7:
                    case 8:  // Fourth treasure shrine pair (tiles 7 and 8)
                        if (!tileMap[i][j].isExist() && tileMap[i][j].isUnCaptured()) {
                            isShrinesFlooded[3]--;  // Decrement counter for fourth treasure
                        }
                        break;
                }
            }
        }

        // Check if any treasure has both shrines sunk (counter reached -1)
        // If true, this means a treasure is lost and game should end
        for (int isFlooded : isShrinesFlooded) {
            if (isFlooded == -1) {
                return true;
            }
        }
        return false;
    }

    public boolean isCanShoreUp() {
        return canShoreUp;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public Tile getTile(int x, int y) {
        return tileMap[x][y];
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }
}
