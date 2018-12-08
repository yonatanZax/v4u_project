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
    }

    public void setSub_scene(Parent sub_scene) {

        this.sub_scene.setRoot(sub_scene);
    }

    public void setLoginStatusLabel(String newLabel){
        login_status_lbl.setText(newLabel);
    }

    @FXML
    public void logInOnAction(){
//        System.out.println("logInOnAction");
        setChanged();
        notifyObservers("login");
    }

    @FXML
    public void messageOnAction(){
//        System.out.println("messageOnAction");

        setChanged();
        notifyObservers("message");
    }

}
