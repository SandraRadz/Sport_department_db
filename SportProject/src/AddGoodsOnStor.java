import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;


public class AddGoodsOnStor extends WindowMenu {
    Color or = new Color(246, 184, 61);
    private String query;
    protected String nameInDB;
    private User user;
    private String id_num;
    JLabel name, amount, summ, bill_id;
    JComboBox stor;
    double am = 1;
    JTextField countf;

    JTable table;
    JButton back;
    JScrollPane scrollPane;
    Vector menu, res, row;
    int lineCount;
    AbstractTableModel tableModel;
    Vector r;

    AddGoodsOnStor(String nameT, String nameDB, User user, String query, String id_num) {
        super(nameT, user);
        this.nameInDB = nameDB;
        this.user = user;
        this.query = query;
        this.id_num = id_num;
    }

    public void show() throws SQLException {
        super.show();
        addDBTable(30, 150, query);
        backButton();
        addCountField();
        addRow();
        back();
        addRowButton();
    }

    public void backButton() {

    }

    public void makeVec(String query) {
        DataBase db = new DataBase();
        int rowHeight = 40;
        ResultSet rs = null;
        try {
            rs = db.select(query);
            int colCount = rs.getMetaData().getColumnCount();
            rs.last();
            lineCount = rs.getRow();
            rs.beforeFirst();
            menu = new Vector();
            for (int i = 1; i <= colCount; i++) {
                System.out.println(rs.getMetaData().getColumnLabel(i));
                menu.add(rs.getMetaData().getColumnLabel(i));
            }
            res = new Vector();
            row = new Vector();
            Vector[] help = new Vector[lineCount];
            for (int i = 0; i < lineCount; i++) {
                rs.next();
                for (int j = 0; j < colCount; j++) {
                    row.add(rs.getString(j + 1));
                }
                help[i] = (Vector) row.clone();
                row.clear();
                res.add(help[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDBTable(int x, int y, String query) throws SQLException {
        makeVec(query);
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
                return menu.size();
            }

            public int getRowCount() {
                return res.size();
            }

            public String getColumnName(int columnIndex) {
                return menu.get(columnIndex).toString();
            }


            public Object getValueAt(int rowIndex, int columnIndex) {
                Vector r = (Vector) res.get(rowIndex);
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
        table.setRowHeight(40);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                r = (Vector) res.get(table.getSelectedRow());
                name.setText((String) r.get(0));
                amount.setText("" + r.get(2));
                summ.setText("" + Double.parseDouble("" + r.get(1)) * Double.parseDouble(amount.getText()));
                bill_id.setText(id_num);
            }
        });
        scrollPane = new JScrollPane(table);
        scrollPane.setSize(frame.getWidth() / 2, 500);
        //if(lineCount<=7)scrollPane.setSize(frame.getWidth()-70,40*lineCount+20);
        //else scrollPane.setSize(frame.getWidth()-70,300);
        scrollPane.setLocation(x, y);
        frame.add(scrollPane);
    }

    public void addCountField() {
        JLabel label = new JLabel("Кількість");
        label.setSize(50, 40);
        label.setLocation(frame.getWidth() / 2 + 50, 150);
        frame.add(label);

        countf = new JTextField();
        countf.setLocation(frame.getWidth() / 2 + 50, 200);
        countf.setSize(70, 40);
        frame.add(countf);

        countf.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                double d1 = 0;
                if (!countf.getText().equals("")) {
                    d1 = Double.parseDouble("" + countf.getText());
                }
                double d2 = Double.parseDouble("" + r.get(2));
                if (d1 > d2) {
                    countf.setText("" + r.get(2));
                }
                name.setText((String) r.get(0));
                amount.setText(countf.getText());
                summ.setText("" + Double.parseDouble("" + r.get(1)) * Double.parseDouble(amount.getText()));
                bill_id.setText(id_num);
            }
        });

    }

    public void addRow() throws SQLException {
        int x = frame.getWidth() / 2 + 150;
        int y = 150;
        JLabel name1 = new JLabel("Назва");
        name1.setSize(100, 40);
        name1.setLocation(x, y);
        frame.add(name1);

        name = new JLabel("");
        name.setSize(150, 40);
        name.setLocation(x + 150, y);
        frame.add(name);
        y += 50;

        JLabel amount1 = new JLabel("Кількість");
        amount1.setSize(100, 40);
        amount1.setLocation(x, y);
        frame.add(amount1);

        amount = new JLabel("");
        amount.setSize(150, 40);
        amount.setLocation(x + 150, y);
        frame.add(amount);
        y += 50;

        JLabel summ1 = new JLabel("Сума");
        summ1.setSize(100, 40);
        summ1.setLocation(x, y);
        frame.add(summ1);
        summ = new JLabel("");
        summ.setSize(150, 40);
        summ.setLocation(x + 150, y);
        frame.add(summ);
        y += 50;

        JLabel bill_id1 = new JLabel("Номер накладної");
        bill_id1.setSize(100, 40);
        bill_id1.setLocation(x, y);
        frame.add(bill_id1);
        bill_id = new JLabel("");
        bill_id.setSize(150, 40);
        bill_id.setLocation(x + 150, y);
        frame.add(bill_id);
        y += 50;

        JLabel stor1 = new JLabel("Склад");
        stor1.setSize(100, 40);
        stor1.setLocation(x, y);
        frame.add(stor1);
        stor = AddItemPage.createComboBox("name_of_storage", "regOfStor", 100, 40, x + 150, y);
        frame.add(stor);
    }

    public void back() {
        back = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'> НАЗАД </div></html>");
        back.setSize(120, 40);
        back.setLocation(780, 550);
        back.setBackground(or);

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowResPage newP = new ShowResPage("НАКЛАДНІ", "bill", user);
                boolean tr = true;
                for (int k = 0; k < res.size(); k++) {
                    r = (Vector) res.get(k);
                    if (Double.parseDouble("" + r.get(2)) != 0) tr = false;
                }
                if (tr) {
                    try {
                        newP.show();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    frame.dispose();
                }
                JLabel errLabel = new JLabel("озподіліть всі товари");
                errLabel.setSize(100, 40);
                errLabel.setLocation(600, 600);
                frame.add(errLabel);
            }
        });
        frame.add(back);
    }

    public void addRowButton() {
        JButton addRow = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'> ВІДПРАВИТИ НА СКЛАД </div></html>");
        addRow.setSize(200, 40);
        addRow.setLocation(550, 550);
        addRow.setBackground(or);

        JLabel error = new JLabel("");
        error.setSize(150, 40);
        error.setLocation(600, 600);
        frame.add(error);

        addRow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (summ.getText().length() > 0) {
                    ShowResPage newP = new ShowResPage("НАКЛАДНІ", "bill", user);

                    int bdgNum = 0;
                    int godstorNum = 0;
                    try {
                        bdgNum = countOfRows("BillDetGoods");
                        godstorNum = countOfRows("GoodsOnStor");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    DataBase dateb = new DataBase();
                    try {
                        String qq = "INSERT INTO GoodsOnStor VALUES(" + godstorNum + ", '" + name.getText() + "', " + amount.getText() +
                                ", " + summ.getText() + ", " + bill_id.getText() + ", '" + (String) stor.getSelectedItem() + "');";
                        System.out.println(qq);
                        dateb.add(qq);

                        qq = "INSERT INTO BillDetGoods VALUES(" + bdgNum + ", " + r.get(4) + ", " + godstorNum + ");";
                        System.out.println(qq);
                        dateb.add(qq);
                        r = (Vector) res.get(table.getSelectedRow());
                        double a = Double.parseDouble("" + r.get(2)) - Double.parseDouble("" + amount.getText());
                        r.set(2, a);
                        table.repaint();
                        name.setText("");
                        amount.setText("");
                        summ.setText("");
                        countf.setText("");



                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        dateb.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    error.setText("Оберіть один з записів");
                }

            }
        });
        frame.add(addRow);
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

}