package Database;
import java.sql.*;


//MariaDB lytter ikke til lokalvært, men localhost
//MariaDB er kompatibel med MySQL
public class MariaDB {
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static String url2 = "jdbc:mariadb://192.168.239.28:3306/AftalerDatabase";
    static String url = "jdbc:mariadb://[2001:878:200:4102:207:e9ff:fe62:bc9]:3306/AftalerDatabase";

    private static Connection conn = null;
    private static Statement statement = null;
    private PreparedStatement prep = null;

    public static void main(String[] args) {
        //Bruges bare til at have en metode som kan k re det hele fra.
        //DBcomm db = new DBcomm();
        try {
            Class.forName("org.mariadb.jdbc.Driver");

            //mysql skal  ndres senere til MariaDB, localhost til en IPaddresse -
            String user, pass;
            user = "sali";
            pass = "fisk";
             url="jdbc:mysql://192.168.239.28:3306/AftalerDatabase?serverTimezone=Europe/Amsterdam&amp";

            // Skal man fx. bruge 127.0.0.1 til en remote maskine?
//Connection connection =
// DriverManager.getConnection("jdbc:mariadb://localhost:3306/DB?user=root&password=myPassword");
            //T nk jer om - kan man opn  mariadb forbindelse til en anden maskine uden at  ndre denne her?
//

            conn = DriverManager.getConnection(url, user, pass);
            if (conn != null) {

                System.out.println("Im in");
            } else {
                System.out.println("connection not made");
            }

            //find out which columns are in current table:
            statement = conn.createStatement();
            String sql = "select * from aftaler;";
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int numberOfColumns = rsMetaData.getColumnCount();

            // get the column names; column indexes start from 1
            for (int i = 1; i < numberOfColumns + 1; i++) {
                String columnName = rsMetaData.getColumnName(i);
                // Get the name of the column's table name
                String tableName = rsMetaData.getTableName(i);
                System.out.println("column name=" + columnName);
            }


            //db.getHomeData();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
