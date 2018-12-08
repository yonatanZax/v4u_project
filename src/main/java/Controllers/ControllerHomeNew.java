package Controllers;

import View.HomeNewView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ControllerHomeNew extends Application implements Observer {


    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;

    private HomeNewView homeNewView;

    private VacationSearchController vacationSearchController = new VacationSearchController();

    public ControllerHomeNew() {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/home_new_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);

        homeNewView = fxmlLoader.getController();
        homeNewView.addObserver(this);


        Parent searchSubSceneParent = vacationSearchController.getRoot();
        homeNewView.setSub_scene(searchSubSceneParent);

        stage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
