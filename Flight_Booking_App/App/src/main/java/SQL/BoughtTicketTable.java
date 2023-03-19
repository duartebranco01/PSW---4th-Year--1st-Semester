package SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class BoughtTicketTable extends DataBase {

    // connects ticket with a FlightID to a User
    public boolean addTicketToUser(String User, String FlightID){

        Connection c = null;
        Statement st = null;

        c = super.connect();
        if(c==null) return false;

        try {
            String query = "INSERT INTO \"FlightBooking\".\"BoughtTicket\" (\"User\", \"FlightID\") VALUES ('"
                    + User +"', '"+ FlightID +"');";
            st = c.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;
    }

    // removes all tickets associated with a certain user
    public boolean removeAllTicketsFromUser(String User){

        Connection c = null;
        Statement st = null;

        c = super.connect();
        if(c==null) return false;

        try {
            String query = "DELETE FROM \"FlightBooking\".\"BoughtTicket\" WHERE \"User\" = '" + User + "'";
            st = c.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;
    }

    // removes all tickets with a certain flightID
    public boolean removeAllTicketsWithFlightID(String FlightID){

        Connection c = null;
        Statement st = null;

        c = super.connect();
        if(c==null) return false;

        try {
            String query = "DELETE FROM \"FlightBooking\".\"BoughtTicket\" WHERE \"FlightID\" = '" + FlightID + "'";
            st = c.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;
    }

    // removes ticket with a certain user and flightID
    public boolean removeTicketWithUserAndFlightID(String User, String FlightID){

        Connection c = null;
        Statement st = null;

        c = super.connect();
        if(c==null) return false;

        try {
            String query = "DELETE FROM \"FlightBooking\".\"BoughtTicket\" WHERE \"User\" = '"+ User +"' AND \"FlightID\" = '"+ FlightID +"'";
            st = c.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;
    }

    // returns all tickets User for a certain User
    public List<String> getTicketsFromUser(String User){

        Connection c = null;
        Statement st = null;
        ResultSet rs = null;

        List<String> allTicketsFromUserResults = new LinkedList<>();

        c = super.connect();
        if(c==null) return null;


        try {
            String query = "SELECT * FROM \"FlightBooking\".\"BoughtTicket\" WHERE \"User\" = '"+ User +"'";
            st = c.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {

                StringBuilder ticketsResult = new StringBuilder();
                buildBoughtTicketsResultFlightID(ticketsResult, rs);
                allTicketsFromUserResults.add(ticketsResult.toString());

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allTicketsFromUserResults;
    }

     //same as previous but only gets one ticket (the first in the table)
    public String getTicketFromUser(String User){

        Connection c = null;
        Statement st = null;
        ResultSet rs = null;

        StringBuilder ticketResult = new StringBuilder();

        c = super.connect();
        if(c==null) return null;


        try {
            String query = "SELECT * FROM \"FlightBooking\".\"BoughtTicket\" WHERE \"User\" = '"+ User +"'";
            st = c.createStatement();
            rs = st.executeQuery(query);


            rs.next();

            buildBoughtTicketsResultFlightID(ticketResult, rs);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ticketResult.toString();
    }

    // returns all Users that bought a ticket with a certain FlightID
    public List<String> getTicketsByFlightID(String FlightID){

        Connection c = null;
        Statement st = null;
        ResultSet rs = null;

        List<String> allUsersFromFlightIDResults = new LinkedList<>();

        c = super.connect();
        if(c==null) return null;


        try {
            String query = "SELECT * FROM \"FlightBooking\".\"BoughtTicket\" WHERE \"FlightID\" = '"+ FlightID +"'";
            st = c.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {

                StringBuilder ticketsResult = new StringBuilder();
                buildBoughtTicketsResultUser(ticketsResult, rs);
                allUsersFromFlightIDResults.add(ticketsResult.toString());

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allUsersFromFlightIDResults;


    }

    //same as previous but only gets one ticket (the first in the table)
    public String getTicketByFlightID(String FlightID){

        Connection c = null;
        Statement st = null;
        ResultSet rs = null;

        StringBuilder ticketResult = new StringBuilder();

        c = super.connect();
        if(c==null) return null;


        try {
            String query = "SELECT * FROM \"FlightBooking\".\"BoughtTicket\" WHERE \"FlightID\" = '"+ FlightID +"'";
            st = c.createStatement();
            rs = st.executeQuery(query);


            rs.next();

            buildBoughtTicketsResultUser(ticketResult, rs);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ticketResult.toString();
    }


    private void buildBoughtTicketsResult(StringBuilder BoughtTicketResult, ResultSet rs) throws SQLException {

        BoughtTicketResult.append(rs.getString("User")).append(";");
        BoughtTicketResult.append(rs.getString("FlightID")).append(";");
    }

    private void buildBoughtTicketsResultFlightID(StringBuilder BoughtTicketResult, ResultSet rs) throws SQLException {

        BoughtTicketResult.append(rs.getString("FlightID"));
    }

    private void buildBoughtTicketsResultUser(StringBuilder BoughtTicketResult, ResultSet rs) throws SQLException {

        BoughtTicketResult.append(rs.getString("User"));
    }






}
