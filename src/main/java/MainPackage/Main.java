package MainPackage;

import Controllers.ControllerCRUD;
import Model.UserModel;
import View.UserCRUDView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        ControllerCRUD controllerCRUD = new ControllerCRUD();
        controllerCRUD.showStage();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
