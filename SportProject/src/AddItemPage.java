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
    JComboBox cb, cb1, cb2;
    Color or = new Color(246, 184, 61);

    public AddItemPage(String nameT, String nameDB) throws SQLException {
        super(nameT);
        this.nameInDB=nameDB;
        this.query="select * from "+this.nameInDB +";";
        DataBase db = new DataBase();
        this.rs=db.select(query);
        //db.close();
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
        int y=150;

        arr =new JTextField[colCount];
        menu = new String[colCount];
        types = new String[colCount];

        //label for error message
        errorlabel=new Label("");
        errorlabel.setForeground(Color.red);
        errorlabel.setSize(400, height);
        errorlabel.setLocation(x, y+height);
        frame.add(errorlabel);

        label = new Label("");
        if(nameInDB=="bill"){
            try {
                printBillDet();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

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
                cb = createComboBox("MFI_bank", "bank", width, height, x, y );
                frame.add(cb);
            }
            else if(nameInDB.equals("contracts") && menu[i].equals("provider_id")){
                cb=createComboBox("provider_id", "providers", width, height, x, y );

                frame.add(cb);
            }
            else if(nameInDB.equals("contracts") && menu[i].equals("name_of_goods")){
                cb1 = createComboBox("name_of_goods", "nomenOfDel", width, height, x, y );
                frame.add(cb1);
            }
            else if(nameInDB.equals("goodsOnStor") && menu[i].equals("name_of_storage")){
                cb2 = createComboBox("name_of_storage", "regOfStor", width, height, x, y );
                frame.add(cb2);
            }
            else if(nameInDB.equals("goodsOnStor") && menu[i].equals("name_of_goods")){
                cb = createComboBox("name_of_goods", "nomenOfDel", width, height, x, y );
                frame.add(cb);
            }
            else if(nameInDB.equals("goodsOnStor") && menu[i].equals("bill_id")){
                cb1 = createComboBox("bill_id", "bill", width, height, x, y );
                frame.add(cb1);
            }
            else if(nameInDB.equals("bill") && menu[i].equals("provider_id")){
                cb=createComboBox("provider_id", "providers", width, height, x, y );
                frame.add(cb);
            }
            else if(nameInDB.equals("bill") && menu[i].equals("account_id")){
                cb1 = createComboBox("account_id", "accounts", width, height, x, y );
                frame.add(cb1);
            }
            //дефолт
            else {
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
                            q += arr[0].getText() + ", ";
                        }
                        q+=cb.getSelectedItem()+");";
                    }

                    else if(nameInDB.equals("contracts")) {
                        if(arr[0].getText().isEmpty() || arr[3].getText().isEmpty() || arr[4].getText().isEmpty()){
                            errorlabel.setText("Заповніть всі поля");
                            return;
                        }
                        if(arr[0].getText().matches("[-+]?\\d+")){
                            q += arr[0].getText() + ", ";}
                        else {
                            errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля (" + arr[0] + ")");
                            arr[0].setText(null);
                            return;
                        }

                        q+="'"+cb.getSelectedItem()+"', '"+cb1.getSelectedItem()+"', ";
                        if(arr[3].getText().matches("(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)") && arr[3].getText().matches("(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)")){
                            q += "'"+arr[3].getText() + "', '"+arr[4].getText()+"');";
                    }
                        else {
                           errorlabel.setText("Введіть дату в форматі рік-місяць-день");
                            return;
                       }
        }
                    else if(nameInDB.equals("goodsOnStor")) {
                        if (arr[0].getText().isEmpty() || arr[2].getText().isEmpty() || arr[3].getText().isEmpty()) {
                            errorlabel.setText("Заповніть всі поля");
                            return;
                        }
                        //ПЕРЕПИСАТИ
                        if (arr[0].getText().matches("[-+]?\\d+")) q += arr[0].getText() + ", ";
                        else {
                            errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля (" + arr[0] + ")");
                            arr[0].setText(null);
                            return;
                        }
                        q += "'" + cb.getSelectedItem() + "', ";
                        if (arr[2].getText().matches("[-+]?\\d+")) q += arr[2].getText() + ", ";
                        else {
                            errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля (" + arr[2] + ")");
                            arr[2].setText(null);
                            return;
                        }

                        if (arr[3].getText().matches("[-+]?\\d+.\\d+")) q += arr[3].getText() + ", ";
                        else {
                            errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля (" + arr[3] + ")");
                            arr[3].setText(null);
                            return;
                        }
                        q += "'"+cb1.getSelectedItem() + "', '"+cb2.getSelectedItem() + "');";
                    }
                    else if(nameInDB.equals("bill")) {
                        if(arr[0].getText().isEmpty() || arr[1].getText().isEmpty() || arr[2].getText().isEmpty() || arr[3].getText().isEmpty()){
                            errorlabel.setText("Заповніть всі поля");
                            return;
                        }
                        q += " '"+arr[0].getText() + "', ";
                        if(arr[1].getText().matches("(19|20)\\d\\d-((0[1-9]|1[012])-(0[1-9]|[12]\\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)") ){
                            q += "'"+arr[1].getText() + "', '"+arr[2].getText() +"', ";
                        }
                        else {
                            errorlabel.setText("Введіть дату в форматі рік-місяць-день");
                            return;
                        }
                        if (arr[3].getText().matches("[-+]?\\d+.\\d+"))
                        {
                            q += arr[3].getText() + ", ";
                        }
                        else {
                            errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля (" + arr[3] + ")");
                            arr[3].setText(null);
                            return;
                        }
                        q+= "'"+cb.getSelectedItem() + "', '"+cb1.getSelectedItem() + "');";

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
        JComboBox comboBox;
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

    public void printBillDet() throws SQLException {
        String q="select * from billDet;";
        DataBase datab = new DataBase();
        ResultSet resset=datab.select(q);
        int count = rs.getMetaData().getColumnCount();
        int width = frame.getWidth()/count -20;
        int height = 30;
        int x = 15;
        int y=280;

        Label lab = new Label("Додати товари в накладну");
        lab.setSize(300, 30);
        lab.setLocation(x, 220);
        frame.add(lab);


        String menuDet[] = new String[count];
        JTextField[] fields = new JTextField[count];
        for (int i = 0; i<count; i++) {
            //підпис
            menuDet[i] = resset.getMetaData().getColumnLabel(i + 1);
            Label l = new Label(menuDet[i]);
            l.setSize(width, height);
            l.setLocation(x, y - height);
            frame.add(l);
            System.out.println("DGYSHASDGUHJDSGAUJSOIAD");
            if(menuDet[i].equals("MFI_bank")) {
                cb = createComboBox("MFI_bank", "bank", width, height, x, y );
                frame.add(cb);
            }
            //дефолт
            else {
                System.out.println(menu[i]);
                fields[i] = new JTextField();
                fields[i].setSize(width, height);
                fields[i].setLocation(x, y);
                frame.add(fields[i]);
            }
            x += width + 10;
        }

    }
    private boolean exists() throws SQLException {

        while(rs.next()){

        }
        return false;
    }

}
