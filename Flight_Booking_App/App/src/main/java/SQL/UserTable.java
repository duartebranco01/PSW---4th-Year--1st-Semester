package SQL;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserTable extends DataBase{
    public boolean addUser(String Email, String Password){

        Connection c = null;
        Statement st = null;

        c = super.connect();
        if(c==null) return false;

        try {
            String query = "INSERT INTO \"FlightBooking\".\"Users\" (\"Email\", \"Password\") VALUES ('"
                    + Email +"', '"+ Password +"');";
            st = c.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;
    }

    public boolean removeUserByEmail(String Email){

        Connection c = null;
        Statement st = null;

        c = super.connect();
        if(c==null) return false;

        try {
            String query = "DELETE FROM \"FlightBooking\".\"Users\" WHERE \"Email\" = '" + Email + "'";
            st = c.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;
    }

    public String getUserByEmail(String Email){

        Connection c = null;
        Statement st = null;
        ResultSet rs = null;

        StringBuilder userResult = new StringBuilder();

        c = super.connect();
        if(c==null) return null;

        try {
            String query = "SELECT * FROM \"FlightBooking\".\"Users\" WHERE \"Email\" = '"+ Email +"'";
            st = c.createStatement();
            rs = st.executeQuery(query);

            rs.next();

            buildUserResult(userResult, rs);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userResult.toString();
    }

    public boolean updateEmail(String Email, String newEmail){

        Connection c = null;
        Statement st = null;

        c = super.connect();
        if(c==null) return false;

        try {
            String query = "UPDATE \"FlightBooking\".\"Users\" SET \"Email\" = '" + newEmail + "' WHERE \"Email\" = '" + Email + "'";
            st = c.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;
    }
    public boolean updatePassword(String Email, String newPassword){

        Connection c = null;
        Statement st = null;

        c = super.connect();
        if(c==null) return false;

        try {
            String query = "UPDATE \"FlightBooking\".\"Users\" SET \"Password\" = '" + newPassword + "' WHERE \"Email\" = '" + Email + "'";
            st = c.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;
    }

    private void buildUserResult(StringBuilder userResult, ResultSet rs) throws SQLException {

        userResult.append(rs.getString("Email")).append(";");
        userResult.append(rs.getString("Password")).append(";");
    }

}

