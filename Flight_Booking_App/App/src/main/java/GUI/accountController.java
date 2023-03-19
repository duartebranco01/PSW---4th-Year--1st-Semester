package GUI;


import Logic.Account;
import Logic.Logic;
import Logic.Flight;
import Logic.Filters;
import SQL.BoughtTicketTable;
import SQL.FlightTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class accountController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label user_tag;
    @FXML
    private VBox historicalVbox;

    @FXML
    private Button delete_acc_button, change_pass_button;

    public void initialize(URL arg0, ResourceBundle arg1){
        if(Account.isLoggedIn == false) user_tag.setText("LOGIN");
        if(Account.isLoggedIn == true) user_tag.setText(Account.username);

        List<String> flightIds;
        BoughtTicketTable flightId = new BoughtTicketTable();
        flightIds =  flightId.getTicketsFromUser(Account.email);

        FlightTable flights = new FlightTable();
        Logic logic = new Logic();



        for(int i=0; i< flightIds.size(); i++)
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("historical_item.fxml"));

            Flight currFlight = new Flight(flights.getFlightsByFlightID(flightIds.get(i)));

            try {
                HBox hBox = fxmlLoader.load();
                historicalController items = fxmlLoader.getController();


                items.setData(currFlight.getDepartureAirport(), currFlight.getArrivalAirport(), currFlight.getDepartureTime(), currFlight.getArrivalTime(), currFlight.getDepartureDate());

                historicalVbox.getChildren().add(hBox);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    public void deleteAccount(ActionEvent event) throws IOException {
        Account.deleteAccount(Account.email);

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

    public void switchToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root = loader.load();

        HelloController helloController = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPassword(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("password_page.fxml"));
        root = loader.load();

        passwordController passwordcontroller = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
