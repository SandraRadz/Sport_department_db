import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                //System.setProperty("file.encoding","UTF-8");
                DataBase db = new DataBase();
                db.addTables();
                try {
                    db.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    //ShowResPage ta = new ShowResPage("Постачальники", "providers",  db);
                    ShowResPage ta = new ShowResPage("Рахунки", "accounts");
                    //ShowResPage ta = new ShowResPage("Постачальники", "providers",  db);
                    ta.show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
                    });
    }
}
