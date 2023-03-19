package GUI;

import Logic.Account;
import Logic.Filters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class itemsOWController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Button add_to_cart;

    @FXML
    private Label arrival_airport1;

    @FXML
    private Label arrival_time1;

    @FXML
    private Label departure_airline1;

    @FXML
    private Label departure_airport1;

    @FXML
    private Label departure_time1;

    @FXML
    private Label flight_time1, error;

    @FXML
    private Label price, flightID, escalas;

    public void initialize(URL arg0, ResourceBundle arg1) {
        if(Filters.isRoundTrip && searchController.isFirtsPage) add_to_cart.setText("Select");
        else if(Filters.isRoundTrip && !searchController.isFirtsPage) add_to_cart.setText("Buy Tickets");
        else add_to_cart.setText("Buy Ticket");
    }


    public void setData(String airlines, String departure_airport, String arrival_airport, String departure_time, String arrival_time, String duration, String Price, String FlightIDs, String Stops){
        departure_airline1.setText(airlines);
        departure_airport1.setText(departure_airport);
        arrival_airport1.setText(arrival_airport);
        departure_time1.setText(departure_time);
        arrival_time1.setText(arrival_time);
        flight_time1.setText(duration+" min");
        price.setText(Price+"€");
        flightID.setText(FlightIDs);
        escalas.setText(Stops);
    }

    public void switchToPayment(ActionEvent event) throws IOException {

        if((!Filters.isRoundTrip || (Filters.isRoundTrip && !searchController.isFirtsPage)) && Account.isLoggedIn) {
            if (!Filters.isRoundTrip) saveFirstFlight();
            else saveSecondFlight();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("payment_page.fxml"));
            root = loader.load();

            paymentController paymentcontroller = loader.getController();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(Filters.isRoundTrip && searchController.isFirtsPage){
            saveFirstFlight();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("return_page.fxml"));
            root = loader.load();

            returnController returncontroller = loader.getController();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            error.setVisible(true);
        }
    }

    public void saveFirstFlight(){
        paymentController.Price_final = price.getText();
        paymentController.flightID = flightID.getText();
        paymentController.departure_airport=departure_airport1.getText();
        paymentController.arrival_airport=arrival_airport1.getText();
        paymentController.depHour=departure_time1.getText();
        paymentController.arrHour=arrival_time1.getText();
    }

    public void saveSecondFlight(){
        paymentController.Price_final2 = price.getText();
        paymentController.flightID2 = flightID.getText();
        paymentController.departure_airport2 = departure_airport1.getText();
        paymentController.arrival_airport2 = arrival_airport1.getText();
        paymentController.depHour2 =departure_time1.getText();
        paymentController.arrHour2 = arrival_time1.getText();
    }
}