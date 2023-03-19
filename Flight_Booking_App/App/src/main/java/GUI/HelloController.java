package GUI;


import Logic.Account;
import Logic.Filters;
import Logic.Flight;
import Logic.Logic;
import Logic.GUI_Logic;
import SQL.FlightTable;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import org.controlsfx.control.textfield.TextFields;


public class HelloController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static String departure, arrival, num_pass, plane_class;
    public static LocalDate date_departure, date_arrival;
    @FXML
    private TextField acDeparture, acArrival, n_pass;
    @FXML
    private Label param_error, lab_arrival, invalid_parameters, city1Label, date1Label, price1, city2Label, date2Label;
    @FXML
    private DatePicker dep_date, arr_date;
    @FXML
    private RadioButton rRound, rOne;
    @FXML
    private ComboBox<String> class_selector;
    @FXML
    private Button login_button;

    @FXML
    private ImageView image1, image2;

    private String[] classes = {"Economy", "Business", "First Class"};


    public void initialize(URL arg0, ResourceBundle arg1){
        GUI_Logic.bestDeals();

        displayBestDeals();

        if(class_selector!=null) class_selector.getItems().addAll(classes);

        if(!Filters.isRoundTrip) {
            rRound.setSelected(false);
            rOne.setSelected(true);
            arr_date.setVisible(false);
            lab_arrival.setVisible(false);
        }


        if(!Account.isLoggedIn) login_button.setText("LOGIN");
        if(Account.isLoggedIn) login_button.setText(Account.username);
        if(departure!=null || !("".equals(departure))) acDeparture.setText(departure);
        if(arrival!=null || !("".equals(arrival))) acArrival.setText(arrival);
        if(num_pass!=null || !("".equals(num_pass))) n_pass.setText(num_pass);
        if(plane_class!=null || !("".equals(plane_class))) class_selector.setValue(plane_class);
        if(date_arrival!=null) arr_date.setValue(date_arrival);
        if(date_departure!=null) dep_date.setValue(date_departure);

        arr_date.setShowWeekNumbers(false);
        dep_date.setShowWeekNumbers(false);

    }


    public void switchToLogin(ActionEvent event) throws IOException {
        //guardar valores
        saveValues();

        if(!Account.isLoggedIn) {
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

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void SearchFlights(ActionEvent event) throws IOException {
        //guardar valores
        saveValues();

        if (Filters.isRoundTrip) {
            if ("".equals(departure) || "".equals(arrival) || "".equals(num_pass) || "".equals(plane_class) || plane_class == null || date_departure == null || date_arrival == null) {
                param_error.setVisible(true);
                invalid_parameters.setVisible(false);
            }else {
                if(checkSearchParameters()) {
                    param_error.setVisible(false);
                    invalid_parameters.setVisible(false);
                    //mandar dados
                    sendValues();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("search_page.fxml"));
                    root = loader.load();
                    searchController searchcontroller = loader.getController();
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else{
                    param_error.setVisible(false);
                    invalid_parameters.setVisible(true);
                }
            }
        } else if (!Filters.isRoundTrip) {
            if ("".equals(departure) || "".equals(arrival) || "".equals(num_pass) || date_departure == null || "".equals(plane_class) || plane_class == null){
                param_error.setVisible(true);
                invalid_parameters.setVisible(false);
            }else {
                if(checkSearchParameters()) {
                    param_error.setVisible(false);
                    invalid_parameters.setVisible(false);

                    //mandar dados
                    sendValues();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("search_page.fxml"));
                    root = loader.load();
                    searchController searchcontroller = loader.getController();
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    param_error.setVisible(false);
                    invalid_parameters.setVisible(true);
                }
            }
        }
    }

    public void tripWay(ActionEvent event)  {
        if(rRound.isSelected()){
            arr_date.setVisible(true);
            lab_arrival.setVisible(true);
            Filters.isRoundTrip=true;
        } else if(rOne.isSelected()){
            arr_date.setVisible(false);
            lab_arrival.setVisible(false);
            Filters.isRoundTrip=false;
        }
    }

    public void autocomplete() throws IOException {
        FlightTable flight = new FlightTable();

        HashSet<String> airports = flight.listAirports();

        TextFields.bindAutoCompletion(acDeparture, airports);
        TextFields.bindAutoCompletion(acArrival, airports);
    }

    public void saveValues()  {
        departure=acDeparture.getText();
        arrival=acArrival.getText();
        num_pass = n_pass.getText();
        plane_class =  class_selector.getValue();
        date_departure = dep_date.getValue();
        date_arrival = arr_date.getValue();
    }

    public void sendValues()  {
        Filters.departureAP = departure;
        Filters.arrivalAP = arrival;
        Filters.departureDate = date_departure.toString();
        if(Filters.isRoundTrip)  Filters.returnDate = date_arrival.toString();
        if(!Filters.isRoundTrip) Filters.returnDate = null;
        Filters.passengerNo = num_pass;
        Filters.flightClass = plane_class;
    }
    public static LocalDate today = LocalDate.now() ;
    public boolean checkSearchParameters(){
        //int pass_numb = Integer.parseInt(num_pass);
        int pass_numb=0;
        try {
            pass_numb = Integer.parseInt(num_pass);
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormat Exception: invalid input string");
        }

        if(Filters.isRoundTrip) {
            if(date_arrival.isBefore(date_departure)) return false;
        }
        if (departure.equals(arrival)) return false;
        if (date_departure.isBefore(today)) return false;
        if(pass_numb > 10 || pass_numb < 1) return false;

        FlightTable flight = new FlightTable();

        HashSet<String> airports = flight.listAirports();

        if(!airports.contains(departure)) return false;
        if(!airports.contains(arrival)) return false;

        return true;
    }



    public void displayBestDeals()  {
        String dep[] = GUI_Logic.City1.split(",");
        String cidade1 = dep[0];
        city1Label.setText("Porto - " + cidade1);
        date1Label.setText(GUI_Logic.depDate.getDayOfMonth() + "/" + GUI_Logic.depDate.getMonthValue());
        price1.setText(GUI_Logic.price.toString() + "€");

        Image city = null;
        if(GUI_Logic.City1 == "Londres, LGW") {
            city = new Image(Objects.requireNonNull(getClass().getResource("/GUI/images/londres.png")).toString());
        }
        if(GUI_Logic.City1 == "Paris, CDG") {
            city = new Image(Objects.requireNonNull(getClass().getResource("/GUI/images/paris.png")).toString());
        }
        image1.setImage(city);


        String dep2[] = GUI_Logic.City2.split(",");
        String cidade2 = dep2[0];
        city2Label.setText("Porto - " + cidade2);

        date2Label.setText(GUI_Logic.depDate2.getDayOfMonth() + "/" + GUI_Logic.depDate2.getMonthValue());

        Image city2 = null;
        if(GUI_Logic.City2 == "Milan, MXP") {
            city2 = new Image(Objects.requireNonNull(getClass().getResource("/GUI/images/milao.png")).toString());
        }
        if(GUI_Logic.City2 == "Barcelona, BCN") {
            city2 = new Image(Objects.requireNonNull(getClass().getResource("/GUI/images/barcelona.png")).toString());
        }
        image2.setImage(city2);

    }

    public void buyBestDealsleftSide(ActionEvent event) throws IOException {
        acArrival.setText(GUI_Logic.City1);
        acDeparture.setText("Porto, OPO");
        class_selector.setValue("Economy");
        n_pass.setText("1");
        rOne.setSelected(true);
        rRound.setSelected(false);
        arr_date.setVisible(false);
        lab_arrival.setVisible(false);
        Filters.isRoundTrip=false;
        dep_date.setValue(GUI_Logic.depDate);
    }

    public void buyBestDealsRightSide(ActionEvent event) throws IOException {
        acArrival.setText(GUI_Logic.City2);
        acDeparture.setText("Porto, OPO");
        class_selector.setValue("Economy");
        n_pass.setText("1");
        rOne.setSelected(true);
        rRound.setSelected(false);
        arr_date.setVisible(false);
        lab_arrival.setVisible(false);
        Filters.isRoundTrip=false;
        dep_date.setValue(GUI_Logic.depDate2);
    }

}