import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleTable extends WindowMenu {
    JLabel name = new JLabel(this.nameOfTable);
    private DataBase dataBase;
    public SimpleTable(String nameT, String nameDB, int num, DataBase dataBase) {
        super(nameT, nameDB, num);
        this.dataBase=dataBase;

    }

    @Override
    public void show() throws SQLException {
        super.show();
        //JLabel name = new JLabel(this.nameOfTable);
        name.setSize(150,30);
        name.setLocation(frame.getWidth()/30,300);
        addDBTable();
        frame.add(name);
    }

    public void addDBTable() throws SQLException {
        try {
            String query = "select * from bank;";
            ResultSet rs =  dataBase.select(query);
            while (rs.next()) {
                System.out.println(rs.getString(1) + "  "+rs.getString(2));
                name.setText(label.getText()+"\n"+rs.getInt(1) + "  "+rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
