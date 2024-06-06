import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AccountPage extends JFrame {
    private static final String RECORDS_FILE = "records.txt";

    public AccountPage() {
        setTitle("記帳");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("日期:"));
        JTextField dateField = new JTextField();
        dateField.setEditable(false);
        dateField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        panel.add(dateField);

        panel.add(new JLabel("金額:"));
        JTextField amountField = new JTextField();
        panel.add(amountField);

        panel.add(new JLabel("類別:"));
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"支出", "收入"});
        panel.add(categoryComboBox);

        panel.add(new JLabel("描述:"));
        JTextArea descriptionArea = new JTextArea();
        panel.add(descriptionArea);

        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(e -> {
            String date = dateField.getText();
            String amount = amountField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            String description = descriptionArea.getText();

            // 保存記錄
            saveRecord(date, amount, category, description);

            // 清空表單
            amountField.setText("");
            descriptionArea.setText("");
        });
        panel.add(submitButton);

        add(panel);

        setVisible(true);
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
