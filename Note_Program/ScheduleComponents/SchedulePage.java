package ScheduleComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class SchedulePage extends JFrame {
    private JTable table;
    private Object[][] data;
    private String[] columnNames = {"時間", "星期一", "星期二", "星期三", "星期四", "星期五"};
    private DefaultTableModel model;
    public ViewCoursePage viewCoursePage;

    public SchedulePage() {
        setTitle("課表");
        setSize(604, 500); // 調整子頁面大小，以適應圖片和表格
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        data = new Object[][]{
            {" 8.05~8.55 ", "", "", "", "", ""},
            {" 9.05~9.55 ", "", "", "", "", ""},
            {"10.10~11.00", "", "", "", "", ""},
            {"11.10~12.00", "", "", "", "", ""},
            {"12.10~13.00", "午休", "午休", "午休", "午休", "午休"},
            {"13.10~14.00", "", "", "", "", ""},
            {"14.10~15.00", "", "", "", "", ""},
            {"15.15~16.05", "", "", "", "", ""},
            {"16.20~17.10", "", "", "", "", ""},
            {"17.20~18.10", "", "", "", "", ""},
            {"18.20~19.10", "", "", "", "", ""},
            {"19.15~20.05", "", "", "", "", ""},
            {"20.10~21.00", "", "", "", "", ""},
            {"21.05~21.55", "", "", "", "", ""},
            {"", "", "", "", "", ""} // 新增一行用來放置圖片
        };

        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                return c;
            }
        };

        // 设置最后一行的高度
        table.setRowHeight(14, 100); // 这一行只在初始化时执行
        table.setDefaultRenderer(Object.class, new CustomCellRenderer());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // 设置每一列的宽度
        int[] columnWidths = {100, 100, 100, 100, 100, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton addButton = new JButton("添加課程");
        buttonPanel.add(addButton);

        JButton deleteButton = new JButton("刪除課程");
        buttonPanel.add(deleteButton);

        JButton viewButton = new JButton("查看課程");
        buttonPanel.add(viewButton);

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

        viewCoursePage = new ViewCoursePage();
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCoursePage.setVisible(true);
            }
        });

        add(panel);
        setVisible(true);
    }

    public void addCourse(int dayOfWeek, int startPeriod, int endPeriod, String courseName) {
        // 检查是否需要调整课程时间
        if (startPeriod >= 4) {
            startPeriod++;
            endPeriod++;
        }

        for (int i = startPeriod; i <= endPeriod; i++) {
            model.setValueAt(courseName, i, dayOfWeek);
        }
        model.fireTableDataChanged();
        table.setRowHeight(14, 100); // 确保最后一行的高度不会被改变

        if (viewCoursePage != null) {
            viewCoursePage.addCourseButton(courseName);
        }
    }

    public void deleteCourse(int dayOfWeek, int startPeriod, int endPeriod, String courseName) {
        // 检查是否需要调整课程时间
        if (startPeriod >= 4) {
            startPeriod++;
            endPeriod++;
        }

        for (int i = startPeriod; i <= endPeriod; i++) {
            if (courseName.equals(model.getValueAt(i, dayOfWeek))) {
                model.setValueAt("", i, dayOfWeek);
            }
        }
        model.fireTableDataChanged();
        table.setRowHeight(14, 100); // 确保最后一行的高度不会被改变
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SchedulePage::new);
    }

    // 自定义表格单元格渲染器
    class CustomCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // 在右下角单元格显示图片
            if (row == table.getRowCount() - 1 && column == table.getColumnCount() - 1) {
                JLabel label = new JLabel();
                label.setHorizontalAlignment(JLabel.CENTER); // 设置居中对齐
                try {
                    ImageIcon imageIcon = new ImageIcon("images.jpeg"); // 图片的路径
                    Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(image));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return label;
            }
            return cellComponent;
        }
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

        // 新建一个按钮面板来居中放置按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
        buttonPanel.add(addButton);

        // 使用Box布局管理器来实现垂直排列组件
        Box box = Box.createVerticalBox();
        box.add(panel);
        box.add(buttonPanel);

        add(box);
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
        Iterator<JButton> iterator = courseButtons.iterator(); // 使用迭代器來避免ConcurrentModificationException
        while (iterator.hasNext()) {
            JButton button = iterator.next();
            if (button.getText().equals(courseName)) {
                coursePanel.remove(button);
                iterator.remove();
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
    private JTextField courseNameField;

    public DeleteCourseDialog(SchedulePage schedulePage) {
        this.schedulePage = schedulePage;
        setTitle("刪除課程");
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

        // 新建一个按钮面板来居中放置按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton deleteButton = new JButton("刪除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dayOfWeek = dayOfWeekComboBox.getSelectedIndex() + 1;
                int startPeriod = (int) startPeriodComboBox.getSelectedItem() - 1;
                int endPeriod = (int) endPeriodComboBox.getSelectedItem() - 1;
                String courseName = courseNameField.getText();

                System.out.println("Deleting course: " + courseName);
                System.out.println("Day of Week: " + dayOfWeek);
                System.out.println("Start Period: " + startPeriod);
                System.out.println("End Period: " + endPeriod);

                schedulePage.deleteCourse(dayOfWeek, startPeriod, endPeriod, courseName);
                if (schedulePage.viewCoursePage != null) {
                    System.out.println("viewCoursePage is not null. Removing course button.");
                    schedulePage.viewCoursePage.removeCourseButton(courseName);
                } else {
                    System.out.println("viewCoursePage is null.");
                }
                dispose();
            }
        });
        buttonPanel.add(deleteButton);

        // 使用Box布局管理器来实现垂直排列组件
        Box box = Box.createVerticalBox();
        box.add(panel);
        box.add(buttonPanel);

        add(box);
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
    private JTextField projectField;
    private JTextField projectPercentageField;

    private double savedMidtermScore;
    private double savedFinalScore;
    private double savedNormalScore;
    private double savedQuizScore;
    private double savedAttendanceScore;
    private double savedProjectScore;

    private Clip clip;

    private Clip playSound(String soundFileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            return clip;
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
            return null;
        }
    }

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

        projectField = new JTextField();
        projectPercentageField = new JTextField();
        projectField.setPreferredSize(textFieldSize);
        projectPercentageField.setPreferredSize(textFieldSize);
        addLabelAndField(panel, "專題成績:", projectField, "百分比:", projectPercentageField, gbc, row++);

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
                total += getScore(projectField, projectPercentageField);
                // Calculate remaining percentage for final exam
                double finalPercentage = getPercentage(finalPercentageField);

                // Calculate needed score for final exam
                double neededFinalScore = (60 - total) / (finalPercentage / 100);
                if (neededFinalScore < 0) {
                    clip = playSound("success.wav");
                    JOptionPane.showMessageDialog(null, "只能說你嘎嘎亂過");
                    if (clip != null && clip.isRunning()) {
                        clip.stop();
                    }
                } else if (neededFinalScore <= 59) {
                    JOptionPane.showMessageDialog(null, "你期末需要考 " + neededFinalScore + " 分加上一點點努力才會過");
                }else if (neededFinalScore <=100) {
                    clip = playSound("cheer.wav");
                    JOptionPane.showMessageDialog(null, "你期末需要考 " + neededFinalScore + " 分加上八點點努力才會過");
                    if (clip != null && clip.isRunning()) {
                        clip.stop();
                    }
                } 
                else {
                    clip = playSound("fail.wav");
                    JOptionPane.showMessageDialog(null, "你期末需要神才會過");
                    if (clip != null && clip.isRunning()) {
                        clip.stop();
                    }
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

        // 添加視窗監聽器來停止音樂
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (clip != null && clip.isRunning()) {
                    clip.stop();
                }
            }
        });
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
        savedProjectScore = projectField.getText().isEmpty() ? -1 : Double.parseDouble(projectField.getText());
    }

    private void viewScores() {
        String message = String.format(
            "期中成績: %s \n" +
            "期末成績: %s \n" +
            "平時成績: %s \n" +
            "小考成績: %s \n" +
            "出缺席成績: %s \n"+
            "專題成績: %s \n",
            formatScore(savedMidtermScore),
            formatScore(savedFinalScore),
            formatScore(savedNormalScore),
            formatScore(savedQuizScore),
            formatScore(savedAttendanceScore),
            formatScore(savedProjectScore)
        );
        JOptionPane.showMessageDialog(null, message);
    }

    private String formatScore(double score) {
        return score == -1 ?"未考" : String.format("%.2f", score);
    }
}
