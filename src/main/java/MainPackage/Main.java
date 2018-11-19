package MainPackage;

import Controllers.ControllerCRUD;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        File directory = new File("db");
        if (! directory.exists())
            directory.mkdir();
        ControllerCRUD controllerCRUD = new ControllerCRUD();
        controllerCRUD.showStage();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
