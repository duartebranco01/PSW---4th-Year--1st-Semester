package Logic;

import GUI.searchController;

import java.util.*;

import static java.util.Collections.sort;

public class Filters {
    public static String departureAP, arrivalAP, departureDate, returnDate, flightClass, passengerNo;
    public static boolean isRoundTrip=true, isDirect=false;
    public ArrayList<Route> filterRoutes(String[] airlinesSelected, Integer[] nLayoversSelected){

        /*Logic logic = new Logic();
        ArrayList<Route> routesList = new ArrayList<Route>();

        if(searchController.isFirtsPage) routesList = logic.calculateRoutes(this.departureAP, this.arrivalAP, this.departureDate, this.flightClass, this.passengerNo , this.isDirect);
        else routesList = logic.calculateRoutes(this.arrivalAP, this.departureAP, this.returnDate, this.flightClass, this.passengerNo , this.isDirect);

        if(routesList==null) return null;*/

        ArrayList<Route> filteredRoutesList = new ArrayList<Route>(searchController.routesList);

        //HashSet<String> airlinesList = listAirlines(routesList);
        boolean keep = false;


        if(airlinesSelected.length < searchController.airlinesList.size() || nLayoversSelected.length < 3){
            for (int i = 0; i < searchController.routesList.size(); i++){
                Route currRoute = searchController.routesList.get(i);

                if(airlinesSelected.length < searchController.airlinesList.size()){
                    for (int j = 0; j < searchController.routesList.get(i).flights.size(); j++) {
                        Flight currFlight = searchController.routesList.get(i).flights.get(j);
                        if(Arrays.stream(airlinesSelected).toList().contains(currFlight.airlineName)){
                            keep = true;
                        }else{
                            keep = false;
                            break;
                        }
                    } if(!keep) filteredRoutesList.remove(currRoute);
                }else if(airlinesSelected.length == 0){
                    filteredRoutesList = null;
                }

                if(nLayoversSelected.length < 3){
                    if(Arrays.stream(nLayoversSelected).toList().contains(searchController.routesList.get(i).flights.size() - 1)){
                        keep = true;
                    }else keep = false;
                    if(!keep) filteredRoutesList.remove(currRoute);
                }else if(nLayoversSelected.length == 0){
                    filteredRoutesList = null;
                }
            }
        }


        //retira as airlines nao selecionadas
        /*if(airlinesSelected.length < airlinesList.size()){
            for (int i = 0; i < routesList.size(); i++) {
                Route currRoute = routesList.get(i);

                for (int j = 0; j < routesList.get(i).flights.size(); j++) {
                    Flight currFlight = routesList.get(i).flights.get(j);
                    if(Arrays.stream(airlinesSelected).toList().contains(currFlight.airlineName)){
                        keep = true;
                    }else{
                        keep = false;
                        break;
                    }
                }
                if(!keep) filteredRoutesList.remove(currRoute);
            }
        }else if(airlinesSelected.length == 0){
            filteredRoutesList = null;
        }
        
        keep = false;

        //retirar n layovers
        if(nLayoversSelected.length < 3){
            for (int i = 0; i < routesList.size(); i++) {
                Route currRoute = routesList.get(i);
                if(Arrays.stream(nLayoversSelected).toList().contains(routesList.get(i).flights.size() - 1)){
                    keep = true;
                }else keep = false;
                if(!keep) filteredRoutesList.remove(currRoute);
            }
        }else if(nLayoversSelected.length == 0){
            filteredRoutesList = null;
        }*/

        return filteredRoutesList;
    }

    public ArrayList<Route> fastestOption(ArrayList<Route> sortedRoutesList){
        Collections.sort(sortedRoutesList, new fastestComparator());
        return sortedRoutesList;
    }

    public ArrayList<Route> cheapestOption(ArrayList<Route> sortedRoutesList){
        Collections.sort(sortedRoutesList, new cheapestComparator());
        return sortedRoutesList;
    }

    public ArrayList<Route> bestOption(ArrayList<Route> sortedRoutesList){
        Collections.sort(sortedRoutesList, new bestComparator());
        return sortedRoutesList;
    }


    class fastestComparator implements Comparator<Route>{
        public int compare(Route f1, Route f2) {
            return f1.totalTime - f2.totalTime;
        }
    };
    class cheapestComparator implements Comparator<Route> {
        public int compare(Route f1, Route f2) {

            if(flightClass == "Economy"){
                return f1.totalPriceEconomy - f2.totalPriceEconomy;
            }
            if(flightClass == "Business"){
                return f1.totalPriceBusiness - f2.totalPriceBusiness;
            }
            if(flightClass == "First Class"){
                return f1.totalPriceFirstClass - f2.totalPriceFirstClass;
            }
            return 0;
        }
    }

    class bestComparator implements Comparator<Route>{
        public int compare(Route f1, Route f2) {
            if(flightClass == "Economy"){
                return Float.compare((float)f1.totalPriceEconomy/f1.totalTime, (float) f2.totalPriceEconomy/f2.totalTime);
            }
            if(flightClass == "Business"){
                return Float.compare((float)f1.totalPriceBusiness/f1.totalTime, (float) f2.totalPriceBusiness/f2.totalTime);
            }
            if(flightClass == "First Class"){
                return Float.compare((float) f1.totalPriceFirstClass/f1.totalTime, (float) f2.totalPriceFirstClass/f2.totalTime);
            }
            return 0;
        }
    };



    public HashSet<String> listAirlines(ArrayList<Route> routesList){
        HashSet<String> airlines = new HashSet<String>();
        for (int i = 0; i < routesList.size(); i++) {
            for (int j = 0; j < routesList.get(i).flights.size(); j++) {
                airlines.add(routesList.get(i).flights.get(j).getAirlineName());
            }
        }
        return airlines;
    }
}
