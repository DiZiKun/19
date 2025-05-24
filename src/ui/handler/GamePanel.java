package ui.handler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GamePanel 是游戏主界面的核心面板，负责组织和布局游戏中的各种子面板：
 * 玩家信息面板（上下两组），游戏地图面板，宝藏面板，洪水面板。
 * 同时保存玩家棋子按钮列表和玩家手牌按钮列表，方便游戏逻辑操作。
 */
public class GamePanel {
    /**
     * 存放所有玩家棋子的按钮，方便在游戏中控制棋子状态和事件。
     * 顺序对应玩家：玩家1-玩家4的棋子按钮
     */
    public static ArrayList<JButton> playerPawnList;

    /**
     * 存放每位玩家手牌按钮的列表。每个元素是一个玩家的手牌按钮列表。
     * playerHandCards.get(i) 表示第 i 个玩家的手牌按钮列表。
     */
    public static ArrayList<List<JButton>> playerHandCards;

    /**
     * 主游戏面板，承载所有子面板
     */
    private final JPanel gamePanel;

    /**
     * 构造器：初始化所有子面板并将它们添加到主游戏面板中，完成布局设置。
     */
    public GamePanel() {
        // 创建上下两个玩家面板，分别显示两个玩家的相关信息与操作按钮
        PlayerPanel playerPanelUp = new PlayerPanel();
        PlayerPanel playerPanelDown = new PlayerPanel();

        // 创建游戏地图面板（瓦片网格）
        TileGridPanel tileGridPanel = new TileGridPanel();

        // 创建宝藏显示面板
        TreasurePanel treasurePanel = new TreasurePanel();

        // 创建洪水显示面板
        FloodPanel floodPanel = new FloodPanel();

        // 创建主面板，使用BorderLayout布局，间隔5像素
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout(5, 5));

        // 按照BorderLayout的方位依次添加子面板
        gamePanel.add(playerPanelDown.getDuoPlayerPanel(), BorderLayout.SOUTH); // 底部玩家面板
        gamePanel.add(playerPanelUp.getDuoPlayerPanel(), BorderLayout.NORTH);// 顶部玩家面板
        gamePanel.add(floodPanel.getFloodPanel(), BorderLayout.EAST);// 右侧洪水面板
        gamePanel.add(treasurePanel.getTreasurePanel(), BorderLayout.WEST);// 左侧宝藏面板
        gamePanel.add(tileGridPanel.getBoard(), BorderLayout.CENTER);// 中央地图面板

        // 初始化并收集所有玩家棋子按钮，方便后续操作
        playerPawnList = new ArrayList<>();
        playerPawnList.add(playerPanelDown.getP1Pawn());    // 底部面板的玩家1棋子按钮
        playerPawnList.add(playerPanelDown.getP2Pawn());    // 底部面板的玩家2棋子按钮
        playerPawnList.add(playerPanelUp.getP1Pawn());      // 顶部面板的玩家1棋子按钮
        playerPawnList.add(playerPanelUp.getP2Pawn());      // 顶部面板的玩家2棋子按钮

        // 获取每个玩家的手牌按钮列表，分别是两个面板上的4个玩家
        List<JButton> p1HandCards = new ArrayList<>(playerPanelDown.getP1HandCards());
        List<JButton> p2HandCards = new ArrayList<>(playerPanelDown.getP2HandCards());
        List<JButton> p3HandCards = new ArrayList<>(playerPanelUp.getP1HandCards());
        List<JButton> p4HandCards = new ArrayList<>(playerPanelUp.getP2HandCards());

        // 汇总所有玩家的手牌列表，方便统一管理
        playerHandCards = new ArrayList<>();
        playerHandCards.add(p1HandCards);
        playerHandCards.add(p2HandCards);
        playerHandCards.add(p3HandCards);
        playerHandCards.add(p4HandCards);
    }

    /**
     * 获取主游戏面板，用于添加到顶层容器或其他面板中。
     * @return 返回主游戏 JPanel 对象
     */
    public JPanel getGamePanel() {
        return gamePanel;
    }

}