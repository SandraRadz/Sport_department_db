import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                //System.setProperty("file.encoding","UTF-8");
                DataBase db = new DataBase();
                db.addTables();
                try {
                    ShowResPage ta = new ShowResPage("Постачальники", "providers",  db);
                    ta.show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }



        });
    }
}
