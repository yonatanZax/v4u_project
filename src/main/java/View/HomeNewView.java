package View;

import Controllers.ControllerCreateVacation;
import Controllers.ControllerLogin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;

public class HomeNewView extends Observable {


    // Static names
    public static final String HOMEVIEW_AGRS_LOGIN = "Login";
    public static final String HOMEVIEW_AGRS_MESSAGECENER = "MessageCenter";
    public static final String HOMEVIEW_AGRS_GOBACK = "GoBack";


    @FXML
    SubScene sub_scene;

    @FXML
    Button login_btn;

    @FXML
    ImageView message_iv;

    @FXML
    Label login_status_lbl;


    @FXML
    public void initialize() {
        setLoginStatusLabel(null);
    }

    public void setSub_scene(Parent sub_scene) {
        this.sub_scene.setRoot(sub_scene);
    }

    /**
     * changes the login status in the home view
     * if userName is null: it means we are doing logout
     * if userName isn't null: it means we successfully logged in
     * @param newLabel the name of the new user or null it it's logout
     */
    public void setLoginStatusLabel(String newLabel){
        if (newLabel == null){
            login_btn.setText("Login");
            login_status_lbl.setText("Not Signed");
        }
        else {
            login_btn.setText("Logout");
            login_status_lbl.setText("Signed As: " + newLabel);
        }
    }

    @FXML
    public void logInOnAction(){
        setChanged();
        notifyObservers(HOMEVIEW_AGRS_LOGIN);
    }

    @FXML
    public void messageOnAction(){
        setChanged();
        notifyObservers(HOMEVIEW_AGRS_MESSAGECENER);
    }

}
