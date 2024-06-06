import javax.swing.*;
import java.awt.*;

public class DiaryPage extends JFrame {
    public DiaryPage() {
        setTitle("日記");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("這是日記的頁面"));

        add(panel);

        setVisible(true);
    }
}
