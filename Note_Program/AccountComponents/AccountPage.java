package AccountComponents;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountPage extends JFrame {
    private ArrayList<Record> records = new ArrayList<>();
    private Record currentRecord = null;

    private JTextField dateField;
    private JTextField amountField;
    private JComboBox<String> categoryComboBox;
    private JTextArea descriptionArea;
    private JTextField searchField;
    private JButton searchButton;

    public AccountPage() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("記帳");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        addComponents(panel, constraints);

        add(panel);
        setVisible(true);
    }

    private void addComponents(JPanel panel, GridBagConstraints constraints) {
        JLabel dateLabel = new JLabel("日期:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(dateLabel, constraints);

        dateField = new JTextField(20);
        dateField.setEditable(false);
        dateField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        constraints.gridx = 1;
        panel.add(dateField, constraints);

        JLabel amountLabel = new JLabel("金額:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(amountLabel, constraints);

        amountField = new JTextField(20);
        constraints.gridx = 1;
        panel.add(amountField, constraints);

        JLabel categoryLabel = new JLabel("類別:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(categoryLabel, constraints);

        categoryComboBox = new JComboBox<>(new String[]{"支出", "收入"});
        constraints.gridx = 1;
        panel.add(categoryComboBox, constraints);

        JLabel descriptionLabel = new JLabel("備註:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(descriptionLabel, constraints);

        descriptionArea = new JTextArea(4, 20);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, constraints);

        JButton submitButton = new JButton("提交");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, constraints);

        JButton viewRecordsButton = new JButton("查看最近的記錄");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        panel.add(viewRecordsButton, constraints);

        JButton viewTotalButton = new JButton("顯示支出和收入總額");
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        panel.add(viewTotalButton, constraints);

        JLabel searchLabel = new JLabel("搜尋:");
        constraints.gridx = 0;
        constraints.gridy = 7;
        panel.add(searchLabel, constraints);

        searchField = new JTextField(20);
        constraints.gridx = 1;
        panel.add(searchField, constraints);

        searchButton = new JButton("搜尋");
        constraints.gridx = 2;
        panel.add(searchButton, constraints);

        JButton pieChartButton = new JButton("顯示圓餅圖");
        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        panel.add(pieChartButton, constraints);

        JButton barChartButton = new JButton("顯示月度收入和支出長條圖");
        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.gridwidth = 2;
        panel.add(barChartButton, constraints);

        submitButton.addActionListener(e -> submitRecord());
        viewRecordsButton.addActionListener(e -> viewRecords());
        viewTotalButton.addActionListener(e -> showTotalAmount());
        searchButton.addActionListener(e -> searchRecords());
        pieChartButton.addActionListener(e -> showPieChart());
        barChartButton.addActionListener(e -> showBarChart());
    }

    private void submitRecord() {
        String dateText = dateField.getText();
        String amountText = amountField.getText();
        String category = (String) categoryComboBox.getSelectedItem();
        String description = descriptionArea.getText();

        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請輸入金額！", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            LocalDate date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            if (currentRecord == null) {
                // 新增記錄
                Record record = new Record(date, amount, category, description);
                records.add(record);
                JOptionPane.showMessageDialog(this, "記錄已保存！");
            } else {
                // 更新現有記錄
                currentRecord.setDate(date);
                currentRecord.setAmount(amount);
                currentRecord.setCategory(category);
                currentRecord.setDescription(description);
                JOptionPane.showMessageDialog(this, "記錄已更新！");
                currentRecord = null; // 清除編輯模式
            }
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "金額格式不正確！", "錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewRecords() {
        new ViewRecordsPage(records, this);
    }

    private void showTotalAmount() {
        double totalIncome = 0;
        double totalExpense = 0;

        for (Record record : records) {
            if (record.getCategory().equals("支出")) {
                totalExpense += record.getAmount();
            } else {
                totalIncome += record.getAmount();
            }
        }

        String message = "支出總額：￥" + totalExpense + "\n收入總額：￥" + totalIncome + "\n淨收入 ：￥"+(totalIncome-totalExpense);
        JOptionPane.showMessageDialog(this, message);
    }

    private void searchRecords() {
        String query = searchField.getText().toLowerCase();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請輸入搜尋關鍵字！");
            return;
        }

        ArrayList<Record> searchResults = new ArrayList<>();
        for (Record record : records) {
            if (record.getDescription().toLowerCase().contains(query)) {
                searchResults.add(record);
            }
        }

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "未找到符合條件的記錄！");
        } else {
            new ViewRecordsPage(searchResults, this);
        }
    }

    private void showPieChart() {
        double totalIncome = 0;
        double totalExpense = 0;
        Map<String, Double> incomeData = new HashMap<>();
        Map<String, Double> expenseData = new HashMap<>();
    
        for (Record record : records) {
            if (record.getCategory().equals("支出")) {
                totalExpense += record.getAmount();
                expenseData.put(record.getDescription(), expenseData.getOrDefault(record.getDescription(), 0.0) + record.getAmount());
            } else {
                totalIncome += record.getAmount();
                incomeData.put(record.getDescription(), incomeData.getOrDefault(record.getDescription(), 0.0) + record.getAmount());
            }
        }
    
        Map<String, Double> dataToShow;
        String title;
    
        if (totalIncome > totalExpense) {
            dataToShow = incomeData;
            title = "收入比例餅圖";
        } else {
            dataToShow = expenseData;
            title = "支出比例餅圖";
        }
    
        JPanel chartPanel = ChartUtils.createPieChart(dataToShow, title);
        JFrame chartFrame = new JFrame("餅圖");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.add(chartPanel);
        chartFrame.pack();
        chartFrame.setLocationRelativeTo(null);
        chartFrame.setVisible(true);
    }
    

    private void showBarChart() {
        Map<String, Map<String, Double>> data = new HashMap<>();
        for (Record record : records) {
            String month = record.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            String category = record.getCategory();

            data.putIfAbsent(category, new HashMap<>());
            data.get(category).put(month, data.get(category).getOrDefault(month, 0.0) + record.getAmount());
        }

        JPanel chartPanel = ChartUtils.createBarChart(data, "月度收入和支出條形圖");
        JFrame chartFrame = new JFrame("條形圖");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.add(chartPanel);
        chartFrame.pack();
        chartFrame.setLocationRelativeTo(null);
        chartFrame.setVisible(true);
    }

    private void clearForm() {
        dateField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        amountField.setText("");
        categoryComboBox.setSelectedIndex(0);
        descriptionArea.setText("");
    }

    public void deleteRecord(UUID recordId) {
        records.removeIf(record -> record.getId().equals(recordId));
        JOptionPane.showMessageDialog(this, "記錄已刪除！");
        viewRecords();
    }

    public void editRecord(UUID id) {
        for (Record record : records) {
            if (record.getId().equals(id)) {
                currentRecord = record;
                dateField.setText(record.getDate().toString());
                amountField.setText(String.valueOf(record.getAmount()));
                categoryComboBox.setSelectedItem(record.getCategory());
                descriptionArea.setText(record.getDescription());
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AccountPage::new);
    }
}
