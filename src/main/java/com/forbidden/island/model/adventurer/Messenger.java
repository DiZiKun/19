package com.forbidden.island.model.adventurer;

/**
 * Messenger character class, extends Adventurer.
 * Character ID is set to 3.
 */
public class Messenger extends Adventurer {
    public Messenger(int order) {
        super(order, "Messenger");
        this.id = 3;
    }
}
