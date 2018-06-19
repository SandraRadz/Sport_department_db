import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class ShowResPage extends WindowMenu {
    Color or = new Color(246, 184, 61);
    Color gr = new Color(12, 184, 61);

    private String query;
    protected String nameInDB;
    private User user;
    JTable table;
    JScrollPane scrollPane;
    Vector menu, res, row;
    String[] types;
    int colC;
    int colCount;
    int lineCount;
    AbstractTableModel tableModel;
    JTextField[] filters;
    Vector r, v;

    public ShowResPage(String nameT, String nameDB, User user) {
        super(nameT, user);
        this.nameInDB=nameDB;
        this.user=user;
        this.query="select * from "+this.nameInDB +";";
    }

    public ShowResPage(String nameT, String nameDB, User user, String query) {
        super(nameT, user);
        this.nameInDB=nameDB;
        this.user=user;
        this.query=query;
    }

    //сторінка з таблицями
    @Override
    public void show() throws SQLException {
        super.show();
        if((user.getStatus().equals("admin") || user.getStatus().equals("buh")) && !nameInDB.equals("billDet") && !nameInDB.equals("goodsOnStor")){
            addButtons();
        }
        if(nameInDB.equals("bill")){
            JLabel info = new JLabel("Для перегляду товарів в накладній натисніть двічі на потрібному записі");
            info.setLocation(500, 200);
            info.setSize(450,30);
            frame.add(info);
        }
        if(nameInDB.equals("billDet")){
            JButton back = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'> НАЗАД </div></html>");
            back.setSize(190, 30);
            back.setLocation( 500, 100);
            back.setBackground(or);
            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ShowResPage newP = new ShowResPage("НАКЛАДНІ", "bill", user);
                    try {
                        newP.show();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    frame.dispose();
                }
            });
            frame.add(back);
        }
        int y = 20;
        if(user.getStatus().equals("admin") || user.getStatus().equals("buh")) {
            JButton bank = new JButton("БАНКИ");
            bank.setSize(150, 30);
            bank.setLocation(frame.getWidth() / 30, y);
            bank.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ShowResPage newP = new ShowResPage("БАНКИ", "bank", user);
                    try {
                        newP.show();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    frame.dispose();
                }
            });

            frame.add(bank);
            y += 30;

            JButton accounts = new JButton("РАХУНКИ");
            accounts.setSize(150, 30);
            accounts.setLocation(frame.getWidth() / 30, y);
            accounts.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ShowResPage newP = new ShowResPage("РАХУНКИ", "accounts", user);
                    try {
                        newP.show();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    frame.dispose();
                }
            });
            frame.add(accounts);
            y += 30;

            JButton nomenOfDel = new JButton("ТОВАРИ");
            nomenOfDel.setSize(150,30);
            nomenOfDel.setLocation(frame.getWidth()/30,y);
            nomenOfDel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ShowResPage newP = new ShowResPage("ТОВАРИ", "nomenOfDel", user);
                    try {
                        newP.show();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    frame.dispose();
                }
            });
            frame.add(nomenOfDel);
            y+=30;

            JButton providers = new JButton("ПОСТАЧАЛЬНИКИ");
            providers.setSize(150,30);
            providers.setLocation(frame.getWidth()/30,y);
            providers.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ShowResPage newP = new ShowResPage("ПОСТАЧАЛЬНИКИ", "providers", user);
                    try {
                        newP.show();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    frame.dispose();
                }
            });
            frame.add(providers);
            y+=30;

            JButton contracts = new JButton("УГОДИ");
            contracts.setSize(150,30);
            contracts.setLocation(frame.getWidth()/30,y);
            contracts.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ShowResPage newP = new ShowResPage("УГОДИ", "contracts", user);
                    try {
                        newP.show();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    frame.dispose();
                }
            });
            frame.add(contracts);
            y+=30;
        }

        JButton bill = new JButton("НАКЛАДНІ");
        bill.setSize(150,30);
        bill.setLocation(frame.getWidth()/30,y);
        bill.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowResPage newP = new ShowResPage("НАКЛАДНІ", "bill", user);
                try {
                    newP.show();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });
        frame.add(bill);
        y+=30;



        JButton goodsOnStor = new JButton("ЗАПАСИ НА СКЛАДАХ");
        goodsOnStor.setSize(150,30);
        goodsOnStor.setLocation(frame.getWidth()/30,y);
        goodsOnStor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowResPage newP = new ShowResPage("ЗАПАСИ НА СКЛАДАХ", "goodsOnStor", user);
                try {
                    newP.show();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });
        frame.add(goodsOnStor);
        y+=30;

        JButton storages = new JButton("СКЛАДИ");
        storages.setSize(150,30);
        storages.setLocation(frame.getWidth()/30,y);
        storages.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowResPage newP = new ShowResPage("СКЛАДИ", "regOfStor", user);
                try {
                    newP.show();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });
        frame.add(storages);
        y+=30;

        addDBTable(30, 380, query);
        addfilters(30, 270, query);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void addButtons(){
        JButton addItem = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'> +"+super.nameOfTable.toUpperCase()+"</div></html>");
        addItem.setSize(190, 30);
        addItem.setLocation( 500, 100);
        addItem.setBackground(or);
        addItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                AddItemPage add = null;
                AddGoodsOnStor goodsOnStorAdd=null;
                try {
//                    if(nameInDB.equals("goodsOnStor")) {
//                        goodsOnStorAdd = new AddGoodsOnStor(nameOfTable, nameInDB, user, "select name_of_goods, price_per_unit, amount, summ from BillDet;");
//                        goodsOnStorAdd.show();
//                        return;
//                    }
                    try {
                        add = new AddItemPage(nameOfTable, nameInDB, user);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                try {
                    add.show();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frame.add(addItem);

        JButton deleteItem = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'> ВИДАЛИТИ </div></html>");
        deleteItem.setSize(190, 30);
        deleteItem.setLocation( 700, 100);
        deleteItem.setBackground(or);
        deleteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int itemnum = table.getSelectedRow();
                r = (Vector) res.get(itemnum);
                System.out.println("vec  "+r);
                System.out.println(r.get(0));
                DataBase db = new DataBase();
                String q = "DELETE FROM " + nameInDB + " WHERE " + menu.get(0) + " = " + r.get(0) + ";";
                System.out.println(q);
                try {
                    db.delete(q);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                lineCount--;
                for (int i = 0; i < lineCount; i++) {
                    v = (Vector) res.get(i);
                    if ((String) v.firstElement() == (String) r.get(0))
                        res.remove(i);
                }
                scrollPane.remove(table);
                frame.remove(scrollPane);
                table = new JTable(res, menu);
                scrollPane = new JScrollPane(table);
                scrollPane.setLocation(30,380);
                table.setRowHeight(40);
                if(lineCount<=7)scrollPane.setSize(frame.getWidth()-70,40*lineCount+20);
                else scrollPane.setSize(frame.getWidth()-70,300);
                frame.add(scrollPane);
                frame.repaint();
                ShowResPage newP = new ShowResPage(nameOfTable, nameInDB, user);
            }
        });
        frame.add(deleteItem);
    }

    public void  addfilters(int x, int y, String query) {
        DataBase db = new DataBase();
        int rowHeight = 40;
        try {
            ResultSet rs = db.select(query);
            colC = rs.getMetaData().getColumnCount();
            rs.last();
            lineCount = rs.getRow();
            rs.beforeFirst();
            int width =(frame.getWidth()-90)/colC;
            //відображення списку атрибутів в бд
            JLabel filtername;
            types = new String[colC];
            filters = new JTextField[colC];
            for (int i = 1; i <= colC; i++) {
                types[i-1]=rs.getMetaData().getColumnTypeName(i);
                filtername = new JLabel(rs.getMetaData().getColumnLabel(i));
                filtername.setSize(width, rowHeight);
                filtername.setLocation(x, y);
                filters[i-1] = new JTextField();
                filters[i-1].setSize(width, rowHeight);
                filters[i-1].setLocation(x, y+rowHeight+10);
                frame.add(filters[i-1]);
                frame.add(filtername);
                x+=width+4;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JButton filterButton = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'> ЗАСТОСУВАТИ ФІЛЬТРИ </div></html>");
        filterButton.setSize(190, 30);
        filterButton.setLocation( 700, 150);
        filterButton.setBackground(or);
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel errorlabel = new JLabel("");
                errorlabel.setSize(200, 40);
                errorlabel.setLocation(100, 500);
                frame.add(errorlabel);
                String filterquery = "select * from " + nameInDB + " where ";
                int numfilter = 0;
                for (int i = 0; i < colC; i++) {
                    if (!filters[i].getText().isEmpty()) {

                        if (numfilter > 0) filterquery += " AND ";
                        if (types[i] == "INT") {
                            if (filters[i].getText().matches("[-+]?\\d+")) {
                                filterquery += menu.get(i).toString() + "=" + filters[i].getText() + " ";
                                //if(i<size-1)q+=", ";
                                //else q+=");";
                            } else {
                                errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля (" + filters[i] + ")");
                                filters[i].setText(null);
                                return;
                            }
                        } else if (types[i] == "REAL") {
                            if (filters[i].getText().matches("[-+]?\\d+[.]*\\d+]")) {
                                filterquery += menu.get(i).toString() + "=" + filters[i].getText() + " ";
                                //if(i<size-1)q+=", ";
                                //else q+=");";
                            } else {
                                errorlabel.setText("Здається, ви ввели некоректні значення в деякі поля (" + filters[i] + ")");
                                filters[i].setText(null);
                                return;
                            }
                        } else {
                            filterquery += menu.get(i).toString() + "='" + filters[i].getText() + "' ";
                        }
                        numfilter++;
                    }
                }
                filterquery += ";";
                System.out.println(filterquery);


                tableModel.fireTableStructureChanged();
                frame.remove(scrollPane);
                if (numfilter > 0) makeVec( filterquery);
                else makeVec(query);
                table.repaint();
                scrollPane = new JScrollPane(table);
                table.setRowHeight(rowHeight);
                scrollPane.setSize(frame.getWidth() - 70, 300);
                //if(lineCount<=7)scrollPane.setSize(frame.getWidth()-70,40*lineCount+20);
                //else scrollPane.setSize(frame.getWidth()-70,300);
                scrollPane.setLocation(30, 380);
                table.setRowHeight(40);
                scrollPane.repaint();
                frame.add(scrollPane);
            }
        });

        frame.add(filterButton);

        JButton showAll = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'> ПРИБРАТИ ФІЛЬТРИ </div></html>");
        showAll.setSize(190, 30);
        showAll.setLocation( 500, 150);
        showAll.setBackground(or);
        showAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.fireTableStructureChanged();
                frame.remove(scrollPane);
                makeVec( query);
                table.repaint();
                scrollPane = new JScrollPane(table);
                table.setRowHeight(rowHeight);
                scrollPane.setSize(frame.getWidth() - 70, 300);
                //if(lineCount<=7)scrollPane.setSize(frame.getWidth()-70,40*lineCount+20);
                //else scrollPane.setSize(frame.getWidth()-70,300);
                scrollPane.setLocation(30, 380);
                table.setRowHeight(40);
                scrollPane.repaint();
                frame.add(scrollPane);
            }
        });
        frame.add(showAll);
    }

    public void makeVec(String query){
        DataBase db = new DataBase();
        int rowHeight=40;
        ResultSet rs = null;
        try {
            rs = db.select(query);
            int colCount = rs.getMetaData().getColumnCount();
            rs.last();
            lineCount = rs.getRow();
            rs.beforeFirst();
            menu = new Vector();
            for (int i=1; i<=colCount; i++) {
                System.out.println(rs.getMetaData().getColumnLabel(i));
                menu.add(rs.getMetaData().getColumnLabel(i));
            }
            res = new Vector();
            row =new Vector();
            Vector[] help = new Vector[lineCount];
            for(int i=0; i<lineCount; i++) {
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

    //вивести результат запиту на фрейм
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
        if(nameInDB.equals("bill")){
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2){
                        Vector v = (Vector) res.get(table.getSelectedRow());
                        String title = "ДЕТАЛІЗАЦІЯ НАКЛАДНОЇ "+v.get(0);
                        String q = "select * from billDet where bill_id = "+v.get(0)+";";
                        ShowResPage billdetpage = new ShowResPage(title, "billDet", user, q);
                        try {
                            billdetpage.show();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        frame.dispose();
                    }




                }
            });

        }
        scrollPane = new JScrollPane(table);
        scrollPane.setSize(frame.getWidth() - 70, 300);
        //if(lineCount<=7)scrollPane.setSize(frame.getWidth()-70,40*lineCount+20);
        //else scrollPane.setSize(frame.getWidth()-70,300);
        scrollPane.setLocation(x,y);
        frame.add(scrollPane);
    }
}
