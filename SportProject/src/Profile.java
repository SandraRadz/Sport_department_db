import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class Profile extends WindowMenu {
	private String fname;
	private String lname;
	private String status;
	private String login;
	private String password;

	Color bl = new Color(22, 145, 217);

	public String getPassword() {
		return password;
	}

	public String getStars() {
		String strs = "*";
		int size = password.length();
		String stars = new String(new char[size]).replace("\0", strs);
		return stars;
	}

	public Profile(User user){
        super("ПРОФІЛЬ", user);
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
		int x = 150 + 150;
		int x2 = 300 + 250;
		int y = 150;
		JLabel fn = new JLabel("ІМ'Я");
		fn.setFont(new Font("", Font.BOLD, 25));
		fn.setSize(width, height);
		fn.setLocation(x, y);
		fn.setForeground(bl);
		frame.add(fn);

		JLabel fnr = new JLabel(fname);
		fnr.setFont(new Font("", Font.ITALIC, 20));
		fnr.setSize(width, height);
		fnr.setLocation(x2, y);
		frame.add(fnr);
		y += height + 10;

		JLabel ln = new JLabel("ПРІЗВИЩЕ");
		ln.setFont(new Font("", Font.BOLD, 25));
		ln.setSize(width, height);
		ln.setLocation(x, y);
		ln.setForeground(bl);
		frame.add(ln);

		JLabel lnr = new JLabel(lname);
		lnr.setFont(new Font("", Font.ITALIC, 20));
		lnr.setSize(width, height);
		lnr.setLocation(x2, y);
		frame.add(lnr);
		y += height + 10;

		JLabel st = new JLabel("СТАТУС");
		st.setFont(new Font("", Font.BOLD, 25));
		st.setSize(width, height);
		st.setLocation(x, y);
		st.setForeground(bl);
		frame.add(st);

		JLabel str = new JLabel(status);
		str.setFont(new Font("", Font.ITALIC, 20));
		str.setSize(width, height);
		str.setLocation(x2, y);
		frame.add(str);
		y += height + 10;

		JLabel lg = new JLabel("ЛОГІН");
		lg.setFont(new Font("", Font.BOLD, 25));
		lg.setSize(width, height);
		lg.setLocation(x, y);
		lg.setForeground(bl);
		frame.add(lg);

		JLabel lgr = new JLabel(login);
		lgr.setFont(new Font("", Font.ITALIC, 20));
		lgr.setSize(width, height);
		lgr.setLocation(x2, y);
		frame.add(lgr);
		y += height + 10;

		JLabel ps = new JLabel("ПАРОЛЬ");
		ps.setFont(new Font("", Font.BOLD, 25));
		ps.setSize(width, height);
		ps.setLocation(x, y);
		ps.setForeground(bl);
		frame.add(ps);

		JLabel psr = new JLabel(getStars());
		psr.setFont(new Font("", Font.ITALIC, 20));
		psr.setSize(width, height);
		psr.setLocation(x2, y);
		frame.add(psr);
		y += height + 10;

		JButton pass = new JButton("ПОКАЗАТИ ПАРОЛЬ");
		pass.setSize(width - 120, height);
		pass.setLocation(x + 400, y - 50);
		pass.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				String change = getPassword();
				psr.setText(change);
			}

			public void mouseReleased(MouseEvent e) {
				String change = getStars();
				psr.setText(change);

			}
		});

		frame.add(pass);

	}
}
