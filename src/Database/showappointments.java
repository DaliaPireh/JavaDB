import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class showappointments {
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static String ip4 = "jdbc:mariadb://192.168.239.24:3306/logins";
    static String ip6 = "jdbc:mariadb://[2001:878:200:4102:207:e9ff:fe62:eed]:3306/logins";
    static String url="";
    String addresse = "jdbc:mariadb://[ip6]:3306/schemanavn";
    private static Connection conn = null;
    private static Statement statement = null;
    private PreparedStatement prep = null;

    static String inputfraCGI = null;

// here we use DBComm as a classname, if you use this method, make sure to change it to whatever class you use the
    // logger from


    public static void main(String[] args) {
        //utilize the arguments to handle HTTP cookies
        showHead();
        try {
            if(InetAddress.getLocalHost().getHostName().contains("su")){
                // System.out.println(InetAddress.getLocalHost().getHostName());
                url = ip4;
            }else{
                url = ip6;
                System.out.println("Remote host detected, from url:"+url);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String[] data = {in.readLine()};

            inputfraCGI = data[0];
            //   System.out.println(data[0]);
            // showBody(new StringTokenizer(data[0],"&\n\r"));

            Class.forName("org.mariadb.jdbc.Driver");

            //mysql skal  ndres senere til MariaDB, localhost til en IPaddresse -
            String user, pass;
            user = "william";
            pass = "budgieboi";
            // url="jdbc:mysql://localhost:3306/phoenixpoint?serverTimezone=Europe/Amsterdam&amp";

            // Skal man fx. bruge 127.0.0.1 til en remote maskine?
//Connection connection =
// DriverManager.getConnection("jdbc:mariadb://localhost:3306/DB?user=root&password=myPassword");
            //T nk jer om - kan man opn  mariadb forbindelse til en anden maskine uden at  ndre denne her?


            conn = DriverManager.getConnection(url, user, pass);
            if (conn != null) {


            }

            //find out which columns are in current table:
            statement = conn.createStatement();
            showAppointments(findAppointments());


            // String finduser = findUser("wilge@dtu.dk","henning");


//            showHead();
            //          showBody();
            //db.getHomeData();
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            String errstring = errors.toString();
            System.out.println(errstring);

        }


        showTail();


    }

    private static void showAppointments(ArrayList<String[]> input) {


        System.out.println(
                "<h3 style=\"position: center;\"> Du har tiderne</h3>\n" +
                        "<table>\n" +
                        "\n" +
                        "    <tr>\n" +
                        "        <th>Dato</th>\n" +
                        "        <th>Tid</th>\n" +
                        "        <th>Afdeling</th>\n" +
                        "        <th> Hospital</th>\n" +
                        "        \n" +
                        "    </tr>\n" );
        //stop med at printe tableheaders




        for( String[] strings : input){

            System.out.println("<tr> ");
            //print a row
            for (String out: strings){
//print contents of that row
                System.out.println("<td>" + out+ "  </td>");
                //System.out.println("<td>" + strings[1]+ " </td>");
                //System.out.println("<td>" + "</td>");
                // System.out.println("<td> </td>");
                // System.out.println("<td> </td>");


            }
            System.out.println("</tr>");
        }
        System.out.println("</table>");

    }


    private static ArrayList<String[]> findAppointments() {
        ArrayList output = new ArrayList();
        int id = 30;
        String findaftaler = "\n" +
                "\n" +
                "SELECT dato,tidspunkt,afdeling,hospital,patientid FROM logins.appointments" +
                " where patientid=" + id + ";";

        //https://theopentutorials.com/examples/java/util/date/how-to-convert-java-util-date-to-mysql-date-format/
        //in case we want to show the
        try {
            ResultSet aftaler = statement.executeQuery(findaftaler);
            while (aftaler.next()) {
                //for each row in the database
                output.add(new String[]
                        //build a new string array for our ArrayList
                        {
                                aftaler.getDate(1).toString(),
                                aftaler.getTime(2).toString(),
                                aftaler.getString(2).toString(),
                                aftaler.getString(3).toString(),
                                // aftaler.getInt("patientid")+""

                        });
                //now we have built the data for each row, lets print it out as HTML
                String HTML = "";

                //we want something clever, to write out as many table data as we have fields returned
                //for now we just will settle for the 5 colomns returned

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        if (output != null) {
            return output;
        } else {
            output.add(new String[]{"No patient found"});
            return output;

        }

        //return output;
    }


    private static void showHead() {


        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
        System.out.println("<HTML>");
        System.out.println();
        System.out.println("<HEAD>");
        System.out.println("<TITLE>Appointments shown</TITLE>");
        System.out.println("<META http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
        System.out.println("<META http-equiv=\"Pragma\" content=\"no-cache\">");
        System.out.println("<META http-equiv=\"expires\" content=\"0\">");
        //style tag
        System.out.println(
                "    <style>\n" +
                        "        table {\n" +
                        "            position: center;\n" +
                        "            font-family: arial, sans-serif;\n" +
                        "            border-collapse: collapse;\n" +
                        "            width: 100%;\n" +
                        "        }\n" +
                        "\n" +
                        "        td, th {\n" +
                        "            border: 1px solid #dddddd;\n" +
                        "            text-align: left;\n" +
                        "            padding: 8px;\n" +
                        "        }\n" +
                        "\n" +
                        "        tr:nth-child(even) {\n" +
                        "            background-color: #dddddd;\n" +
                        "        }\n" +
                        "\n" +
                        "\n" +
                        "        h1 {text-align: center;}\n" +
                        "        h2 {text-align: center;}\n" +
                        "        h3 {text-align: center;}\n" +
                        "        p {text-align: center;}\n" +
                        "        div {text-align: center;}\n" +
                        "    </style>\n" +
                        "\n" +
                        "\n"
        );
        System.out.println("</HEAD>");

        System.out.println("<BODY>");

    }

    private static void showBody(StringTokenizer stringTokenizer) {

    }

    private static void showTail() {
        System.out.println("</BODY>\n</HTML>");
    }

    private static String findUser(String mail, String Password) {
        String userCPR = null;

        String sqlFindUser = "select idloginoplysninger,cpr,mail from loginoplysninger where password ='" + Password + "'and mail ='" + mail + "';";
        try {
            ResultSet rs = statement.executeQuery(sqlFindUser);
            rs.next();
            int id = rs.getInt(1);
            String cpr = rs.getString(2);
            String email = rs.getString(3);
            System.out.println("Id:" + id);
            System.out.println("cpr:" + cpr);
            System.out.println("mail:" + email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return userCPR;
    }
}
