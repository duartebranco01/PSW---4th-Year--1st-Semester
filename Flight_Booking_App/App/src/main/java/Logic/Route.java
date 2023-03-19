package Logic;

import java.util.ArrayList;

public class Route {

    public ArrayList<Flight> flights;
    public int totalPriceEconomy;
    public int totalPriceBusiness;
    public int totalPriceFirstClass;
    public int totalTime;

    public Route() {
        this.flights = new ArrayList<Flight>();
    }
    public Route(ArrayList<Flight> inFlights, int inTotalPriceEconomy, int inTotalPriceBusiness, int inTotalPriceFirstClass, int inTotalTime){
        this.flights = new ArrayList<Flight>();
        this.flights.addAll(inFlights);
        this.totalPriceEconomy=inTotalPriceEconomy;
        this.totalPriceBusiness=inTotalPriceBusiness;
        this.totalPriceFirstClass=inTotalPriceFirstClass;
        this.totalTime =inTotalTime;
    }
}
