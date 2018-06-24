import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

public class BillsReport extends Report {

	private DataBase dataBase;
	private JButton OK, OK1, all, OK2, OK3;
	private JButton report1, report2, report3, report4, report5;
	private ResultSet rs;
	private JComboBox cb, cb1, cb2, cb3;
	private JTextField more, less;
	private JScrollPane mainScroll;
	private JPanel mainPanel = new JPanel();
	private JTable table;
	private JTable table1, table2, table3, table4, table5;
	private JScrollPane scrollPane1, scrollPane2, scrollPane3, scrollPane4, scrollPane5;
	private boolean flag;

	public BillsReport(String nameT, User user) {
		super(nameT, user);

	}

	public void show() throws SQLException {
		super.show();
		JLabel repOf = new JLabel("Звіти по Накладним:", SwingConstants.CENTER);
		repOf.setFont(new Font("", Font.ITALIC, 30));
		repOf.setSize(400, 30);
		repOf.setLocation(380, 170);
		frame.add(repOf);

		int y = mainPanel.getY();
		int x = mainPanel.getX();
		int plus = 20;
		int between = 50;

		// перший звіт
		JLabel rep1 = new JLabel("Переглянути номери всіх накладних, в яких купувався певний товар :");
		rep1.setFont(new Font("", Font.PLAIN, 15));
		rep1.setSize(500, 30);
		rep1.setLocation(x + plus, y + plus);

		cb = AddItemPage.createComboBox("name_of_goods", "nomenofdel", 200, 30, rep1.getWidth(), rep1.getY());
		String q = "" + cb.getSelectedItem();
		scrollPane1 = new JScrollPane();
		String query = "select distinct bill.bill_id,bill.date_of_bill,providers.name_of_provider,"
				+ "billdet.name_of_goods FROM providers inner join (bill inner join billdet on bill.bill_id = "
				+ "billdet.bill_id) on providers.provider_id = bill.provider_id where billdet.name_of_goods = " + q
				+ ";";
		cb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				try {
					mainPanel.remove(scrollPane1);

					String q = "" + cb.getSelectedItem();

					BillByGoods(rep1.getX(), rep1.getHeight() + 20, query, q);
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		});

		report1 = new JButton("ЕКСПОРТ В Excel");
		report1.setSize(150, 30);
		report1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String q = "" + cb.getSelectedItem();
				String q1 = "Накл " + q;
				try {
					Export exp1 = new Export(table1, "Накладні " + q, q1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// другий звіт
		JLabel rep2 = new JLabel("Переглянути всі накладні за певний період:");
		rep2.setFont(new Font("", Font.PLAIN, 15));
		rep2.setSize(300, 30);
		rep2.setLocation(x + plus, rep1.getY() + 350 + between);

		JDateChooser calendar_from = new JDateChooser();
		calendar_from.setCalendar(Calendar.getInstance());
		calendar_from.setSize(100, 30);
		calendar_from.setLocation(rep2.getWidth() + 50, rep2.getY());
		calendar_from.setDateFormatString("yyyy-MM-dd");

		JDateChooser calendar_to = new JDateChooser();
		calendar_to.setCalendar(Calendar.getInstance());
		calendar_to.setSize(100, 30);
		calendar_to.setLocation(calendar_from.getX() + 130, rep2.getY());
		calendar_to.setDateFormatString("yyyy-MM-dd");

		String fromStr = calendar_from.getDate().toString();
		String toStr = calendar_to.getDate().toString();

		OK = new JButton("ok");
		OK.setSize(50, 30);
		OK.setLocation(calendar_to.getX() + 150, rep2.getY());
		String query1 = "SELECT * FROM bill WHERE date_of_bill >= '" + fromStr + "' and date_of_bill <= '" + toStr
				+ "';";
		scrollPane2 = new JScrollPane();
		OK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainPanel.remove(scrollPane2);

					SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

					String fromStr1 = format2.format(calendar_from.getDate());
					String toStr1 = format2.format(calendar_to.getDate());

					BillByDate(x + plus, rep2.getY() + 30, query1, fromStr1, toStr1);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		report2 = new JButton("ЕКСПОРТ В Excel");
		report2.setSize(150, 30);
		report2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
				String fromStr1 = format2.format(calendar_from.getDate());
				String toStr1 = format2.format(calendar_to.getDate());
				String q1 = "Накл За Датою";
				try {
					Export exp1 = new Export(table2, "Накладні за період", q1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// третій звіт
		JLabel rep3 = new JLabel("Переглянути накладні від певного постачальника,");
		rep3.setFont(new Font("", Font.PLAIN, 15));
		rep3.setSize(350, 30);
		rep3.setLocation(x + plus, rep2.getY() + 350 + between);

		JLabel rep3h = new JLabel("в яких купується певний товар :");
		rep3h.setFont(new Font("", Font.PLAIN, 15));
		rep3h.setSize(300, 30);
		rep3h.setLocation(x + plus + 120, rep3.getY() + 30);

		cb1 = AddItemPage.createComboBox("name_of_provider", "providers", 200, 30, rep3.getWidth() + 50,
				rep3.getY() - 5);
		cb2 = AddItemPage.createComboBox("name_of_goods", "nomenofdel", 200, 30, rep3h.getWidth() + 100,
				rep3h.getY() + 5);

		String prov = "" + cb1.getSelectedItem();
		String good = "" + cb2.getSelectedItem();

		String query2 = "select distinct bill.bill_id,bill.date_of_bill,bill.sum_of_bill,bill.number_from_provider,account_id,providers.name_of_provider,billdet.name_of_goods FROM providers inner join (bill inner join billdet on bill.bill_id = billdet.bill_id) on providers.provider_id = bill.provider_id "
				+ "where name_of_provider = '" + prov + "' AND billdet.name_of_goods = '" + good + "';";
		scrollPane3 = new JScrollPane();
		cb2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				try {
					mainPanel.remove(scrollPane3);

					String prov = "" + cb1.getSelectedItem();
					String good = "" + cb2.getSelectedItem();

					BillByProvAndGood(rep3.getX(), rep3h.getY() + 40, query2, prov, good);
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		});

		report3 = new JButton("ЕКСПОРТ В Excel");
		report3.setSize(150, 30);
		report3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String prov = "" + cb1.getSelectedItem();
				String good = "" + cb2.getSelectedItem();
				String q1 = "Накл За " + good + " Від " + prov;
				try {
					Export exp1 = new Export(table3, "Накладні з " + good + " від " + prov, q1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// четвертий звіт
		JLabel rep4 = new JLabel("Переглянути накладні від певного постачальника,");
		rep4.setFont(new Font("", Font.PLAIN, 15));
		rep4.setSize(400, 30);
		rep4.setLocation(x + plus, rep3h.getY() + 350 + between);

		JLabel rep4h = new JLabel("за певний період :");
		rep4h.setFont(new Font("", Font.PLAIN, 15));
		rep4h.setSize(200, 30);
		rep4h.setLocation(x + plus + 220, rep4.getY() + 30);

		cb3 = AddItemPage.createComboBox("name_of_provider", "providers", 200, 30, rep4.getX() + 350, rep4.getY());

		JDateChooser calendar_from1 = new JDateChooser();
		calendar_from1.setCalendar(Calendar.getInstance());
		calendar_from1.setSize(100, 30);
		calendar_from1.setLocation(rep4h.getX() + 130, rep4h.getY() + 5);
		calendar_from1.setDateFormatString("yyyy-MM-dd");

		JDateChooser calendar_to1 = new JDateChooser();
		calendar_to1.setCalendar(Calendar.getInstance());
		calendar_to1.setSize(100, 30);
		calendar_to1.setLocation(calendar_from1.getX() + 130, calendar_from1.getY());
		calendar_to1.setDateFormatString("yyyy-MM-dd");

		JLabel rep4p = new JLabel("по");
		rep4p.setFont(new Font("", Font.PLAIN, 16));
		rep4p.setSize(30, 30);
		rep4p.setLocation(calendar_from1.getX() + 110, rep4h.getY());

		String fromStr2 = calendar_from1.getDate().toString();
		String toStr2 = calendar_to1.getDate().toString();
		String prov1 = "" + cb3.getSelectedItem();

		String query3 = "Select bill.bill_id,bill.number_from_provider,bill.sum_of_bill,providers.name_of_provider, "
				+ "bill.date_of_bill from providers inner join bill on providers.provider_id=bill.provider_id where"
				+ " date_of_bill >= '" + fromStr2 + "' and date_of_bill <= '" + toStr2 + "' "
				+ "and name_of_provider = '" + prov1 + "';";

		scrollPane4 = new JScrollPane();

		OK1 = new JButton("ok");
		OK1.setSize(50, 30);
		OK1.setLocation(calendar_to1.getX() + 150, calendar_to1.getY());

		OK1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainPanel.remove(scrollPane4);

					SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");

					String from_sec = format2.format(calendar_from1.getDate());
					String to_sec = format2.format(calendar_to1.getDate());
					String prov_sec = "" + cb3.getSelectedItem();

					BillByProvAndDate(x + plus, rep4.getY() + 100, query3, from_sec, to_sec, prov_sec);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		all = new JButton("За весь період");
		all.setSize(125, 30);
		all.setLocation(rep4h.getX(), rep4h.getY() + 30);

		all.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainPanel.remove(scrollPane4);

					String prov1 = "" + cb3.getSelectedItem();

					BillByProvAndDateAll(x + plus, rep4.getY() + 100, query3, prov1);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		report4 = new JButton("ЕКСПОРТ В Excel");
		report4.setSize(150, 30);
		report4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String prov = "" + cb3.getSelectedItem();
				String q1 = "Накл Від " + prov;
				try {
					Export exp1 = new Export(table4, "Накладні від " + prov, q1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// п'ятий звіт
		JLabel rep5 = new JLabel("Переглянути накладні загальна сума яких перевищує : ");
		rep5.setFont(new Font("", Font.PLAIN, 15));
		rep5.setSize(400, 30);
		rep5.setLocation(x + plus, rep4p.getY() + 400 + between);

		JLabel rep5h = new JLabel("не перевищує :");
		rep5h.setFont(new Font("", Font.PLAIN, 15));
		rep5h.setSize(200, 30);
		rep5h.setLocation(x + plus + 270, rep5.getY() + 30);

		more = new JTextField();
		more.setSize(100, 30);
		more.setLocation(rep5.getX() + 400 - 10, rep5.getY() - 5);

		less = new JTextField();
		less.setSize(100, 30);
		less.setLocation(more.getX(), rep5h.getY() + 5);

		OK2 = new JButton("ok");
		OK2.setSize(50, 30);
		OK2.setLocation(more.getX() + 120, more.getY());

		OK3 = new JButton("ok");
		OK3.setSize(50, 30);
		OK3.setLocation(less.getX() + 120, less.getY());

		scrollPane5 = new JScrollPane();

		String moreStr = more.getText();
		String lessStr = less.getText();

		String query4 = "Select * from bill where sum_of_bill >= '" + moreStr + "';";

		OK2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainPanel.remove(scrollPane5);

					String moreStr = more.getText();

					BillByMoreSum(x + plus, rep5h.getY() + 30, query4, moreStr);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		String query5 = "Select * from bill where sum_of_bill <= '" + lessStr + "';";

		OK3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mainPanel.remove(scrollPane5);

					String lessStr = less.getText();

					BillByLessSum(x + plus, rep5h.getY() + 30, query5, lessStr);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		report5 = new JButton("ЕКСПОРТ В Excel");
		report5.setSize(150, 30);
		report5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String q;
				if(flag==true)
				{
					q = more.getText();
				}else 
				{
					q = less.getText();
				}
				String q1 = "Накл За Сум " + q;
				try {
					Export exp1 = new Export(table5, "Накладні за певною сумою " , q1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		mainPanel.setLayout(null);
		mainPanel.setVisible(true);
		mainPanel.setPreferredSize(new Dimension(950, 2150));

		mainPanel.add(cb);
		mainPanel.add(rep1);

		mainPanel.add(rep2);
		mainPanel.add(calendar_from);
		mainPanel.add(calendar_to);
		mainPanel.add(OK);

		mainPanel.add(rep3);
		mainPanel.add(rep3h);
		mainPanel.add(cb1);
		mainPanel.add(cb2);

		mainPanel.add(rep4);
		mainPanel.add(rep4h);
		mainPanel.add(cb3);
		mainPanel.add(calendar_from1);
		mainPanel.add(calendar_to1);
		mainPanel.add(OK1);
		mainPanel.add(all);
		mainPanel.add(rep4p);

		mainPanel.add(rep5);
		mainPanel.add(rep5h);
		mainPanel.add(more);
		mainPanel.add(less);
		mainPanel.add(OK2);
		mainPanel.add(OK3);

		mainPanel.add(report1);
		mainPanel.add(report2);
		mainPanel.add(report3);
		mainPanel.add(report4);
		mainPanel.add(report5);

		mainScroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainScroll.setLocation(frame.getX() + 10, frame.getHeight() / 3 + 5);
		mainScroll.setSize(frame.getWidth() - 20, 450);

		report1.setLocation(mainScroll.getWidth() - 200, rep2.getY() - 40);
		report2.setLocation(mainScroll.getWidth() - 200, rep3.getY() - 40);
		report3.setLocation(mainScroll.getWidth() - 200, rep4.getY() - 40);
		report4.setLocation(mainScroll.getWidth() - 200, rep5.getY() - 40);
		report5.setLocation(mainScroll.getWidth() - 200, rep5.getY() + 380);

		frame.add(mainScroll);
	}

	public void BillByGoods(int x, int y, String query, String helpquery) throws SQLException {
		DataBase db = new DataBase();
		query = "select distinct bill.bill_id,bill.date_of_bill,providers.name_of_provider,billdet.name_of_goods FROM providers inner join (bill inner join billdet on bill.bill_id = billdet.bill_id) on providers.provider_id = bill.provider_id where billdet.name_of_goods = '"
				+ helpquery + "';";
		paintTheTable(query, x, y, db, 1);
	}

	public void BillByDate(int x, int y, String query, String from, String to) throws SQLException {
		DataBase db = new DataBase();
		query = "SELECT * FROM bill WHERE date_of_bill >= '" + from + "' and date_of_bill <= '" + to + "';";
		paintTheTable(query, x, y, db, 2);
	}

	public void BillByProvAndGood(int x, int y, String query, String prov, String good) throws SQLException {
		DataBase db = new DataBase();
		query = "select distinct bill.bill_id,bill.date_of_bill,bill.sum_of_bill,bill.number_from_provider,account_id,providers.name_of_provider,billdet.name_of_goods FROM providers inner join (bill inner join billdet on bill.bill_id = billdet.bill_id) on providers.provider_id = bill.provider_id "
				+ "where name_of_provider = '" + prov + "' AND billdet.name_of_goods = '" + good + "';";
		paintTheTable(query, x, y, db, 3);
	}

	public void BillByProvAndDate(int x, int y, String query, String from, String to, String prov) throws SQLException {
		DataBase db = new DataBase();
		query = "Select bill.bill_id,bill.number_from_provider,bill.sum_of_bill,providers.name_of_provider, "
				+ "bill.date_of_bill from providers inner join bill on providers.provider_id=bill.provider_id where"
				+ " date_of_bill >= '" + from + "' and date_of_bill <= '" + to + "' " + "and name_of_provider = '"
				+ prov + "';";
		paintTheTable(query, x, y, db, 4);
	}

	public void BillByProvAndDateAll(int x, int y, String query, String prov) throws SQLException {
		DataBase db = new DataBase();
		query = "Select bill.bill_id,bill.number_from_provider,bill.sum_of_bill,providers.name_of_provider, "
				+ "bill.date_of_bill from providers inner join bill on providers.provider_id=bill.provider_id where"
				+ " name_of_provider = '" + prov + "';";
		paintTheTable(query, x, y, db, 4);
	}

	public void BillByMoreSum(int x, int y, String query, String more) throws SQLException {
		DataBase db = new DataBase();
		query = "Select * from bill where sum_of_bill >= '" + more + "';";
		paintTheTable(query, x, y, db, 5);
		flag = true;
	}

	public void BillByLessSum(int x, int y, String query, String less) throws SQLException {
		DataBase db = new DataBase();
		query = "Select * from bill where sum_of_bill <= '" + less + "';";
		paintTheTable(query, x, y, db, 5);
		flag = false;
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
				table1 = table;
				table1.setEnabled(false);
				scrollPane1 = new JScrollPane(table);
				scrollPane1.setLocation(x, y + 10);
				table.setRowHeight(rowHeight);
				if (lineCount <= 7)
					scrollPane1.setSize(mainPanel.getWidth() - 30, 300);
				else
					scrollPane1.setSize(mainPanel.getWidth() - 30, 300);

				mainPanel.add(scrollPane1);
				break;
			case 2:
				table = new JTable(res, menu);
				table2 = table;
				table2.setEnabled(false);
				scrollPane2 = new JScrollPane(table);
				scrollPane2.setLocation(x, y + 10);
				table.setRowHeight(rowHeight);
				if (lineCount <= 7)
					scrollPane2.setSize(mainPanel.getWidth() - 30, 300);
				else
					scrollPane2.setSize(mainPanel.getWidth() - 30, 300);

				mainPanel.add(scrollPane2);
				break;
			case 3:
				table = new JTable(res, menu);
				table3 = table;
				table3.setEnabled(false);
				scrollPane3 = new JScrollPane(table);
				scrollPane3.setLocation(x, y + 10);
				table.setRowHeight(rowHeight);
				if (lineCount <= 7)
					scrollPane3.setSize(mainPanel.getWidth() - 30, 300);
				else
					scrollPane3.setSize(mainPanel.getWidth() - 30, 300);

				mainPanel.add(scrollPane3);
				break;
			case 4:
				table = new JTable(res, menu);
				table4 = table;
				table4.setEnabled(false);
				scrollPane4 = new JScrollPane(table);
				scrollPane4.setLocation(x, y + 10);
				table.setRowHeight(rowHeight);
				if (lineCount <= 7)
					scrollPane4.setSize(mainPanel.getWidth() - 30, 300);
				else
					scrollPane4.setSize(mainPanel.getWidth() - 30, 300);

				mainPanel.add(scrollPane4);
				break;
			case 5:
				table = new JTable(res, menu);
				table5 = table;
				table5.setEnabled(false);
				scrollPane5 = new JScrollPane(table);
				scrollPane5.setLocation(x, y + 10);
				table.setRowHeight(rowHeight);
				if (lineCount <= 7)
					scrollPane5.setSize(mainPanel.getWidth() - 30, 300);
				else
					scrollPane5.setSize(mainPanel.getWidth() - 30, 300);

				mainPanel.add(scrollPane5);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
