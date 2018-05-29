import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowResPage extends WindowMenu {
    Color or = new Color(246, 184, 61);
    Color gr = new Color(12, 184, 61);

    private DataBase dataBase;
    private String query;
    protected String nameInDB;
    JTable table;

    public ShowResPage(String nameT, String nameDB, DataBase dataBase) {
        super(nameT);
        this.nameInDB=nameDB;
        this.dataBase=dataBase;
        this.query="select * from "+this.nameInDB +";";
    }

    //сторінка з таблицями
    @Override
    public void show() throws SQLException {
        super.show();
        addButtons();


        addDBTable(30, 300, query);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);
    }

    public void addButtons(){
        JButton addItem = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'> +"+super.nameOfTable.toUpperCase()+"</div></html>");
        addItem.setSize(190, 30);
        addItem.setLocation( 500, 100);
        addItem.setBackground(or);
        addItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddItemPage add = new AddItemPage(nameOfTable, nameInDB, dataBase);
                try {
                    add.show();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }

        });
        frame.add(addItem);


    }
//вивести результат запиту на фрейм
    public void addDBTable(int x, int y, String query) throws SQLException {
        int rowHeight=40;
        try {
            ResultSet rs =  dataBase.select(query);
            int colCount = rs.getMetaData().getColumnCount();
            rs.last();
            int lineCount = rs.getRow();
            rs.beforeFirst();
            String[] menu = new  String[colCount];
            for (int i=0; i<menu.length; i++)
                menu[i]=rs.getMetaData().getColumnLabel(i+1);
            String[][] res = new String[lineCount][colCount];

            for(int i=0; i<lineCount; i++) {
                rs.next();
                for (int j = 0; j < colCount; j++){
                    System.out.print(rs.getString(j+1)+"   ");
                    res[i][j] = rs.getString(j+1);
                }
                System.out.println("");
            }
            table = new JTable(res, menu);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setLocation(x,y);
            table.setRowHeight(rowHeight);
            if(lineCount<=7)scrollPane.setSize(frame.getWidth()-70,rowHeight*lineCount+20);
            else scrollPane.setSize(frame.getWidth()-70,300);
            frame.add(scrollPane);

            JButton deleteItem = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'> ВИДАЛИТИ </div></html>");
            deleteItem.setSize(190, 30);
            deleteItem.setLocation( 700, 100);
            deleteItem.setBackground(or);
            deleteItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int itemnum = table.getSelectedRow();
                    System.out.println(res[itemnum][0]);
                    // table.removeRowSelectionInterval(itemnum, itemnum+1 );
                    String q = "DELETE FROM " + nameInDB + " WHERE "+menu[0]+ " = " + res[itemnum][0]+ ";";
                    try {
                        dataBase.delete(q);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    ShowResPage newP = new ShowResPage(nameOfTable, nameInDB, dataBase);
                    try {
                        newP.show();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    frame.dispose();
                }
            });
            frame.add(deleteItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
