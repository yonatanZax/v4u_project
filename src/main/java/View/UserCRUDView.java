package View;

import Controllers.ControllerCRUD;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import MainPackage.Enum_CRUD;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class UserCRUDView extends Observable implements Initializable {

    public Button readUser_btn;
    public Button createUser_btn;
    public Button updateUser_btn;
    public Button deleteUser_btn;
    public TextField userName;
    public Label status_lbl;
    public Label info_lbl;

        public static final String info_lblTitle = "Info from DB:\n";



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void notifyController(Enum_CRUD arg) {
        System.out.println("UserCRUDView: " + arg);
        setChanged();
        notifyObservers(arg);
    }

    public void readUser() {
        notifyController(Enum_CRUD.READ);
    }

    public void resetLabels() {
        info_lbl.setText(info_lblTitle + "");
        status_lbl.setText("");
    }

    public void createUser() {
        notifyController(Enum_CRUD.CREATE);
    }

    public void updateUser() {
        notifyController(Enum_CRUD.UPDATE);
    }

    public void deleteUser() {
        notifyController(Enum_CRUD.DELETE);
    }
}