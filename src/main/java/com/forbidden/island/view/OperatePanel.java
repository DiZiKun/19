package com.forbidden.island.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * OperatePanel 是一个操作面板类，用于展示游戏中各种操作按钮。
 * 使用垂直 Box 布局容器，并在其中嵌套一个包含按钮的水平 GridLayout。
 */
public class OperatePanel {
    // 静态按钮列表，供全局访问使用（如控制器为按钮添加监听器）
    public static ArrayList<JButton> opButtons = new ArrayList<>();

    // 垂直方向的 Box 容器，作为该面板的根容器
    private final Box box = Box.createVerticalBox();

    /**
     * 构造方法：初始化按钮并构建面板布局
     */
    public OperatePanel() {

        // 0. 初始化所有按钮（按顺序添加到列表中）
        opButtons.add(new JButton("Start"));
        opButtons.add(new JButton("Move"));
        opButtons.add(new JButton("Shore"));
        opButtons.add(new JButton("Pass"));
        opButtons.add(new JButton("Capture"));
        opButtons.add(new JButton("Lift Off"));
        opButtons.add(new JButton("Special Actions"));
        opButtons.add(new JButton("Next"));
        opButtons.add(new JButton("Discard"));
        opButtons.add(new JButton("Reset"));

        // 1. 创建一个 1 行 9 列的网格布局（用于排列按钮）
        GridLayout gridLayout = new GridLayout(1, 9, 0, 2);
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(gridLayout);
        // 2. 添加除 "Start" 按钮（index 0）外的其他按钮到 actionPanel
        for (int i = 1; i <= 9; i++) {
            actionPanel.add(opButtons.get(i));
        }

        // 设置按钮面板的首选大小（宽度会由布局控制，高度设为 50）
        actionPanel.setPreferredSize(new Dimension(50, 50));

        // 3. 将 actionPanel 添加到 box 布局中
        // 添加上下间隔（垂直间距）
        box.add(Box.createVerticalStrut(5));      // 顶部空白
        box.add(Box.createVerticalStrut(5));      // 第二层空白（可去重）
        box.add(actionPanel);                     // 主操作按钮区
        box.add(Box.createVerticalGlue());        // 底部弹性填充，自动填满剩余空间
    }

    /**
     * 获取构建完成的 Box 容器，用于外部组件嵌套调用。
     * @return Box 包含操作按钮的面板
     */
    public Box getBox() {
        return box;
    }

}
