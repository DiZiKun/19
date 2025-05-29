package com.forbidden.island.view.handler;

/**
 * RenderingEngine is the game's rendering manager, responsible for unified management
 * and provision of rendering objects for each sub-module.
 * These sub-modules are responsible for rendering different parts of the game
 * (such as tiles, flood cards, players, treasures, water meter, and controllers).
 * This class implements the singleton pattern through static members and methods,
 * ensuring uniqueness and unified access to rendering components in the game.
 */
public class RenderingEngine {
    // Static instances of rendering modules, responsible for rendering different game elements
    private static TileRendering tileRendering;            // Tile (map grid) renderer
    private static FloodRendering floodRendering;          // Flood card renderer
    private static PlayerRendering playerRendering;        // Player elements renderer
    private static TreasureRendering treasureRendering;    // Treasure card renderer
    private static WaterMeterRendering waterMeterRendering;// Water meter renderer
    private static ControllersRendering controllersRendering; // Control button renderer

    /**
     * Initialize all rendering module instances, typically called once at game start.
     * Ensures all rendering modules are created and can be uniformly accessed and updated
     * by other parts of the game.
     */
    public static void init() {
        tileRendering = new TileRendering();
        floodRendering = new FloodRendering();
        playerRendering = new PlayerRendering();
        treasureRendering = new TreasureRendering();
        waterMeterRendering = new WaterMeterRendering();
        controllersRendering = new ControllersRendering();
    }

    public static TileRendering getBoardRendering() {
        return tileRendering;
    }

    public static FloodRendering getFloodRendering() {
        return floodRendering;
    }

    public static PlayerRendering getPlayerRendering() {
        return playerRendering;
    }

    public static TreasureRendering getTreasureRendering() {
        return treasureRendering;
    }

    public static WaterMeterRendering getWaterMeterRendering() {
        return waterMeterRendering;
    }

    public static ControllersRendering getControllersRendering() {
        return controllersRendering;
    }
}
