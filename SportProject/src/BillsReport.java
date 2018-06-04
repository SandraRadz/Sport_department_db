import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class BillsReport extends Report {

	public BillsReport(String nameT) {
		super(nameT);
	}

	public void show() throws SQLException {
		super.show();
		JLabel repOf = new JLabel("Звіти по Накладним:", SwingConstants.CENTER);
		repOf.setFont(new Font("", Font.ITALIC, 30));
		repOf.setSize(400, 30);
		repOf.setLocation(380, 170);
		frame.add(repOf);
	}
}