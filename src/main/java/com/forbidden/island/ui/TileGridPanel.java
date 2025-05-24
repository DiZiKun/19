package com.forbidden.island.ui;

import com.forbidden.island.utils.Constant;
import com.forbidden.island.utils.Map;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * TileGridPanel 类负责创建并管理游戏主棋盘界面。
 * 棋盘由 6x6 的格子组成，其中部分格子为不可用区域（blankLayout），其余格子可供玩家操作。
 * 每个可用格子由一个 JButton 表示，并统一添加到静态列表 tileCards 中，以便游戏逻辑控制（例如显示、点击等）。
 * 棋盘的背景图为 Arena.jpg，并使用 GridLayout 排列格子。
 *
 * 主要用途：
 * - 初始化并渲染游戏地图界面；
 * - 提供格子按钮的访问支持；
 * - 管理背景图和布局。
 *
 * 依赖项：
 * - Constant：包含格子尺寸与资源路径常量；
 * - Map.blankLayout：用于标记哪些位置是空白不可用；
 * - ImagePanel：一个带背景图片的自定义面板类。
 */
public class TileGridPanel {
    // 静态列表，存储所有可交互的格子按钮（非空白区域的 JButton）
    public static ArrayList<JButton> tileCards = new ArrayList<>();

    // 游戏棋盘的图像背景面板，包含 6x6 的格子
    private final ImagePanel board;

    /**
     * 构造函数：创建并初始化棋盘界面，包括背景图与所有格子按钮的设置。
     * 将可用格子的按钮添加到 tileCards 中，以供游戏控制使用。
     */
    public TileGridPanel() {
        // 设置单个格子的尺寸
        Dimension tileSize = new Dimension(Constant.TILE_WIDTH, Constant.TILE_HEIGHT);

        // 设置整个棋盘面板的尺寸
        Dimension boardSize = new Dimension(Constant.BOARD_WIDTH, Constant.BOARD_HEIGHT);

        // 创建背景为地图图像的面板，并设置为 6x6 网格布局
        board = new ImagePanel(Constant.RESOURCES_PATH + "/Map/Arena.jpg", new GridLayout(6, 6, 2, 2));

        // 初始化 36 个按钮（对应 6x6 的棋盘格子）
        for (int i = 0; i < 36; i++) {
            JButton tileCard = new JButton();
            tileCard.setPreferredSize(tileSize);    // 设置按钮尺寸
            tileCard.setMargin(new Insets(-0,-0,-0,-0));

            // 判断是否为空白区域（不可用格子）
            if (Map.blankLayout.contains(i)) {
                tileCard.setVisible(false); // 空白格子不可见
            } else {
                tileCard.setVisible(false); // 初始也隐藏，但可通过逻辑控制显示
                tileCards.add(tileCard);    // 添加到可操作格子列表中
            }
            // 添加按钮到棋盘背景面板
            board.add(tileCard);
        }
        // 设置棋盘面板的尺寸
        board.setPreferredSize(boardSize);
    }

    /**
     * 获取棋盘图像面板（含所有按钮和背景图）
     * @return board 游戏棋盘背景图与按钮组成的面板
     */
    public ImagePanel getBoard() {
        return board;
    }

}
