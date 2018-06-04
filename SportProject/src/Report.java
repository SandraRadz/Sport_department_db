import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.Font;

public class Report extends WindowMenu {
	Color or = new Color(246, 184, 61);
	Color gr = new Color(12, 184, 61);

	// private DataBase dataBase;
	// private String query;
	protected String nameInDB;
	JTable table;
	JScrollPane scrollPane;
	int lineCount;
	// Vector r, v;

	public Report(String nameT) {
		super(nameT);
		// this.nameInDB=nameDB;
		// this.query="select * from "+this.nameInDB +";";
	}

	// сторінка з таблицями
	@Override
	public void show() throws SQLException {
		super.show();
		// addButtons();
		int y = 80;

		JLabel repOf = new JLabel("Звіти по:", SwingConstants.CENTER);
		repOf.setFont(new Font("", Font.PLAIN, 23));
		repOf.setSize(150, 30);
		repOf.setLocation(frame.getWidth() / 30, y);
		frame.add(repOf);
		y += 50;

		JButton contracts = new JButton("КОНТРАКТАМ");
		contracts.setSize(150, 30);
		contracts.setLocation(frame.getWidth() / 30, y);
		contracts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContractsReport newCrRep = new ContractsReport("ЗВІТИ ПО КОНТРАКТАМ");
				try {
					newCrRep.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});

		frame.add(contracts);
		y += 30;

		JButton bills = new JButton("НАКЛАДНИМ");
		bills.setSize(150, 30);
		bills.setLocation(frame.getWidth() / 30, y);
		bills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BillsReport newBlRep = new BillsReport("ЗВІТИ ПО НАКЛАДНИМ");
				try {
					newBlRep.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		frame.add(bills);
		y += 30;

		JButton goods = new JButton("ТОВАРАМ");
		goods.setSize(150, 30);
		goods.setLocation(frame.getWidth() / 30, y);
		goods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GoodsReport newGdRep = new GoodsReport("ЗВІТИ ПО ТОВАРАМ");
				try {
					newGdRep.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		frame.add(goods);
		y += 30;

		JButton providers = new JButton("ПОСТАЧАЛЬНИКАМ");
		providers.setSize(150, 30);
		providers.setLocation(frame.getWidth() / 30, y);
		providers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProvidersReport newPrRep = new ProvidersReport("ЗВІТИ ПО ПОСТАЧАЛЬНИКАМ");
				try {
					newPrRep.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
		frame.add(providers);

	}
}