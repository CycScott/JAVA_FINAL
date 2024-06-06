import javax.swing.*;
import java.awt.*;

public class HealthPage extends JFrame {
    public HealthPage() {
        setTitle("健康紀錄");
        setSize(400, 300); // 調整子頁面大小
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("健康狀況:"));
        JTextField healthField = new JTextField();
        panel.add(healthField);

        panel.add(new JLabel("描述:"));
        JTextArea descriptionArea = new JTextArea();
        panel.add(descriptionArea);

        JButton submitButton = new JButton("提交");
        panel.add(submitButton);

        add(panel);

        setVisible(true);
    }
}
