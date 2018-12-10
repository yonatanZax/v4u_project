package Controllers;

import Model.User.UserModel;
import View.HomeView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ControllerHome extends Application implements Observer {


    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;

    private HomeView homeView;

    private VacationSearchController vacationSearchController = new VacationSearchController();
    private ControllerMessageCenter controllerMessageCenter = new ControllerMessageCenter();
    private ControllerLogin controllerLogin = new ControllerLogin();
    private ControllerCreateVacation controllerCreateVacation = new ControllerCreateVacation();

    public ControllerHome() {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/home_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);

        homeView = fxmlLoader.getController();
        homeView.addObserver(this);

        // Set Observers
        controllerLogin.addObserver(this);
        controllerMessageCenter.addObserver(this);
        controllerCreateVacation.addObserver(this);
        vacationSearchController.addObserver(this);



        // Stack holds the last subSceneName
//        subSceneStack.push(vacationSearchController);
        // Set the subSceneName to be vacationSearchController
        vacationSearchController.updateSubScene();
        homeView.setSub_scene(vacationSearchController.getRoot());


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
        if (userName == null)
            vacationSearchController.updateSubScene();
        homeView.setLoginStatusLabel(userName);
    }

    private String subSceneName = "";

    @Override
    public void update(Observable o, Object arg) {
        if (o.equals(homeView)){
            if (arg.equals(HomeView.HOMEVIEW_AGRS_LOGIN)){
                // Show login stage in another window
                if(UserModel.isLoggedIn()){
                    UserModel.logOff();
                    changeLoginStatus(null);
                }

                else
                    controllerLogin.showStage();

            }else if(arg.equals(HomeView.HOMEVIEW_AGRS_MESSAGECENER)){

                //Updates the MessageCenter and sets it as subSceneName
                Parent newRoot;
                if(!subSceneName.equals(controllerMessageCenter.getClass().getSimpleName())) {
                    subSceneName = controllerMessageCenter.getClass().getSimpleName();
                    controllerMessageCenter.updateSubScene();
                    newRoot = controllerMessageCenter.getRoot();
                }else{
                    subSceneName = vacationSearchController.getClass().getSimpleName();
                    vacationSearchController.updateSubScene();
                    newRoot = vacationSearchController.getRoot();
                }
                homeView.setSub_scene(newRoot);

            }else if (arg.equals(HomeView.HOMEVIEW_AGRS_GOBACK)){
//                if(!subSceneStack.empty()){
//                    // Prepares the last subSceneName to 'Show'
//                    SubScenable last = subSceneStack.pop();
//                    last.updateSubScene();
//                    homeView.setSub_scene(last.getRoot());
//
//                }
            }

        }else if (o.equals(vacationSearchController)){
            if (arg.equals(VacationSearchController.BTN_ADD)) {
                if (UserModel.isLoggedIn()) {
                    controllerCreateVacation.showStage();
                } else {
                    controllerLogin.errorMessageNotLoggedIn("Only Registered User can publish new vacation for sale.");
                }

            }else if(arg.equals(VacationSearchController.VACATION_PICKED)){

            }
        }else if(o.equals(controllerLogin)){
            if (arg.equals(ControllerLogin.CONTROLLER_LOGIN_ARGS_LOGGEDIN)){
                changeLoginStatus(UserModel.getUserName());
            }
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}
