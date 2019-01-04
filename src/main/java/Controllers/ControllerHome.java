package Controllers;

import Model.Request.RequestModel;
import Model.User.UserModel;
import Model.Vacation.VacationModel;
import View.HomeView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;

//public class ControllerHome extends Application implements Observer {
public class ControllerHome implements Observer {


    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;

    private HomeView homeView;

    private VacationSearchController vacationSearchController;
    private ControllerMessageCenter controllerMessageCenter;
    private ControllerLogin controllerLogin;
    private ControllerCreateVacation controllerCreateVacation;
    private RequestModel requestModel;
    private String subSceneName = "";
    private SubScenable currentSceneController;
    private Thread refreshThread = new Thread();

    public ControllerHome() {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/home_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        VacationModel vacationModel = new VacationModel();
        requestModel = new RequestModel(vacationModel);
        UserModel userModel = new UserModel();
        controllerMessageCenter = new ControllerMessageCenter(requestModel,userModel);
        controllerLogin = new ControllerLogin(userModel);
        controllerCreateVacation = new ControllerCreateVacation(vacationModel);
        vacationSearchController = new VacationSearchController(vacationModel);
        stage.getIcons().add(new Image("/images/vacation.png"));
        stage.setTitle("Vacation 4 U");
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
        currentSceneController = vacationSearchController;
        startRefreshThread();
        stage.show();
    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//    }

    /**
     * changes the login status in the home view
     * if userName is null: it means we are doing logout
     * if userName isn't null: it means we successfully logged in
     * @param userName the name of the new user or null it it's logout
     */
    public void changeLoginStatus(String userName){
        if (userName == null) {
            vacationSearchController.updateSubScene();
            currentSceneController = vacationSearchController;
            startRefreshThread();
        }
        homeView.setLoginStatusLabel(userName);
    }

    private void startRefreshThread(){
        if(refreshThread.isAlive()){
            refreshThread.interrupt();
        }
        refreshThread = new Thread(this::runRefreshThread);
        refreshThread.setDaemon(true);
        refreshThread.start();
    }

    private void runRefreshThread(){

        try {
            while(true) {
                Thread.sleep(((long) (5 * 1000)));
                Platform.runLater(() -> {
                    currentSceneController.updateSubScene();
                });
            }
        } catch (InterruptedException e) {
            return;
        }

    }



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
                String imagePath = "";
                if(!subSceneName.equals(controllerMessageCenter.getClass().getSimpleName())) {
                    subSceneName = controllerMessageCenter.getClass().getSimpleName();
                    controllerMessageCenter.updateSubScene();
                    currentSceneController = controllerMessageCenter;
                    newRoot = controllerMessageCenter.getRoot();
                    imagePath = "/images/search.png";
                }else{
                    subSceneName = vacationSearchController.getClass().getSimpleName();
                    vacationSearchController.updateSubScene();
                    currentSceneController = vacationSearchController;
                    newRoot = vacationSearchController.getRoot();
                    imagePath = "/images/mail.png";
                }
                homeView.setSubsceneIcon(imagePath);
                homeView.setSub_scene(newRoot);
                startRefreshThread();

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
            else if (arg.equals(VacationSearchController.SEND_VACATION_PURCHASE_REQUEST)){
                homeView.setStatusBarString("Purchase request was sent to the seller");
                String vacationKey = vacationSearchController.getVacationPickedKey();
                String vacationSellerKey = vacationSearchController.getVacationPickedSeller();
                if (vacationKey != null && vacationSellerKey != null){
                    // todo - change exchange (last parameter next func) as needed
                    requestModel.insertRequestToTable(vacationKey, vacationSellerKey, 0);
                }
            }
            else if (arg.equals(VacationSearchController.EXCHANGE)){
                homeView.setStatusBarString("Purchase request was sent to the seller");
                String vacationKey = vacationSearchController.getVacationPickedKey();
                String vacationSellerKey = vacationSearchController.getVacationPickedSeller();
                String exchangeKey = vacationSearchController.getExchangedKey();
                if (vacationKey != null && vacationSellerKey != null){
                    requestModel.insertRequestToTable(vacationKey, vacationSellerKey, Integer.valueOf(exchangeKey));
                }
            }
        }else if(o.equals(controllerLogin)){
            if (arg.equals(ControllerLogin.CONTROLLER_LOGIN_ARGS_LOGGEDIN)){
                changeLoginStatus(UserModel.getUserName());
                homeView.setSubsceneIcon("/images/mail.png");
            }
        }
        else if (o.equals(controllerCreateVacation)){
            if (arg.equals(ControllerCreateVacation.VACATION_ADDED)){
                vacationSearchController.updateSubScene();
                homeView.setStatusBarString("Vacation was added successfully");
            }
        }


    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}
