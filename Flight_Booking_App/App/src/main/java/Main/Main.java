package Main;

import GUI.HelloApplication;
import Logic.Logic;
import Logic.Route;
import SQL.BoughtTicketTable;
import SQL.FlightTable;

import java.util.*;

public class Main {
    public static void main(String[] args){


        System.out.println("Hello Developing!");

        //testSQL(); //se nao quiserem testar comentem
        //testLogic();
        //testBoughtTicketTableSQL();
        //testFilters();

        HelloApplication.startGUI();

    }

    /*public static void testFilters(){
        Filters filter = new Filters();
        Logic logic = new Logic();
        ArrayList<ArrayList<Flight>> filteredRoutesList = null;
        ArrayList<ArrayList<Flight>> sortedRoutesList = null;

        String[] airlines = {"LoganAir", "TAP Air Portugal"};
        Integer[] nLayoversSelected = {0 , 1};

        filteredRoutesList = filter.filterRoutes(airlines, nLayoversSelected);

        if(filteredRoutesList == null) return;
        System.out.println("FILTERED");
        logic.printRoutesList(filteredRoutesList);

        sortedRoutesList = filter.sortRoutes(filteredRoutesList, 2);

        if(sortedRoutesList == null) return;
        System.out.println("SORTED");
        logic.printRoutesList(sortedRoutesList);
    }*/
    public static void testLogic(){
        Logic logic = new Logic();
        ArrayList<Route> routesList = logic.calculateRoutes("Porto, OPO", "Londres, LGW", "2022-12-24", "First Class", "9",  true);
        System.out.println("DIRECT --------------------------------------------------");
        logic.printRoutesList(routesList);
        ArrayList<Route> routesList2 = logic.calculateRoutes("Porto, OPO", "Berlin, BER", "2022-12-24", "Economy", "9",  false);
        System.out.println("NOT DIRECT --------------------------------------------------");
        logic.printRoutesList(routesList2);
    }


    public static void testBoughtTicketTableSQL()
    {
        BoughtTicketTable bt = new BoughtTicketTable();

        // add tickets
        if(bt.addTicketToUser("miguel@gmail.com", "LOGAN34")){
            System.out.println("connect ticket to user success.");
        }
        else System.out.println("connect ticket to user failure.");

        if(bt.addTicketToUser("miguel@gmail.com", "CARLAO6666")){
            System.out.println("connect ticket to user success.");
        }
        else System.out.println("connect ticket to user failure.");

        if(bt.addTicketToUser("bea@gmail.com", "CARLAO6666")){
            System.out.println("connect ticket to user success.");
        }
        else System.out.println("connect ticket to user failure.");

        if(bt.addTicketToUser("pedrosilva@gmail.com", "POLY654")){
            System.out.println("connect ticket to user success.");
        }
        else System.out.println("connect ticket to user failure.");

        // remove all tickets for user
        if(bt.removeAllTicketsFromUser("miguel@gmail.com")){
            System.out.println("tickets removed from user success.");
        }
        else System.out.println("tickets removed from user failure.");

        // remove all tickets with a certain flightID
        if(bt.removeAllTicketsWithFlightID("CARLAO6666")){
            System.out.println("tickets removed success.");
        }
        else System.out.println("tickets removed failure.");

        // remove ticket with a certain user and flightID
        if(bt.removeTicketWithUserAndFlightID("miguel@gmail.com", "LOGAN34")){
            System.out.println("tickets removed success.");
        }
        else System.out.println("tickets removed failure.");

        //get tickets from user
        System.out.println("TODOS OS BILHETES QUE O USER COMPROU");
        List<String> allTicketsFromUser =bt.getTicketsFromUser("miguel@gmail.com");
        System.out.println(allTicketsFromUser);

        //get tickets by flightID
        System.out.println("TODOS OS USER QUE TEM UM BILHETE COM O FLIGHTID");
        List<String> allUsersByFlightID =bt.getTicketsByFlightID("CARLAO6666");
        System.out.println(allUsersByFlightID);

        //gets one ticket from user
        System.out.println("PRIMEIRO BILHETE QUE O USER COMPROU");
        String TicketFromUser =bt.getTicketFromUser("miguel@gmail.com");
        System.out.println(TicketFromUser);

        //gets one ticket by flightID
        System.out.println("PRIMEIRO USER QUE TEM UM BILHETE COM O FLIGHTID");
        String UserByFlightID =bt.getTicketByFlightID("CARLAO6666");
        System.out.println(UserByFlightID);

    }



    public static void testSQL(){

        FlightTable flight = new FlightTable();

        List<String> allFlightResultsFromTo = flight.getFlightsByFromTo("Porto, OPO", "Londres, LGW", "2022-12-24");
        System.out.println((allFlightResultsFromTo));


        /*List<String> allFlightResultsID = flight.getFlightsByFlightID("LA420");
        System.out.println(allFlightResultsID);

        /*List<String> allFlightResultsFrom = flight.getFlightsByFrom("Londres, LGW");
        System.out.println(allFlightResultsFrom);
        List<String> allFlightResultsTo = flight.getFlightsByTo("Londres, LGW");
        System.out.println(allFlightResultsTo);*/

        /*if (flight.updateClassCapacityByFlightID("FirstClass", "123", "LA420")) {
            System.out.println("updateClassCapacityByFlightID success.");
        } else System.out.println("updateClassCapacityByFlightID failure.");*/

        /*if (flight.addFlight("Porto, OPO", "Madrid, MAD", "2022-12-24", "2022-12-24", "06:20:00", "08:20:00",
                "120", "DifficultJet", "DJ2002", "120", "20", "4", "90", "150", "210")){
            System.out.println("addFlight success.");
        }
        else System.out.println("addFlight failure.");*/

        /*if(flight.removeFlightByFlightID("TP 1334")){
            System.out.println("removeFlightByFlightID success.");
        }
        else System.out.println("removeFlightByFlightID failure.");*/


        //UserTable user = new UserTable();

        /*if(user.addUser("carlao@loganmail.com", "Carlota")){
            System.out.println("addUser success.");
        }
        else System.out.println("addUser failure.");*/

        /*if(user.removeUserByEmail("PedroSilva@pedrosilva.com")){
            System.out.println("removeUserByEmail success.");
        }
        else System.out.println("removeUserByEmail failure.");*/

        //String userResultEmail = user.getUserByEmail("PedroSilva@pedrosilva.com");
        //System.out.println(userResultEmail);

        /*if(user.updateEmail("pedro moment", "XxX_pedroSilva_XxX")) {
            System.out.println("updateEmail success.");
        }
        else System.out.println("updateEmail failure.");*/

        /*if(user.updatePassword("XxX_pedroSilva_XxX", "conseguimos")) {
            System.out.println("updatePassword success.");
        }
        else System.out.println("updatePassword failure.");*/

        /*AirportDistanceTable airportDistance = new AirportDistanceTable();
        String distance = airportDistance.getAirportDistanceByFromTo("Porto, OPO", "Londres, LGW");
        System.out.println(distance);*/
        /*if(airportDistance.addAirportDistance("Porto, OPO", "Paris, CDG", "1213")){
            System.out.println("addAirportDistance success");
        }
        else System.out.println("addAirportDistance success");*/

        /*if(airportDistance.removeAirportDistance("Porto, OPO", "Londres, LGW")){
            System.out.println("removeAirportDistance success");
        }
        else System.out.println("removeAirportDistance success");*/


    }
}
