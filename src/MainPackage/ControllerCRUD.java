package MainPackage;

import db.User;
import db.UserTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;


public class ControllerCRUD {

    public TextField userName;
    public Button readUser_btn;
    public Label error_lbl;
    public Label info_lbl;
    public static final String info_lblTitle = "Info from DB:\n";

    private ControllerCreateUser userController = new ControllerCreateUser();


    private void errorWindow(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



    public void readUser(ActionEvent actionEvent) {
        info_lbl.setText(info_lblTitle);
        String userName = this.userName.getText();
        User userFromDB = new User();
        UserTable userTable = UserTable.getInstance();
        String selection = UserTable.COLUMN_USERTABLE_USER_NAME + " = \"" + userName + "\"";
        List<User> userList = userTable.select(null,selection,null);
        if(userList != null && userList.size() > 0) {
            userFromDB = userList.get(0);
            if (userFromDB == null) {
//            errorWindow("ERROR", "Invalid Connection", "Incorrect Username or Password...");
                error_lbl.setText("Incorrect Username or Password");
            } else {
                info_lbl.setText(info_lblTitle + userFromDB.toString());
                //Stage stage = (Stage) readUser_btn.getScene().getWindow();
                //stage.close();
            }
        }
        else{
            info_lbl.setText(info_lblTitle + userName +" is not stored in DB..");
            //TODO - make it show nothing or error/window of not finding anything
        }

        this.userName.textProperty().addListener((observable, oldValue, newValue) -> {
            error_lbl.setText(" ");
        });
/*        this.password.textProperty().addListener((observable, oldValue, newValue) -> {
            error_lbl.setText(" ");
        });*/

    }

    public void createUser(ActionEvent event) throws Exception{
        this.info_lbl.setText(info_lblTitle);
        userController.openCreate();
    }


    public void updateUser(ActionEvent event) throws Exception{
        String userName = this.userName.getText();
        List<User> list = UserTable.getInstance().select(null, UserTable.COLUMN_USERTABLE_USER_NAME + " = \"" + userName + "\"", null);
        if(list != null && list.size() > 0 ){
            userController.openUpdate(list.get(0));
        }
    }

    public void deleteUser(ActionEvent event) {

    }
}
