package ui.handler;

import com.forbidden.island.utils.ImageUtil;
import com.forbidden.island.utils.Constant;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * FloodPanel 类负责创建和管理游戏中洪水牌区域的图形界面组件，
 * 包括显示洪水弃牌堆、洪水牌堆背面图和当前显示的洪水牌按钮。
 */
public class FloodPanel {


    /**
     * 静态列表，存储当前显示的洪水牌按钮，
     * 用于游戏过程中显示、隐藏或更新对应的洪水牌。
     */
    public static ArrayList<JButton> floodCards;

    /**
     * JPanel容器，用于承载所有洪水相关的组件（标签、按钮等）。
     */
    private final JPanel floodPanel;

    /**
     * FloodPanel 构造方法，初始化界面组件：
     * 创建一个8行1列的网格布局面板，添加弃牌堆和牌堆背面标签，
     * 并创建6个初始不可见的洪水牌按钮，方便动态显示和交互。
     */
    public FloodPanel() {
        // 创建一个网格布局面板，8行1列，水平间距1，垂直间距3
        floodPanel = new JPanel(new GridLayout(8, 1, 1, 3));

        // 创建显示“洪水弃牌堆”的标签
        JLabel pile = new JLabel();
        // 设置标签尺寸，使用预定义常量宽高
        Dimension floodCardSize = new Dimension(Constant.FLOOD_WIDTH, Constant.FLOOD_HEIGHT);
        pile.setPreferredSize(floodCardSize);
        // 设置标签图标，使用ImageUtil工具加载图片并调整大小
        pile.setIcon(new ImageIcon(ImageUtil.getImage("/Back/Flood Discard.png", Constant.FLOOD_WIDTH, Constant.FLOOD_HEIGHT)));
        floodPanel.add(pile); // 将标签添加到面板中

        // 创建显示“洪水牌堆背面”的标签
        JLabel back = new JLabel();
        back.setPreferredSize(floodCardSize);
        // 加载背面图片，旋转90度（参数90d表示旋转角度）
        back.setIcon(new ImageIcon(Objects.requireNonNull(
                ImageUtil.getImage("/Back/Flood Deck.png", Constant.FLOOD_WIDTH, Constant.FLOOD_HEIGHT, 90d))));
        floodPanel.add(back); // 添加到面板中

        // 初始化存放洪水牌按钮的列表
        floodCards = new ArrayList<>();

        // 创建6个洪水牌按钮，用于显示当前洪水牌
        for (int i = 0; i < 6; i++) {
            JButton button = new JButton();
            button.setPreferredSize(floodCardSize); // 设置按钮尺寸
            button.setVisible(false); // 初始隐藏，等待游戏逻辑激活显示
            floodCards.add(button);
            floodPanel.add(button); // 添加按钮到面板
        }
    }

    /**
     * 获取FloodPanel的JPanel容器，供外部调用并加入到主界面中。
     * @return floodPanel 组件容器
     */
    public JPanel getFloodPanel() {
        return floodPanel;
    }
}
