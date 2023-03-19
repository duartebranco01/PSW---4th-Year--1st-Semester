package GUI;


import Logic.Account;
import Logic.Filters;
import Logic.Flight;
import Logic.Payment;
import SQL.BoughtTicketTable;
import SQL.FlightTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class paymentController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean boughByBestDeals;

    @FXML
    private VBox purchaseLayout;
    @FXML
    private ImageView validate_image;
    @FXML
    private Label finalize_error, invalid_card, payment_price, label_depAirport, label_arrAirport, label_depTime, label_arrTime, label_num, label_date, label_class;
    @FXML
    private TextField card_name, card_number;

    public static String Price_final, flightID, departure_airport, arrival_airport, depHour, arrHour;
    public static String Price_final2, flightID2, departure_airport2, arrival_airport2, depHour2, arrHour2;

    public void initialize(URL arg0, ResourceBundle arg1){
        Payment.isCardValid=false;


        if(!Filters.isRoundTrip) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("purchaseOW_item.fxml"));

        try {
            HBox hBox = fxmlLoader.load();
            purchaseController oneWay = fxmlLoader.getController();

            oneWay.setData();

            purchaseLayout.getChildren().add(hBox);

            } catch(IOException e){
                e.printStackTrace();
            }
        } else if(Filters.isRoundTrip){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("purchaseRT_item.fxml"));

            try {
                VBox vBox = fxmlLoader.load();
                purchaseRTController roundTrip = fxmlLoader.getController();

                roundTrip.setData();

                purchaseLayout.getChildren().add(vBox);

            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void switchToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root = loader.load();

        HelloController helloController = loader.getController();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logout(ActionEvent event) throws IOException {
        Account.LogOut();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root = loader.load();

        HelloController helloController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void validate_card(ActionEvent event) throws IOException {
        finalize_error.setVisible(false);
        try {
            if (Payment.validateCard(card_number.getText(), card_name.getText())) {
                validate_image.setVisible(true);
                invalid_card.setVisible(false);
            } else {
                validate_image.setVisible(false);
                invalid_card.setVisible(true);
            }
        }catch(NumberFormatException e) {
            validate_image.setVisible(false);
            invalid_card.setVisible(true);
        }
    }


    public void finalize_purchase(ActionEvent event) throws IOException {
        int startIndex=0, endIndex=0;


        if(Payment.isCardValid){

            BoughtTicketTable b = new BoughtTicketTable();

            while (flightID.indexOf(" ", startIndex) != -1) {

                endIndex = flightID.indexOf(" ", startIndex);
                String currFlightID = flightID.substring(startIndex, endIndex);
                b.addTicketToUser(Account.email, currFlightID);
                startIndex = endIndex + 1;

                updateClassCapacity(currFlightID);
            }

            if (Filters.isRoundTrip) {
                startIndex = 0;
                endIndex = 0;
                while (flightID2.indexOf(" ", startIndex) != -1) {

                    endIndex = flightID2.indexOf(" ", startIndex);
                    String currFlightID2 = flightID2.substring(startIndex, endIndex);
                    b.addTicketToUser(Account.email, currFlightID2);
                    startIndex = endIndex + 1;

                    updateClassCapacity(currFlightID2);
                }
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("final_page.fxml"));
            root = loader.load();

            finalController finalController = loader.getController();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
        else {
            finalize_error.setVisible(true);
        }
    }
    private void updateClassCapacity(String currFlightID) {
        FlightTable flightTable = new FlightTable();
        Flight currFlight = new Flight(flightTable.getFlightsByFlightID(currFlightID));

        if (currFlight == null) {
            return;
        }


        if (Filters.flightClass.equals("Economy")) {
            flightTable.updateClassCapacityByFlightID("Economy", Integer.toString(currFlight.getEconomyCapacity() - Integer.parseInt(Filters.passengerNo)), currFlightID);
        } else if (Filters.flightClass.equals("Business")) {
            flightTable.updateClassCapacityByFlightID("Business", Integer.toString(currFlight.getBusinessCapacity() - Integer.parseInt(Filters.passengerNo)), currFlightID);

        } else if (Filters.flightClass.equals("First Class")) {
            flightTable.updateClassCapacityByFlightID("FirstClass", Integer.toString(currFlight.getFirstClassCapacity() - Integer.parseInt(Filters.passengerNo)), currFlightID);

        }

    }
}
