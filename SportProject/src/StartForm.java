import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StartForm extends Window {
    public StartForm(){
        super("ВВЕДІТЬ ДАНІ, ЩОБ УВІЙТИ ДО СИСТЕМИ");
        return;
    }

    @Override
    public void show() throws SQLException {
        super.show();
        int height = 40;
        int width = 300;
        int x = frame.getWidth()/2-width/2;
        int y = 100;
        JLabel log = new JLabel("ЛОГІН");
        log.setSize(width, height);
        log.setLocation(x, y);
        frame.add(log);
        y+=height+10;

        JTextField login = new JTextField();
        login.setSize(width, height);
        login.setLocation(x, y);
        frame.add(login);
        y+=height+10;

        JLabel pass = new JLabel("ПАРОЛЬ");
        pass.setSize(width, height);
        pass.setLocation(x, y);
        frame.add(pass);
        y+=height+10;

        JPasswordField password = new JPasswordField();
        password.setSize(width, height);
        password.setLocation(x, y);
        frame.add(password);
        y+=height+10;

        JButton go = new JButton("УВІЙТИ");
        go.setSize(width, height);
        go.setLocation(x, y);
        frame.add(go);
        y+=height+10;

        JLabel error = new JLabel("");
        error.setSize(width+100, height);
        error.setLocation(x, y);
        frame.add(error);

        go.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                error.setText("");
                DataBase db = new DataBase();
				String query = "select * from users where login='" + login.getText()+"';";
                ResultSet rs;
                try {
                    System.out.println(query);
                    rs = db.select(query);
                    if(!rs.next()){
                        error.setText("Користувача з таким логіном не існує, спробуйте ще раз");
                        return;
                    }
                    rs.first();
                    System.out.println(rs.getString(4));
                    if(rs.getString(5).equals(password.getText())){
                        User user = new User (rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                        Profile pr = new Profile(user);
                        //ShowResPage ta = new ShowResPage("НАКЛАДНІ", "bill");
                        pr.show();
                        frame.dispose();
                    }
                    else{
                        error.setText("Пароль введено не правильно, спробуйте ще раз");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                    try {
                        db.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

            }
        });

    }

}
