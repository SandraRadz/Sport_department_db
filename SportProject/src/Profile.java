import javax.swing.*;
import java.sql.SQLException;

public class Profile extends WindowMenu   {
    private String fname;
    private String lname;
    private String status;
    private String login;
    private String password;

    public Profile(User user){
        super("ПРОФІЛЬ");
        this.fname=user.getFname();
        this.lname=user.getLname();
        this.status=user.getStatus();
        this.login=user.getLogin();
        this.password=user.getPassword();
        return;
    }

    @Override
    public void show() throws SQLException {
        super.show();
        int height = 40;
        int width = 300;
        int x = 150;
        int x2=300;
        int y = 150;
        JLabel fn = new JLabel("ІМ'Я");
        fn.setSize(width, height);
        fn.setLocation(x, y);
        frame.add(fn);

        JLabel fnr = new JLabel(fname);
        fnr.setSize(width, height);
        fnr.setLocation(x2, y);
        frame.add(fnr);
        y+=height+10;

        JLabel ln = new JLabel("ПРІЗВИЩЕ");
        ln.setSize(width, height);
        ln.setLocation(x, y);
        frame.add(ln);

        JLabel lnr = new JLabel(lname);
        lnr.setSize(width, height);
        lnr.setLocation(x2, y);
        frame.add(lnr);
        y+=height+10;

        JLabel st = new JLabel("СТАТУС");
        st.setSize(width, height);
        st.setLocation(x, y);
        frame.add(st);

        JLabel str = new JLabel(status);
        str.setSize(width, height);
        str.setLocation(x2, y);
        frame.add(str);
        y+=height+10;

        JLabel lg = new JLabel("ЛОГІН");
        lg.setSize(width, height);
        lg.setLocation(x, y);
        frame.add(lg);

        JLabel lgr = new JLabel(login);
        lgr.setSize(width, height);
        lgr.setLocation(x2, y);
        frame.add(lgr);
        y+=height+10;

        JLabel ps = new JLabel("ПАРОЛЬ");
        ps.setSize(width, height);
        ps.setLocation(x, y);
        frame.add(ps);

        JLabel psr = new JLabel(password);
        psr.setSize(width, height);
        psr.setLocation(x2, y);
        frame.add(psr);
        y+=height+10;
    }
}
