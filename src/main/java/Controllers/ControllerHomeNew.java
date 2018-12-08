package Controllers;

import Model.Request.RequestModel;
import Model.User.UserModel;
import Model.Vacation.VacationModel;
import View.HomeNewView;
import View.HomeView;
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
    private ControllerLogin controllerLogin;
    private ControllerCreateVacation controllerCreateVacation;
    //    private ControllerMessageCenter controllerMessageCenter;



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

    /**
     * changes the login status in the home view
     * if userName is null: it means we are doing logout
     * if userName isn't null: it means we successfully logged in
     * @param userName the name of the new user or null it it's logout
     */
    public void changeLoginStatus(String userName){
        homeNewView.setLoginStatusLabel(userName);
    }



    @Override
    public void update(Observable o, Object arg) {
        /*else*/ if (o.equals(vacationSearchController)) {
            if (arg.equals(VacationSearchController.BTN_ADD)) {
                if (controllerLogin.checkIfUserLoggedIn()) {
                    controllerCreateVacation.showStage();
                } else {
                    controllerLogin.errorMessageNotLoggedIn();
                }
            }
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}
