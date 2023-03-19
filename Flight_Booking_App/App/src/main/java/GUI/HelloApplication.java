package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(root);
            //scene.getStylesheets().getClass().getResource("design.css").toExternalForm());
            stage.setScene(scene);
            //Image icon = new Image("images/gradient.png");
            Image icon = new Image(Objects.requireNonNull(getClass().getResource("/GUI/images/LOGOTIPO_LOGAN.png")).toString());
            stage.getIcons().add(icon);
            stage.setTitle("LOGAN AIR");
            stage.show();

            // Font IBMPlexSans_r;
            //IBMPlexSans_r= Font.createFont(Font.TRUETYPE_FONT, new File("IBMPlexSans-Regular.ttf"));
            // nao aceita o create font????????????

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void startGUI() { launch(); }

}