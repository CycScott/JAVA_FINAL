import javax.swing.*;
import java.awt.*;

public class DietPage extends JFrame {
    public DietPage() {
        setTitle("飲食紀錄");
        setSize(400, 300); // 調整子頁面大小
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("食物名稱:"));
        JTextField foodField = new JTextField();
        panel.add(foodField);

        panel.add(new JLabel("描述:"));
        JTextArea descriptionArea = new JTextArea();
        panel.add(descriptionArea);

        JButton submitButton = new JButton("提交");
        panel.add(submitButton);

        add(panel);

        setVisible(true);
    }
}
