package Logic;

import SQL.FlightTable;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class LogicTest {

    Logic logic;
    FlightTable table;
    LocalDate Today;

    @BeforeEach
    void setUp(){
        logic = new Logic();
        Today = LocalDate.now();
    }
    @Test
    @Order(1)
    void addTestFlight(){
        table = new FlightTable();
        table.addFlight("Keflavík, KEF","Kansas, KCI", Today.toString(), Today.toString(), "05:00:00", "06:00:00", "60", "DifficultJet", "DJ867", "200", "100", "50", "100", "150", "200" );
        table.addFlight("Keflavík, KEF","Louisiana, MSY", Today.toString(), Today.toString(), "05:00:00", "06:00:00", "60", "DifficultJet", "DJ868", "200", "100", "50", "100", "150", "200" );
        table.addFlight("Louisiana, MSY","Dallas, DFW", Today.toString(), Today.toString(), "07:00:00", "08:00:00", "60", "DifficultJet", "DJ869", "200", "100", "50", "100", "150", "200" );
        table.addFlight("Dallas, DFW","Oklahoma, WRWA", Today.toString(), Today.toString(), "09:00:00", "10:00:00", "60", "DifficultJet", "DJ870", "200", "100", "50", "100", "150", "200" );
        table.addFlight("Oklahoma, WRWA","Kansas, KCI", Today.toString(), Today.toString(), "11:00:00", "12:00:00", "60", "DifficultJet", "DJ871", "200", "100", "50", "100", "150", "200" );
    }
    @Test
    @Order(2)
    void calculateRoutesDirect_InvalidDepartureAirport(){
        assertNull(logic.calculateRoutes("MAL", "Kansas, KCI", Today.toString(), "First Class", "1", true));
    }

    @Test
    @Order(3)
    void calculateRoutesDirect_InvalidArrivalAirport(){
        assertNull(logic.calculateRoutes("Keflavík, KEF", "MAL", Today.toString(), "First Class", "1", true));
    }

    @Test
    @Order(4)
    void calculateRoutesDirect_InvalidDepartureDate(){
        assertNull(logic.calculateRoutes("Keflavík, KEF", "Kansas, KCI", "20-20-1000", "First Class", "1", true));
    }

    @Test
    @Order(5)
    void calculateRoutesDirect_InvalidClass(){
        ArrayList<Route> routes=logic.calculateRoutes("Keflavík, KEF", "Kansas, KCI", Today.toString(), "12th Class", "1", true);
        assertEquals(0, routes.size());
    }

    @Test
    @Order(6)
    void calculateRoutesDirect_InvalidPassengerNum(){
        ArrayList<Route> routes=logic.calculateRoutes("Keflavík, KEF", "Kansas, KCI", Today.toString(), "First Class", "-1", true);
        assertEquals(null, routes);
    }

    @Test
    @Order(7)
    void calculateRoutesDirect_nullParameters(){
        assertNull(logic.calculateRoutes(null, null, null, null, null, null));
    }

    @Test
    @Order(8)
    void calculateRoutesDirect_goodParameters_RoutesReachKCIFromKEF(){
        int reachedDestination=0;
        ArrayList<Route> routes = logic.calculateRoutes("Keflavík, KEF", "Kansas, KCI", Today.toString(), "First Class", "1",  true);
        int numRoutes = routes.size();
        while (routes.size()!=0){
            Route route = routes.get(0);
            routes.remove(0);
            while (route.flights.size()!=0){
                Flight flight = route.flights.get(0);
                route.flights.remove(0);
                if(flight.arrivalAirport.equals("Kansas, KCI"))  reachedDestination++;
            }
        }
        assertEquals(numRoutes, reachedDestination);
    }

    @Test
    @Order(9)
    void calculateRoutesNotDirect_InvalidDepartureAirport(){
        assertNull(logic.calculateRoutes("MAL", "Kansas, KCI", Today.toString(), "First Class", "1", false));
    }

    @Test
    @Order(10)
    void calculateRoutesNotDirect_InvalidArrivalAirport(){
        ArrayList<Route> routes=logic.calculateRoutes("Keflavík, KEF", "MAL", Today.toString(), "First Class", "1", false);
        assertEquals(0, routes.size());
    }

    @Test
    @Order(11)
    void calculateRoutesNotDirect_InvalidDepartureDate(){
        assertNull(logic.calculateRoutes("Keflavík, KEF", "Kansas, KCI", "14/02/2023", "First Class", "1", false));
    }

    @Test
    @Order(12)
    void calculateRoutesNotDirect_InvalidClass(){
        ArrayList<Route> routes=logic.calculateRoutes("Keflavík, KEF", "Kansas, KCI", Today.toString(), "12th Class", "1", false);
        assertEquals(0, routes.size());
    }

    @Test
    @Order(13)
    void calculateRoutesNotDirect_InvalidPassengerNum(){
        assertNull(logic.calculateRoutes("Keflavík, KEF", "Kansas, KCI", Today.toString(), "First Class", "-1", false));
    }

    @Test
    @Order(14)
    void calculateRoutesNotDirect_nullParameters(){
        assertNull(logic.calculateRoutes(null, null, null, null, null, null));
    }

    @Test
    @Order(15)
    void calculateRoutesNotDirect_goodParameters_allRoutesReachKEFFromKCI(){
        int reachedDestination=0;
        ArrayList<Route> routes = logic.calculateRoutes("Keflavík, KEF", "Kansas, KCI", Today.toString(), "First Class", "1",  false);
        int numRoutes = routes.size();
        while (routes.size()!=0){
            Route route = routes.get(0);
            routes.remove(0);
            while (route.flights.size()!=0){
                Flight flight = route.flights.get(0);
                route.flights.remove(0);
                if(flight.arrivalAirport.equals("Kansas, KCI")) reachedDestination++;
            }
        }
        assertEquals(numRoutes, reachedDestination);
    }
    @Test
    @Order(16)
    void calculateRoutesNotDirect_goodParameters_checkRoutesPriceKEFToKCI(){

        boolean passed=true;

        ArrayList<Route> routes = logic.calculateRoutes("Keflavík, KEF", "Kansas, KCI", Today.toString(), "First Class", "1",  false);

        while (routes.size()!=0){
            int totalPriceEconomy = 0, totalPriceBusiness = 0, totalPriceFirstClass = 0;
            Route route = routes.get(0);
            routes.remove(0);
            while (route.flights.size()!=0){
                Flight flight = route.flights.get(0);
                route.flights.remove(0);
                totalPriceEconomy+=flight.economyPrice;
                totalPriceBusiness+=flight.businessPrice;
                totalPriceFirstClass+=flight.firstClassPrice;
            }
            if(route.totalPriceEconomy!=totalPriceEconomy || route.totalPriceBusiness!=totalPriceBusiness || route.totalPriceFirstClass!=totalPriceFirstClass){
                passed=false;
                break;
            }
        }
        assertTrue(passed);
    }

    @Test
    @Order(17)
    void calculateRoutesNotDirect_goodParameters_checkRoutesTimeKEFTo(){

        boolean passed=true;

        ArrayList<Route> routes = logic.calculateRoutes("Keflavík, KEF", "Kansas, KCI", Today.toString(), "First Class", "1",  false);

        while (routes.size()!=0){
            int totalTime=0;
            Route route = routes.get(0);
            totalTime = (int) route.flights.get(0).departureTime.until(route.flights.get(route.flights.size()-1).arrivalTime, ChronoUnit.MINUTES);
            routes.remove(0);
            while (route.flights.size()!=0){
                Flight flight = route.flights.get(0);
                route.flights.remove(0);

            }

            if(route.totalTime !=totalTime){
                passed=false;
                break;
            }
        }
        assertTrue(passed);
    }

    @Test
    @Order(18)
    void deleteTestFlight(){
        table = new FlightTable();
        table.removeFlightByFlightID("DJ867");
        table.removeFlightByFlightID("DJ868");
        table.removeFlightByFlightID("DJ869");
        table.removeFlightByFlightID("DJ870");
        table.removeFlightByFlightID("DJ871");
    }

}