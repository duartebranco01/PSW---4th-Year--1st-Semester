package GUI;

import Logic.Flight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

public class itemsController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button add_to_cart;

    @FXML
    private Label arrival_airport1;

    @FXML
    private Label arrival_airport2;

    @FXML
    private Label arrival_time1;

    @FXML
    private Label arrival_time2;

    @FXML
    public Label departure_airline1;

    @FXML
    private Label departure_airline2;

    @FXML
    private Label departure_airport1;

    @FXML
    private Label departure_airport2;

    @FXML
    private Label departure_time1;

    @FXML
    private Label departure_time2;

    @FXML
    private Label flight_time1;

    @FXML
    private Label flight_time2;

    @FXML
    private Label price, flightID;


        public void setData(String airline1, String departure_airport, String arrival_airport, String departure_time, String arrival_time, String duration, String Price, String FlightID, String airline2, String Departure_airport, String Arrival_airport, String Departure_time, String Arrival_time, String Flight_time){
            departure_airline1.setText(airline1);
            departure_airport1.setText(departure_airport);
            arrival_airport1.setText(arrival_airport);
            departure_time1.setText(departure_time);
            arrival_time1.setText(arrival_time);
            flight_time1.setText(duration+" min");
            price.setText(Price+"€");
            flightID.setText(FlightID);

            departure_airline2.setText(airline2);
            departure_airport2.setText(Departure_airport);
            arrival_airport2.setText(Arrival_airport);

            departure_time2.setText(Departure_time);
            arrival_time2.setText(Arrival_time);
            flight_time2.setText(Flight_time+" min");
        }



    /*public void setData(ArrayList<Flight> route){

        if(route==null) return;
        Iterator<Flight> it = route.listIterator();

        while (it.hasNext()) {
            Flight currFlight = it.next();
            airline1.setText(currFlight.getAirlineName());
        }

        airline1.setText(curr);
        airline2.setText();
        airport1.setText();
        airport2.setText();
        airport3.setText();
        airport4.setText();
        hour1.setText();
        hour2.setText();
        price.setText("€");
        time1.setText();
        time2.setText();
        time3.setText();
        time4.setText();
    }*/


    public void switchToPayment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("payment_page.fxml"));
        root = loader.load();

        paymentController paymentcontroller = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
