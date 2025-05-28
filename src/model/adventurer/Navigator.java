package model.adventurer;

/**
 * Navigator character class, extends Adventurer.
 * Character ID is set to 4.
 */
public class Navigator extends Adventurer {
    public Navigator(int order) {
        super(order, "Navigator");
        this.id = 4;
    }
}
