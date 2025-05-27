package model.adventurer;

/**
 * 导航员角色类，继承自Adventurer。
 * 角色ID设为4。
 */
public class Navigator extends Adventurer {
    public Navigator(int order) {
        super(order, "Navigator");
        this.id = 4;
    }
}
