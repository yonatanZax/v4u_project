package View;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginView extends Observable implements Initializable {

    // Static Args
    public static final String LOGINVIEW_CHECKLOGIN = "CheckLogin";
    public static final String LOGINVIEW_SIGNIN = "SignIn";


    public Button cancel;
    @FXML
    private TextField userId;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorMessage;

    public void resetLoginCredentials() {
        userId.setText("");
        password.setText("");
    }


    @FXML
    protected void processLogin() {
        setChanged();
        notifyObservers(LOGINVIEW_CHECKLOGIN);
    }


    public void CreateAccount() {
        setChanged();
        notifyObservers(LOGINVIEW_SIGNIN);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void closeWindow() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }


    public String getUserId() {
        return userId.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public void startLoginProcessAgain() {
        setErrorMessageVisble(false);
    }

    public void setErrorMessageVisble(boolean isVisible) {
        errorMessage.setVisible(isVisible);
    }

    public void CancelLogIn(ActionEvent actionEvent) {
        closeWindow();
    }
}
