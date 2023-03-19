package SQL;


import Logic.Flight;
import Logic.Route;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class FlightTable extends DataBase {

    public List<String> getFlightsByFromTo(String departureAirport, String arrivalAirport, String departureDate) {

        Connection c = null;
        Statement st = null;
        ResultSet rs = null;

        List<String> allFlightResults = new ArrayList<>();

        c = super.connect();
        if(c==null) return null;


        try {
            String query;

            if(departureDate==null){
                query = "SELECT * FROM \"FlightBooking\".\"Flights\" WHERE \"DepartureAirport\" = '" + departureAirport + "' AND \"ArrivalAirport\" = '" + arrivalAirport + "'";
            }
            else{
                query = "SELECT * FROM \"FlightBooking\".\"Flights\" WHERE \"DepartureAirport\" = '" + departureAirport + "' AND \"ArrivalAirport\" = '" + arrivalAirport +"' AND \"DepartureDate\" = '" + departureDate + "'";
            }
            st = c.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {

                StringBuilder flightResult = new StringBuilder();
                buildFlightResult(flightResult, rs);
                allFlightResults.add(flightResult.toString());

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allFlightResults;
    }

    public List<String> getFlightsByFrom(String departureAirport, String departureDate) {

        Connection c = null;
        Statement st = null;
        ResultSet rs = null;

        List<String> allFlightResults = new ArrayList<>();

        c = super.connect();
        if(c==null) return null;


        try {
            String query;

            if(departureDate==null){
                query= "SELECT * FROM \"FlightBooking\".\"Flights\" WHERE \"DepartureAirport\" = '" + departureAirport + "'";
            }
            else{
                query= "SELECT * FROM \"FlightBooking\".\"Flights\" WHERE \"DepartureAirport\" = '" + departureAirport + "' AND \"DepartureDate\" = '" + departureDate + "'";
            }
            st = c.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {

                StringBuilder flightResult = new StringBuilder();
                buildFlightResult(flightResult, rs);
                allFlightResults.add(flightResult.toString());

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allFlightResults;
    }

    public List<String> getFlightsByTo(String arrivalAirport, String departureDate) {

        Connection c = null;
        Statement st = null;
        ResultSet rs = null;

        List<String> allFlightResults = new ArrayList<>();

        c = super.connect();
        if(c==null) return null;


        try {
            String query;

            if(departureDate==null){
                query= "SELECT * FROM \"FlightBooking\".\"Flights\" WHERE \"ArrivalAirport\" = '" + arrivalAirport + "'";
            }
            else {
                query= "SELECT * FROM \"FlightBooking\".\"Flights\" WHERE \"ArrivalAirport\" = '" + arrivalAirport + "' AND \"DepartureDate\" = '" + departureDate + "'";
            }
            st = c.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {

                StringBuilder flightResult = new StringBuilder();
                buildFlightResult(flightResult, rs);
                allFlightResults.add(flightResult.toString());

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allFlightResults;
    }

    public String getFlightsByFlightID(String FlightID) {

        Connection c = null;
        Statement st = null;
        ResultSet rs = null;

        StringBuilder flightResult = new StringBuilder();

        c = super.connect();
        if(c==null) return null;


        try {
            String query = "SELECT * FROM \"FlightBooking\".\"Flights\" WHERE \"FlightID\" = '" + FlightID + "'";
            st = c.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {

                buildFlightResult(flightResult, rs);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return flightResult.toString();
    }

    public boolean updateClassCapacityByFlightID(String flightClass, String newCapacity, String FlightID) {

        Connection c = null;
        Statement st = null;

        c = super.connect();
        if(c==null) return false;

        try {
            String query = "UPDATE \"FlightBooking\".\"Flights\" SET \"" + flightClass + "Capacity\" = '" + newCapacity + "' WHERE \"FlightID\" = '" + FlightID + "'";
            st = c.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;
    }

    public boolean addFlight(String DepartureAirport, String ArrivalAirport, String DepartureDate, String ArrivalDate, String DepartureTime, String ArrivalTime, String FlightDurationMin, String AirlineName,
                             String FlightID, String EconomyCapacity, String BusinessCapacity, String FirstClassCapacity, String EconomyPrice, String BusinessPrice, String FirsClassPrice) {

        Connection c = null;
        Statement st = null;

        c = super.connect();
        if(c==null) return false;

        try {
            String query = "INSERT INTO \"FlightBooking\".\"Flights\" (\"DepartureAirport\", \"ArrivalAirport\", \"DepartureDate\", \"ArrivalDate\", \"DepartureTime\", \"ArrivalTime\", " +
                    "\"FlightDurationMin\", \"AirlineName\", \"FlightID\", \"EconomyCapacity\", \"BusinessCapacity\", \"FirstClassCapacity\", \"EconomyPrice\", \"BusinessPrice\", \"FirstClassPrice\") " +
                    "VALUES ('" + DepartureAirport + "', '" + ArrivalAirport + "', '"
                    + DepartureDate + "', '" + ArrivalDate + "', '" + DepartureTime + "', '" + ArrivalTime + "', '" + FlightDurationMin + "', '" + AirlineName + "', '" + FlightID + "', '"
                    + EconomyCapacity + "', '" + BusinessCapacity + "', '" + FirstClassCapacity + "', '" + EconomyPrice + "', '" + BusinessPrice + "', '" + FirsClassPrice + "');";
            st = c.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;
    }

    public boolean removeFlightByFlightID(String FlightID) {

        Connection c = null;
        Statement st = null;

        c = super.connect();
        if(c==null) return false;

        try {
            String query = "DELETE FROM \"FlightBooking\".\"Flights\" WHERE \"FlightID\" = '" + FlightID + "'";
            st = c.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }


    private void buildFlightResult(StringBuilder flightResult, ResultSet rs) throws SQLException {

        flightResult.append(rs.getString("DepartureAirport")).append(";");
        flightResult.append(rs.getString("ArrivalAirport")).append(";");
        flightResult.append(rs.getString("DepartureDate")).append(";");
        flightResult.append(rs.getString("ArrivalDate")).append(";");
        flightResult.append(rs.getString("DepartureTime")).append(";");
        flightResult.append(rs.getString("ArrivalTime")).append(";");
        flightResult.append(rs.getString("FlightDurationMin")).append(";");
        flightResult.append(rs.getString("AirlineName")).append(";");
        flightResult.append(rs.getString("FlightID")).append(";");
        flightResult.append(rs.getString("EconomyCapacity")).append(";");
        flightResult.append(rs.getString("BusinessCapacity")).append(";");
        flightResult.append(rs.getString("FirstClassCapacity")).append(";");
        flightResult.append(rs.getString("EconomyPrice")).append(";");
        flightResult.append(rs.getString("BusinessPrice")).append(";");
        flightResult.append(rs.getString("FirstClassPrice")).append(";");
    }

    public HashSet<String> listAirports(){
        HashSet<String> airports = new HashSet<String>();

        Connection c = null;
        Statement st = null;
        ResultSet rs = null;

        c = super.connect();
        if(c==null) return null;

        try {
            String query = "SELECT \"DepartureAirport\" FROM \"FlightBooking\".\"Flights\" ";
            st = c.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                airports.add(rs.getString("DepartureAirport"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String query = "SELECT \"ArrivalAirport\" FROM \"FlightBooking\".\"Flights\" ";
            st = c.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                airports.add(rs.getString("ArrivalAirport"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return airports;
    }

}

