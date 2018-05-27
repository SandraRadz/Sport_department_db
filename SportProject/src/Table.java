import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Table {
    protected String nameOfTable;
    protected String nameInDB;
    protected int numOfColumn;
    protected JFrame frame;

    public static JLabel label = new JLabel(" ");

    public Table (String nameT, String nameDB, int num){
        this.nameOfTable=nameT;
        this.nameInDB=nameDB;
        this.numOfColumn=num;
        this.frame=new JFrame(nameT);
        return;
    }

    public Table (){
        this.nameOfTable="test";
        this.nameInDB="bank";
        this.numOfColumn=1;
        this.frame=new JFrame(this.nameOfTable);
        return;
    }

    public String getNameOfTable(){
        return this.nameOfTable;
    }

    public String getNameInDB(){
        return this.nameInDB;
    }

    public int getNumOfColumn(){
        return this.numOfColumn;
    }

    public void setNameOfTable(String name){
        this.nameOfTable=name;
    }

    public void setNameInDB(String nameDB){
        this.nameInDB=nameDB;
    }

    public void setNumOfColumn(int num){
        this.numOfColumn=num;
    }


    public void createWindow() throws SQLException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DataBase db = new DataBase();
        String query = "select * from bank;";
        ResultSet rs =db.select(query);
        while (rs.next()) {
            System.out.println(rs.getString(1) + "  "+rs.getString(2));
            label.setText(label.getText()+"\n"+rs.getInt(1) + "  "+rs.getString(2));
        }
        db.close();
        frame.getContentPane().add(label);
        frame.setPreferredSize(new Dimension(700, 500));
        frame.pack();
        frame.setVisible(true);
    }
}


