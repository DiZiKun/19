package com.forbidden.island.model.adventurer;

/**
 * Pilot character class, extends Adventurer.
 * Character ID is set to 5.
 */
public class Pilot extends Adventurer {
    public Pilot(int order) {
        super(order, "Pilot");
        this.id = 5;
    }
}
