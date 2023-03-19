package SQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DataBase {

    private static final String db_url = "jdbc:postgresql://db.fe.up.pt:5432/pswa0501";
    private static final String db_user = "pswa0501";
    private static final String db_userPass = "LoganAir";

    public static Connection connect(){ //fazer disconnect?

        Connection c = null;


        try{
            Class.forName("org.postgresql.Driver");

            c = DriverManager.getConnection(db_url, db_user, db_userPass);


        }
        catch(SQLException | ClassNotFoundException e ){
            System.out.print("Exception: ");
            System.out.println(e.getMessage());

        }

        if(c==null) System.out.println("Connection error. Please connect with FEUP VPN.");

        return c;
    }


}
