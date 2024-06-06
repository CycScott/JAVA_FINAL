package DiaryComponents;
import javax.swing.*;
import java.awt.*;

public class DiaryPage extends JFrame {
    public DiaryPage() {
        setTitle("日記");
        setSize(400, 300); // 調整子頁面大小
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea diaryArea = new JTextArea();
        panel.add(new JScrollPane(diaryArea), BorderLayout.CENTER);

        JButton submitButton = new JButton("提交");
        panel.add(submitButton, BorderLayout.SOUTH);

        add(panel);

        setVisible(true);
    }
}
