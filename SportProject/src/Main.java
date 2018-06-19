import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                DataBase db = new DataBase();
                db.addTables();
                try {
                    db.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //BillsReport startForm = new BillsReport(null, null);
               StartForm startForm = new StartForm();
                try {
                    startForm.show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
                    });
    }
}
