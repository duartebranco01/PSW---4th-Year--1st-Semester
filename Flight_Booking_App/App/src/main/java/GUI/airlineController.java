package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class airlineController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private RadioButton airlineName;

    //public static String[] airlineList;

    public void setData(String airline){
        airlineName.setText(airline);
    }

    public void RemoveAirline(ActionEvent event) throws IOException {
        String name = airlineName.getText();

        if(!airlineName.isSelected()) {
            for (int a = 0; a < searchController.airlines.length; a++) {
                if (searchController.airlines[a].equals(name)) {
                    List<String> list = new ArrayList<String>(Arrays.asList(searchController.airlines));
                    list.remove(searchController.airlines[a]);
                    searchController.airlines = list.toArray(new String[0]);
                }
            }
        }

        if(airlineName.isSelected()) {
            List<String> list = new ArrayList<String>(Arrays.asList(searchController.airlines));
            list.add(name);
            searchController.airlines = list.toArray(new String[0]);
        }

    }
}
