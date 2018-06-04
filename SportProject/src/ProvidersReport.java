import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ProvidersReport extends Report {
	private DataBase dataBase;
	private String query;
	private ResultSet rs;
	private JButton OK;
	private JComboBox cb, cb1, cb2;
	private String q, q1, q2, q3;
	private JTextField from, to;

	public ProvidersReport(String nameT) {
		super(nameT);
	}

	public void show() throws SQLException {
		super.show();
		DataBase db = new DataBase();
		this.query = "select * from providers;";
		this.rs = db.select(query);

		JLabel repOf = new JLabel("Звіти по Постачальникам:", SwingConstants.CENTER);
		repOf.setFont(new Font("", Font.ITALIC, 30));
		repOf.setSize(400, 30);
		repOf.setLocation(380, 170);
		frame.add(repOf);

		int y = 250;

		// перший звіт
		JLabel rep1 = new JLabel("Переглянути всіх постачальників, в яких купувався певний товар:");
		rep1.setFont(new Font("", Font.PLAIN, 15));
		rep1.setSize(500, 30);
		rep1.setLocation(frame.getWidth() / 30, y);
		frame.add(rep1);
		y += 20;

		cb = AddItemPage.createComboBox("name_of_goods", "nomenofdel", 200, 30, frame.getWidth() / 2, y - 20);
		frame.add(cb);
		String q = "" + cb.getSelectedItem();
		providersByGood(frame.getWidth() / 30, 300, query, q);
		cb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				try {
					deleteRows();
					String q = "" + cb.getSelectedItem();
					providersByGood(frame.getWidth() / 30, 300, query, q);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		y += 30;

		// другий звіт
		JLabel rep2 = new JLabel(
				"Переглянути всіх постачальників, в яких купувався товар в певний проміжок часу:  з                       по ");
		rep2.setFont(new Font("", Font.PLAIN, 15));
		rep2.setSize(700, 30);
		rep2.setLocation(frame.getWidth() / 30, frame.getHeight() / 3 + scrollPane.getHeight() + 100);
		frame.add(rep2);
		from = new JTextField("");
		from.setSize(50, 30);
		from.setLocation(rep2.getX() + 600, rep2.getY());
		frame.add(from);

		to = new JTextField();
		to.setSize(50, 30);
		to.setLocation(rep2.getX() + 700, rep2.getY());
		frame.add(to);

		OK = new JButton("ok");
		OK.setSize(30, 30);
		OK.setLocation(rep2.getX() + 800, rep2.getY());
		frame.add(OK);

		cb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				rep2.setLocation(frame.getWidth() / 30, frame.getHeight() / 3 + scrollPane.getHeight() + 100);
				from.setLocation(rep2.getX() + 600, rep2.getY());
				to.setLocation(rep2.getX() + 700, rep2.getY());
				OK.setLocation(rep2.getX() + 800, rep2.getY());
			}
		});

		y = rep2.getY();
		// String fromStr = from.getText();
		// String toStr = to.getText();
		// providersByTime(frame.getWidth() / 30, y + 50, query, fromStr,
		// toStr);
		String query1 = "SELECT distinct contracts.provider_id,providers.name_of_provider,date_from,date_to,name_of_goods FROM providers inner join contracts on providers.provider_id=contracts.provider_id WHERE (date_from BETWEEN '"
				+ from + "' AND '" + to + "');";
		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// deleteRows(scrollPane1);
					String fromStr = from.getText();
					String toStr = to.getText();
					System.out.println(fromStr);
					System.out.println(toStr);
					providersByTime(frame.getWidth() / 30, frame.getHeight() / 3 + scrollPane.getHeight() + 200, query1,
							fromStr, toStr);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	// методи запитів і вивід
	public void providersByGood(int x, int y, String query, String helpquery) throws SQLException {
		DataBase db = new DataBase();
		query = "select distinct contracts.provider_id,providers.name_of_provider,date_from,date_to,name_of_goods from providers inner join contracts on providers.provider_id=contracts.provider_id where contracts.name_of_goods='"
				+ helpquery + "';";
		int rowHeight = 50;
		try {
			ResultSet rs = db.select(query);
			int colCount = rs.getMetaData().getColumnCount();
			rs.last();
			lineCount = rs.getRow();
			rs.beforeFirst();

			// відображення списку атрибутів в бд
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
			table = new JTable(res, menu);
			scrollPane = new JScrollPane(table);
			scrollPane.setLocation(x, y);
			table.setRowHeight(rowHeight);
			if (lineCount <= 7)
				scrollPane.setSize(frame.getWidth() - 70, rowHeight * lineCount + 20);
			else
				scrollPane.setSize(frame.getWidth() - 70, 300);

			frame.add(scrollPane);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void providersByTime(int x, int y, String query1, String from, String to) throws SQLException {
		DataBase db = new DataBase();
		query1 = "SELECT distinct contracts.provider_id,providers.name_of_provider,date_from,date_to,name_of_goods FROM providers inner join contracts on providers.provider_id=contracts.provider_id WHERE (date_from BETWEEN '"
				+ from + "' AND '" + to + "');";
		int rowHeight = 50;
		try {
			ResultSet rs = db.select(query1);
			int colCount = rs.getMetaData().getColumnCount();
			rs.last();
			lineCount = rs.getRow();
			rs.beforeFirst();

			// відображення списку атрибутів в бд
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
			table = new JTable(res, menu);
			scrollPane = new JScrollPane(table);
			scrollPane.setLocation(x, y);
			table.setRowHeight(rowHeight);
			if (lineCount <= 7)
				scrollPane.setSize(frame.getWidth() - 70, rowHeight * lineCount + 20);
			else
				scrollPane.setSize(frame.getWidth() - 70, 300);

			frame.add(scrollPane);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteRows() throws SQLException {
		scrollPane.setVisible(false);
	}
}