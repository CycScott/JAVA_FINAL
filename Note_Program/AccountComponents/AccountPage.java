package AccountComponents;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class AccountPage extends JFrame {
    private ArrayList<Record> records = new ArrayList<>();
    private Record currentRecord = null;

    private JTextField dateField;
    private JTextField amountField;
    private JComboBox<String> categoryComboBox;
    private JTextArea descriptionArea;

    public AccountPage() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("記帳");
        setSize(600, 400);
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

        JLabel descriptionLabel = new JLabel("描述:");
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

        submitButton.addActionListener(e -> submitRecord());
        viewRecordsButton.addActionListener(e -> viewRecords());
        viewTotalButton.addActionListener(e -> showTotalAmount());
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
                // 編輯現有記錄
                currentRecord = new Record(date, amount, category, description); // 更新現有記錄
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

        String message = "支出總額：￥" + totalExpense + "\n收入總額：￥" + totalIncome;
        JOptionPane.showMessageDialog(this, message, "支出和收入總額", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        dateField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        amountField.setText("");
        categoryComboBox.setSelectedIndex(0);
        descriptionArea.setText("");
    }

    public void editRecord(UUID recordId) {
        for (Record record : records) {
            if (record.getId().equals(recordId)) {
                // 使用記錄的數據填充表單
                dateField.setText(record.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                amountField.setText(String.valueOf(record.getAmount()));
                categoryComboBox.setSelectedItem(record.getCategory());
                descriptionArea.setText(record.getDescription());
                currentRecord = record;
                break;
            }
        }
    }

    public void deleteRecord(UUID recordId) {
        records.removeIf(record -> record.getId().equals(recordId));
        JOptionPane.showMessageDialog(this, "記錄已刪除！");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AccountPage::new);
    }
}
