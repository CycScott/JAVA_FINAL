import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AccountPage extends JFrame {
    private ArrayList<Record> records = new ArrayList<>();

    public AccountPage() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("記帳");
        setSize(400, 300);
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

        JTextField dateField = new JTextField(20);
        dateField.setEditable(false);
        dateField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        constraints.gridx = 1;
        panel.add(dateField, constraints);

        JLabel amountLabel = new JLabel("金額:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(amountLabel, constraints);

        JTextField amountField = new JTextField(20);
        constraints.gridx = 1;
        panel.add(amountField, constraints);

        JLabel categoryLabel = new JLabel("類別:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(categoryLabel, constraints);

        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"支出", "收入"});
        constraints.gridx = 1;
        panel.add(categoryComboBox, constraints);

        JLabel descriptionLabel = new JLabel("描述:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(descriptionLabel, constraints);

        JTextArea descriptionArea = new JTextArea(4, 20);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        constraints.gridx = 1;
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
        panel.add(viewRecordsButton, constraints);

        submitButton.addActionListener(e -> submitRecord(dateField, amountField, categoryComboBox, descriptionArea));
        viewRecordsButton.addActionListener(e -> viewRecords());
    }

    private void submitRecord(JTextField dateField, JTextField amountField, JComboBox<String> categoryComboBox, JTextArea descriptionArea) {
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
            Record record = new Record(date, amount, category, description);
            records.add(record);
            JOptionPane.showMessageDialog(this, "記錄已保存！");
            clearForm(amountField, descriptionArea);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "金額格式不正確！", "錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewRecords() {
        // 打開一個新的窗口顯示最近的記錄
        new ViewRecordsPage(records);
    }

    private void clearForm(JTextField amountField, JTextArea descriptionArea) {
        amountField.setText("");
        descriptionArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AccountPage::new);
    }
}
