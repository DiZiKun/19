package view.handler;

import com.forbidden.island.view.ForbiddenIslandFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WelcomeFrame extends JFrame {

    public WelcomeFrame() {
        setTitle("Welcome to Forbidden Island");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 背景图路径
        ImageIcon bgIcon = new ImageIcon("src/main/resources/image/TitleScreen.png");
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // 按钮定义
        JButton startBtn = createStyledButton("Start Game", new Color(46, 204, 113), new Color(39, 174, 96));
        JButton ruleBtn = createStyledButton("Rule", new Color(52, 152, 219), new Color(41, 128, 185));
        JButton helpBtn = createStyledButton("Help", new Color(52, 152, 219), new Color(41, 128, 185));
        JButton exitBtn = createStyledButton("Exit", new Color(231, 76, 60), new Color(192, 57, 43));

        // 按钮功能绑定
        startBtn.addActionListener(e -> {
            new ForbiddenIslandFrame("Forbidden Island");
            dispose();
        });

        ruleBtn.addActionListener(e -> new RuleDialog());

        helpBtn.addActionListener(e -> {
            new HelpDialog(); // 弹出帮助窗口
        });

        exitBtn.addActionListener(e -> System.exit(0));

        // 按钮区域布局
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        buttonPanel.add(startBtn);
        buttonPanel.add(ruleBtn);
        buttonPanel.add(helpBtn);
        buttonPanel.add(exitBtn);

        background.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color normalColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(normalColor);

        button.setFocusPainted(false);
        button.setContentAreaFilled(true); // ✅ 显示背景颜色
        button.setOpaque(true);            // ✅ 显示颜色需要这个
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });

        return button;
    }

}
