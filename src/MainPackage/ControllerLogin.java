package MainPackage;

import db.Managers.DBResult;
import db.User;
import db.UserTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/*
I THINK WE NEED TO CALL THIS CLASS: ControllerCRUD
 */

public class ControllerLogin implements Initializable{
    public TextField textField;
    public Button readUser_btn;
    public Label error_lbl;
    public Label info_lbl;
    public static final String info_lblTitle = "Info from DB:\n";
    private UserTable userTable;


    private void errorWindow(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    /**
     * This method is called when the "Read" button is clicked
     * The textField is split to an array of userName and sent to the UserModel
     * It receives back a list of users, and prints it on the screen
     * @param actionEvent
     */
    public void readUser(ActionEvent actionEvent) {
        // The user's input
        String list = this.textField.getText();
        // Check that input is not a mistake by the user
        if (list == "" || list == " ")
            return;
        // An array of userName
        String[] listOfUserName = list.split(",");
        // Users from the db, only if the user exists.
        List<User> usersFromDB = userTable.readUsers(listOfUserName);

        if (usersFromDB == null) {
            // Null returns if an error occurs
            error_lbl.setText("Incorrect Username or Password");
        }else if (usersFromDB.size() == 0){
            // Empty list means that not even one of the list was in the db
            info_lbl.setText(info_lblTitle + list +" is/are not stored in DB..");
        }else {
            // Generate the list of users as String to print
            String textForLable = info_lblTitle;
            for (User user:usersFromDB) {
                textForLable += "\n" + user.toString();
            }
            info_lbl.setText(textForLable);
        }


        this.textField.textProperty().addListener((observable, oldValue, newValue) -> {
            error_lbl.setText(" ");
        });
/*        this.password.textProperty().addListener((observable, oldValue, newValue) -> {
            error_lbl.setText(" ");
        });*/

    }


    /**
     * Creates a new window with a form to add a new user to the DB
     * @param event
     * @throws Exception
     */
    public void createUser(ActionEvent event) throws Exception{
        this.info_lbl.setText(info_lblTitle);
        Stage createStage = new Stage();
        createStage.setTitle("Create a new user");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("createUser_view.fxml").openStream());
        Scene scene = new Scene(root,400,300);
        createStage.setScene(scene);
        createStage.show();
    }


    public void updateUser(ActionEvent event) throws Exception{
        if(textField.getText().equals("")) {
            info_lbl.setText(info_lblTitle + "Please Enter A Valid User Name");
            return;
        }
        ControllerCreateUser.setUserForUpdate(this.textField.getText());
        this.info_lbl.setText(info_lblTitle);
        Stage updateStage = new Stage();
        updateStage.setTitle("Update user");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("createUser_view.fxml").openStream());
        Scene scene = new Scene(root,400,300);
        updateStage.setScene(scene);
        updateStage.show();
    }

    public void deleteUser(ActionEvent event) {
//        info_lbl.setText(info_lblTitle);
        String id = textField.getText();
        DBResult result = UserTable.getInstance().DeleteFromTable(id);
        if(result == DBResult.NOTHING_TO_DELETE){
            info_lbl.setText(info_lblTitle + "User " + id + " is not in the DB");
        } else if(result == DBResult.DELETED){
            info_lbl.setText(info_lblTitle + "User " + id + " Deleted");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Singleton
        userTable = UserTable.getInstance();
    }
}
