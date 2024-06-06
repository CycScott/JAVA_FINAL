package ScheduleComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SchedulePage extends JFrame {
    private JTable table;
    private Object[][] data;
    private String[] columnNames = {"時間","星期一", "星期二", "星期三", "星期四", "星期五"};
    private ViewCoursePage viewCoursePage; // 新增這一行

    public SchedulePage() {
        setTitle("課表");
        setSize(600, 400); // 調整子頁面大小
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        data = new Object[][] {
            {" 8.05~8.55 ", "","", "", "", ""},
            {" 9.05~9.55 ", "","", "", "", ""},
            {"10.10~11.00", "","", "", "", ""},
            {"11.10~12.00", "","", "", "", ""},
            {"12.10~13.00","午休","午休","午休","午休","午休"},
            {"13.10~14.00", "","", "", "", ""},
            {"14.10~15.00", "","", "", "", ""},
            {"15.15~16.05", "","", "", "", ""},
            {"16.20~17.10", "","", "", "", ""},
            {"17.20~18.10", "","", "", "", ""},
            {"18.20~19.10", "","", "", "", ""},
            {"19.15~20.05", "","", "", "", ""},
            {"20.10~21.00", "","", "", "", ""},
            {"21.05~21.55", "","", "", "", ""},
        };

        table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        JButton addButton = new JButton("添加課程");
        buttonPanel.add(addButton);

        JButton deleteButton = new JButton("刪除課程");
        buttonPanel.add(deleteButton);

        JButton viewButton = new JButton("查看課程"); // 新增這一行
        buttonPanel.add(viewButton); // 新增這一行
        
        panel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCourseDialog(SchedulePage.this).setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteCourseDialog(SchedulePage.this).setVisible(true);
            }
        });

        viewButton.addActionListener(new ActionListener() { // 新增這一段
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewCoursePage == null) {
                    viewCoursePage = new ViewCoursePage();
                }
                viewCoursePage.setVisible(true);
            }
        });

        add(panel);
        setVisible(true);
    }

    public void addCourse(int dayOfWeek, int startPeriod, int endPeriod, String courseName) {
        for (int i = startPeriod; i <= endPeriod; i++) {
            data[i][dayOfWeek] = courseName;
        }
        table.repaint();

        if (viewCoursePage != null) { // 新增這一段
            viewCoursePage.addCourseButton(courseName);
        }
    }

    public void deleteCourse(int dayOfWeek, int startPeriod, int endPeriod) {
        for (int i = startPeriod; i <= endPeriod; i++) {
            data[i][dayOfWeek] = "";
        }
        table.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SchedulePage::new);
    }
}

class AddCourseDialog extends JDialog {
    private JComboBox<String> dayOfWeekComboBox;
    private JComboBox<Integer> startPeriodComboBox;
    private JComboBox<Integer> endPeriodComboBox;
    private JTextField courseNameField;
    private SchedulePage schedulePage;

    public AddCourseDialog(SchedulePage schedulePage) {
        this.schedulePage = schedulePage;
        setTitle("添加課程");
        setSize(300, 200);
        setLocationRelativeTo(schedulePage);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("星期幾:"));
        String[] days = {"星期一", "星期二", "星期三", "星期四", "星期五"};
        dayOfWeekComboBox = new JComboBox<>(days);
        panel.add(dayOfWeekComboBox);

        panel.add(new JLabel("開始節:"));
        Integer[] periods = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        startPeriodComboBox = new JComboBox<>(periods);
        panel.add(startPeriodComboBox);

        panel.add(new JLabel("結束節:"));
        endPeriodComboBox = new JComboBox<>(periods);
        panel.add(endPeriodComboBox);

        panel.add(new JLabel("課程名稱:"));
        courseNameField = new JTextField();
        panel.add(courseNameField);

        JButton addButton = new JButton("添加");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dayOfWeek = dayOfWeekComboBox.getSelectedIndex() + 1;
                int startPeriod = (int) startPeriodComboBox.getSelectedItem() - 1;
                int endPeriod = (int) endPeriodComboBox.getSelectedItem() - 1;
                String courseName = courseNameField.getText();

                schedulePage.addCourse(dayOfWeek, startPeriod, endPeriod, courseName);
                dispose();
            }
        });
        panel.add(addButton);

        add(panel);
    }
}


class DeleteCourseDialog extends JDialog {
    private JComboBox<String> dayOfWeekComboBox;
    private JComboBox<Integer> startPeriodComboBox;
    private JComboBox<Integer> endPeriodComboBox;
    private SchedulePage schedulePage;

    public DeleteCourseDialog(SchedulePage schedulePage) {
        this.schedulePage = schedulePage;
        setTitle("刪除課程");
        setSize(300, 200);
        setLocationRelativeTo(schedulePage);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("星期幾:"));
        String[] days = {"星期一", "星期二", "星期三", "星期四", "星期五"};
        dayOfWeekComboBox = new JComboBox<>(days);
        panel.add(dayOfWeekComboBox);

        panel.add(new JLabel("開始節:"));
        Integer[] periods = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        startPeriodComboBox = new JComboBox<>(periods);
        panel.add(startPeriodComboBox);

        panel.add(new JLabel("結束節:"));
        endPeriodComboBox = new JComboBox<>(periods);
        panel.add(endPeriodComboBox);

        JButton deleteButton = new JButton("刪除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dayOfWeek = dayOfWeekComboBox.getSelectedIndex() + 1;
                int startPeriod = (int) startPeriodComboBox.getSelectedItem() - 1;
                int endPeriod = (int) endPeriodComboBox.getSelectedItem() - 1;

                schedulePage.deleteCourse(dayOfWeek, startPeriod, endPeriod);
                dispose();
            }
        });
        panel.add(deleteButton);

        add(panel);
    }
}

class ViewCoursePage extends JFrame {
    private JPanel coursePanel;
    private ArrayList<JButton> courseButtons; // 用於保存課程按鈕

    public ViewCoursePage() {
        setTitle("查看課程");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(coursePanel);
        add(scrollPane);

        courseButtons = new ArrayList<>(); // 初始化課程按鈕列表
    }

    public void addCourseButton(String courseName) {
        JButton courseButton = new JButton(courseName);
        courseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CourseDetailsDialog(courseName).setVisible(true);
            }
        });
        coursePanel.add(courseButton);
        coursePanel.revalidate();
        coursePanel.repaint();
        courseButtons.add(courseButton); // 添加按鈕到列表中
    }

    public void removeCourseButton(String courseName) {
        for (JButton button : courseButtons) {
            if (button.getText().equals(courseName)) {
                coursePanel.remove(button);
                courseButtons.remove(button);
                coursePanel.revalidate();
                coursePanel.repaint();
                break;
            }
        }
    }
}

class DeleteCourseDialog extends JDialog {
    private JComboBox<String> dayOfWeekComboBox;
    private JComboBox<Integer> startPeriodComboBox;
    private JComboBox<Integer> endPeriodComboBox;
    private SchedulePage schedulePage;
    private JTextField courseNameField; // 新增這一行

    public DeleteCourseDialog(SchedulePage schedulePage) {
        this.schedulePage = schedulePage;
        setTitle("刪除課程");
        setSize(300, 200);
        setLocationRelativeTo(schedulePage);

        JPanel panel = new JPanel(new GridLayout(5, 2)); // 修改為 5 行

        panel.add(new JLabel("星期幾:"));
        String[] days = {"星期一", "星期二", "星期三", "星期四", "星期五"};
        dayOfWeekComboBox = new JComboBox<>(days);
        panel.add(dayOfWeekComboBox);

        panel.add(new JLabel("開始節:"));
        Integer[] periods = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        startPeriodComboBox = new JComboBox<>(periods);
        panel.add(startPeriodComboBox);

        panel.add(new JLabel("結束節:"));
        endPeriodComboBox = new JComboBox<>(periods);
        panel.add(endPeriodComboBox);

        panel.add(new JLabel("課程名稱:")); // 新增這一行
        courseNameField = new JTextField(); // 新增這一行
        panel.add(courseNameField); // 新增這一行

        JButton deleteButton = new JButton("刪除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dayOfWeek = dayOfWeekComboBox.getSelectedIndex() + 1;
                int startPeriod = (int) startPeriodComboBox.getSelectedItem() - 1;
                int endPeriod = (int) endPeriodComboBox.getSelectedItem() - 1;
                String courseName = courseNameField.getText(); // 獲取課程名稱

                schedulePage.deleteCourse(dayOfWeek, startPeriod, endPeriod);
                if (schedulePage.viewCoursePage != null) {
                    schedulePage.viewCoursePage.removeCourseButton(courseName); // 刪除按鈕
                }
                dispose();
            }
        });
        panel.add(deleteButton);

        add(panel);
    }
}

class CourseDetailsDialog extends JDialog {
    private JTextField midtermField;
    private JTextField midtermPercentageField;
    private JTextField finalField;
    private JTextField finalPercentageField;
    private JTextField normalField;
    private JTextField normalPercentageField;
    private JTextField quizField;
    private JTextField quizPercentageField;
    private JTextField attendanceField;
    private JTextField attendancePercentageField;

    private double savedMidtermScore;
    private double savedFinalScore;
    private double savedNormalScore;
    private double savedQuizScore;
    private double savedAttendanceScore;

    public CourseDetailsDialog(String courseName) {
        setTitle(courseName + " 詳情");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Define preferred size for text fields
        Dimension textFieldSize = new Dimension(100, 30);

        // Add labels and fields
        int row = 0;
        midtermField = new JTextField();
        midtermPercentageField = new JTextField();
        midtermField.setPreferredSize(textFieldSize);
        midtermPercentageField.setPreferredSize(textFieldSize);
        addLabelAndField(panel, "期中成績:", midtermField, "百分比:", midtermPercentageField, gbc, row++);

        finalField = new JTextField();
        finalPercentageField = new JTextField();
        finalField.setPreferredSize(textFieldSize);
        finalPercentageField.setPreferredSize(textFieldSize);
        addLabelAndField(panel, "期末成績:", finalField, "百分比:", finalPercentageField, gbc, row++);

        normalField = new JTextField();
        normalPercentageField = new JTextField();
        normalField.setPreferredSize(textFieldSize);
        normalPercentageField.setPreferredSize(textFieldSize);
        addLabelAndField(panel, "平時成績:", normalField, "百分比:", normalPercentageField, gbc, row++);

        quizField = new JTextField();
        quizPercentageField = new JTextField();
        quizField.setPreferredSize(textFieldSize);
        quizPercentageField.setPreferredSize(textFieldSize);
        addLabelAndField(panel, "小考成績:", quizField, "百分比:", quizPercentageField, gbc, row++);

        attendanceField = new JTextField();
        attendancePercentageField = new JTextField();
        attendanceField.setPreferredSize(textFieldSize);
        attendancePercentageField.setPreferredSize(textFieldSize);
        addLabelAndField(panel, "出缺席成績:", attendanceField, "百分比:", attendancePercentageField, gbc, row++);

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveScores();
                JOptionPane.showMessageDialog(null, "成績已保存");
            }
        });
        buttonPanel.add(saveButton);

        JButton calculateButton = new JButton("計算");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = 0;

                total += getScore(midtermField, midtermPercentageField);
                total += getScore(normalField, normalPercentageField);
                total += getScore(quizField, quizPercentageField);
                total += getScore(attendanceField, attendancePercentageField);
                // Calculate remaining percentage for final exam
                double finalPercentage = getPercentage(finalPercentageField);

                // Calculate needed score for final exam
                double neededFinalScore = (60 - total) / (finalPercentage / 100);
                if (neededFinalScore < 0) {
                    JOptionPane.showMessageDialog(null, "只能說你嘎嘎亂過");
                } else if (neededFinalScore <= 100) {
                    JOptionPane.showMessageDialog(null, "你期末需要考 " + neededFinalScore + " 分才會過");
                } else {
                    JOptionPane.showMessageDialog(null, "你期末需要神才會過");
                }

            }
        });
        buttonPanel.add(calculateButton);

        JButton viewButton = new JButton("查看");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewScores();
            }
        });
        buttonPanel.add(viewButton);

        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        add(panel);
    }

    private void addLabelAndField(JPanel panel, String labelText1, JTextField field1, String labelText2, JTextField field2, GridBagConstraints gbc, int row) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText1), gbc);

        gbc.gridx = 1;
        panel.add(field1, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel(labelText2), gbc);

        gbc.gridx = 3;
        panel.add(field2, gbc);
    }

    private double getScore(JTextField scoreField, JTextField percentageField) {
        String scoreText = scoreField.getText();
        String percentageText = percentageField.getText();
        if (!scoreText.isEmpty() && !percentageText.isEmpty()) {
            double score = Double.parseDouble(scoreText);
            double percentage = Double.parseDouble(percentageText);
            return (score * percentage) / 100;
        }
        return 0;
    }

    private double getPercentage(JTextField percentageField) {
        String percentageText = percentageField.getText();
        if (!percentageText.isEmpty()) {
            return Double.parseDouble(percentageText);
        }
        return 0;
    }

    private void saveScores() {
        savedMidtermScore = midtermField.getText().isEmpty() ? -1 : Double.parseDouble(midtermField.getText());
        savedFinalScore = finalField.getText().isEmpty() ? -1 : Double.parseDouble(finalField.getText());
        savedNormalScore = normalField.getText().isEmpty() ? -1 : Double.parseDouble(normalField.getText());
        savedQuizScore = quizField.getText().isEmpty() ? -1 : Double.parseDouble(quizField.getText());
        savedAttendanceScore = attendanceField.getText().isEmpty() ? -1 : Double.parseDouble(attendanceField.getText());
    }

    private void viewScores() {
        String message = String.format(
            "期中成績: %s \n" +
            "期末成績: %s \n" +
            "平時成績: %s \n" +
            "小考成績: %s \n" +
            "出缺席成績: %s \n",
            formatScore(savedMidtermScore),
            formatScore(savedFinalScore),
            formatScore(savedNormalScore),
            formatScore(savedQuizScore),
            formatScore(savedAttendanceScore)
        );
        JOptionPane.showMessageDialog(null, message);
    }

    private String formatScore(double score) {
        return score == -1 ? "未考" : String.format("%.2f", score);
    }
}
