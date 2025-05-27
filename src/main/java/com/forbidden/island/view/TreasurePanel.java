package com.forbidden.island.view;

import com.forbidden.island.utils.Constant;
import com.forbidden.island.utils.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * TreasurePanel 类用于展示游戏中的宝藏卡牌和水位计面板。
 */
public class TreasurePanel {
    // 静态标签：用于显示水位计（供外部访问和设置）
    public static JLabel waterMeter = new JLabel();

    // 静态按钮列表：用于显示当前抽到的宝藏卡牌（最多显示两张）
    public static ArrayList<JButton> treasureCards;

    // 主面板，封装所有 UI 组件
    private final JPanel treasurePanel;

    /**
     * 构造方法，初始化面板布局和各个 UI 元件。
     * 包括两个宝藏卡牌位、一个弃牌堆图标、一个抽牌堆图标以及水位计。
     */
    public TreasurePanel() {
        // 设置主面板为 BorderLayout：顶部放置卡牌堆，中间放置水位计
        treasurePanel = new JPanel(new BorderLayout(1, 3));

        // 创建宝藏卡牌堆面板，垂直排列（4行1列）：弃牌堆、抽牌堆、两张手牌
        JPanel treasureCardPile = new JPanel(new GridLayout(4, 1, 1, 3));
        treasureCards = new ArrayList<>();

        // 创建弃牌堆图标标签
        JLabel pile = new JLabel();
        Dimension treasureCardSize = new Dimension(Constant.TREASURE_WIDTH, Constant.TREASURE_HEIGHT);
        pile.setPreferredSize(treasureCardSize);

        // 设置弃牌堆的图像
        pile.setIcon(new ImageIcon(ImageUtil.getImage("/Back/Treasure Discard.png", Constant.TREASURE_WIDTH, Constant.TREASURE_HEIGHT)));
        treasureCardPile.add(pile); // 添加弃牌堆图标到面板

        // 创建抽牌堆图标标签
        JLabel back = new JLabel();
        back.setPreferredSize(treasureCardSize);
        // 设置抽牌堆图像，并旋转 90 度
        back.setIcon(new ImageIcon(Objects.requireNonNull(ImageUtil.getImage("/Back/Treasure Deck.png", Constant.TREASURE_WIDTH, Constant.TREASURE_HEIGHT, 90d))));
        treasureCardPile.add(back); // 添加抽牌堆图标到面板

        // 初始化并添加两张卡位（按钮，默认禁用）
        for (int i = 0; i < 2; i++) {
            treasureCards.add(new JButton());
            treasureCards.get(i).setPreferredSize(treasureCardSize);
            treasureCards.get(i).setEnabled(false);
            treasureCardPile.add(treasureCards.get(i));
        }

        // 将宝藏卡堆面板添加到主面板顶部
        treasurePanel.add(treasureCardPile, BorderLayout.NORTH);

        // 添加水位计标签到主面板中间区域
        treasurePanel.add(waterMeter, BorderLayout.CENTER);
    }

    /**
     * 获取构建好的宝藏面板，用于嵌入其他容器中。
     *
     * @return 包含宝藏卡堆和水位计的主面板
     */
    public JPanel getTreasurePanel() {
        return treasurePanel;
    }
}
