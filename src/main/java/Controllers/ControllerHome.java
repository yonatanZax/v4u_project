package Controllers;

import Model.MessageCenter.MessageModel;
import Model.Request.RequestModel;
import Model.User.UserModel;
import Model.Vacation.VacationModel;
import View.HomeView;
import View.LoginView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ControllerHome implements Observer{


    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;
    private ControllerUserCRUD controllerUserCRUD = new ControllerUserCRUD();
    private VacationSearchController vacationSearchController = new VacationSearchController();
    private ControllerLogin controllerLogin;
    private ControllerCreateVacation controllerCreateVacation;
    private ControllerMessageCenter controllerMessageCenter;
    private HomeView homeView = new HomeView();
    private UserModel userModel = new UserModel();
    private RequestModel requestModel = new RequestModel();
    private VacationModel vacationModel = new VacationModel(userModel);
    private MessageModel messageModel = new MessageModel(userModel, requestModel,vacationModel);


    public ControllerHome(){
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/home_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root,400,400);
        stage.setScene(scene);

        homeView = fxmlLoader.getController();
        homeView.addObserver(this);

        controllerLogin = new ControllerLogin(userModel);
        controllerCreateVacation = new ControllerCreateVacation(vacationModel);
        controllerMessageCenter = new ControllerMessageCenter(messageModel);

    }


    @Override
    public void update(Observable o, Object arg) {
        if (o.equals(homeView)){

            if (arg.equals("UserCRUD")){
                controllerUserCRUD.showStage();

            }else if (arg.equals("Login")){
                controllerLogin.showStage();

            }else if (arg.equals("Search")){
                vacationSearchController.start(stage);

            }else if (arg.equals("CreateVacation")){
                if (controllerLogin.checkIfUserLoggedIn()) {
                    controllerCreateVacation.showStage();
                } else {
                    controllerLogin.errorMessageNotLoggedIn();
                }
            } else if (arg.equals("MessageCenter")){
                controllerMessageCenter.showStage();
            }
        }
    }

    public void showStage(){
        stage.show();
    }

}
