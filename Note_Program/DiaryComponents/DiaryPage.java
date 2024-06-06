package DiaryComponents;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class DiaryPage extends JFrame {
    ArrayList<Diary> DiaryList;
    private static final String DIARY_FILE = "Note_Program/DiaryComponents/dairys.dat";
    DefaultListModel<String> DiaryListModel = new DefaultListModel<>();

    JLabel titleLabel = new JLabel("標題: ");
    JTextArea diaryArea = new JTextArea(10, 20);
    JTextArea diaryTitle = new JTextArea(2, 20);
    JButton submitButton = new JButton("儲存");
    JList<String> explorer = new JList<>();
    JScrollPane explorerScroller = new JScrollPane(explorer);
    
    public DiaryPage() {
        
        DiaryList = loadData();
        updateExplorer();
        
        setTitle("日記");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 5, 5, 5);

        addComponents(panel, constraints);

        add(panel);
        setVisible(true);
    }

    public void addComponents(JPanel _panel, GridBagConstraints _constraints){


        _constraints.anchor = GridBagConstraints.PAGE_START;
        _constraints.gridx = 2;
        _constraints.gridy = 1;
        _panel.add(titleLabel, _constraints);
        

        diaryTitle.setPreferredSize(new Dimension(200, 25));
        _constraints.anchor = GridBagConstraints.PAGE_END;
        _constraints.gridx = 3;
        _constraints.gridy = 1;
        _panel.add(diaryTitle, _constraints);


        _constraints.gridx = 4;
        _constraints.gridy = 8;
        _constraints.anchor = GridBagConstraints.LAST_LINE_END;
        _panel.add(submitButton, _constraints);


        explorerScroller.setPreferredSize(new Dimension(150, 200));
        _constraints.gridx = 0;
        _constraints.gridy = 1;
        _constraints.gridwidth = 1;
        _constraints.gridheight = 7;
        _constraints.fill = GridBagConstraints.BOTH;
        _panel.add((explorerScroller), _constraints);


        _constraints.gridx = 2;
        _constraints.gridy = 2;
        _constraints.gridwidth = 3;
        _constraints.gridheight = 5;
        _constraints.weightx = 1.0;
        _constraints.weighty = 1.0;
        _constraints.fill = GridBagConstraints.BOTH;
        _panel.add(new JScrollPane(diaryArea), _constraints);

        submitButton.addActionListener(e -> submitDiary(diaryTitle,diaryArea));
        explorer.addListSelectionListener(e -> diarySelected());
    }

    public void updateExplorer(){
        if (!DiaryList.isEmpty()) {
            DiaryListModel.clear();
            for (Diary dry : DiaryList) {
                DiaryListModel.addElement(dry.getTitle());
            }
        }
        DiaryListModel.addElement("New...");
        explorer.setModel(DiaryListModel); 
        explorer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    public void submitDiary(JTextArea _title,JTextArea _Content){
       for(Diary dry : DiaryList){
            if(dry.getTitle().equals(_title.getText())){
                if(JOptionPane.showConfirmDialog(this, "將會覆蓋"+_title.getText()+"!", "注意",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE)
                == JOptionPane.YES_OPTION){
                    dry.setContent(_Content.getText());
                }
                saveData();
                return;
            }
        }
        Diary temp = new Diary(_title.getText(),_Content.getText());
        DiaryList.add(temp);
        saveData();
        updateExplorer();
        diaryTitle.setText("");
        diaryArea.setText("");
    }

    public void diarySelected(){
        if(explorer.getSelectedValue()!=null){
            if(explorer.getSelectedValue().equals("New...")){
                diaryTitle.setText("");
                diaryArea.setText("");
                submitButton.setText("新增");
                return;
            }
            else if(!explorer.getSelectedValue().equals(null)){
                for(Diary dry : DiaryList){
                    if(dry.getTitle().equals(explorer.getSelectedValue())){
                        diaryTitle.setText(dry.getTitle());
                        diaryArea.setText(dry.getContent());
                        submitButton.setText("儲存");
                        return;
                    }
                }
            }
        }
    }

    private void saveData(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DIARY_FILE))){
            oos.writeObject(DiaryList);
        }
        catch(IOException ie){
            ie.printStackTrace();
        }
    }

    private ArrayList<Diary> loadData(){
        File read = new File(DIARY_FILE);
        if(read.exists()){
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(read))) {
                return (ArrayList<Diary>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<Diary>();
    }
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DiaryPage::new);
    }
}

