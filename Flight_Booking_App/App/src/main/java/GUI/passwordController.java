package GUI;


import Logic.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class passwordController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private PasswordField new_pass, repeat_pass;
    @FXML
    private Label acc_err, pass_err, acc_suc, mail_err, err_mail_pass, param_err;

    public void switchToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root = loader.load();

        HelloController helloController = loader.getController();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changepassword(ActionEvent event) throws IOException {
        String first_pass=new_pass.getText();
        String second_pass = repeat_pass.getText();

        //verifica se estão as duas labels preenchidas
       if((!("".equals(first_pass))) && (!("".equals(second_pass)))) {
            //verifica se as passes são iguais nas duas labels
            if(first_pass.equals(second_pass)) {
                //Account muda a pass
                Account.changePassword(Account.email, new_pass.getText());

                param_err.setVisible(false);
                pass_err.setVisible(false);
                acc_suc.setVisible(true);

                //muda para a página account_page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("account_page.fxml")); //mudar nome
                root = loader.load();

                accountController accountcontroller = loader.getController(); //mudar controlador

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else{
                pass_err.setVisible(true);
                param_err.setVisible(false);
                acc_suc.setVisible(false);
            }
        } else {
            param_err.setVisible(true);
            pass_err.setVisible(false);
            acc_suc.setVisible(false);
        }
    }

    public void switchToAccount(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("account_page.fxml")); //mudar nome
        root = loader.load();

        accountController accountcontroller = loader.getController(); //mudar controlador

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
