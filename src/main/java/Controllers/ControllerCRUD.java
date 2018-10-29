package Controllers;

import Model.UserModel;
import View.UserCRUDView;
import View.UserDetailsView;
import db.DBResult;
import Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


public class ControllerCRUD implements Observer {

    private ControllerCreateUser controllerCreateUser;
    private UserCRUDView userCRUDView;
    private UserModel myModel;
    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;



    public void readUser() {
        userCRUDView.error_lbl.setText("");
        userCRUDView.info_lbl.setText("");
        String textField = userCRUDView.userName.getText();
        if (textField == "" || textField == " "){
            userCRUDView.error_lbl.setText("Please insert a valid userName..");
        }
        else{
            String userName = textField;
            User user = myModel.readUser(userName);
            if (user == null) {
                // Empty list means that not even one of the list was in the db

                userCRUDView.error_lbl.setText(textField + " is not stored in DB..");

            } else {
                // Generate the list of users as String to print
                userCRUDView.info_lbl.setText(user.toString());
            }
        }


    }



    public ControllerCRUD(UserModel userModel) {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/user_crud_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root,400,400);
        stage.setScene(scene);
        userCRUDView = fxmlLoader.getController();

        myModel = userModel;
        myModel.addObserver(this);
//        myModel = userModel;
//        myModel.addObserver(this);
//        userCRUDView = new UserCRUDView(myModel);
    }

/*    private void errorWindow(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }*/




    public void createUser() throws Exception{
        userCRUDView.info_lbl.setText(userCRUDView.info_lblTitle);
        controllerCreateUser.openCreate();
    }


    public void updateUser() throws Exception{
        String userName = userCRUDView.userName.getText();
        User user = myModel.readUser(userName);
        if (user == null){
            userCRUDView.info_lbl.setText(userCRUDView.info_lblTitle + userName + "Doesn't exist");
        }else{
            controllerCreateUser.openUpdate(user);
        }
    }

    public void deleteUser() {
        String id = userCRUDView.userName.getText();
        myModel.deleteUser(id);
    }


    @Override
    public void update(Observable o, Object arg){
        if(o == myModel){
            if(arg == DBResult.UPDATED){
                userCRUDView.error_lbl.setText("User was updated successfully");
            }
            else if ( arg == DBResult.DELETED){
                userCRUDView.error_lbl.setText("User was deleted successfully");
            }
            else if(arg == DBResult.ADDED){
                userCRUDView.error_lbl.setText("User was created successfully");
            }
        }
        //TODO - make it show the result after update, create and
        //TODO - try to make it show the new information after we finish with update
    }
}
