package View;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import Model.UserModel;
import db.DBResult;
import Model.User;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserCRUDView extends Observable implements Initializable {

    public Button readUser_btn;
    public Button createUser_btn;
    public Button updateUser_btn;
    public Button deleteUser_btn;
    public TextField userName;

    public Label error_lbl;
    public Label info_lbl;

    public static final String info_lblTitle = "Info from DB:\n";



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void notifyController(String arg){
        System.out.println("UserCRUDView: "+arg);
        setChanged();
        notifyObservers(arg);
    }

    public void readUser(ActionEvent actionEvent) {
        notifyController("readUser");
    }

    public void createUser(ActionEvent actionEvent) {
        notifyController("createUser");
    }

    public void updateUser(ActionEvent actionEvent) {
        notifyController("updateUser");
    }

    public void deleteUser(ActionEvent actionEvent) {
        notifyController("deleteUser");
    }
    //TODO - make this class the view of the main window
}
