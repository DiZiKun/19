package model.cards;

import com.forbidden.island.ui.ElementEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * 洪水牌堆类，继承自抽象的Deck类，专门处理游戏中的洪水牌逻辑。
 */
public class FloodDeck extends Deck {
    /** 当前显示在桌面上的洪水牌 */
    private final ArrayList<Integer> displayedCards;
    /** 已移除的洪水牌（对应已经沉没的地块） */
    private final ArrayList<Integer> removedFloodCard;
    /** 是否为初始化状态，初始化时固定抽6张牌 */
    private boolean isInit;

    /**
     * 构造方法，初始化洪水牌堆。
     * 初始化时加入编号1-24的洪水牌，并打乱顺序。
     * 设置初始状态为true，表示第一次抽牌固定数量。
     */
    public FloodDeck() {
        super(6);   // 初始化父类，默认抽6张牌
        displayedCards = new ArrayList<>();
        removedFloodCard = new ArrayList<>();
        isInit = true;

        // 添加洪水牌编号1-24
        for (int i = 1; i <= 24; i++) {
            deck.add(i);
        }

        // 洗牌
        Collections.shuffle(deck);
    }

    /**
     * 获取当前需要抽取的洪水牌（根据初始化状态和水位动态调整抽牌数量）。
     * @return 当前显示的洪水牌列表
     */
    public ArrayList<Integer> getCards() {
        if (!isInit) {
            // 非初始化时，动态获取水位决定抽牌数量
            Num = ElementEngine.getFloodCardCount();
        }

        // 检查并补充牌堆数量
        CheckAvailability(Num);

        // 清空当前显示牌列表，准备更新
        displayedCards.clear();

        // 牌堆和弃牌堆数量不足时，显示所有牌
        if (deck.size() + discardPile.size() < Num) {
            displayedCards.addAll(deck);
        } else {
            // 否则显示牌堆顶端Num张牌
            displayedCards.addAll(deck.subList(0, Num));
        }
        return displayedCards;
    }

    /**
     * 弃牌操作，将当前抽出的洪水牌从牌堆移除并加入弃牌堆。
     */
    public void discard(){
        int count = 0;
        Iterator<Integer> iterator = deck.iterator();
        // 遍历牌堆，移除前Num张牌加入弃牌堆
        while (iterator.hasNext()) {
            int floodCard = iterator.next();
            if (count < Num) {
                discardPile.add(floodCard);
                iterator.remove();
                count++;
            }
            if (count >= Num) {
                break;
            }
        }
    }

    /**
     * 将弃牌堆的洪水牌洗牌后放回牌堆顶端。
     * 通常用于洗牌重新开始时使用。
     */
    public void putBack2Top() {
        if (!discardPile.isEmpty()) {
            Collections.shuffle(discardPile);
            // 把弃牌堆放到牌堆顶端
            discardPile.addAll(deck);
            deck.clear();
            deck.addAll(discardPile);
            discardPile.clear();
        }
    }

    /**
     * 当对应地块沉没时，从牌堆中移除对应的洪水牌，并记录被移除的洪水牌。
     * @param removedTile 被移除的地块编号（对应洪水牌编号）
     */
    public void removeFloodCard(int removedTile) {
        removedFloodCard.add(removedTile);
        deck.remove((Integer) removedTile);
    }

    /**
     * 设置为非初始化状态，表示不再固定抽6张洪水牌，
     * 而是根据当前水位动态调整抽牌数量。
     */
    public void set2Norm() {
        this.isInit = false;
    }
}
