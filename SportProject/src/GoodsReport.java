import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GoodsReport extends Report {

	public GoodsReport(String nameT) {
		super(nameT);
		// TODO Auto-generated constructor stub
	}

	public void show() throws SQLException {
		super.show();
		JLabel repOf = new JLabel("Звіти по Товарам:", SwingConstants.CENTER);
		repOf.setFont(new Font("", Font.ITALIC, 30));
		repOf.setSize(400, 30);
		repOf.setLocation(380, 170);
		frame.add(repOf);
	}
}
