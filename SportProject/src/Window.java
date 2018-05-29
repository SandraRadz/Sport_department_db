import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Window {
    protected String nameOfTable;
    protected String nameInDB;
    protected int numOfColumn;
    protected JFrame frame;

    public static JLabel label = new JLabel(" ");

    public Window(String nameT, String nameDB, int num){
        this.nameOfTable=nameT;
        this.nameInDB=nameDB;
        this.numOfColumn=num;
        this.frame=new JFrame(nameT);
        return;
    }

    public Window(){
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


    public void show() throws SQLException {

        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(1000,800);
        frame.setLayout(null);
        frame.setVisible(true);

    }


}


