package model.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 抽象类，表示一副牌的基本结构和操作。
 * 牌使用整数标识，牌堆和弃牌堆均用ArrayList存储。
 * 选择ArrayList而非Stack是为了方便将弃牌放到底部。
 */
public abstract class Deck {
    /** 当前牌堆，存储可用的牌 */
    protected ArrayList<Integer> deck;
    /** 弃牌堆，存储已弃的牌，待补充回牌堆 */
    protected ArrayList<Integer> discardPile;
    /** 牌的总数量（初始化时使用） */
    protected int Num;

    /**
     * 构造方法，初始化牌堆和弃牌堆。
     * @param num 牌的总数量
     */
    public Deck(int num) {
        deck = new ArrayList<>();
        discardPile = new ArrayList<>();
        Num = num;
    }

    /**
     * 抽取指定数量的牌（由子类实现具体逻辑）
     * @return 抽取的牌列表
     */
    protected abstract ArrayList<Integer> getCards();

    /**
     * 检查当前牌堆是否有足够数量的牌可供抽取。
     * 如果牌堆数量不足，则将弃牌堆洗牌后补充到牌堆底部，
     * 然后清空弃牌堆。
     * @param n 需要抽取的牌数量
     */
    protected void CheckAvailability(int n) {
        // 如果牌堆中牌数量不足
        if (deck.size() < n) {
            // 洗牌弃牌堆
            Collections.shuffle(discardPile);
            // 将弃牌堆补充到牌堆底部
            deck.addAll(discardPile);
            // 清空弃牌堆
            discardPile.clear();
        }
    }

}
