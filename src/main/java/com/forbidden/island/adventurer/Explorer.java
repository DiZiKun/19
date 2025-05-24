package com.forbidden.island.adventurer;

/**
 * 探险家角色类，继承自Adventurer。
 * 角色ID设为2。
 */
public class Explorer extends Adventurer {
    public Explorer(int order) {
        super(order, "Explorer");
        this.id = 2;
    }
}
