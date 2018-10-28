package Controllers;

import Model.UserModel;
import db.DBResult;
import Model.User;
import db.UserTable;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class ControllerCRUD implements Observer {

    public TextField userName;
    public Button readUser_btn;
    public Label error_lbl;
    public Label info_lbl;
    public static final String info_lblTitle = "Info from DB:\n";

    private ControllerCreateUser userController;

    private UserModel myModel;

    public ControllerCRUD() {
        myModel = new UserModel();
        myModel.addObserver(this);
        userController = new ControllerCreateUser(myModel);
    }

/*    private void errorWindow(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }*/


    public void readUser() {
        error_lbl.setText("");
        info_lbl.setText("");
        String textField = userName.getText();
        if (textField == "" || textField == " "){
            error_lbl.setText("Please insert a valid userName..");
        }
        else{
            String userName = textField;
            User user = myModel.readUser(userName);
            if (user == null) {
                // Empty list means that not even one of the list was in the db

                error_lbl.setText(textField + " is not stored in DB..");

            } else {
                // Generate the list of users as String to print
                info_lbl.setText(user.toString());
            }
        }


    }

    public void createUser() throws Exception{
        this.info_lbl.setText(info_lblTitle);
        userController.openCreate();
    }


    public void updateUser() throws Exception{
        String userName = this.userName.getText();
        User user = myModel.readUser(userName);
        if (user == null){
            this.info_lbl.setText(info_lblTitle + userName + "Doesn't exist");
        }else{
            userController.openUpdate(user);
        }
    }

    public void deleteUser() {
        String id = this.userName.getText();
        myModel.deleteUser(id);
    }


    @Override
    public void update(Observable o, Object arg){
        if(o == myModel){
            if(arg == DBResult.UPDATED){
                error_lbl.setText("User was updated successfully");
            }
            else if ( arg == DBResult.DELETED){
                error_lbl.setText("User was deleted successfully");
            }
            else if(arg == DBResult.ADDED){
                error_lbl.setText("User was created successfully");
            }
        }
        //TODO - make it show the result after update, create and
        //TODO - try to make it show the new information after we finish with update
    }
}
