package Logic;

import GUI.HelloController;
import SQL.FlightTable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class GUI_Logic {

    public static String City1, City2;
    public static LocalDate depDate, depDate2;
    public static LocalDate arrDate, arrDate2;
    public static Integer price, price20;
    public static String arrAirport, depAirport;
    public static LocalTime depHour, arrHour,  depHour2, arrHour2;
    public static String arrAirport2, depAirport2;
    public static String FlightID;
    public static String FlightID2;

    public static ArrayList<Flight> cheapestOption(ArrayList<Flight> sortedFlightsList)
    {
        Collections.sort(sortedFlightsList, new GUI_Logic.cheapestComparator());
        return sortedFlightsList;
    }

    static class cheapestComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.getEconomyPrice()-f2.getEconomyPrice();
        }

    };


    public static void bestDeals()
    {
        Random rand = new Random();
        FlightTable flights= new FlightTable();
        Logic logic = new Logic();

        String[] BestDealsCity1 = {"Londres, LGW", "Paris, CDG"};
        City1=BestDealsCity1[rand.nextInt(BestDealsCity1.length)];

        List<String> flightResults;

        flightResults = flights.getFlightsByFromTo("Porto, OPO",City1, null);

        ArrayList<Flight> results;
        ArrayList<Flight> rightResults= new ArrayList<>();

        results = logic.buildFlightsList(flightResults);


        LocalDate Today=LocalDate.now();

        for (int i=0; i<results.size(); i++) {
            if(results.get(i).getDepartureDate().isAfter(Today)) {
                rightResults.add(results.get(i));
            }
        }

        ArrayList<Flight> flightFinal = cheapestOption(rightResults);

        depDate=flightFinal.get(0).getDepartureDate();
        arrDate=flightFinal.get(0).getArrivalDate();
        price=flightFinal.get(0).getEconomyPrice();
        arrAirport=flightFinal.get(0).getArrivalAirport();
        depAirport=flightFinal.get(0).getDepartureAirport();
        FlightID=flightFinal.get(0).getFlightID();
        arrHour = flightFinal.get(0).arrivalTime;
        depHour = flightFinal.get(0).departureTime;


        Random rand2 = new Random();
        String[] BestDealsCity2 = {"Milan, MXP", "Barcelona, BCN"};
        City2=BestDealsCity2[rand2.nextInt(BestDealsCity2.length)];

        List<String> flightResults2;

        flightResults2 = flights.getFlightsByFromTo("Porto, OPO", City2, null);

        ArrayList<Flight> results2;
        ArrayList<Flight> rightResults2= new ArrayList<>();


        results2 = logic.buildFlightsList(flightResults2);



        for (int i=0; i<results2.size(); i++) {
            if(results2.get(i).getDepartureDate().isAfter(Today)) {
                rightResults2.add(results2.get(i));
            }
        }

        ArrayList<Flight> flightFinal2 = cheapestOption(rightResults2);

        depDate2=flightFinal2.get(0).getDepartureDate();
        arrDate2=flightFinal2.get(0).getArrivalDate();
        price20=flightFinal2.get(0).getEconomyPrice();
        arrAirport2=flightFinal2.get(0).getArrivalAirport();
        depAirport2=flightFinal2.get(0).getDepartureAirport();
        FlightID2=flightFinal2.get(0).getFlightID();
        arrHour2 = flightFinal2.get(0).arrivalTime;
        depHour2 = flightFinal2.get(0).departureTime;

    }
}
