package GUI;

import Logic.Account;
import Logic.Filters;
import Logic.Flight;
import Logic.Logic;
import Logic.Route;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static GUI.searchController.airlines;

public class returnController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button login_button;
    @FXML
    private Label Dairport, Aairport, calendar, person;
    @FXML
    public static Label login_err;

    @FXML
    private RadioButton zeroStops, oneStop, twoStops;

    @FXML
    private VBox contactsLayout, airlinesLayout;
    public static boolean isCheapest, isFastest, isBestOption;

    private String departure_air, arrival_air;

    HashSet<String> listAirlines;
    //public static String[] airlines;

    public static HashSet<String> airlinesList;
    public static String string;
    ArrayList<Route> results;
    ArrayList<Route> routesList;
    Logic logic;
    Filters filters;
    Integer zero=1, one=1, two=2;
    Integer[] nLayoversSelected = {0,1,2};
    Integer[] n0 = {0}, n01 = {0, 1}, n012 = {0,1,2}, n1 = {1}, n12 = {1,2}, n2 = {2}, n02 = {0,2}, n000 = {};


    public void initialize(URL arg0, ResourceBundle arg1) {
        searchController.isFirtsPage=false;

        if (Account.isLoggedIn == false) login_button.setText("LOGIN");
        if (Account.isLoggedIn == true) login_button.setText(Account.username);
        Aairport.setText(Filters.departureAP);
        Dairport.setText(Filters.arrivalAP);
        if (Filters.isRoundTrip) {
            calendar.setText(HelloController.date_departure.getDayOfMonth() + "/" + HelloController.date_departure.getMonthValue() + " - " + HelloController.date_arrival.getDayOfMonth() + "/" + HelloController.date_arrival.getMonthValue());
        } else {
            calendar.setText(HelloController.date_departure.getDayOfMonth() + "/" + HelloController.date_departure.getMonthValue());
        }
        person.setText(Filters.passengerNo);

        /*String copy = Filters.arrivalAP;
        Filters.arrivalAP=Filters.departureAP;
        Filters.departureAP=copy;*/

        logic = new Logic();
        Logic logic = new Logic();
        //ArrayList<Route> routesList;

        searchController.routesList = logic.calculateRoutes(Filters.arrivalAP, Filters.departureAP, Filters.returnDate, Filters.flightClass, Filters.passengerNo, Filters.isDirect);

        if (searchController.routesList == null) return;

        filters = new Filters();
        //HashSet<String> airlinesList = flight.listAirlines(routesList);
        airlinesList = filters.listAirlines(searchController.routesList);
        listAirlines = airlinesList;
        airlines = listAirlines.toArray(new String[listAirlines.size()]);

        results = filters.filterRoutes(airlines, nLayoversSelected);

        results = filters.cheapestOption(results);
        cheapest();

        addResults();

        for (int a = 0; a < airlines.length; a++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("airline_item.fxml"));

            try {
                HBox hBox = fxmlLoader.load();
                airlineController air = fxmlLoader.getController();
                air.setData(airlines[a]);
                airlinesLayout.getChildren().add(hBox);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void switchToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root = loader.load();

        HelloController helloController = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLogin(ActionEvent event) throws IOException {
        if (Account.isLoggedIn == false) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login_page.fxml"));
            root = loader.load();

            LoginController loginController = loader.getController();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("account_page.fxml")); //mudar nome
            root = loader.load();

            accountController accountcontroller = loader.getController(); //mudar controlador

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void fastestButton(ActionEvent event) throws IOException {

        calculateResults();

        results = filters.fastestOption(results);
        fastest();

        addResults();

    }


    public void cheapestButton(ActionEvent event) throws IOException {

        calculateResults();

        results = filters.cheapestOption(results);
        cheapest();

        addResults();

    }


    public void BestOption(ActionEvent event) throws IOException {


        calculateResults();

        results = filters.bestOption(results);
        bestOption();

        addResults();


    }


    public void applyFilters(ActionEvent event) throws IOException {
        calculateResults();

        if(isCheapest) results = filters.cheapestOption(results);
        if(isFastest) results = filters.fastestOption(results);
        if(isBestOption) results = filters.bestOption(results);

        addResults();

    }

    public void calculateResults(){
        contactsLayout.getChildren().clear();

        results = filters.filterRoutes(airlines, nLayoversSelected);

    }

    public void addResults(){

        for (int i = 0; i < results.size(); i++) {

            StringBuilder stops = new StringBuilder();
            StringBuilder airlines = new StringBuilder();
            StringBuilder flightIDs = new StringBuilder();

            Route currRoute = results.get(i);

            airlines.append(currRoute.flights.get(0).getAirlineName());
            flightIDs.append(currRoute.flights.get(0).getFlightID()+" ");

            if(currRoute.flights.size()>1){

                for (int j = 1; j < currRoute.flights.size(); j++) {

                    Flight currFlight = currRoute.flights.get(j);

                    stops.append(currFlight.getDepartureAirport().substring(currFlight.getDepartureAirport().indexOf(",")+1)+" ");

                    if(airlines.indexOf(currFlight.getAirlineName(), 0)==-1){
                        airlines.append(", "+currFlight.getAirlineName());
                    }

                    flightIDs.append(currFlight.getFlightID()+" ");
                }
                if(airlines.indexOf(currRoute.flights.get(currRoute.flights.size()-1).getAirlineName(), 0)==-1) airlines.append(", "+currRoute.flights.get(currRoute.flights.size()-1).getAirlineName());
            }

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("resultsOW_items.fxml"));

            try {
                HBox hBox = fxmlLoader.load();
                itemsOWController items = fxmlLoader.getController();

                separateNames(currRoute.flights.get(0).getDepartureAirport(), currRoute.flights.get(currRoute.flights.size()-1).getArrivalAirport());


                if (Filters.flightClass == "Economy") {
                    items.setData(airlines.toString(), departure_air, arrival_air, currRoute.flights.get(0).getDepartureTime().toString(), currRoute.flights.get(currRoute.flights.size()-1).getArrivalTime().toString(), Integer.toString(currRoute.totalTime), Integer.toString(currRoute.totalPriceEconomy), flightIDs.toString(), stops.toString());
                }
                else if (Filters.flightClass == "Business") {
                    items.setData(airlines.toString(), departure_air, arrival_air, currRoute.flights.get(0).getDepartureTime().toString(), currRoute.flights.get(currRoute.flights.size()-1).getArrivalTime().toString(), Integer.toString(currRoute.totalTime), Integer.toString(currRoute.totalPriceBusiness), flightIDs.toString(), stops.toString());
                }
                else if (Filters.flightClass == "First Class") {
                    items.setData(airlines.toString(), departure_air, arrival_air, currRoute.flights.get(0).getDepartureTime().toString(), currRoute.flights.get(currRoute.flights.size()-1).getArrivalTime().toString(), Integer.toString(currRoute.totalTime), Integer.toString(currRoute.totalPriceFirstClass), flightIDs.toString(), stops.toString());
                }
                contactsLayout.getChildren().add(hBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cheapest(){
        isCheapest=true;
        isFastest=false;
        isBestOption=false;
    }

    public void fastest(){
        isCheapest=false;
        isFastest=true;
        isBestOption=false;
    }

    public void bestOption(){
        isCheapest=false;
        isFastest=false;
        isBestOption=true;
    }

    public void stops(){
        if(!zeroStops.isSelected() && !oneStop.isSelected() && !twoStops.isSelected()) nLayoversSelected=n000;
        if(zeroStops.isSelected() && !oneStop.isSelected() && !twoStops.isSelected()) nLayoversSelected=n0;
        if(zeroStops.isSelected() && oneStop.isSelected() && !twoStops.isSelected()) nLayoversSelected=n01;
        if(zeroStops.isSelected() && oneStop.isSelected() && twoStops.isSelected()) nLayoversSelected=n012;
        if(!zeroStops.isSelected() && oneStop.isSelected() && !twoStops.isSelected()) nLayoversSelected=n1;
        if(!zeroStops.isSelected() && oneStop.isSelected() && twoStops.isSelected()) nLayoversSelected=n12;
        if(!zeroStops.isSelected() && !oneStop.isSelected() && twoStops.isSelected()) nLayoversSelected=n2;
        if(zeroStops.isSelected() && !oneStop.isSelected() && twoStops.isSelected()) nLayoversSelected=n02;
    }

    public void separateNames(String departure,String arrival){
        String dep[] = departure.split(", ");
        departure_air = dep[1];

        String arr[] = arrival.split(", ");
        arrival_air = arr[1];
    }


}
