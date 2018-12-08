package Controllers;

import Model.User.UserModel;
import View.HomeNewView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

public class ControllerHomeNew extends Application implements Observer {


    private Stage stage;
    private Parent root;
    private Stack<SubScenable> subSceneStack = new Stack<>();

    private FXMLLoader fxmlLoader;

    private HomeNewView homeNewView;

    private VacationSearchController vacationSearchController = new VacationSearchController();
    private ControllerMessageCenter controllerMessageCenter = new ControllerMessageCenter();
    private ControllerLogin controllerLogin = new ControllerLogin();
    private ControllerCreateVacation controllerCreateVacation = new ControllerCreateVacation();

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

        // Set Observers
        controllerLogin.addObserver(this);
        controllerMessageCenter.addObserver(this);
        controllerCreateVacation.addObserver(this);
        vacationSearchController.addObserver(this);



        // Stack holds the last subScene
        subSceneStack.push(vacationSearchController);
        // Set the subScene to be vacationSearchController
        vacationSearchController.updateSubScene();
        homeNewView.setSub_scene(vacationSearchController.getRoot());


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
        if (o.equals(homeNewView)){
            if (arg.equals(HomeNewView.HOMEVIEW_AGRS_LOGIN)){
                // Show login stage in another window
                if(UserModel.isLoggedIn()){
                    UserModel.logOff();
                    changeLoginStatus(null);
                }

                else
                    controllerLogin.showStage();

            }else if(arg.equals(HomeNewView.HOMEVIEW_AGRS_MESSAGECENER)){

                // Updates the MessageCenter and sets it as subScene
                //controllerMessageCenter.updateSubScene();
                //homeNewView.setSub_scene(controllerMessageCenter.getRoot());

            }else if (arg.equals(HomeNewView.HOMEVIEW_AGRS_GOBACK)){
                if(!subSceneStack.empty()){
                    // Prepares the last subScene to 'Show'
                    SubScenable last = subSceneStack.pop();
                    last.updateSubScene();
                    homeNewView.setSub_scene(last.getRoot());

                }
            }

        }else if (o.equals(controllerLogin)){
            if(arg.equals(ControllerLogin.CONTROLLER_LOGIN_ARGS_LOGGEDIN)){
                // Logged in successfully

            }
        }else if (o.equals(vacationSearchController)){
            if (arg.equals(VacationSearchController.BTN_ADD)) {
                if (UserModel.isLoggedIn()) {
                    controllerCreateVacation.showStage();
                } else {
                    controllerLogin.errorMessageNotLoggedIn();
                }

            }else if(arg.equals(VacationSearchController.VACATION_PICKED)){

            }
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
