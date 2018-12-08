package Controllers;

import Model.User.UserModel;
import Model.Vacation.VacationModel;
import View.HomeView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

public class ControllerHome implements Observer{


    private Stage stage;
    private Stack<Parent> subSceneStack = new Stack<>();
    private Parent root;
    private FXMLLoader fxmlLoader;
    private VacationSearchController vacationSearchController = new VacationSearchController();
    private ControllerLogin controllerLogin;
    private ControllerCreateVacation controllerCreateVacation;
    private ControllerMessageCenter controllerMessageCenter;
    private HomeView homeView = new HomeView();
    //private UserModel userModel = new UserModel();
    //private RequestModel requestModel = new RequestModel();
    //private VacationModel vacationModel = new VacationModel(userModel);
    //private MessageModel messageModel = new MessageModel(userModel, requestModel,vacationModel);


    public ControllerHome(){

        // Set stages and scenes
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


        // Init controllers
        controllerLogin = new ControllerLogin();
        controllerCreateVacation = new ControllerCreateVacation();
        //controllerMessageCenter = new ControllerMessageCenter(messageModel);

    }


    @Override
    public void update(Observable o, Object arg) {
        if (o.equals(homeView)){


        }
    }

    public void showStage(){
        stage.show();
    }

}
