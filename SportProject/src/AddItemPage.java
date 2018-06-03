import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class AddItemPage extends WindowMenu {
    private DataBase dataBase;
    private String query;
    private String nameInDB;
    private ResultSet rs;
    private Label errorlabel;


    JTextField[] arr, fields;
    Vector  goodsline, goodstable,menuDet;
    String[] menu, types;
    JScrollPane scrollPane;
    JTable table;
    AbstractTableModel tableModel;
    int count;
    Label label, lab;
    JComboBox cb, cb1, cb2;
    Color or = new Color(246, 184, 61);
    Color gr = new Color(6, 100, 1);

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
                cb = createComboBox("name_of_goods", "billDet", width, height, x, y );
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

    }


    public void saveButtons(){
        JButton saveItem = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'>ЗБЕРЕГТИ</div></html>");
        saveItem.setSize(190, 30);
        saveItem.setLocation( 400, 600);
        saveItem.setBackground(or);
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                errorlabel.setLocation(15, 190);
                errorlabel.setText("");
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

                        if (arr[3].getText().matches("[-+]?\\d+[.]?\\d+")) q += arr[3].getText() + ", ";
                        else {
                            errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля (" + arr[3] + ")");
                            arr[3].setText(null);
                            return;
                        }
                        q += "'"+cb1.getSelectedItem() + "', '"+cb2.getSelectedItem() + "');";
                    }
                    else if(nameInDB.equals("bill")) {
                        if(goodstable.size()==0) {
                            errorlabel.setText("Накладна не може існувати без жодного товару");
                            return;
                        }
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
                        if (arr[3].getText().matches("[-+]?\\d+[.]?\\d+"))
                        {
                            q += arr[3].getText() + ", ";
                        }
                        else {
                            errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля (" + arr[3] + ")");
                            arr[3].setText(null);
                            return;
                        }
                        q+= "'"+cb.getSelectedItem() + "', '"+cb1.getSelectedItem() + "');";
                        try {
                            db.add(q);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                        String addGoodsQuery ="INSERT INTO billDet VALUES";
                        for(int cq=0; cq<goodstable.size(); cq++){
                            Vector line = (Vector) goodstable.get(cq);
                            int qq= 0;
                            try {
                               qq = countOfRows("billDet");
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            qq++;
                            System.out.println("QQ     =   "+qq);
                            addGoodsQuery+="("+qq+", '"+line.get(0)+"', "+line.get(1)+", "+line.get(2)+", "+line.get(3)+", "+line.get(4)+", "+line.get(5)+", "+arr[0].getText()+")";
                            if(cq<goodstable.size()-1)addGoodsQuery+=",";
                            else addGoodsQuery+=";";
                        }

                        System.out.println(addGoodsQuery);
                        DataBase dq = new DataBase();
                        try {
                            dq.add(addGoodsQuery);
                            dq.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                        try {
                            main.show();
                            db.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        frame.dispose();

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
                        db.close();
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
        count = resset.getMetaData().getColumnCount();
        System.out.println(count);
        int w = frame.getWidth()/(count-2) -20;
        int h = 30;
        int x = 15;
        int y=295;

        lab = new Label("Додати товари в накладну");
        lab.setSize(200, 30);
        lab.setLocation(x, 220);
        frame.add(lab);


        menuDet = new Vector();
        fields = new JTextField[count];
        for (int i = 1; i<count-1; i++) {
            //підпис
            menuDet.add(resset.getMetaData().getColumnLabel(i + 1));
            Label l = new Label(menuDet.get(i-1).toString());
            l.setSize(w, h);
            l.setLocation(x, y - h);
            frame.add(l);
            if(menuDet.get(i-1).toString().equals("name_of_goods")) {
                cb2 = createComboBox("name_of_goods", "nomenOfDel", w, h, x, y );
                frame.add(cb2);
            }
            //дефолт
            else {
                fields[i] = new JTextField();
                fields[i].setSize(w, h);
                fields[i].setLocation(x, y);
                frame.add(fields[i]);
            }
            x += w + 10;
        }

        goodstable = new Vector();
        addsmallplusButton();
        addsmallremoveButton();

        //goodsline.add();
           }

    public void addsmallplusButton(){
        JButton goodAddItem = new JButton("<html> <style type='text/css'>.plus{color: white; font-size:20; text-align:center;} </style><div class='plus'>+</div></html>");
        goodAddItem.setSize(25, 25);
        goodAddItem.setLocation(lab.getX()+lab.getWidth()+10, lab.getY()+10);
        goodAddItem.setBackground(gr);

            tableModel = new AbstractTableModel() {

                private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

                public void addTableModelListener(TableModelListener listener) {
                    listeners.add(listener);
                }

                public void removeTableModelListener(TableModelListener listener) {
                    listeners.remove(listener);
                }

                public Class<?> getColumnClass(int columnIndex) {
                    return String.class;
                }

                public int getColumnCount() {
                    return menuDet.size();
                }

                public int getRowCount() {
                    return goodstable.size();
                }
                public String getColumnName(int columnIndex) {
                    return menuDet.get(columnIndex).toString();
                }


                public Object getValueAt(int rowIndex, int columnIndex) {
                    Vector r = (Vector) goodstable.get(rowIndex);
                    return r.get(columnIndex);
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }

                public void setValueAt(Object value, int rowIndex, int columnIndex) {
                }
            };

        table = new JTable(tableModel);
        table.setAutoCreateColumnsFromModel(true);
        table.setIgnoreRepaint(false);
        table.repaint();
        table.setRowHeight(30);
        scrollPane = new JScrollPane(table);
        scrollPane.setSize(frame.getWidth()-70, 180);
        scrollPane.setLocation(15,400);
        frame.add(scrollPane);
        //table.setSize(frame.getWidth()-70, 180);
        //table.setLocation(15,400);
        //frame.add(table);


        goodAddItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                errorlabel.setLocation(15, 350);
                errorlabel.setText("");
                goodsline = new Vector();
                goodsline.add(cb2.getSelectedItem());
                for (int t=2; t<count-1; t++){
                    if(fields[t].getText().isEmpty()){
                        errorlabel.setLocation(15, 350);
                        errorlabel.setText("Заповніть всі поля");
                        return;
                    }
                    if (fields[t].getText().matches("\\d*[+.?\\d+]")) {
                        goodsline.add(fields[t].getText());
                        System.out.println(fields[t].getText()+"    *******   ");
                    } else {
                        errorlabel.setLocation(15, 350);
                        errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля ("+menuDet.get(t-1)+")");
                        return;
                    }
                }
                goodstable.add(goodsline.clone());
                for (int t=2; t<count-1; t++){
                    fields[t].setText("");
                    }

                tableModel.fireTableStructureChanged();
                frame.remove(scrollPane);
                table.repaint();
                scrollPane = new JScrollPane(table);
                scrollPane.setSize(frame.getWidth()-70, 180);
                scrollPane.setLocation(15,400);
                table.setRowHeight(30);
                frame.add(scrollPane);
                scrollPane.repaint();
            }
        });
        frame.add(goodAddItem);
    }


    public void addsmallremoveButton() {
        JButton goodRemoveItem = new JButton("<html> <style type='text/css'>.plus{color: white; font-size:20; text-align:center;} </style><div class='plus'>-</div></html>");
        goodRemoveItem.setSize(25, 25);
        goodRemoveItem.setLocation(lab.getX() + lab.getWidth() + 60, lab.getY()+10);
        goodRemoveItem.setBackground(Color.red);
        goodRemoveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DataBase data = new DataBase();
                int itemnum = table.getSelectedRow();
                Vector r = (Vector) goodstable.get(itemnum);
                System.out.println("vec  "+r);
                System.out.println(r.get(0));
                String q = "DELETE FROM " + nameInDB + " WHERE bill_id = " + arr[0].getText() +" AND name_of_goods='"+ cb2.getSelectedItem().toString() +"' amount="+ r.get(1)+";";
                System.out.println(q);
                try {
                    data.delete(q);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                try {
                    data.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frame.add(goodRemoveItem);
    }
    private boolean exists() throws SQLException {

        while(rs.next()){

        }
        return false;
    }

    private int countOfRows(String nameOfTable) throws SQLException {
        DataBase q = new DataBase();
        String que = "select * from "+nameOfTable+";";
        ResultSet rs=q.select(que);
        int count=0;
        while(rs.next())
            count++;
        q.close();
        return count;
    }

}




