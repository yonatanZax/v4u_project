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

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class ControllerCRUD implements Observer{

    public TextField userName;
    public Button readUser_btn;
    public Label error_lbl;
    public Label info_lbl;
    public static final String info_lblTitle = "Info from DB:\n";

    private UserModel userModel;

    public ControllerCRUD() {
        userModel = UserModel.getInstance();
        userModel.addObserver(this);
    }





    private void errorWindow(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



    public void readUser(ActionEvent actionEvent) {
        info_lbl.setText(info_lblTitle);
        String textField = this.userName.getText();
        String[] userNames = textField.split(",");
        User userFromDB = new User();

        List<User> userList = userModel.readUsers(userNames);
        // Todo (DONE) - change to read multiple users
        if (userList == null) {
            // Null returns if an error occurs
            error_lbl.setText("Incorrect Username or Password");
        }else if (userList.size() == 0){
            // Empty list means that not even one of the list was in the db
            info_lbl.setText(info_lblTitle + userList +" is/are not stored in DB..");
        }else {
            // Generate the list of users as String to print
            String textForLable = info_lblTitle;
            for (User user:userList) {
                textForLable += "\n" + user.toString();
            }
            info_lbl.setText(textForLable);
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
        ControllerCreateUser controllerCreateUser = new ControllerCreateUser(userModel,"Create");
        controllerCreateUser.openCreate();
    }


    public void updateUser(ActionEvent event) throws Exception{
        String userName = this.userName.getText();
        List<User> userList = userModel.getUserList(userName);
        if(userList != null && userList.size() > 0 ){
            ControllerCreateUser controllerCreateUser = new ControllerCreateUser(userModel,"Update");
            controllerCreateUser.openUpdate(userList.get(0));
        }
    }

    public void deleteUser(ActionEvent event) {

    }

    @Override
    public void update(Observable o, Object arg) {
        if(o == userModel){

        }
    }
}
