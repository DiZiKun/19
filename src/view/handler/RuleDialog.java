package view.handler;

import javax.swing.*;
import java.awt.*;

public class RuleDialog extends JDialog {
    public RuleDialog() {
        setTitle("Game Help");
        setSize(900, 1100); // 初始窗口尺寸可调节
        setLocationRelativeTo(null);
        setModal(true);

        // 内容面板采用 BoxLayout（竖直排列）
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.DARK_GRAY);

        for (int i = 0; i <= 7; i++) {
            // 注意路径格式必须匹配
            String path = "/Rules/forbidden-island-rulebook_0" + i + ".png";

            ImageIcon icon = new ImageIcon(getClass().getResource(path));

            JLabel imageLabel = new JLabel(icon);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中

            contentPanel.add(Box.createVerticalStrut(20)); // 间距
            contentPanel.add(imageLabel);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 滚动速度
        add(scrollPane);

        setVisible(true);
    }
}
