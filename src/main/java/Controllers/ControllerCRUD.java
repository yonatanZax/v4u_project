package Controllers;

import Model.UserModel;
import db.DBResult;
import Model.User;
import db.UserTable;
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

    private ControllerCreateUser userController = new ControllerCreateUser();

    private UserModel myModel;

    public ControllerCRUD() {
        myModel = new UserModel();
    }

/*    private void errorWindow(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }*/


    public void readUser() {
        String textField = userName.getText();
        if (textField == "" || textField == " "){
            info_lbl.setText(info_lblTitle + "Insert a valid userName..");
        }
        String userName = textField;
        User user = myModel.readUser(userName);
        // Todo (DONE) - change to read multiple users
        if (user == null) {
            // Empty list means that not even one of the list was in the db
            info_lbl.setText(info_lblTitle + textField +" is not stored in DB..");
        }else {
            // Generate the list of users as String to print
            info_lbl.setText(user.toString());
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

    }


}
