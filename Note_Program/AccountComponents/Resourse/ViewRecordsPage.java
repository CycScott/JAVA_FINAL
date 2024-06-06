package AccountComponents.Resourse;
import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class ViewRecordsPage extends JFrame {
    private ArrayList<Record> records;

    public ViewRecordsPage(ArrayList<Record> records) {
        this.records = records;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("最近的記錄");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        StringBuilder sb = new StringBuilder();
        for (Record record : records) {
            sb.append(record).append("\n");
        }
        textArea.setText(sb.toString());

        add(panel);
        setVisible(true);
    }
}
