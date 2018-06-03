import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class AddItemPage extends WindowMenu {
    private DataBase dataBase;
    private String query;
    private String nameInDB;
    private ResultSet rs;
    private Label errorlabel;


    JTextField[] arr;
    String[] menu;
    String[] types;
    Label label;
    JComboBox comboBox;
    Color or = new Color(246, 184, 61);

    public AddItemPage(String nameT, String nameDB) throws SQLException {
        super(nameT);
        this.nameInDB=nameDB;
        this.query="select * from "+this.nameInDB +";";
        DataBase db = new DataBase();
        this.rs=db.select(query);

    }

    public int m(){
        return 0;
    }

    @Override
    public void show() throws SQLException {
        super.show();

        int colCount = rs.getMetaData().getColumnCount();
        //параметри для розміщення об*єктів
        int width = frame.getWidth()/colCount-20;
        int height = 40;
        int x = 15;
        int y=250;

        arr =new JTextField[colCount];
        menu = new String[colCount];
        types = new String[colCount];

        //label for error message
        errorlabel=new Label("");
        errorlabel.setSize(400, height);
        errorlabel.setLocation(x, y+height);
        frame.add(errorlabel);

        label = new Label("");
        for (int i = 0; i<colCount; i++) {
            //тип значення, що вводиться
            types[i] = rs.getMetaData().getColumnTypeName(i + 1);
            //підпис
            menu[i] = rs.getMetaData().getColumnLabel(i + 1);
            label = new Label(menu[i]);
            label.setSize(width, height);
            label.setLocation(x, y - height);
            frame.add(label);

            if(nameInDB.equals("accounts") && menu[i].equals("MFI_bank")) {
                JComboBox cb = createComboBox("MFI_bank", "bank", width, height, x, y );
                frame.add(cb);
            }
            else if(nameInDB.equals("contracts") && menu[i].equals("provider_id")){
                JComboBox cb = createComboBox("provider_id", "providers", width, height, x, y );
                frame.add(cb);
            }
            //дефолт
            else {
                System.out.println(menu[i]);
                System.out.println(nameInDB);
                arr[i] = new JTextField();
                arr[i].setSize(width, height);
                arr[i].setLocation(x, y);
                frame.add(arr[i]);
            }
            x += width + 10;
        }

        backButton();
        saveButtons();
        JFrame.setDefaultLookAndFeelDecorated(true);
    }


    public void saveButtons(){
        JButton saveItem = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'>ЗБЕРЕГТИ</div></html>");
        saveItem.setSize(190, 30);
        saveItem.setLocation( 400, 600);
        saveItem.setBackground(or);
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowResPage main = new ShowResPage(nameOfTable, nameInDB);
                int size = menu.length;
                DataBase db = new DataBase();
                String q ="INSERT INTO "+ nameInDB+"\n VALUES (";
                    if(nameInDB.equals("accounts")) {
                        if(arr[0].getText().isEmpty() || arr[0].getText() == null){
                            errorlabel.setText("Заповніть всі поля");
                            return;
                        }
                        if(arr[0].getText().matches("[-+]?\\d+")){
                            q += arr[0].getText() + ", ";}
                        q+=comboBox.getSelectedItem()+");";
                    }
                    else if(nameInDB.equals("contracts")) {
                        if(arr[0].getText().matches("[-+]?\\d+")){
                            q += arr[0].getText() + ", ";}
                        q+=comboBox.getSelectedItem()+");";


                    }
                    else {
                        for (int i = 0; i < size; i++) {
                            if (arr[i].getText().isEmpty() || arr[i].getText() == null) {
                                if (menu[i].equals("flat")) {
                                    q += "null, ";
                                } else {
                                    errorlabel.setText("Заповніть всі поля");
                                    return;
                                }
                            }
                            else if (types[i] == "INT" ) {
                                    if (arr[i].getText().matches("[-+]?\\d+") ) {
                                        q += arr[i].getText();
                                        if(i<size-1)q+=", ";
                                        else q+=");";
                                    } else {
                                        errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля ("+arr[i]+")");
                                        arr[i].setText(null);
                                        return;
                                    }
                                } else {
                                    q += "'" + arr[i].getText();
                                    if(i<size-1)q+="', ";
                                    else q+="');";
                                }
                            }
                    }
                System.out.println(q);
                try {
                        db.add(q);
                        main.show();
                        frame.dispose();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frame.add(saveItem);
    }

    public void backButton(){
        JButton cancelItem = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'>ВІДМІНИТИ</div></html>");
        cancelItem.setSize(190, 30);
        cancelItem.setLocation( 610, 600);
        cancelItem.setBackground(or);
        cancelItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowResPage main = new ShowResPage(nameOfTable, nameInDB);
                try {
                    main.show();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });
        frame.add(cancelItem);
    }

    private JComboBox createComboBox(String fieldName, String tableName, int width, int height, int x, int y ) throws SQLException {
            DataBase db = new DataBase();
            String q = "select "+fieldName +" from "+ tableName+";";
            ResultSet res =  db.select(q);
            Vector FKlist = new Vector();
            try {
                while(res.next()){
                    FKlist.add(res.getString(1));
                    System.out.println(res.getString(1));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            comboBox = new JComboBox(FKlist);
            comboBox.setSize(width, height);
            comboBox.setLocation(x, y);
            db.close();
            return comboBox;
    }

    private boolean exists() throws SQLException {

        while(rs.next()){

        }
        return false;
    }

}
