import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Table {
    private String nameOfTable;
    private String nameInDB;
    private int numOfColumn;
    private JFrame frame;
    //private String query;

    public static JLabel label = new JLabel(" ");

    public Table (String nameT, String nameDB, int num){
        this.nameOfTable=nameT;
        this.nameInDB=nameDB;
        this.numOfColumn=num;
        this.frame=new JFrame(nameT);
        //this.query="select * from "+ this.nameInDB +";";
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

    //public String getQuery(){
    //    return this.query;
    //}

    public void setNameOfTable(String name){
        this.nameOfTable=name;
    }

    public void setNameInDB(String nameDB){
        this.nameInDB=nameDB;
    }

    public void setNumOfColumn(int num){
        this.numOfColumn=num;
    }

    //public void setQuery(String newquery){
    //  this.query=newquery;
    //}


    public void createWindow() throws SQLException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DataBase db = new DataBase();
        String query = "select * from bank;";
        ResultSet rs =db.select(query);
        while (rs.next()) {
            //JLabel label = new JLabel(" ");
            // int count = rs.getString(1);
            System.out.println(rs.getString(1) + "  "+rs.getString(2));
            label.setText(label.getText()+"\n"+rs.getInt(1) + "  "+rs.getString(2));
            //frame.getContentPane().add(label);
        }
        db.close();
        frame.getContentPane().add(label);
        frame.setPreferredSize(new Dimension(700, 500));
        frame.pack();
        frame.setVisible(true);
    }
}


