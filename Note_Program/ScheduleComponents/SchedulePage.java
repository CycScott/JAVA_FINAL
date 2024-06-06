package ScheduleComponents;
import javax.swing.*;
import java.awt.*;

public class SchedulePage extends JFrame {
    public SchedulePage() {
        setTitle("課表");
        setSize(600, 400); // 調整子頁面大小
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"星期一", "星期二", "星期三", "星期四", "星期五"};
        Object[][] data = {
            {"", "", "", "", ""},
            {"", "", "", "", ""},
            {"", "", "", "", ""},
            {"", "", "", "", ""},
            {"", "", "", "", ""},
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("添加課程");
        panel.add(addButton, BorderLayout.SOUTH);

        add(panel);

        setVisible(true);
    }
}
