import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Table t = new Table("First", "bank", 6);
                try {
                    t.createWindow();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        });
    }
}
