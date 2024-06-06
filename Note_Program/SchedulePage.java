import javax.swing.*;
import java.awt.*;

public class SchedulePage extends JFrame {
    public SchedulePage() {
        setTitle("課表");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("這是課表的頁面"));

        add(panel);

        setVisible(true);
    }
}
