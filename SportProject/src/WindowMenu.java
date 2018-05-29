import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WindowMenu extends Window {

    public WindowMenu(String nameT){
        super(nameT);
            return;
    }

    @Override
    public void show() throws SQLException {
        super.show();

        JLabel name = new JLabel(this.nameOfTable);
        name.setSize(150,30);
        name.setLocation(frame.getWidth()/30,20);
        frame.add(name);


        JButton profile = new JButton("ПРОФІЛЬ");
        profile.setSize(90, 30);
        profile.setLocation(frame.getWidth()/2-120 , 20);
        frame.add(profile);


        JButton readme = new JButton("ЗВІТИ");
        readme.setSize(80, 30);
        readme.setLocation(profile.getX() + 130, 20);
        frame.add(readme);

        JButton tables = new JButton("ТАБЛИЦІ");
        tables.setSize(80, 30);
        tables.setLocation(readme.getX() + 110, 20);
        frame.add(tables);


        JButton exit = new JButton("ВИХІД");
        exit.setSize(80, 30);
        exit.setLocation(tables.getX()+ 110, 20);
        frame.add(exit);


        JButton help = new JButton("ДОПОМОГА");
        help.setSize(120, 30);
        help.setLocation(exit.getX()+ 110, 20);
        frame.add(help);
        frame.setVisible(true);
    }

}
