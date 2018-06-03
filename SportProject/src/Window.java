import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Window {
    protected String nameOfTable;
    protected JFrame frame;


    public Window(String nameT){
        this.nameOfTable=nameT;
        this.frame=new JFrame(nameT);
        return;
    }

    public Window(){
        this.nameOfTable="test";
        this.frame=new JFrame(this.nameOfTable);
        return;
    }

    public String getNameOfTable(){
        return this.nameOfTable;
    }

    public void setNameOfTable(String name){
        this.nameOfTable=name;
    }


    public void show() throws SQLException {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(1000,800);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}


