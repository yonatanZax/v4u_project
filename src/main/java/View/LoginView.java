package View;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import Controllers.ControllerUserCRUD;
import Model.User.UserModel;
import db.Tables.UserTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginView extends Observable implements Initializable {
    @FXML
    private TextField userId;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorMessage;

    @FXML
    protected void processLogin() {

        // Todo - implement in ControllerLogin
        setChanged();
        notifyObservers("CheckLogin");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userId.setPromptText("user1");
        password.setPromptText("password");
    }


    public String getUserId() {
        return userId.getText();
    }

    public String getPassword() {
        return password.getText();
    }
}
