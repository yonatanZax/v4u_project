package Controllers;

import Model.User.UserModel;
import View.LoginView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ControllerLogin implements Observer {

    private LoginView loginView;
    private UserModel userModel;
    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;
    private ControllerUserCRUD controllerUserCRUD = new ControllerUserCRUD();

    public ControllerLogin(UserModel model) {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/login_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        userModel = model;
        loginView = fxmlLoader.getController();
        loginView.addObserver(this);
    }

    public boolean checkIfUserLoggedIn() {
        if (userModel.getUserName() != null)
            return true;
        return false;
    }

    public void errorMessageNotLoggedIn(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("You are NOT Logged in!");
        errorAlert.setContentText("Only Registered User can publish new vacation for sale.");
        errorAlert.showAndWait();
    }

    public String getUserName() {
        return userModel.getUserName();
    }


    public void showStage() {
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.equals(loginView) && arg.equals("CheckLogin")) {
            String userName = "\"" + loginView.getUserId() + "\"";
            String password = "\"" + loginView.getPassword() + "\"";
            boolean checkUser = this.userModel.tryToLogin(userName, password);

            // Static variable - Main.user
            if (checkUser) {
                userName = loginView.getUserId();
                userModel.setUserName(userName);
                loginView.closeWindow();
            } else {
                loginView.setErrorMessageVisble(true);
            }
        } else if (o.equals(loginView) && arg.equals("SignIn")) {
            controllerUserCRUD.createUser();
        }
    }
}
