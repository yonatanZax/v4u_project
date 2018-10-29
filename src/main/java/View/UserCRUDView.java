package View;

import Controllers.ControllerCRUD;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

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
    //TODO (DONE!) - make this class the view of the main window
}
