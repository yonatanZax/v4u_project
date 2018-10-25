package MainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("userManagement_view.fxml"));
        primaryStage.setTitle("Manage users");
        primaryStage.setScene(new Scene(root, 400, 450));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
//TODO - learn how to work with java log and add it

//TODO (DONE) - add check criteria to create new user, checking user doesn't exist already, and all the information is valid

//TODO (DONE) - add enums to returning and calling functions and act accordingly

//TODO - fix the views appearance