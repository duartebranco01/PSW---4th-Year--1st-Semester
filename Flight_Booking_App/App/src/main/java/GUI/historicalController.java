package GUI;

import Logic.Account;
import Logic.Filters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class historicalController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label airports;

    @FXML
    private Label date_class;

    @FXML
    private Label passengers;

    @FXML
    private Label price;

    @FXML
    private Label time;

    public void setData(String departureAirport, String arrivalAirport, LocalTime departureHour, LocalTime arrivalHour, LocalDate date){
        airports.setText(departureAirport + " - " + arrivalAirport);
        time.setText(departureHour.toString());
        date_class.setText(date.getDayOfMonth() + "/" + date.getMonthValue());
    }

    public void switchToSearchPage(ActionEvent event) throws IOException {
        String dep[] = airports.getText().split(" - ");
        HelloController.departure = dep[0];
        HelloController.arrival = dep[1];

        HelloController.num_pass=null;
        HelloController.plane_class = null;
        HelloController.date_departure=null;
        HelloController.date_arrival=null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root = loader.load();

        HelloController hellocontroller = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
