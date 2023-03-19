package Logic;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Flight {
    //fazer private no futuro, so vai ser preciso getters
    protected String departureAirport;
    protected String arrivalAirport;
    protected LocalDate departureDate;
    protected LocalDate arrivalDate;
    protected LocalTime departureTime;
    protected LocalTime arrivalTime;
    protected int flightDurationMin;
    protected String airlineName;
    protected String flightID;
    protected int economyCapacity;
    protected int businessCapacity;
    protected int firstClassCapacity;
    protected int economyPrice;
    protected int businessPrice;
    protected int firstClassPrice;


    public Flight (String inFlight){

        if(inFlight==null) return;
        String[] elements=inFlight.split(";");

        this.departureAirport = elements[0];
        this.arrivalAirport = elements[1];
        this.departureDate = LocalDate.parse(elements[2], DateTimeFormatter.ISO_LOCAL_DATE);
        this.arrivalDate = LocalDate.parse(elements[3], DateTimeFormatter.ISO_LOCAL_DATE);
        this.departureTime = LocalTime.parse(elements[4], DateTimeFormatter.ISO_LOCAL_TIME);
        this.arrivalTime = LocalTime.parse(elements[5], DateTimeFormatter.ISO_LOCAL_TIME);
        this.flightDurationMin = Integer.parseInt(elements[6]);
        this.airlineName = elements[7];
        this.flightID = elements[8];
        this.economyCapacity = Integer.parseInt(elements[9]);
        this.businessCapacity = Integer.parseInt(elements[10]);
        this.firstClassCapacity = Integer.parseInt(elements[11]);
        this.economyPrice = Integer.parseInt(elements[12]);
        this.businessPrice = Integer.parseInt(elements[13]);
        this.firstClassPrice = Integer.parseInt(elements[14]);
    }

    public Flight(String departureAirport, String arrivalAirport, LocalDate departureDate, LocalDate arrivalDate, LocalTime departureTime, LocalTime arrivalTime, int flightDurationMin, String airlineName, String flightID, int economyCapacity, int businessCapacity, int firstClassCapacity, int economyPrice, int businessPrice, int firstClassPrice) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightDurationMin = flightDurationMin;
        this.airlineName = airlineName;
        this.flightID = flightID;
        this.economyCapacity = economyCapacity;
        this.businessCapacity = businessCapacity;
        this.firstClassCapacity = firstClassCapacity;
        this.economyPrice = economyPrice;
        this.businessPrice = businessPrice;
        this.firstClassPrice = firstClassPrice;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }
    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public int getFlightDurationMin() {
        return flightDurationMin;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public String getFlightID() {
        return flightID;
    }

    public int getEconomyCapacity() {
        return economyCapacity;
    }

    public int getBusinessCapacity() {
        return businessCapacity;
    }

    public int getFirstClassCapacity() {
        return firstClassCapacity;
    }
    public int getEconomyPrice() {
        return economyPrice;
    }

    public int getBusinessPrice() {
        return businessPrice;
    }

    public int getFirstClassPrice() {
        return firstClassPrice;
    }
}
