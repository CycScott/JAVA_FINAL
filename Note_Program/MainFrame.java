import javax.swing.*;

import AccountComponents.AccountPage;
import DiaryComponents.DiaryPage;
import HealthComponents.HealthPage;
import ScheduleComponents.SchedulePage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("JAVA_FINAL");
        setSize(600, 500); // 調整主頁面大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 創建主面板並設置為邊界布局
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 創建一個標籤來顯示圖片
        JLabel imageLabel = new JLabel(new ImageIcon("主頁.png"));
        mainPanel.add(imageLabel, BorderLayout.CENTER);

        // 創建按鈕面板並設置為網格佈局
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 5, 5)); // 設置按鈕間的間距

        JButton accountButton = new JButton("記帳");
        JButton scheduleButton = new JButton("課表");
        JButton diaryButton = new JButton("日記");
        JButton healthButton = new JButton("健身");

        // 設置按鈕的首選大小
        Dimension buttonSize = new Dimension(100, 100); // 調整按鈕的大小
        accountButton.setPreferredSize(buttonSize);
        scheduleButton.setPreferredSize(buttonSize);
        diaryButton.setPreferredSize(buttonSize);
        healthButton.setPreferredSize(buttonSize);

        accountButton.addActionListener(new ButtonClickListener());
        scheduleButton.addActionListener(new ButtonClickListener());
        diaryButton.addActionListener(new ButtonClickListener());
        healthButton.addActionListener(new ButtonClickListener());

        buttonPanel.add(accountButton);
        buttonPanel.add(scheduleButton);
        buttonPanel.add(diaryButton);
        buttonPanel.add(healthButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            switch (source.getText()) {
                case "記帳":
                    new AccountPage();
                    break;
                case "課表":
                    new SchedulePage();
                    break;
                case "日記":
                    new DiaryPage();
                    break;
                case "健身":
                    new HealthPage();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
