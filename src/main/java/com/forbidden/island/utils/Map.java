package com.forbidden.island.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Abstract class Map, defines map structure and related matchers.
 * Contains bidirectional mappings between map coordinates, numbers, and adventurers.
 */
public abstract class Map {

    /**
     * Represents "blank areas" on the map (valid numbers from 0-35).
     * Used to construct walkable areas on the map.
     */
    public final static ArrayList<Integer> blankLayout = new ArrayList<Integer>() {{
        add(0);
        add(1);
        add(4);
        add(5);
        add(6);
        add(11);
        add(24);
        add(29);
        add(30);
        add(31);
        add(34);
        add(35);
    }};

    /**
     * Number of rows in the map
     */
    public final static int rows = 6;

    /**
     * Number → Coordinates mapping.
     * Example: 0 -> [0, 2]
     */
    public final static HashMap<Integer, int[]> coordinatesMatcher = new HashMap<>();

    /**
     * Coordinate string → Number mapping.
     * Example: "[0, 2]" -> 0
     */
    public final static HashMap<String, Integer> numberMatcher = new HashMap<>();

    /**
     * Adventurer number → Name mapping.
     * Example: 0 -> "Diver"
     */
    public final static HashMap<Integer, String> adventurerMatcher = new HashMap<>();

    /**
     * Initialize all matchers: coordinate-number mapping, number-coordinate mapping, number-profession mapping, etc.
     */
    public static void setLocation() {
        setUpCoordinateAndNumberMatchers(); // Initialize number ↔ coordinate bidirectional mapping
        setUpAdventurerMatcher();           // Initialize number → adventurer name mapping
    }

    /**
     * Initialize bidirectional mapping between numbers and coordinates.
     * <p>
     * coordinatesMatcher: used to get coordinates (int[]) from number.
     * numberMatcher: used to get number from coordinates (in Arrays.toString format).
     */
    private static void setUpCoordinateAndNumberMatchers() {
        // All available number-coordinate pairs
        int[][] coordinates = {
                {0, 2}, {0, 3},
                {1, 1}, {1, 2}, {1, 3}, {1, 4},
                {2, 0}, {2, 1}, {2, 2}, {2, 3}, {2, 4}, {2, 5},
                {3, 0}, {3, 1}, {3, 2}, {3, 3}, {3, 4}, {3, 5},
                {4, 1}, {4, 2}, {4, 3}, {4, 4},
                {5, 2}, {5, 3}
        };

        for (int i = 0; i < coordinates.length; i++) {
            int[] coord = coordinates[i];
            coordinatesMatcher.put(i, coord);   // Number → Coordinates
            numberMatcher.put(Arrays.toString(coord), i);   // Coordinates (as string) → Number
        }
    }

    /**
     * Initialize mapping between adventurer numbers and names.
     * adventurerMatcher: used to get adventurer name from number.
     */
    private static void setUpAdventurerMatcher() {
        String[] adventurers = {"Diver", "Engineer", "Explorer", "Messenger", "Navigator", "Pilot"};
        for (int i = 0; i < adventurers.length; i++) {
            adventurerMatcher.put(i, adventurers[i]);   // Adventurer number → Name
        }
    }
}

