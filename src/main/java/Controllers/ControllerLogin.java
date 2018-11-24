package Controllers;

import MainPackage.Main;
import Model.User.UserModel;
import View.LoginView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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


    public ControllerLogin(UserModel model){
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/login_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        loginView = fxmlLoader.getController();
        loginView.addObserver(this);



        this.userModel = model;
        this.loginView = new LoginView();
        this.loginView.addObserver(this);
    }


    public void showStage(){
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.equals(loginView) && arg.equals("CheckLogin")){
            String userName = loginView.getUserId();
            String password = loginView.getPassword();
            boolean checkUser = this.userModel.tryToLogin(userName, password);

            // Static variable - Main.user
            if (checkUser){
                Main.user = userName;
            }
        }
    }
}
