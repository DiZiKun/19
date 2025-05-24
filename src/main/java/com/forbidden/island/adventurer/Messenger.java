package com.forbidden.island.adventurer;

/**
 * 信使角色类，继承自Adventurer。
 * 角色ID设为3。
 */
public class Messenger extends Adventurer {
    public Messenger(int order) {
        super(order, "Messenger");
        this.id = 3;
    }
}
