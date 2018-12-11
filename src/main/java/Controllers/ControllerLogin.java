package Controllers;

import Model.User.UserModel;
import View.LoginView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ControllerLogin extends Observable implements Observer {


    public static final String CONTROLLER_LOGIN_ARGS_LOGGEDIN = "LoggedIn";

    private LoginView loginView;
    private UserModel userModel = new UserModel();
    private ControllerUserCRUD controllerUserCRUD = new ControllerUserCRUD();

    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;


    public ControllerLogin() {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/login_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/images/user.png"));
        stage.setScene(scene);
        loginView = fxmlLoader.getController();
        loginView.addObserver(this);
    }


    public void errorMessageNotLoggedIn(String contentText) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("You are NOT Logged in!");
        errorAlert.setContentText(contentText);
        errorAlert.showAndWait();
    }


    public void showStage() {
        loginView.resetLoginCredentials();
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.equals(loginView) && arg.equals(LoginView.LOGINVIEW_CHECKLOGIN)) {
            String userName = "\"" + loginView.getUserId() + "\"";
            String password = "\"" + loginView.getPassword() + "\"";
            if (userModel.tryToLogin(userName, password)) {
                loginView.closeWindow();
                setChanged();
                notifyObservers(CONTROLLER_LOGIN_ARGS_LOGGEDIN);
            } else
                loginView.setErrorMessageVisble(true);


        } else if (o.equals(loginView) && arg.equals(LoginView.LOGINVIEW_SIGNIN)) {
            controllerUserCRUD.createUser();
        }
    }
}
