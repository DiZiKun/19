package view;

import com.forbidden.island.controller.ForbiddenIslandGame;
import com.forbidden.island.controller.GameController;
import com.forbidden.island.controller.GameListener;
import com.forbidden.island.utils.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ForbiddenIslandFrame 继承自 JFrame，是游戏主窗口的顶层容器，
 * 负责初始化窗口属性，布局游戏面板与操作面板，并在打开时弹出设置对话框。
 */
public class ForbiddenIslandFrame extends JFrame {

    /**
     * 构造方法，设置窗口标题并初始化窗口大小、位置和布局。
     * @param title 窗口标题
     * @throws HeadlessException 无显示环境时抛出
     */
    public ForbiddenIslandFrame(String title) throws HeadlessException {
        super(title);   // 调用父类构造器设置标题

        // 获取屏幕尺寸，方便窗口居中显示
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int screenWidth = toolkit.getScreenSize().width;
        int screenHeight = toolkit.getScreenSize().height;
        this.setResizable(false);

        // 设置窗口布局为BorderLayout，5像素水平和垂直间距
        this.setLayout(new BorderLayout(5, 5)); // 组件方向

        // 设置窗口初始位置和大小，使窗口居中显示
        this.setBounds((screenWidth - Constant.FRAME_WIDTH) / 2,
                (screenHeight - Constant.FRAME_HEIGHT) / 2-20,
                Constant.FRAME_WIDTH,
                Constant.FRAME_HEIGHT);

        // 调用初始化方法，添加面板和监听器
        init();
        setVisible(true);
    }

    /**
     * 初始化窗口内容：
     * 创建主面板，设置垂直布局，添加游戏面板和操作面板，
     * 并设置窗口打开监听器，弹出游戏设置对话框。
     */
    private void init() {
        // 1. 创建主容器（改用垂直Box布局或GridBagLayout）
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // 垂直排列

        // 2. 获取游戏面板和控制台面板
        JPanel gamePanel = new GamePanel().getGamePanel();
        Box operateBox = new OperatePanel().getBox();

        // 3. 设置面板对齐方式
        gamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        operateBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 4. 添加面板到主容器
        mainPanel.add(gamePanel);
        mainPanel.add(operateBox);

        // 5. 将主容器添加到JFrame的中心区域
        this.add(mainPanel, BorderLayout.CENTER);

        // 6. 添加窗口事件监听器，窗口打开时弹出设置对话框
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                showSettingsDialog();   // 弹出游戏参数设置窗口
            }
        });
    }

    /**
     * 显示游戏设置对话框，允许用户选择玩家数和难度，
     * 根据用户选择初始化游戏或退出程序。
     */
    private void showSettingsDialog() {
        SettingsDialog dialog = new SettingsDialog(this);
        dialog.setVisible(true);    // 显示模态对话框，阻塞直到关闭

        if (dialog.isConfirmed()) {
            // 如果用户确认设置，获取玩家数量和难度等级
            int players = dialog.getPlayerCount();
            int difficulty = dialog.getDifficulty();

            // 初始化游戏核心逻辑，传入玩家数量和水位难度
            ForbiddenIslandGame.init(players, difficulty);

            // 初始化控制器和监听器，设置游戏交互逻辑
            GameController.getInstance();
            new GameListener();
        } else {
            // 用户取消设置，关闭程序
            System.exit(0);
        }
    }
}
