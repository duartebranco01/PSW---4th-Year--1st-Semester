package GUI;

import Logic.Filters;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class purchaseRTController {

    @FXML
    private Label airports, date_class, price, time, number;

    @FXML
    private Label airports2, date_class2, price2, time2, number2;


    public void setData(){

        price.setText(paymentController.Price_final);
        airports.setText(paymentController.departure_airport + " - " + paymentController.arrival_airport);
        time.setText(paymentController.depHour + " - " + paymentController.arrHour);

        date_class.setText(HelloController.date_departure.getDayOfMonth() + "/" + HelloController.date_departure.getMonthValue() + "  " + Filters.flightClass);
        number.setText(Filters.passengerNo);

        price2.setText(paymentController.Price_final2);
        airports2.setText(paymentController.departure_airport2 + " - " + paymentController.arrival_airport2);
        time2.setText(paymentController.depHour2 + " - " + paymentController.arrHour2);

        date_class2.setText(HelloController.date_arrival.getDayOfMonth() + "/" + HelloController.date_arrival.getMonthValue() + "  " + Filters.flightClass);
        number2.setText(Filters.passengerNo);
    }

}
