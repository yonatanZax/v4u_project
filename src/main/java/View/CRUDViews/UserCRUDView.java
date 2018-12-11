package View.CRUDViews;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.VacationSearchController;
import MainPackage.Enum_CRUD;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class UserCRUDView extends ACRUDView {

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


    public void resetLabels() {
        info_lbl.setText(info_lblTitle + "");
        status_lbl.setText("");
    }


    public void search(ActionEvent event) {
        setChanged();
        notifyObservers("Search");
    }
}
