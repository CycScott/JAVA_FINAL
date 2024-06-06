import javax.swing.*;
import java.awt.*;

public class AccountPage extends JFrame {
    public AccountPage() {
        setTitle("記帳");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel("這是記帳的頁面"));

        add(panel);

        setVisible(true);
    }
}
