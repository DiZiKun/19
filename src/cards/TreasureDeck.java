package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * 宝藏牌堆类，继承自抽象的Deck类，实现宝藏牌的具体逻辑。
 */
public class TreasureDeck extends Deck{
    /** 当前抽出的宝藏牌 */
    private final ArrayList<Integer> NTreasureCards;

    /**
     * 构造方法，初始化宝藏牌堆。
     * 添加编号0-27的宝藏牌，并进行洗牌。
     * 其中编号25-27通常对应“水位上升”（Water Rise）牌。
     */
    public TreasureDeck() {
        super(2);  // 初始化父类，默认抽2张牌
        NTreasureCards = new ArrayList<>();
        // 初始化宝藏牌编号0-27
        for (int i = 0; i < 28; i++) {
            deck.add(i);
        }
        // 洗牌
        Collections.shuffle(deck);
    }

    /**
     * 抽取当前需要的宝藏牌数量（Num张），
     * 并将抽出的牌从牌堆移除，返回抽出的牌列表。
     * @return 抽出的宝藏牌列表
     */
    public ArrayList<Integer> getCards() {
        // 检查牌堆是否足够，必要时补充
        CheckAvailability(Num);

        // 清空当前抽牌列表，准备添加新抽的牌
        NTreasureCards.clear();

        // 从牌堆头部取出Num张牌
        NTreasureCards.addAll(deck.subList(0, Num));
        // 从牌堆中移除已抽取的牌
        deck.subList(0, Num).clear();

        return NTreasureCards;
    }

    /**
     * 初始化时抽取宝藏牌的方法，
     * 玩家初始抽牌时不会抽到“水位上升”牌（编号25-27）。
     * 抽到水位上升牌时直接弃掉并重新洗牌。
     * @return 初始抽取的宝藏牌列表（不含水位上升牌）
     */
    public ArrayList<Integer> getNoRiseCards() {
        // 检查牌堆是否足够，必要时补充
        CheckAvailability(Num);
        NTreasureCards.clear();
        int count = 0;
        Iterator<Integer> iterator = deck.iterator();
        // 遍历牌堆，跳过水位上升牌，收集其他宝藏牌直到达到数量
        while (iterator.hasNext()) {
            int treasureCard = iterator.next();
            if (treasureCard >= 25 && treasureCard <= 27) {
                // 遇到水位上升牌，直接弃牌
                discard(treasureCard);
            } else {
                // 收集普通宝藏牌
                NTreasureCards.add(treasureCard);
                count++;
            }

            // 无论是否收集，当前牌均从牌堆移除
            iterator.remove();

            // 达到需要抽的牌数，停止抽取
            if (count >= Num) {
                break;
            }
        }

        // 把弃牌堆的牌重新放回牌堆并洗牌
        deck.addAll(discardPile);
        Collections.shuffle(deck);
        return NTreasureCards;
    }

    /**
     * 弃牌方法，将指定宝藏牌加入弃牌堆。
     * @param treasureID 被弃置的宝藏牌编号
     */
    public void discard(int treasureID) {
        discardPile.add(treasureID);
    }

}
