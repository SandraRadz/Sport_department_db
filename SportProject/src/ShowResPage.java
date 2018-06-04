import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ShowResPage extends WindowMenu {
	Color or = new Color(246, 184, 61);
	Color gr = new Color(12, 184, 61);

	// private DataBase dataBase;
	private String query;
	protected String nameInDB;
	JTable table;
	JScrollPane scrollPane;
	int lineCount;
	Vector r, v;

	public ShowResPage(String nameT, String nameDB) {
		super(nameT);
		this.nameInDB = nameDB;
		this.query = "select * from " + this.nameInDB + ";";
	}

	// сторінка з таблицями
	@Override
	public void show() throws SQLException {
		super.show();
		addButtons();

		int y = 20;
		JButton bank = new JButton("БАНКИ");
		bank.setSize(150, 30);
		bank.setLocation(frame.getWidth() / 30, y);
		bank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowResPage newP = new ShowResPage("БАНКИ", "bank");
				try {
					newP.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});

		frame.add(bank);
		y += 30;

		JButton accounts = new JButton("РАХУНКИ");
		accounts.setSize(150, 30);
		accounts.setLocation(frame.getWidth() / 30, y);
		accounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowResPage newP = new ShowResPage("РАХУНКИ", "accounts");
				try {
					newP.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		frame.add(accounts);
		y += 30;

		JButton bill = new JButton("НАКЛАДНІ");
		bill.setSize(150, 30);
		bill.setLocation(frame.getWidth() / 30, y);
		bill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowResPage newP = new ShowResPage("НАКЛАДНІ", "bill");
				try {
					newP.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		frame.add(bill);
		y += 30;

		JButton nomenOfDel = new JButton("ТОВАРИ");
		nomenOfDel.setSize(150, 30);
		nomenOfDel.setLocation(frame.getWidth() / 30, y);
		nomenOfDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowResPage newP = new ShowResPage("ТОВАРИ", "nomenOfDel");
				try {
					newP.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		frame.add(nomenOfDel);
		y += 30;

		JButton providers = new JButton("ПОСТАЧАЛЬНИКИ");
		providers.setSize(150, 30);
		providers.setLocation(frame.getWidth() / 30, y);
		providers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowResPage newP = new ShowResPage("ПОСТАЧАЛЬНИКИ", "providers");
				try {
					newP.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		frame.add(providers);
		y += 30;

		JButton goodsOnStor = new JButton("ЗАПАСИ НА СКЛАДАХ");
		goodsOnStor.setSize(150, 30);
		goodsOnStor.setLocation(frame.getWidth() / 30, y);
		goodsOnStor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowResPage newP = new ShowResPage("ЗАПАСИ НА СКЛАДАХ", "goodsOnStor");
				try {
					newP.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		frame.add(goodsOnStor);
		y += 30;

		JButton storages = new JButton("СКЛАДИ");
		storages.setSize(150, 30);
		storages.setLocation(frame.getWidth() / 30, y);
		storages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowResPage newP = new ShowResPage("СКЛАДИ", "regOfStor");
				try {
					newP.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		frame.add(storages);
		y += 30;

		JButton contracts = new JButton("УГОДИ");
		contracts.setSize(150, 30);
		contracts.setLocation(frame.getWidth() / 30, y);
		contracts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowResPage newP = new ShowResPage("УГОДИ", "contracts");
				try {
					newP.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		frame.add(contracts);
		y += 30;

		addDBTable(30, 300, query);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void addButtons() {
		JButton addItem = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'> +"
				+ super.nameOfTable.toUpperCase() + "</div></html>");
		addItem.setSize(190, 30);
		addItem.setLocation(500, 100);
		addItem.setBackground(or);
		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				AddItemPage add = null;
				try {
					add = new AddItemPage(nameOfTable, nameInDB);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {
					add.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}

		});
		frame.add(addItem);

	}

	// вивести результат запиту на фрейм
	public void addDBTable(int x, int y, String query) throws SQLException {
		DataBase db = new DataBase();
		int rowHeight = 40;
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

			// кнопка видалення
			JButton deleteItem = new JButton(
					"<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'> ВИДАЛИТИ </div></html>");
			deleteItem.setSize(190, 30);
			deleteItem.setLocation(700, 100);
			deleteItem.setBackground(or);
			deleteItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int itemnum = table.getSelectedRow();

					r = (Vector) res.get(itemnum);
					System.out.println("vec  " + r);
					System.out.println(r.get(0));
					String q = "DELETE FROM " + nameInDB + " WHERE " + menu.get(0) + " = " + r.get(0) + ";";
					System.out.println(q);
					try {
						db.delete(q);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					lineCount--;
					for (int i = 0; i < lineCount; i++) {
						v = (Vector) res.get(i);
						if ((String) v.firstElement() == (String) r.get(0))
							res.remove(i);
					}

					scrollPane.remove(table);
					frame.remove(scrollPane);
					table = new JTable(res, menu);
					scrollPane = new JScrollPane(table);
					scrollPane.setLocation(x, y);
					table.setRowHeight(rowHeight);
					if (lineCount <= 7)
						scrollPane.setSize(frame.getWidth() - 70, rowHeight * lineCount + 20);
					else
						scrollPane.setSize(frame.getWidth() - 70, 300);
					frame.add(scrollPane);
					frame.repaint();
					ShowResPage newP = new ShowResPage(nameOfTable, nameInDB);

				}
			});
			frame.add(deleteItem);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.close();
	}
}
