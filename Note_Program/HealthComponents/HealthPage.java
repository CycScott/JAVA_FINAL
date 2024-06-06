package HealthComponents;

import java.time.LocalDate;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HealthPage extends JFrame {
    public HealthPage() {
        setTitle("健康紀錄");
        setSize(400, 300); // 调整子页面大小
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // 外间距

        // 添加 "健康狀況:" 标签
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 占据一列
        panel.add(new JLabel("強化部位:"), gbc);

        // 添加第一个 JComboBox 并设置宽度
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // 跨越两列
        String[] options = {"","手臂", "胸部", "背部", "腹部","下半身"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setPreferredSize(new Dimension(200, comboBox.getPreferredSize().height)); // 设置宽度为 200
        panel.add(comboBox, gbc);

        // 添加 "健身紀錄:" 标签
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 占据一列
        panel.add(new JLabel("健身紀錄:"), gbc);

        // 添加第二个 JComboBox 并设置宽度，包含一个 "新增" 选项
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2; 
        JComboBox<String> comboBox2 = new JComboBox<>(new String[]{"+ 點擊新增",});
        comboBox2.setPreferredSize(new Dimension(200, comboBox2.getPreferredSize().height)); // 设置宽度为 200
        panel.add(comboBox2, gbc);

        // 添加 JTextArea
        gbc.gridx = 0;     // 起始列为 0
        gbc.gridy = 2;     // 第三行
        gbc.gridwidth = 1; // 只占用1列
        gbc.weightx = 0.75; // JTextArea 占大部分水平空间
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH; // 使其占满整个区域
        JTextArea descriptionArea = new JTextArea(1, 10);
        panel.add(new JScrollPane(descriptionArea), gbc); 

        // 添加第三個 JComboBox
        gbc.gridx = 1;     // 紧接着 JTextArea，从第2列开始
        gbc.gridy = 2;
        gbc.gridwidth = 1; // 占用1列
        gbc.weightx = 0.25; // JComboBox 占较少的水平空间
        JComboBox<String> comboBox3 = new JComboBox<>(new String[]{"","*10組","*20組","*30組"});
        comboBox3.setPreferredSize(new Dimension(200, comboBox3.getPreferredSize().height)); // 设置宽度为 200
        panel.add(comboBox3, gbc);

        // 添加 JTextArea
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH; // 使其占满整个区域
        JTextArea textArea = new JTextArea(1, 10);
        panel.add(new JScrollPane(textArea), gbc);
        
        // 為comboBox 添加事件監聽器
        comboBox.addActionListener(new ActionListener() {
            String newItem;
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("手臂".equals(comboBox.getSelectedItem()))
                {
                    newItem = "二頭肌屈臂。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "三頭肌後屈伸。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "過頭三頭肌後伸展。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "交叉垂式彎舉。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "三頭肌下壓。彈力帶";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                }
                else if ("胸部".equals(comboBox.getSelectedItem()))
                {
                    newItem = "上斜板凳臥推。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "板凳飛鳥。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "伏地挺身。彈力帶";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "下斜板凳臥推。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                }
                else if ("背部".equals(comboBox.getSelectedItem()))
                {
                    newItem = "屈體划船。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "跪姿下拉。彈力帶";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "硬舉。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "地上背部伸展";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "古巴旋轉。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                }
                else if ("腹部".equals(comboBox.getSelectedItem()))
                {
                    newItem = "反向捲腹";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "臥姿腿部鐘擺";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "站資轉體。彈力帶";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                }
                else if ("下半身".equals(comboBox.getSelectedItem()))
                {
                    newItem = "深蹲。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "俯臥屈腿。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "反向弓箭步。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                    newItem = "硬舉。啞鈴";
                    comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                }
            }
        });
         // 為comboBox2 添加事件監聽器
        comboBox2.addActionListener(new ActionListener() {
            String newItem;
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("+ 點擊新增".equals(comboBox2.getSelectedItem())) {
                    newItem = JOptionPane.showInputDialog(panel, "請添加新健身項目：");
                    if (newItem != null && !newItem.trim().isEmpty()) 
                    {
                        comboBox2.insertItemAt(newItem, comboBox2.getItemCount() - 1);
                        comboBox2.setSelectedItem(newItem);
                    }
                }
                newItem = (String) comboBox2.getSelectedItem();
                descriptionArea.setText(newItem);
            }
        });
        // 為comboBox3 添加事件監聽器
        comboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if ("*10".equals(comboBox3.getSelectedItem()))
                {
                    textArea.append(descriptionArea.getText()+(String)comboBox3.getSelectedItem()+"\r\n");
                }
            }
        });


        // 添加 JTextField
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // 跨越三列
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField healthField = new JTextField();
        panel.add(healthField, gbc);

        // 添加第一个提交按钮
        gbc.gridx = 0; // 第二列
        gbc.gridy = 4;
        gbc.gridwidth = 1; // 占用一列
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JButton submitButton = new JButton("儲存");
        panel.add(submitButton, gbc);

        // 添加第二个提交按钮
        gbc.gridx = 1; // 紧随第一个按钮的下一列
        gbc.gridy = 4;
        gbc.gridwidth = 1; // 同样占用一列
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        JButton button = new JButton("取消");
        panel.add(button, gbc);

        add(panel);
        setVisible(true);
        
        LocalDate today = LocalDate.now();  
        System.out.println(today);
        // submitButton 的 Click 事件
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate today = LocalDate.now(); // 檔名
                String str = textArea.getText();   // 內文

            }
        });
        // Button 的Click 事件
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HealthPage());
    }
}
