package DiaryComponents;
import javax.swing.*;
import java.awt.*;

public class DiaryPage extends JFrame {
    public DiaryPage() {
        setTitle("日記");
        setSize(400, 300); // 調整子頁面大小
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        addComponents(panel, constraints);

        add(panel);
        setVisible(true);
    }

    public void addComponents(JPanel _panel, GridBagConstraints _constraints){
        JTextArea diaryArea = new JTextArea();
        _panel.add(new JScrollPane(diaryArea), BorderLayout.CENTER);

        JButton submitButton = new JButton("提交");
        _panel.add(submitButton, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> submitDiary(diaryArea));
    }

    public void submitDiary(JTextArea _diaryContent){
       // _diaryContent.getText().toString()
    }
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DiaryPage::new);
    }
}

