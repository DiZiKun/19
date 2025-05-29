package com.forbidden.island.utils;

import java.util.Objects;

/**
 * Global constants class used in the game.
 * Contains configuration information such as interface dimensions, image sizes, and resource paths.
 */
public abstract class Constant {

    // ====== Window Dimensions ======

    /** Main window width (pixels) */
    public static final int FRAME_WIDTH = 1080;

    /** Main window height (pixels) */
    public static final int FRAME_HEIGHT = 810;

    // ====== Adventurer Image Dimensions ======

    /** Adventurer image width (pixels) */
    public static final int ADVENTURER_WIDTH = 73;

    /** Adventurer image height (pixels) */
    public static final int ADVENTURER_HEIGHT = 50;

    // ====== Flood Image Dimensions ======

    /** Flood image width (pixels) */
    public static final int FLOOD_WIDTH = 120;

    /** Flood image height (pixels) */
    public static final int FLOOD_HEIGHT = 50;

    // ====== Treasure Image Dimensions ======

    /** Treasure image width (pixels) */
    public static final int TREASURE_WIDTH = 120;

    /** Treasure image height (pixels) */
    public static final int TREASURE_HEIGHT = 50;

    // ====== Tile Image Dimensions ======

    /** Map tile width (pixels) */
    public static final int TILE_WIDTH = 159;

    /** Map tile height (pixels) */
    public static final int TILE_HEIGHT = 90;

    // ====== Game Board Dimensions ======

    /** Game board area width (pixels) */
    public static final int BOARD_WIDTH = 700;

    /** Game board area height (pixels) */
    public static final int BOARD_HEIGHT = 700;

    // ====== Water Meter Image Dimensions ======

    /** Water meter image width (pixels) */
    public static final int WATER_METER_WIDTH = 120;

    /** Water meter image height (pixels) */
    public static final int WATER_METER_HEIGHT = 350;

    // ====== Resource Paths ======

    /** Root directory path for image resources (gets the /image folder from the classpath) */
    public static final String RESOURCES_PATH = Objects.requireNonNull(
            Constant.class.getResource("/image")
    ).getPath();
}
