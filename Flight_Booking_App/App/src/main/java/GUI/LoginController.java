package GUI;


import Logic.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField mail_string, pass_string;
    @FXML
    private Label acc_err, pass_err, acc_suc, mail_err, err_mail_pass;

    public void switchToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root = loader.load();

        HelloController helloController = loader.getController();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void register(ActionEvent event) throws IOException {
        String email = mail_string.getText();
        String pass = pass_string.getText();
        if(validate_email(email) && !("".equals(pass))){
            mail_err.setVisible(false);
            err_mail_pass.setVisible(false);
            acc_err.setVisible(false);
            pass_err.setVisible(false);
            if(Account.createAccount(email, pass)==true) {
                acc_err.setVisible(false);
                mail_err.setVisible(false);
                acc_suc.setVisible(false);
                pass_err.setVisible(false);
                err_mail_pass.setVisible(false);
                acc_suc.setVisible(true); //"Account Created"
                //mudar para a pagina inicial
                FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                root = loader.load();

                HelloController helloController = loader.getController();

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else if(Account.createAccount(email, pass)==false){
                mail_err.setVisible(true);
                acc_err.setVisible(false);
                acc_suc.setVisible(false);
                pass_err.setVisible(false);
                err_mail_pass.setVisible(false);
            }
        } else {
            if(!validate_email(email)) {
                mail_err.setVisible(false);
                err_mail_pass.setVisible(false);
                acc_suc.setVisible(false);
                acc_err.setVisible(true);
                pass_err.setVisible(false);
            } else {
                mail_err.setVisible(false);
                err_mail_pass.setVisible(false);
                acc_suc.setVisible(false);
                pass_err.setVisible(true);
                acc_err.setVisible(false);
            }
        }
    }
    public static boolean validate_email(String mail) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(mail);
        return matcher.find();
    }

    public void login(ActionEvent event) throws IOException {
        String email = mail_string.getText();
        String pass = pass_string.getText();
        if(Account.LogIn(email, pass)==true){
            //guarda o username
            acc_err.setVisible(false);
            mail_err.setVisible(false);
            acc_suc.setVisible(false);
            pass_err.setVisible(false);
            err_mail_pass.setVisible(false);

            //mudar para a pagina inicial
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            root = loader.load();

            HelloController helloController = loader.getController();
            //helloController.displayName(username);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } else if(Account.LogIn(email, pass)==false){
            acc_err.setVisible(false);
            err_mail_pass.setVisible(true);
            //por label a dar erro no login
            mail_err.setVisible(false);
            acc_suc.setVisible(false);
            pass_err.setVisible(false);

            Account.isLoggedIn = false;
        }
    }
}
