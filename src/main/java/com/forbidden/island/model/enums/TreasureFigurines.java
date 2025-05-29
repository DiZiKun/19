package com.forbidden.island.model.enums;

/**
 * Represents the four types of treasure figurines in the game.
 * Each enum constant corresponds to a treasure type with its associated name string.
 */
public enum TreasureFigurines {
    /** Earth treasure figurine */
    Earth("Earth"),
    /** Wind treasure figurine */
    Wind("Wind"),
    /** Fire treasure figurine */
    Fire("Fire"),
    /** Ocean treasure figurine */
    Ocean("Ocean");

    private final String name;

    TreasureFigurines(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
