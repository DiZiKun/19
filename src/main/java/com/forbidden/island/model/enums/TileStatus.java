package com.forbidden.island.model.enums;

/**
 * Represents the status of tiles on the game map.
 */
public enum TileStatus {
    /** Normal status, tile is in normal condition */
    Normal,
    /** Flooded status, tile is partially submerged */
    Flooded,
    /** Sunk status, tile is completely submerged and impassable */
    Sunk
}