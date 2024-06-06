package AccountComponents;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class ViewRecordsPage extends JFrame {
    private ArrayList<Record> records;
    private AccountPage accountPage;

    public ViewRecordsPage(ArrayList<Record> records, AccountPage accountPage) {
        this.records = records;
        this.accountPage = accountPage;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("最近的記錄");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        int y = 0;
        for (Record record : records) {
            constraints.gridx = 0;
            constraints.gridy = y;
            panel.add(new JLabel(record.toString()), constraints);

            JButton editButton = new JButton("編輯");
            editButton.addActionListener(e -> editRecord(record.getId()));
            constraints.gridx = 1;
            panel.add(editButton, constraints);

            JButton deleteButton = new JButton("刪除");
            deleteButton.addActionListener(e -> deleteRecord(record.getId()));
            constraints.gridx = 2;
            panel.add(deleteButton, constraints);

            y++;
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        setVisible(true);
    }

    private void editRecord(UUID recordId) {
        accountPage.editRecord(recordId);
        dispose();
    }

    private void deleteRecord(UUID recordId) {
        accountPage.deleteRecord(recordId);
        dispose();
    }
}
