package Controllers;

import db.DBResult;
import Model.User;
import db.UserTable;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;


public class ControllerCRUD {

    public TextField userName;
    public Button readUser_btn;
    public Label error_lbl;
    public Label info_lbl;
    public static final String info_lblTitle = "Info from DB:\n";

    private ControllerCreateUser userController = new ControllerCreateUser();

    private UserTable myModel;

    public ControllerCRUD() {
        myModel = UserTable.getInstance();
    }

/*    private void errorWindow(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }*/


    public void readUser() {
        info_lbl.setText(info_lblTitle);
        String userName = this.userName.getText();
        User userFromDB = new User();
        String selection = myModel.COLUMN_USERTABLE_USER_NAME + " = \"" + userName + "\"";
        List<User> userList = myModel.select(null, selection,null);
        if(userList != null && userList.size() > 0) {
            userFromDB = userList.get(0);
            if (userFromDB == null) {
                error_lbl.setText("Incorrect Username or Password");
            } else {
                info_lbl.setText(info_lblTitle + userFromDB.toString());

            }
        }
        else{
            info_lbl.setText(info_lblTitle + userName +" is not stored in DB..");
        }

        this.userName.textProperty().addListener((observable, oldValue, newValue) -> {
            error_lbl.setText(" ");
        });
    }

    public void createUser() throws Exception{
        this.info_lbl.setText(info_lblTitle);
        userController.openCreate();
    }


    public void updateUser() throws Exception{
        String userName = this.userName.getText();
        List<User> list = myModel.select(null, myModel.COLUMN_USERTABLE_USER_NAME + " = \"" + userName + "\"", null);
        if(list != null && list.size() > 0 ){
            userController.openUpdate(list.get(0));
        }
        else{
            this.info_lbl.setText(info_lblTitle + "The User Doesn't exist");
        }
    }

    public void deleteUser() {
        String id = this.userName.getText();
        DBResult result = myModel.DeleteFromTable(id);
        if(result == DBResult.NOTHING_TO_DELETE){
            info_lbl.setText(info_lblTitle + "User " + id + " is not in the DB");
        } else if(result == DBResult.DELETED){
            info_lbl.setText(info_lblTitle + "User " + id + " Deleted");
        }
    }
}
