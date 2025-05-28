package com.forbidden.island.model.adventurer;

/**
 * Explorer character class, extends Adventurer.
 * Character ID is set to 2.
 */
public class Explorer extends Adventurer {
    public Explorer(int order) {
        super(order, "Explorer");
        this.id = 2;
    }
}
