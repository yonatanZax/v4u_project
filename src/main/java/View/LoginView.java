package View;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginView implements Initializable {
    @FXML
    private TextField userId;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorMessage;

    @FXML
    protected void processLogin() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userId.setPromptText("user1");
        password.setPromptText("password");
    }
}
