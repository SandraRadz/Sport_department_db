import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GoodsReport extends Report {
	
	private DataBase dataBase;
	private String query;
	private ResultSet rs;
	private JComboBox cb;
	private JScrollPane mainScroll;
	private JPanel mainPanel = new JPanel();
	private JTable table;
	private JScrollPane scrollPane;
	
	
	public GoodsReport(String nameT, User user) {
		super(nameT, user);
	}

	public void show() throws SQLException {
		super.show();
		JLabel repOf = new JLabel("Звіти по Товарам:", SwingConstants.CENTER);
		repOf.setFont(new Font("", Font.ITALIC, 30));
		repOf.setSize(400, 30);
		repOf.setLocation(380, 170);
		frame.add(repOf);
		
		
		int y = mainPanel.getY();
		int x = mainPanel.getX();
		int plus = 20;
		int between = 50;
	
		mainPanel.setLayout(null);
		mainPanel.setVisible(true);
		mainPanel.setPreferredSize(new Dimension(950, 400));
		
		mainScroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainScroll.setLocation(frame.getX() + 10, frame.getHeight() / 3 + 5);
		mainScroll.setSize(frame.getWidth() - 20, 450);

		frame.add(mainScroll);
	}
	
	public void GoodsByAccAcc(int x, int y, String query, String helpquery) throws SQLException {
		DataBase db = new DataBase();
		query = "select distinct * FROM NomenOfDel where accountancy_account = " + helpquery + ";";
		paintTheTable(query, x, y, db, 1);
	}
	
	
	private void paintTheTable(String query, int x, int y, DataBase db, int check) {

		int rowHeight = 50;
		try {
			ResultSet rs = db.select(query);
			int colCount = rs.getMetaData().getColumnCount();
			rs.last();
			int lineCount = rs.getRow();
			rs.beforeFirst();

			Vector menu = new Vector();
			for (int i = 1; i <= colCount; i++) {
				System.out.println(rs.getMetaData().getColumnLabel(i));
				menu.add(rs.getMetaData().getColumnLabel(i));
			}
			// відображення інфи з бд
			Vector res = new Vector();
			Vector row = new Vector();
			Vector[] help = new Vector[lineCount];
			for (int i = 0; i < lineCount; i++) {
				rs.next();
				for (int j = 0; j < colCount; j++) {
					row.add(rs.getString(j + 1));
				}
				help[i] = (Vector) row.clone();
				row.clear();
				res.add(help[i]);
			}
			switch (check) {
			case 1:
				table = new JTable(res, menu);
				scrollPane = new JScrollPane(table);
				scrollPane.setLocation(x, y + 10);
				table.setRowHeight(rowHeight);
				if (lineCount <= 7)
					scrollPane.setSize(mainPanel.getWidth() - 30, 300);
				else
					scrollPane.setSize(mainPanel.getWidth() - 30, 300);

				mainPanel.add(scrollPane);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}