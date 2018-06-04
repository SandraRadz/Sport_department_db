import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WindowMenu extends Window {
	public WindowMenu(String nameT) {
		super(nameT);
		return;
	}

	@Override
	public void show() throws SQLException {
		super.show();

		// JLabel profileName = new JLabel(" ", SwingConstants.CENTER);
		// profileName.setFont(new Font("", Font.PLAIN, 10));
		// profileName.setSize(100, 10);
		// profileName.setLocation(frame.getWidth()/2-120 , 10);
		// frame.add(profileName);

		JButton profile = new JButton("ПРОФІЛЬ");
		profile.setSize(90, 30);
		profile.setLocation(frame.getWidth() / 2 - 120, 20);
		frame.add(profile);
		profile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// DataBase db = new DataBase();
				// String query ="select * from users where
				// login='"+login.getText()+"';";
				// ResultSet rs;
				// Profile rep = new Profile("ЗВІТИ");
				// try {
				// rep.show();
				// } catch (SQLException e1) {
				// e1.printStackTrace();
				// }
				// frame.dispose();
			}
		});

		JButton readme = new JButton("ЗВІТИ");
		readme.setSize(80, 30);
		readme.setLocation(profile.getX() + 130, 20);
		frame.add(readme);
		readme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Report rep = new Report("ЗВІТИ");
				try {
					rep.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});

		JButton tables = new JButton("ТАБЛИЦІ");
		tables.setSize(80, 30);
		tables.setLocation(readme.getX() + 110, 20);
		frame.add(tables);
		tables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowResPage ta = new ShowResPage("НАКЛАДНІ", "bill");
				try {
					ta.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});

		JButton exit = new JButton("ВИХІД");
		exit.setSize(80, 30);
		exit.setLocation(tables.getX() + 110, 20);
		frame.add(exit);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartForm sf = new StartForm();
				try {
					sf.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();

			}
		});

		JButton help = new JButton("ДОПОМОГА");
		help.setSize(120, 30);
		help.setBackground(Color.red);
		help.setLocation(exit.getX() + 110, 20);
		frame.add(help);
		frame.setVisible(true);
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpPage hp = new HelpPage();
				try {
					hp.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
	}

}
