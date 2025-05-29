package com.forbidden.island.model.adventurer;

/**
 * Diver character class, extends Adventurer.
 * Character ID is set to 0
 */
public class Diver extends Adventurer {
    public Diver(int order) {
        super(order, "Diver");
        this.id = 0;
    }
}
