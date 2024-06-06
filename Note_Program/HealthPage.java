import javax.swing.*;
import java.awt.*;

public class HealthPage extends JFrame {
    public HealthPage() {
        setTitle("健康紀錄");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("這是健康紀錄的頁面"));

        add(panel);

        setVisible(true);
    }
}
