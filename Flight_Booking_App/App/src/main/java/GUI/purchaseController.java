package GUI;

import Logic.Filters;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class purchaseController {

    @FXML
    private Label airports, date_class, price, time, passengers;



    public void setData(){

        price.setText(paymentController.Price_final);
        airports.setText(paymentController.departure_airport + " - " + paymentController.arrival_airport);
        time.setText(paymentController.depHour + " - " + paymentController.arrHour);

        date_class.setText(HelloController.date_departure.getDayOfMonth() + "/" + HelloController.date_departure.getMonthValue() + "  " + Filters.flightClass);
        passengers.setText(Filters.passengerNo);
    }

}
