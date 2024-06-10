package HealthComponents;

import java.time.LocalDate;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.util.ArrayList;

public class HealthPage extends JFrame {
    ArrayList<Health> HealthList;
    LocalDate today; 
    private static final String HEALTH_FILE = "Note_Program/HealthComponents/Resource/healths.dat";
    DefaultListModel<String> HealthListModel = new DefaultListModel<>();

    GridBagConstraints gbc = new GridBagConstraints();
    JList<String> explorer = new JList<>();

    public HealthPage() 
    {
        HealthList = loadData();
        today = LocalDate.now();
        // 子页面大小與關閉設定
        setTitle("健身紀錄");
        setSize(400, 300); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // UI 繪製
        initializeUI();
    }
    public void initializeUI()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        //GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // 外間距

        // 添加 "健康狀況:" 标签
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(new JLabel("訓練部位:"), gbc);

        // 添加 partComboBox 可以選擇訓練的部分
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2; 
        String[] options = {"","手臂", "胸部", "背部", "腹部","下半身"};
        JComboBox<String> partComboBox = new JComboBox<>(options);
        partComboBox.setPreferredSize(new Dimension(200, partComboBox.getPreferredSize().height));
        panel.add(partComboBox, gbc);

        // 添加 "健身紀錄:" label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 占据一列
        panel.add(new JLabel("健身紀錄:"), gbc);

        // 添加第二个 methodComboBox，選擇訓練項目，且使用者可以自行新增
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2; 
        JComboBox<String> methodComboBox = new JComboBox<>(new String[]{"+ 點擊新增",});
        methodComboBox.setPreferredSize(new Dimension(200, methodComboBox.getPreferredSize().height)); // 设置宽度为 200
        panel.add(methodComboBox, gbc);

        // 添加 descriptionArea 顯示選擇訓練內容
        gbc.gridx = 0;     
        gbc.gridy = 2;     
        gbc.gridwidth = 1; 
        gbc.weightx = 0.75; 
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.BOTH;
        JTextArea descriptionArea = new JTextArea(1, 10);
        panel.add(new JScrollPane(descriptionArea), gbc); 

        // 添加 numberComboBox 用來設定訓練組數
        gbc.gridx = 1;     
        gbc.gridy = 2;
        gbc.gridwidth = 1; 
        gbc.weightx = 0.25; 
        JComboBox<String> numberComboBox = new JComboBox<>(new String[]{"","*10組","*20組","*30組"});
        numberComboBox.setPreferredSize(new Dimension(200, numberComboBox.getPreferredSize().height));
        panel.add(numberComboBox, gbc);

        // 添加 JTextArea
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH; // 使其占满整个区域
        JTextArea submitTextArea = new JTextArea(1, 10);
        panel.add(new JScrollPane(submitTextArea), gbc);
        
        // 為 partComboBox 添加事件監聽器
        partComboBox.addActionListener(new ActionListener() {
            String newItem;
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                descriptionArea.setText("");
                //numberComboBox.setSelectedIndex(0);
                methodComboBox.removeAllItems();
                methodComboBox.insertItemAt("+ 點擊新增", 0);
                if ("手臂".equals(partComboBox.getSelectedItem()))
                {
                    newItem = "二頭肌屈臂。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "三頭肌後屈伸。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "過頭三頭肌後伸展。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "交叉垂式彎舉。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "三頭肌下壓。彈力帶";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                }
                else if ("胸部".equals(partComboBox.getSelectedItem()))
                {
                    newItem = "上斜板凳臥推。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "板凳飛鳥。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "伏地挺身。彈力帶";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "下斜板凳臥推。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                }
                else if ("背部".equals(partComboBox.getSelectedItem()))
                {
                    newItem = "屈體划船。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "跪姿下拉。彈力帶";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "硬舉。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "地上背部伸展";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "古巴旋轉。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                }
                else if ("腹部".equals(partComboBox.getSelectedItem()))
                {
                    newItem = "反向捲腹";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "臥姿腿部鐘擺";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "站資轉體。彈力帶";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                }
                else if ("下半身".equals(partComboBox.getSelectedItem()))
                {
                    newItem = "深蹲。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "俯臥屈腿。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "反向弓箭步。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                    newItem = "硬舉。啞鈴";
                    methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                }
            }
        });

         // 為 methodComboBox 添加事件監聽器
        methodComboBox.addActionListener(new ActionListener() {
            String newItem;
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("+ 點擊新增".equals(methodComboBox.getSelectedItem())) 
                {
                    newItem = JOptionPane.showInputDialog(panel, "請添加新健身項目：");
                    if (newItem != null && !newItem.trim().isEmpty()) 
                    {
                        methodComboBox.insertItemAt(newItem, methodComboBox.getItemCount() - 1);
                        methodComboBox.setSelectedItem(newItem);
                    }
                }
                newItem = (String) methodComboBox.getSelectedItem();
                descriptionArea.setText(newItem);
            }
        });

        // 為 numberComboBox 添加事件監聽器
        numberComboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                    submitTextArea.append(descriptionArea.getText()+(String)numberComboBox.getSelectedItem()+"\r\n");
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


        // 添加"儲存" Button
        gbc.gridx = 0; 
        gbc.gridy = 4;
        gbc.gridwidth = 1; 
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JButton submitButton = new JButton("儲存");
        panel.add(submitButton, gbc);
        
        // 添加"取消" Button
        gbc.gridx = 1; 
        gbc.gridy = 4;
        gbc.gridwidth = 0; 
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        JButton cancelbButton = new JButton("取消");
        panel.add(cancelbButton, gbc);

        // 添加"檢視" Button
        gbc.gridx = 1; 
        gbc.gridy = 4;
        gbc.gridwidth = 0; 
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JButton checkButton = new JButton("檢視");
        panel.add(checkButton, gbc);



        add(panel);
        setVisible(true);

        // submitButton 的 Click 事件
        submitButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String string = today.toString() + " 的訓練菜單：\r\n";
                string += "========================\r\n";
                string += submitTextArea.getText();
                submitHealth(today.toString(), string);

                numberComboBox.setSelectedIndex(0);
                submitTextArea.setText("");
            }
        });
        // cancelbButton 的 Click 事件
        cancelbButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitTextArea.setText("");
            }
        });

        // checkButton 的 Click 事件
        checkButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewWindow();
            }
        });
    }

    private void openNewWindow()
    {
        JFrame RecordView = new JFrame();
        JList<String> indexList = new JList<>();       // 用于显示索引的列表
        JTextArea contentArea;                         // 显示内容的文本区域
        JButton deletedButton;

        RecordView.setTitle("Health Record Viewer");
        RecordView.setSize(600, 400);
        RecordView.setLocationRelativeTo(null);
        RecordView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        RecordView.setLayout(new BorderLayout());
        
        // 創建索引列表
        HealthListModel.clear();
        for (Health heal : HealthList) {
            HealthListModel.addElement(heal.getTitle());
        }
        indexList.setModel(HealthListModel);
        indexList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 创建文本区域
        contentArea = new JTextArea();
        contentArea.setEditable(false);

        // 添加滚动面板
        JScrollPane listScrollPane = new JScrollPane(indexList);
        listScrollPane.setPreferredSize(new Dimension(100, 400));
        JScrollPane textScrollPane = new JScrollPane(contentArea);

        // 监听列表选择事件
        indexList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) 
            {
                String selectedValue = indexList.getSelectedValue();
                if(selectedValue != null)   // 檢查是否有被選中
                {
                    for (Health heal : HealthList) 
                    {
                        contentArea.setText(heal.getContent());
                        if(heal.getTitle().equals(selectedValue)){
                            contentArea.setText(heal.getContent());
                            return;
                        }
                    }
                }
            }
        });

        deletedButton = new JButton();
        deletedButton.setText("刪除");

        // deletedButton 的 Click 事件
        deletedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String selectedValue = indexList.getSelectedValue();
                Boolean isDeleted = false;
                if(selectedValue != null)   // 檢查是否有被選中
                {
                    if(JOptionPane.showConfirmDialog(RecordView, "將要刪除"+indexList.getSelectedValue()+"!", "注意",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE)
                == JOptionPane.YES_OPTION){
                        isDeleted = true;
                        for(Health heal : HealthList)
                        {
                            if(heal.getTitle().equals(indexList.getSelectedValue())){
                                HealthList.remove(heal);
                                heal = null;
                                System.out.println("Remove Succes");                
                                saveData();
                                break;
                            }
                        }
                    }
                }
                // 顯示更新
                if(isDeleted)
                {
                    HealthListModel.clear();
                    for (Health heal : HealthList) {
                        HealthListModel.addElement(heal.getTitle());
                    }
                    indexList.setModel(HealthListModel); 
                    indexList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    contentArea.setText("");
                }
            }
        });

        // 将组件添加到窗口
        RecordView.add(listScrollPane, BorderLayout.WEST);
        RecordView.add(textScrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.add(deletedButton);
        RecordView.add(buttonPanel, BorderLayout.SOUTH);
        RecordView.setVisible(true);
    }
    
    private void saveData()
    {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HEALTH_FILE))){
            oos.writeObject(HealthList);
        }
        catch(IOException ie){
            ie.printStackTrace();
        }
    }

    public void submitHealth(String _title,String _Content)
    {
        for(Health heal : HealthList)
        {
             if(heal.getTitle().equals(_title))
             {
                 if(JOptionPane.showConfirmDialog(this, "將會覆蓋"+_title+"!", "注意",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE)
                 == JOptionPane.YES_OPTION){
                     heal.setContent(_Content);
                 }
                 saveData();
                 return;
            }
         }
         JOptionPane.showConfirmDialog(this, "紀錄已新增","提示",JOptionPane.CLOSED_OPTION, JOptionPane.INFORMATION_MESSAGE);
         Health temp = new Health(_title,_Content);
         HealthList.add(temp);
         saveData();
     }

    private ArrayList<Health> loadData(){
        File read = new File(HEALTH_FILE);
        if(read.exists()){
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(read))) {
                return (ArrayList<Health>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<Health>();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HealthPage());
    }
}
