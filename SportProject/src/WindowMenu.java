import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


public class WindowMenu extends Window {

    private User user;

    public WindowMenu(String nameT, User user){
        super(nameT);
        this.user=user;
        return;
    }

	@Override
	public void show() throws SQLException {
		super.show();


        JButton profile = new JButton("ПРОФІЛЬ");
        profile.setSize(90, 30);
        profile.setLocation(frame.getWidth()/2-120 , 20);
        frame.add(profile);
        profile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Profile pr = new Profile(user);
                try {
                    pr.show();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });


		JButton readme = new JButton("ЗВІТИ");
		readme.setSize(80, 30);
		readme.setLocation(profile.getX() + 110, 20);
		frame.add(readme);
		readme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BillsReport rep = new BillsReport("ЗВІТИ", user);
				try {
					rep.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});

        JButton tables = new JButton("ТАБЛИЦІ");
        tables.setSize(100, 30);
        tables.setLocation(readme.getX() + 100, 20);
        frame.add(tables);
        tables.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowResPage ta = new ShowResPage("НАКЛАДНІ", "bill", user);
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
        exit.setLocation(tables.getX()+ 110, 20);
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
        help.setLocation(exit.getX()+ 110, 20);
        help.setForeground(Color.WHITE);
        frame.add(help);
        frame.setVisible(true);
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HelpPage hp = new HelpPage(user);
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
