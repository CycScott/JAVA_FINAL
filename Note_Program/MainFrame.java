import javax.swing.*;

import AccountComponents.AccountPage;
import DiaryComponents.DiaryPage;
import DietComponents.DietPage;
import HealthComponents.HealthPage;
import ScheduleComponents.SchedulePage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("JAVA_FINAL");
        setSize(600, 400); // 調整主頁面大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton accountButton = new JButton("記帳");
        JButton dietButton = new JButton("飲食紀錄");
        JButton scheduleButton = new JButton("課表");
        JButton diaryButton = new JButton("日記");
        JButton healthButton = new JButton("健康紀錄");

        accountButton.addActionListener(new ButtonClickListener());
        dietButton.addActionListener(new ButtonClickListener());
        scheduleButton.addActionListener(new ButtonClickListener());
        diaryButton.addActionListener(new ButtonClickListener());
        healthButton.addActionListener(new ButtonClickListener());

        panel.add(accountButton);
        panel.add(dietButton);
        panel.add(scheduleButton);
        panel.add(diaryButton);
        panel.add(healthButton);

        add(panel);

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
                case "飲食紀錄":
                    new DietPage();
                    break;
                case "課表":
                    new SchedulePage();
                    break;
                case "日記":
                    new DiaryPage();
                    break;
                case "健康紀錄":
                    new HealthPage();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}