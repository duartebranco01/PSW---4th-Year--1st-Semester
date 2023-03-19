module gui.flight_booking {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;


    opens GUI to javafx.fxml;
    exports GUI;
}