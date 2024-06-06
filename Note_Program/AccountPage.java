import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AccountPage extends JFrame {
    private static final String RECORDS_FILE = "records.txt";

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

        submitButton.addActionListener(e -> submitRecord(dateField, amountField, categoryComboBox, descriptionArea));
    }

    private void submitRecord(JTextField dateField, JTextField amountField, JComboBox<String> categoryComboBox, JTextArea descriptionArea) {
        String date = dateField.getText();
        String amount = amountField.getText();
        String category = (String) categoryComboBox.getSelectedItem();
        String description = descriptionArea.getText();

        if (amount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "請輸入金額！", "錯誤", JOptionPane.ERROR_MESSAGE);
            return;
        }

        saveRecord(date, amount, category, description);

        // 清空表單
        amountField.setText("");
        descriptionArea.setText("");
    }

    private void saveRecord(String date, String amount, String category, String description) {
        try (FileWriter writer = new FileWriter(RECORDS_FILE, true)) {
            writer.write(date + "," + amount + "," + category + "," + description + "\n");
            writer.flush();
            JOptionPane.showMessageDialog(this, "記錄已保存！");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "保存記錄時出現錯誤：" + ex.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }
}
