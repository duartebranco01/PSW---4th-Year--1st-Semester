package Logic;

import SQL.FlightTable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Logic {

    //return trip vai usar a mesma funçao mas departureDate=returnDate
    //route is list of flights from departure to arrival, routesList is list of all routes
    public ArrayList<Route> calculateRoutes(String departureAirport, String arrivalAirport, String departureDate, String flightClass, String numPassenger, Boolean isDirect){



        if(departureAirport==null || arrivalAirport==null || departureDate==null || flightClass==null || numPassenger==null || isDirect==null || checkDateFormat(departureDate)==false) return null;


        ArrayList<Route> routesList = new ArrayList<Route>();

        if(isDirect) routesList = calculateDirectRoutes(departureAirport, arrivalAirport, departureDate, flightClass, Integer.parseInt(numPassenger));
        else routesList = calculateNotDirectRoutes(departureAirport, arrivalAirport, departureDate, flightClass, Integer.parseInt(numPassenger));

        return routesList;
    }

    //calculate not direct flights with BFS/DFS algorithm
    private ArrayList<Route> calculateNotDirectRoutes(String departureAirport, String arrivalAirport, String departureDate, String flightClass, int numPassenger) {

        Route inRoute= new Route();

        if(numPassenger<1) return null;

        ArrayList<Route> routesList = searchFlightsDFS(departureAirport, arrivalAirport, departureDate, "00:00:00", flightClass, numPassenger, inRoute);
        if(routesList==null) return null;


        return routesList;
    }
    private ArrayList<Route> searchFlightsDFS(String departureAirport, String arrivalAirport, String departureDate, String departureTime, String flightClass, int numPassenger, Route inRoute) {

        ArrayList<Route> routes = new ArrayList<Route>();
        Route route = new Route(inRoute.flights, inRoute.totalPriceEconomy, inRoute.totalPriceBusiness, inRoute.totalPriceFirstClass, inRoute.totalTime);

        FlightTable flightTable = new FlightTable();

        List<String> flightsFromListString = flightTable.getFlightsByFrom(departureAirport, departureDate);
        if (flightsFromListString == null) return null;

        ArrayList<Flight> currFlightsFrom = buildFlightsList(flightsFromListString);
        if (currFlightsFrom == null) return null;

        while (!currFlightsFrom.isEmpty()) {
            Flight currFlight = currFlightsFrom.get(0);
            currFlightsFrom.remove(0);

            if (/*currFlight.departureDate.equals(LocalDate.parse(departureDate, DateTimeFormatter.ISO_LOCAL_DATE)) &&*/ currFlight.departureTime.isAfter(LocalTime.parse(departureTime, DateTimeFormatter.ISO_LOCAL_TIME).plusMinutes(30))
                    && ((flightClass.equals("Economy") && currFlight.economyCapacity >= numPassenger) || (flightClass.equals("Business") && currFlight.businessCapacity >= numPassenger)
                    || (flightClass.equals("First Class") && currFlight.firstClassCapacity >= numPassenger))) {

                route.flights.add(currFlight);
                route.totalPriceEconomy += currFlight.economyPrice * numPassenger;
                route.totalPriceBusiness += currFlight.businessPrice * numPassenger;
                route.totalPriceFirstClass += currFlight.firstClassPrice * numPassenger;


                if (currFlight.arrivalAirport.equals(arrivalAirport)) {


                    route.totalTime = (int) route.flights.get(0).departureTime.until(route.flights.get(route.flights.size()-1).arrivalTime, ChronoUnit.MINUTES);
                    Route routeGood = new Route(route.flights, route.totalPriceEconomy, route.totalPriceBusiness, route.totalPriceFirstClass, route.totalTime);


                    routes.add(routeGood);
                } else {
                    ArrayList<Route> aux = searchFlightsDFS(currFlight.arrivalAirport, arrivalAirport, currFlight.departureDate.toString(), currFlight.departureTime.toString(), flightClass, numPassenger, route);
                    if (aux != null) {
                        routes.addAll(aux);
                    }
                }
                route.flights.remove(route.flights.size() - 1);
                route.totalPriceEconomy -= currFlight.economyPrice * numPassenger;
                route.totalPriceBusiness -= currFlight.businessPrice * numPassenger;
                route.totalPriceFirstClass -= currFlight.firstClassPrice * numPassenger;
                //route.totalTime -= currFlight.flightDurationMin;
            }
        }

        return routes;
    }
    private ArrayList<Route> calculateDirectRoutes(String departureAirport, String arrivalAirport, String departureDate, String flightClass, int numPassenger){

        FlightTable flightTable = new FlightTable();
        ArrayList<Route> routesList = new ArrayList<Route>();

        if(numPassenger<1) return null;

        List<String> flightsListString = flightTable.getFlightsByFromTo(departureAirport, arrivalAirport, departureDate);
        if(flightsListString==null) return null;

        List<Flight> flightsList = buildFlightsList(flightsListString);
        if(flightsList==null) return null;

        Iterator<Flight> it = flightsList.listIterator();

        while (it.hasNext()) {
            Route route = new Route();
            Flight currFlight = it.next();

            if ((currFlight.departureDate.equals(LocalDate.parse(departureDate, DateTimeFormatter.ISO_LOCAL_DATE))) && ((flightClass.equals("Economy") && currFlight.economyCapacity>=numPassenger)
                    || (flightClass.equals("Business") && currFlight.businessCapacity>=numPassenger) || (flightClass.equals("First Class") && currFlight.firstClassCapacity>=numPassenger))) {

                route.flights.add(currFlight);
                route.totalPriceEconomy+=currFlight.economyPrice;
                route.totalPriceBusiness+=currFlight.businessPrice;
                route.totalPriceFirstClass+=currFlight.firstClassPrice;
                route.totalTime +=currFlight.flightDurationMin;
                routesList.add(route);
            }
        }
        return routesList;
    }


    //build list of flight objects from list of flight string
    public ArrayList<Flight> buildFlightsList(List<String> flightsListString){

        if(flightsListString.isEmpty()) return null;

        ArrayList<Flight> flightsList = new ArrayList<>();
        Iterator<String> it = flightsListString.listIterator();

        while (it.hasNext()){
            flightsList.add(new Flight(it.next()));
        }
        return flightsList;
    }

    public void printRoutesList(ArrayList<Route> routesList){

        if(routesList==null) return;
        Iterator<Route> it1 = routesList.listIterator();

        while (it1.hasNext()){
            Route route=it1.next();
            Iterator<Flight> it2 = route.flights.listIterator();

            while (it2.hasNext()){
                Flight currFlight = it2.next();

            }
        }

    }

    private boolean checkDateFormat(String date){

        if(date.length()>10) return false;
        char c;
        for (int i=0; i<date.length(); i++){
            c=date.charAt(i);
            if(c!=0x2D && (i==4 || i==7)) return false;
            else if((c<0x30 || c>0x39) && (i!=4 && i!=7)) return false;
        }
        return true;
    }
}
