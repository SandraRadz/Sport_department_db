import java.sql.*;

public class DataBase {
    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/sport_department";
    private static final String user = "root";
    private static final String password = "root";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs=null;
    //private static String query;

    public DataBase() {
        try {
            this.con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            //stmt.executeUpdate(this.query);
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }
    public void addTables(){
        String query = "";
        try {
            query = "CREATE TABLE IF NOT EXISTS bank ("+
                    "MFI_bank VARCHAR(6) NOT NULL,"+
                    "name_of_bank VARCHAR(150) NOT NULL,"+
                    "PRIMARY KEY (MFI_bank));";
            stmt.executeUpdate(query);

            query = "CREATE TABLE IF NOT EXISTS bank ("+
                    "MFI_bank VARCHAR(6) NOT NULL,"+
                    "name_of_bank VARCHAR(150) NOT NULL,"+
                    "PRIMARY KEY (MFI_bank));";
            stmt.executeUpdate(query);

            query = "CREATE TABLE IF NOT EXISTS bank ("+
                    "MFI_bank VARCHAR(6) NOT NULL,"+
                    "name_of_bank VARCHAR(150) NOT NULL,"+
                    "PRIMARY KEY (MFI_bank));";
            stmt.executeUpdate(query);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet select(String what, String from, String where) throws SQLException {//select from... query
        String query = "select "+what +" from "+ from+" where " +where +";";
        rs = stmt.executeQuery(query);
        return rs;
    }
    public ResultSet select(String what, String from) throws SQLException {//select from... query
        String query = "select "+what +" from "+ from+";";
        rs = stmt.executeQuery(query);
        return rs;
    }

    public ResultSet select(String query) throws SQLException {//select from... query
        rs = stmt.executeQuery(query);
        return rs;
    }

    //вставка. Поки ніяких ідей для того, щоб загально прописати назви таблиць при вставці немає
    //лише світч кайс

    //видалення


    public void close() throws SQLException { //not forget to close throws after using db
        try { con.close(); } catch(SQLException se) { /*can't do anything */ }
        try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
        if(rs!=null)try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
    }

}
