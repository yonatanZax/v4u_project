package View;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import Controllers.ControllerUserCRUD;
import Model.User.UserModel;
import db.Tables.UserTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;

public class LoginView extends Observable implements Initializable {
    public Button cancel;
    @FXML
    private TextField userId;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorMessage;

    @FXML
    protected void processLogin() {

        // Todo - implement in ControllerLogin - DONE
        // Todo - add Create user to "Login" scene
        setChanged();
        notifyObservers("CheckLogin");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userId.setText("user1");
        password.setText("p");
    }

    public void closeWindow(){
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }


    public String getUserId() {
        return userId.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public void startLoginProcessAgain(InputMethodEvent inputMethodEvent) {
        setErrorMessageVisble(false);
    }

    public void setErrorMessageVisble(boolean isVisible){
        errorMessage.setVisible(isVisible);
    }

    public void CancelLogIn(ActionEvent actionEvent) {
        closeWindow();
    }
}
