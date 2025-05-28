package model.adventurer;


/**
 * Engineer character class, extends Adventurer.
 * Has special attribute shoreUpCount representing the number of shore up actions available.
 * Character ID is set to 1.
 */
public class Engineer extends Adventurer {

    private int shoreUpCount = 1;

    public Engineer(int order) {
        super(order, "Engineer");
        this.id = 1;
    }


    /**
     * Gets the remaining number of shore up actions available.
     * @return remaining shore up count
     */
    public int getShoreUpCount() {
        return shoreUpCount;
    }

    /**
     * Executes a shore up action, consuming one shore up count.
     */
    public void ShoreUp() {
        this.shoreUpCount -= 1;
    }

    /**
     * Resets shore up count, typically used at the start of a new round.
     */
    public void resetShoreUpCount() {
        this.shoreUpCount = 1;
    }
}
