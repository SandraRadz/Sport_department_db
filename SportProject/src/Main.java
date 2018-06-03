import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
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
