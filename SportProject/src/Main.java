import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                //Window t = new Window("First", "bank", 6);
                DataBase db = new DataBase();
                db.addTables();
                try {
                    SimpleTable ta = new SimpleTable("Банки", "bank", 6, db);
                    ta.show();
                    db.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }



        });
    }
}
