import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

public class ContractsReport extends Report {
	private DataBase dataBase;
	private JButton OK, OK1;
	private ResultSet rs;
	private JComboBox cb;
	private JScrollPane mainScroll;
	private JPanel mainPanel = new JPanel();
	private JTable table;
	private JScrollPane scrollPane1, scrollPane2;

	public ContractsReport(String nameT, User user) {

		super(nameT, user);
		// TODO Auto-generated constructor stub
	}

	public void show() throws SQLException {
		super.show();
		JLabel repOf = new JLabel("Звіти по Угодам:", SwingConstants.CENTER);
		repOf.setFont(new Font("", Font.ITALIC, 30));
		repOf.setSize(400, 30);
		repOf.setLocation(380, 170);
		frame.add(repOf);

		int y = mainPanel.getY();
		int x = mainPanel.getX();
		int plus = 20;
		int between = 50;

		// перший
		JLabel rep1 = new JLabel("Переглянути угоди від певного постачальника,");
		rep1.setFont(new Font("", Font.PLAIN, 15));
		rep1.setSize(400, 30);
		rep1.setLocation(x + plus, y + plus);

		JLabel rep1h = new JLabel("за певний період :");
		rep1h.setFont(new Font("", Font.PLAIN, 15));
		rep1h.setSize(200, 30);
		rep1h.setLocation(x + plus + 195, rep1.getY() + 30);

		cb = AddItemPage.createComboBox("name_of_provider", "providers", 200, 30, rep1.getX() + 350, rep1.getY());

		JDateChooser calendar_from = new JDateChooser();
		calendar_from.setCalendar(Calendar.getInstance());
		calendar_from.setSize(100,30);
		calendar_from.setLocation(rep1h.getX() + 150, rep1h.getY() + 5);
		calendar_from.setDateFormatString("yyyy-MM-dd");
		
		JLabel rep1p = new JLabel("по");
		rep1p.setFont(new Font("", Font.PLAIN, 16));
		rep1p.setSize(30, 30);
		rep1p.setLocation(calendar_from.getX() + 110, rep1h.getY());

		JDateChooser calendar_to= new JDateChooser();
		calendar_to.setCalendar(Calendar.getInstance());
		calendar_to.setSize(100,30);
		calendar_to.setLocation(calendar_from.getX() + 130, rep1h.getY() + 5);
		calendar_to.setDateFormatString("yyyy-MM-dd");
	
		//SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		
		String fromStr = calendar_from.getDate().toString();
		String toStr = calendar_to.getDate().toString();
		
		String prov = "" + cb.getSelectedItem();

		String query = "Select contracts.contracts_id,providers.provider_id,contracts.date_from,"
				+ "contracts.date_to,name_of_goods from providers inner join contracts on providers.provider_id = "
				+ "contracts.provider_id where name_of_provider = '" + prov + "' and contracts.date_to >= '" + toStr
				+ "' and contracts.date_from <= '" + fromStr + "';";

		scrollPane = new JScrollPane();

		OK = new JButton("ok");
		OK.setSize(50, 30);
		OK.setLocation(calendar_to.getX() + 130, calendar_to.getY());

		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainPanel.remove(scrollPane);

					SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

					String from = format2.format(calendar_from.getDate());
					String to = format2.format(calendar_to.getDate());
					String prov = "" + cb.getSelectedItem();
					
					System.out.println(from);
					System.out.println(to);

					ContractsByProvAndDate(x + plus, rep1.getY() + 60, query, from, to, prov);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		OK1 = new JButton("За весь період");
		OK1.setSize(125, 30);
		OK1.setLocation(rep1h.getX(), rep1h.getY() + 30);

		/*
		 * OK1.addActionListener(new ActionListener() {
		 * 
		 * public void actionPerformed(ActionEvent e) { try {
		 * mainPanel.remove(scrollPane1);
		 * 
		 * BillByProvAndDateAll(x + plus, rep4.getY() + 180 + between, query3,
		 * prov1); } catch (SQLException e1) { e1.printStackTrace(); }
		 * 
		 * });}
		 */
		mainPanel.setLayout(null);
		mainPanel.setVisible(true);
		mainPanel.setPreferredSize(new Dimension(950, 400));

		mainPanel.add(cb);
		mainPanel.add(rep1);
		mainPanel.add(rep1h);

		mainPanel.add(calendar_from);
		mainPanel.add(calendar_to);
		mainPanel.add(OK);

		mainScroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainScroll.setLocation(frame.getX() + 10, frame.getHeight() / 3 + 5);
		mainScroll.setSize(frame.getWidth() - 20, 450);

		frame.add(mainScroll);
	}

	public void ContractsByProvAndDate(int x, int y, String query, String from, String to, String prov)
			throws SQLException {
		DataBase db = new DataBase();
		query = "Select contracts.contracts_id,providers.provider_id,contracts.date_from,"
				+ "contracts.date_to,name_of_goods from providers inner join contracts on providers.provider_id = "
				+ "contracts.provider_id where name_of_provider = '" + prov + "' and contracts.date_from <= '" + from
				+ "' and contracts.date_to >= '" + to + "';";
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
