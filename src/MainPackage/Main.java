package MainPackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login_view.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
//TODO - learn how to work with java log and add it

//TODO - add check critirea to create new user, checking user doesnt exist already, and all the information is valid

//TODO - add enums to returning and calling functions and act accordingly

//TODO - fix the views appearance