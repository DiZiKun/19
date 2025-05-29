package com.forbidden.island.view;

import com.forbidden.island.controller.ForbiddenIslandGame;
import com.forbidden.island.model.adventurer.Adventurer;
import com.forbidden.island.model.enums.TileStatus;
import com.forbidden.island.utils.LogUtil;

import java.util.ArrayList;

/**
 * Tile class represents a single cell on the game map,
 * storing all information about the tile including image path, status, existence,
 * treasure capture state, and adventurers on this tile.
 */
public class Tile {
    // Tile ID (unique identifier for this Tile), default value is -1
    private int tileId = -1;
    // Current tile status (normal, flooded, or sunk)
    private TileStatus status;
    // Image path for UI display (complete path = imgFolder + imgFile)
    private String img;
    // Image folder path, defaults to "/Tiles/" or "/SubmersedTiles/"
    private String imgFolder;
    // Image filename, format like "1.png", auto-generated based on tileId
    private String imgFile;
    // Indicates if this tile still exists on the map (set to false when sunk)
    private boolean isExist;
    // List of adventurer IDs currently standing on this tile (stored as Integers)
    private ArrayList<Integer> adventurersOnBoard;
    // Whether treasure has been captured on this tile
    private boolean isCaptured = false;

    private static final int FOOLS_LANDING_TILE_ID = 14;

    /**
     * Create a tile in initial state with one adventurer on it.
     *
     * @param tileId tile number
     * @param playerID ID of player initially on this tile
     * @param isExist whether tile exists
     */
    public Tile(int tileId, int playerID, boolean isExist) {
        this.adventurersOnBoard = new ArrayList<>();
        this.tileId = tileId;
        this.status = TileStatus.Normal;
        this.imgFolder = "/Tiles/";
        this.imgFile = tileId + ".png";
        this.img = imgFolder + imgFile;
        this.adventurersOnBoard.add(playerID);
        this.isExist = isExist;
    }

    /**
     * Create a tile in initial state with no adventurers on it.
     *
     * @param tileId tile number
     * @param isExist whether tile exists
     */
    public Tile(int tileId, boolean isExist) {
        this.adventurersOnBoard = new ArrayList<>();
        this.tileId = tileId;
        this.status = TileStatus.Normal;
        this.imgFolder = "/Tiles/";
        this.imgFile = tileId + ".png";
        this.img = imgFolder + imgFile;
        this.isExist = isExist;
    }

    /**
     * Create an empty placeholder tile (used for boundary filling or inaccessible areas).
     *
     * @param isExist whether tile exists
     */
    public Tile(boolean isExist) {
        this.isExist = isExist;
    }

    /**
     * Move a player onto this tile (add their ID to the list).
     *
     * @param playerID player ID
     */
    public void moveOn(int playerID) {
        this.adventurersOnBoard.add(playerID);
    }

    /**
     * Remove a player from this tile (when moving away).
     *
     * @param adventurer adventurer object to remove
     */
    public void moveOff(Adventurer adventurer) {
        adventurersOnBoard.remove((Integer) adventurer.getId());
    }

    /**
     * Check if two adventurers are both on this tile.
     * Used to determine if item exchange or other operations are possible.
     *
     * @param sender initiator
     * @param receiver recipient
     * @return whether both are on the current tile
     */
    public boolean CanPass(Adventurer sender, Adventurer receiver) {
        return adventurersOnBoard.contains(sender.getId()) && adventurersOnBoard.contains(receiver.getId());
    }

    /**
     * Attempt to restore this tile from flooded state (shore up the tile).
     * Operation only allowed when status is Flooded.
     */
    public void shoreUp() {
        if (status == TileStatus.Flooded) {
            status = TileStatus.Normal;
            this.imgFolder = "/Tiles/";
            this.img = imgFolder + imgFile;
        } else {
            System.out.println("ERROR! Tile is not flooded");
        }
    }

    /**
     * Sink this tile (change status and update image).
     *
     * Normal → Flooded: Update image to "flooded version"
     * Flooded → Sunk: Remove image and existence flag, end game if critical location (like Fool's Landing)
     *
     * @return true if tile is completely sunk, false if only flooded
     */
    public boolean sinkTile() {
        if (status == TileStatus.Normal) {
            status = TileStatus.Flooded;
            imgFolder = "/SubmersedTiles/";
            img = imgFolder + imgFile;
            return false;
        } else if (status == TileStatus.Flooded) {
            status = TileStatus.Sunk;
            img = null;
            isExist = false;
            if (tileId == FOOLS_LANDING_TILE_ID) {
                ForbiddenIslandGame.finish(false);  // End game (failure)
                LogUtil.console("[!] Fool's Landing Is Flooded!");
            }
            return true;
        } else {
            LogUtil.console("ERROR! This tile has sunk");
            return true;
        }
    }

    /**
     * Set this tile to captured state and update image file.
     * Usually called after players successfully capture the treasure on the tile.
     */
    public void setCaptured() {
        isCaptured = true;
        this.imgFile = tileId + 24 + ".png";    // Special image indicating "captured"
        this.img = imgFolder + imgFile;
    }

    /**
     * Check if this tile has not been captured yet.
     *
     * @return true if not yet captured
     */
    public boolean isUnCaptured() {
        return !isCaptured;
    }

    public int getTileId() {
        return tileId;
    }

    public TileStatus getStatus() {
        return status;
    }

    public String getImg() {
        return img;
    }

    public boolean isExist() {
        return isExist;
    }

    public ArrayList<Integer> getPlayerOnBoard() {
        return adventurersOnBoard;
    }
}
