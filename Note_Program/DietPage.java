import javax.swing.*;
import java.awt.*;

public class DietPage extends JFrame {
    public DietPage() {
        setTitle("飲食紀錄");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("這是飲食紀錄的頁面"));

        add(panel);

        setVisible(true);
    }
}
