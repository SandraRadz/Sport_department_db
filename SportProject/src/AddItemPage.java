
        import com.toedter.calendar.JDateChooser;
        import org.apache.poi.hssf.usermodel.HSSFCell;
        import org.apache.poi.hssf.usermodel.HSSFRow;
        import org.apache.poi.hssf.usermodel.HSSFSheet;
        import org.apache.poi.hssf.usermodel.HSSFWorkbook;
        import org.apache.poi.ss.usermodel.*;
        import javax.swing.*;
        import javax.swing.event.TableModelListener;
        import javax.swing.table.AbstractTableModel;
        import javax.swing.table.TableModel;
        import javax.xml.crypto.Data;
        import java.awt.*;
        import java.awt.Color;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.event.KeyAdapter;
        import java.awt.event.KeyEvent;
        import java.io.*;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.text.SimpleDateFormat;
        import java.time.format.DateTimeFormatter;
        import java.time.temporal.TemporalAccessor;
        import java.util.*;

public class AddItemPage extends WindowMenu {
    private DataBase dataBase;
    private String query;
    private String nameInDB;
    private ResultSet rs;
    private Label errorlabel;
    private User user;


    File fileRead;
    JTextField[] arr, fields;
    Vector goodsline, goodstable, menuDet;
    Vector xRow, xTable;
    String[] menu, types;
    JScrollPane scrollPane;
    JTable table;
    AbstractTableModel tableModel;
    int name_of_provider = 0;
    int count;
    double sum_of_bill = 0;
    Label label, lab;
    JComboBox cb, cb1, cb2;
    JDateChooser[] calend = new JDateChooser[2];
    Color or = new Color(246, 184, 61);
    Color gr = new Color(6, 100, 1);

    public AddItemPage(String nameT, String nameDB, User user) throws SQLException, IOException {
        super(nameT, user);
        this.nameInDB = nameDB;
        this.query = "select * from " + this.nameInDB + ";";
        this.user = user;
        DataBase db = new DataBase();
        this.rs = db.select(query);
        //db.close();
    }

    public AddItemPage(String nameT, String nameDB, User user, String query) throws SQLException {
        super(nameT, user);
        this.nameInDB = nameDB;
        this.query = query;
        this.user = user;
        DataBase db = new DataBase();
        this.rs = db.select(query);
        //db.close();
    }



    @Override
    public void show() throws SQLException {
        super.show();
        int colCount = rs.getMetaData().getColumnCount();
        //параметри для розміщення об*єктів
        int width = frame.getWidth() / colCount - 20;
        int height = 40;
        int x = 15;
        int y = 150;

        arr = new JTextField[colCount];
        menu = new String[colCount];
        types = new String[colCount];

        //label for error message
        errorlabel = new Label("");
        errorlabel.setForeground(Color.red);
        errorlabel.setSize(400, height);
        errorlabel.setLocation(x, y + height);
        frame.add(errorlabel);

        label = new Label("");

        for (int i = 0; i < colCount; i++) {
            //тип значення, що вводиться
            types[i] = rs.getMetaData().getColumnTypeName(i + 1);
            //підпис
            menu[i] = rs.getMetaData().getColumnLabel(i + 1);
            label = new Label(menu[i]);
            label.setLocation(x, y - height);
            label.setSize(width, height);
            frame.add(label);

            if (nameInDB.equals("accounts") && menu[i].equals("MFI_bank")) {
                cb = createComboBox("MFI_bank", "bank", width, height, x, y);
                frame.add(cb);
            } else if (nameInDB.equals("contracts") && menu[i].equals("provider_id")) {
                cb = createComboBox("provider_id", "providers", width, height, x, y);
                frame.add(cb);
            } else if (nameInDB.equals("contracts") && menu[i].equals("name_of_goods")) {
                cb1 = createComboBox("name_of_goods", "nomenOfDel", width, height, x, y);
                frame.add(cb1);
            } else if (nameInDB.equals("goodsOnStor") && menu[i].equals("name_of_storage")) {
                cb2 = createComboBox("name_of_storage", "regOfStor", width, height, x, y);
                frame.add(cb2);
            } else if (nameInDB.equals("goodsOnStor") && menu[i].equals("bill_id")) {
                cb1 = createComboBox("bill_id", "bill", width, height, x, y);
                frame.add(cb1);
            } else if (nameInDB.equals("bill") && menu[i].equals("provider_id")) {
                cb = createComboBox("provider_id", "providers", width, height, x, y);
                name_of_provider = Integer.parseInt((String) cb.getSelectedItem());
                System.out.println("int ===== " + name_of_provider);
                cb.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String str = (String) cb.getSelectedItem();
                        name_of_provider = Integer.parseInt(str);
                        DataBase db = new DataBase();
                        String quer = "SELECT DISTINCT Contracts.name_of_goods\n" +
                                "FROM Contracts\n" +
                                "WHERE provider_id=" + name_of_provider + " AND date_from<=CURDATE() AND date_to>=CURDATE();";
                        System.out.println(quer);
                        ResultSet res = null;
                        try {
                            res = db.select(quer);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        Vector FKlist = new Vector();
                        try {
                            while (res.next()) {
                                FKlist.add(res.getString(1));
                                System.out.println(res.getString(1));
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        System.out.println(FKlist);
                        frame.remove(cb2);
                        cb2 = new JComboBox(FKlist);
                        int w = frame.getWidth() / (count - 2) - 20;
                        int h = 30;
                        int x = 15;
                        int y = 295;
                        cb2.setSize(w, h);
                        cb2.setLocation(x, y);
                        try {
                            db.close();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        frame.repaint();
                        frame.add(cb2);
                    }
                });
                frame.add(cb);
            } else if (nameInDB.equals("bill") && menu[i].equals("account_id")) {
                cb1 = createComboBox("account_id", "accounts", width, height, x, y);
                frame.add(cb1);
            } else if (menu[i].equals("date_from") || menu[i].equals("date_to") || menu[i].equals("date_of_bill")) {
                JDateChooser calendar = new JDateChooser();
                calendar.setSize(width, height);
                calendar.setLocation(x, y);
                frame.add(calendar);
                int r = menu[i].equals("date_from") ? 0 : 1;
                calend[r] = calendar;
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
        if (nameInDB == "bill") {
            try {
                printBillDet();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        backButton();
        saveButtons();
        addExel();

    }

    public void chooseFile(){
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            fileRead = fileopen.getSelectedFile();
        }
    }
    public void addExel(){
        if(!nameInDB.equals("bill")) {
            JButton exelButton = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'>EXCEL</div></html>");
            exelButton.setSize(190, 30);
            exelButton.setLocation(190, 600);
            exelButton.setBackground(or);
            exelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        try {
                            createQueryAdd();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    ShowResPage main = new ShowResPage(nameOfTable, nameInDB, user);
                    try {
                        main.show();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    frame.dispose();
                }
            });
            frame.add(exelButton);
        }
    }


    public void saveButtons() {
        JButton saveItem = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'>ЗБЕРЕГТИ</div></html>");
        saveItem.setSize(190, 30);
        saveItem.setLocation(400, 600);
        saveItem.setBackground(or);
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                errorlabel.setLocation(15, 190);
                errorlabel.setText("");
                ShowResPage main = new ShowResPage(nameOfTable, nameInDB, user);
                int size = menu.length;
                DataBase db = new DataBase();
                String q = "INSERT INTO " + nameInDB + "\n VALUES (";
                if (nameInDB.equals("accounts")) {
                    if (arr[0].getText().isEmpty() || arr[0].getText() == null) {
                        errorlabel.setText("Заповніть всі поля");
                        return;
                    }
                    if (arr[0].getText().matches("[-+]?\\d+[.]?\\d+")) {
                        if (arr[0].getText().length() <= 16) {
                            q += arr[0].getText() + ", ";
                        } else {
                            errorlabel.setText("поле accounts має містити до 16 символів");
                            return;
                        }
                    }
                    q += cb.getSelectedItem() + ");";
                } else if (nameInDB.equals("contracts")) {
                    if (arr[0].getText().isEmpty()) {
                        errorlabel.setText("Заповніть всі поля");
                        return;
                    }
                    if (arr[0].getText().matches("[-+]?\\d+")) {
                        q += arr[0].getText() + ", ";
                    } else {
                        errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля (" + arr[0] + ")");
                        arr[0].setText(null);
                        return;
                    }

                    q += "'" + cb.getSelectedItem() + "', '" + cb1.getSelectedItem() + "', ";
                    SimpleDateFormat formmat1 = new SimpleDateFormat("yyyy-MM-dd");
                    String formatter = formmat1.format(calend[0].getCalendar().getTime());
                    String formatter2 = formmat1.format(calend[1].getCalendar().getTime());
                    q += "'" + formatter + "', '" + formatter2 + "');";
                } else if (nameInDB.equals("goodsOnStor")) {
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
                    if (arr[2].getText().matches("[-+]?\\d+[.]?\\d+")) q += arr[2].getText() + ", ";
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
                    q += "'" + cb1.getSelectedItem() + "', '" + cb2.getSelectedItem() + "');";
                } else if (nameInDB.equals("bill")) {

                    if (goodstable.size() == 0) {
                        errorlabel.setText("Накладна не може існувати без жодного товару");
                        return;
                    }
                    if (arr[0].getText().isEmpty() || arr[2].getText().isEmpty() || arr[3].getText().isEmpty()) {
                        errorlabel.setText("Заповніть всі поля");
                        return;
                    }
                    if (arr[0].getText().matches("[-+]?\\d+")) {
                        q += " '" + arr[0].getText() + "', '";
                    } else {
                        errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля ");
                        arr[0].setText(null);
                        return;
                    }
                    SimpleDateFormat formmat1 = new SimpleDateFormat("yyyy-MM-dd");
                    String formatter2 = formmat1.format(calend[1].getCalendar().getTime());
                    q +=  formatter2 + "', '" + arr[2].getText() + "', ";

                    if (arr[3].getText().matches("[-+]?\\d+[.]?\\d+")) {
                        q += arr[3].getText() + ", ";
                    } else {
                        errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля ");
                        arr[3].setText(null);
                        return;
                    }
                    q += "'" + cb.getSelectedItem() + "', '" + cb1.getSelectedItem() + "');";
                    try {
                        db.add(q);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    String addGoodsQuery = "INSERT INTO billDet VALUES";
                    int qq = 0;
                    try {
                        qq = countOfRows("billDet");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    for (int cq = 0; cq < goodstable.size(); cq++) {
                        Vector line = (Vector) goodstable.get(cq);
                        qq++;
                        addGoodsQuery += "(" + qq + ", '" + line.get(0) + "', " + line.get(1) + ", " + line.get(2) + ", " + line.get(3) + ", " + line.get(4) + ", " + line.get(5) + ", " + arr[0].getText() + ")";
                        if (cq < goodstable.size() - 1) addGoodsQuery += ",";
                        else addGoodsQuery += ";";
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
                        db.close();
                        frame.dispose();
                        if (nameInDB.equals("bill")) {
                            String que = "select  name_of_goods, price_per_unit, amount, summ, bill_det_id from BillDet where bill_id = " + arr[0].getText() + ";";
                            AddGoodsOnStor goodsOnStorAdd = new AddGoodsOnStor(nameOfTable, nameInDB, user, que, arr[0].getText());
                            goodsOnStorAdd.show();
                            return;
                        } else {
                            main.show();
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    for (int i = 0; i < size; i++) {
                        if (arr[i].getText().isEmpty() || arr[i].getText() == null) {
                            if (menu[i].equals("flat")) {
                                q += "null, ";
                            } else {
                                errorlabel.setText("Заповніть всі поля");
                                return;
                            }
                        } else if (types[i] == "INT") {
                            if (arr[i].getText().matches("[-+]?\\d+?")) {

                                q += arr[i].getText();
                                if (i < size - 1) q += ", ";
                                else q += ");";
                            } else {
                                errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля (" + arr[i] + ")");
                                arr[i].setText(null);
                                return;
                            }
                        } else {
                            if (nameInDB.equals("bank")) {
                                if (arr[0].getText().length() > 6 && menu[i].equals("MFI_bank")) {
                                    errorlabel.setText("поле MFI_bank має містити до 6 символів");
                                    return;
                                }
                                if (arr[0].getText().length() > 150 && menu[i].equals("name_of_bank")) {
                                    errorlabel.setText("поле name_of_bank має містити до 150 символів");
                                    return;
                                }
                            }
                            if (nameInDB.equals("providers")) {
                                if (arr[0].getText().length() > 6 && menu[i].equals("phone")) {
                                    errorlabel.setText("поле phone має містити до 20 символів");
                                    return;
                                }
                            }
                            q += "'" + arr[i].getText();
                            if (i < size - 1) q += "', ";
                            else q += "');";
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
                    errorlabel.setText("Неможливо зберегти запис. Наімовірніше, ви намагаєтеся додати запис з вже існуючим кодом (назвою)");
                }
            }
        });
        frame.add(saveItem);
    }

    public void backButton() {
        JButton cancelItem = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'>ВІДМІНИТИ</div></html>");
        cancelItem.setSize(190, 30);
        cancelItem.setLocation(610, 600);
        cancelItem.setBackground(or);
        cancelItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowResPage main = new ShowResPage(nameOfTable, nameInDB, user);
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

    public static JComboBox createComboBox(String fieldName, String tableName, int width, int height, int x, int y) throws SQLException {
        JComboBox comboBox;
        DataBase db = new DataBase();
        String q = "select " + fieldName + " from " + tableName + ";";
        ResultSet res = db.select(q);
        Vector FKlist = new Vector();
        try {
            while (res.next()) {
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
        double sumVal = 0;
        double sumVAT = 0;
        String q = "select * from billDet;";
        DataBase datab = new DataBase();
        ResultSet resset = datab.select(q);
        count = resset.getMetaData().getColumnCount();
        System.out.println(count);
        int w = frame.getWidth() / (count - 2) - 20;
        int h = 30;
        int x = 15;
        int y = 295;

        lab = new Label("Додати товари в накладну");
        lab.setSize(200, 30);
        lab.setLocation(x, 220);
        frame.add(lab);


        menuDet = new Vector();
        fields = new JTextField[count];
        for (int i = 1; i < count - 1; i++) {
            //підпис
            menuDet.add(resset.getMetaData().getColumnLabel(i + 1));
            Label l = new Label(menuDet.get(i - 1).toString());
            l.setSize(w, h);
            l.setLocation(x, y - h);
            frame.add(l);
            if (menuDet.get(i - 1).toString().equals("name_of_goods")) {
                DataBase db = new DataBase();
                String quer = "SELECT DISTINCT Contracts.name_of_goods\n" +
                        "FROM Contracts\n" +
                        "WHERE provider_id=" + name_of_provider + " AND date_from<=CURDATE() AND date_to>=CURDATE();";
                System.out.println(quer);
                ResultSet res = db.select(quer);
                Vector FKlist = new Vector();
                try {
                    while (res.next()) {
                        FKlist.add(res.getString(1));
                        System.out.println(res.getString(1));
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                System.out.println(FKlist);
                cb2 = new JComboBox(FKlist);
                cb2.setSize(w, h);
                cb2.setLocation(x, y);
                db.close();
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
        fields[2].addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                double r = 0;
                String myString;
                if (fields[2].getText().length() > 0) {
                    myString = fields[2].getText();
                    r = Integer.parseInt(myString);
                }
                double r2 = 1;
                if (fields[3].getText().length() > 0) {
                    myString = fields[3].getText();
                    r2 = Integer.parseInt(myString);
                }
                double vat = 0;
                if (fields[5].getText().length() > 0) {
                    myString = fields[5].getText();
                    vat = Integer.parseInt(myString);
                }
                double res = r * r2 + vat;
                fields[4].setText("" + (r * r2));
                fields[6].setText("" + (res));
            }
        });
        fields[3].addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                double r = 0;
                String myString;
                if (fields[2].getText().length() > 0) {
                    myString = fields[2].getText();
                    r = Integer.parseInt(myString);
                }
                double r2 = 1;
                if (fields[3].getText().length() > 0) {
                    myString = fields[3].getText();
                    r2 = Integer.parseInt(myString);
                }
                double vat = 0;
                if (fields[5].getText().length() > 0) {
                    myString = fields[5].getText();
                    vat = Integer.parseInt(myString);
                }
                double res = r * r2;
                fields[4].setText("" + (r * r2));
                fields[6].setText("" + (res + vat));
            }
        });
        fields[5].addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                double r = 0;
                String myString;
                if (fields[2].getText().length() > 0) {
                    myString = fields[2].getText();
                    r = Integer.parseInt(myString);
                }
                double r2 = 1;
                if (fields[3].getText().length() > 0) {
                    myString = fields[3].getText();
                    r2 = Integer.parseInt(myString);
                }
                double vat = 0;
                if (fields[5].getText().length() > 0) {
                    myString = fields[5].getText();
                    vat = Integer.parseInt(myString);
                }
                double res = r * r2;
                fields[4].setText("" + (r * r2));
                fields[6].setText("" + (res + vat));
            }
        });


        goodstable = new Vector();
        addsmallplusButton();
        addsmallremoveButton();
    }


    public void addsmallplusButton() {
    	JButton goodAddItem = new JButton(new ImageIcon(getClass().getResource("pictures/plus.png")));
        goodAddItem.setSize(25, 25);
        goodAddItem.setLocation(lab.getX() + lab.getWidth() + 10, lab.getY() + 10);
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
        scrollPane.setSize(frame.getWidth() - 70, 180);
        scrollPane.setLocation(15, 400);
        frame.add(scrollPane);


        goodAddItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                errorlabel.setLocation(15, 350);
                errorlabel.setText("");
                goodsline = new Vector();
                goodsline.add(cb2.getSelectedItem());
                for (int t = 2; t < count - 1; t++) {
                    if (fields[t].getText().isEmpty()) {
                        errorlabel.setLocation(15, 350);
                        errorlabel.setText("Заповніть всі поля");
                        return;
                    }
                    if (fields[t].getText().matches("[-+]?\\d+[.]?\\d+") || fields[t].getText().matches("[-+]?\\d+")) {
                        goodsline.add(fields[t].getText());
                    } else {
                        errorlabel.setLocation(15, 350);
                        errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля (" + menuDet.get(t - 1) + ")");
                        return;
                    }
                }
                goodstable.add(goodsline.clone());
                String sumVAT = fields[6].getText();
                sum_of_bill += Double.parseDouble(sumVAT);
                arr[3].setText("" + sum_of_bill);
                for (int t = 2; t < count - 1; t++) {
                    fields[t].setText("");
                }

                tableModel.fireTableStructureChanged();
                frame.remove(scrollPane);
                table.repaint();
                scrollPane = new JScrollPane(table);
                scrollPane.setSize(frame.getWidth() - 70, 180);
                scrollPane.setLocation(15, 400);
                table.setRowHeight(30);
                frame.add(scrollPane);
                scrollPane.repaint();

            }
        });
        frame.add(goodAddItem);
    }


    public void addsmallremoveButton() {
    	JButton goodRemoveItem = new JButton(new ImageIcon(getClass().getResource("pictures/minus.png")));        goodRemoveItem.setSize(25, 25);
        goodRemoveItem.setLocation(lab.getX() + lab.getWidth() + 60, lab.getY() + 10);
        goodRemoveItem.setBackground(Color.red);
        goodRemoveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goodstable.remove(table.getSelectedRow());
                tableModel.fireTableStructureChanged();
                frame.remove(scrollPane);
                table.repaint();
                scrollPane = new JScrollPane(table);
                scrollPane.setSize(frame.getWidth() - 70, 180);
                scrollPane.setLocation(15, 400);
                table.setRowHeight(30);
                frame.add(scrollPane);
                scrollPane.repaint();
            }
        });
        frame.add(goodRemoveItem);
    }

    private boolean exists() throws SQLException {

        while (rs.next()) {

        }
        return false;
    }

    private int countOfRows(String nameOfTable) throws SQLException {
        DataBase q = new DataBase();
        String que = "select * from " + nameOfTable + ";";
        ResultSet rs = q.select(que);
        int count = 0;
        while (rs.next())
            count++;
        q.close();
        return count;
    }

    public void readFromExcel(File file) throws IOException {
        // Read XSL file
        FileInputStream inputStream = new FileInputStream(new File(String.valueOf(file)));

        // Get the workbook instance for XLS file
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        // Get first sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);

        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = sheet.iterator();
        xTable = new Vector();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // Get iterator to all cells of current row
            Iterator<Cell> cellIterator = row.cellIterator();

            xRow = new Vector();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                // Change to getCellType() if using POI 4.x
                CellType cellType = cell.getCellTypeEnum();
                System.out.println("TYPE "+cellType);

                switch (cellType) {
                    case _NONE:
                        xRow.add("");
                        break;
                    case BOOLEAN:
                        xRow.add(cell.getBooleanCellValue());
                        break;
                    case NUMERIC:
                        xRow.add(cell.getNumericCellValue());
                        break;
                    case STRING:
                        xRow.add(cell.getStringCellValue());
                        break;
                    case ERROR:
                        xRow.add("null");
                        break;
                        default:
                            break;
                }

            }
            xTable.add(xRow.clone());
            System.out.println("add " + xRow.clone());
        }
    }

    public void createQueryAdd() throws SQLException, IOException {
        chooseFile();
        if (fileRead.getName().endsWith(".xls")) {
            readFromExcel(fileRead);
            DataBase xData = new DataBase();
            for (int i = 0; i < xTable.size(); i++) {
                Vector help = (Vector) xTable.get(i);
                if (nameInDB.equals("bank")) {
                    try {
                        Double d = Double.parseDouble(""+help.get(0));
                        String q = "INSERT INTO bank VALUES (" + d.intValue() + ", '" + help.get(1) + "');";
                        System.out.println(q);
                        xData.add(q);
                    } catch (SQLException e) {
                        errorlabel.setText("Сталася помилка при завантажені");
                    }

                }
                if (nameInDB.equals("accounts")) {
                    try {
                        Double d = Double.parseDouble(""+help.get(0));
                        Double d2 =Double.parseDouble(""+help.get(1));
                        String q = "INSERT INTO accounts VALUES (" + d.intValue() + ", " + d2.intValue() + ");";
                        System.out.println(q);
                        xData.add(q);
                    } catch (SQLException e) {
                        errorlabel.setText("Сталася помилка при завантажені");
                    }

                }

                if (nameInDB.equals("nomenOfDel")) {
                    try {
                        Double d = Double.parseDouble(""+help.get(1));
                        Double d2 =Double.parseDouble(""+help.get(2));
                        String q = "INSERT INTO nomenOfDel VALUES ('" + help.get(0) + "', " +d.intValue()+", "+ d2.intValue() +", '"+help.get(3) +"');";
                        System.out.println(q);
                        xData.add(q);
                    } catch (SQLException e) {
                        errorlabel.setText("Сталася помилка при завантажені");
                    }

                }

                if (nameInDB.equals("providers")) {
                    try {
                        Double d = Double.parseDouble(""+help.get(0));
                        Double d2 =Double.parseDouble(""+help.get(5));
                        Double d3 =Double.parseDouble(""+help.get(6));
                        String q = "INSERT INTO providers VALUES (" + d.intValue() + ", '" +help.get(1)+"', '"+ help.get(2) +"', '"+help.get(3)+"', '"+ help.get(4) +"', "+d2.intValue()+","+d3.intValue() +");";
                        System.out.println(q);
                        xData.add(q);
                    } catch (SQLException e) {
                        errorlabel.setText("Сталася помилка при завантажені");
                    }

                }


                if (nameInDB.equals("regOfStor")) {
                    try {
                        String q = "INSERT INTO regOfStor VALUES ('" + help.get(0) + "', '" + help.get(1) + "', '"+help.get(2)+"');";
                        System.out.println(q);
                        xData.add(q);
                    } catch (SQLException e) {
                        errorlabel.setText("Сталася помилка при завантажені");
                    }

                }

                if (nameInDB.equals("contracts")) {
                    try {
                        Double d = Double.parseDouble(""+help.get(0));
                        String q = "INSERT INTO bank contracts (" + d.intValue() + ", '" + help.get(1) + "', '"+help.get(2)+", '" + help.get(3) + "', '"+", '" + help.get(4)+ "');";
                        System.out.println(q);
                        xData.add(q);
                    } catch (SQLException e) {
                        errorlabel.setText("Сталася помилка при завантажені");
                    }

                }

            }
            xData.close();
        }
    }
}


