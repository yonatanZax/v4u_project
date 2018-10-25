package MainPackage;

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
import java.util.ResourceBundle;

/*
I THINK WE NEED TO CALL THIS CLASS: ControllerCRUD
 */

public class UserManagement_view implements Initializable{
    public TextField textField;
    public Button readUser_btn;
    public Label error_lbl;
    public Label info_lbl;
    public static final String INFO_LBL_TITLE = "Info from DB:\n";

    // Todo - change this
    public UserManagement_controller userManagement_controller;

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
        String[] listOfUsers = userManagement_controller.readUsers(listOfUserName);
        if (listOfUsers == null) {
            // Null returns if an error occurs
            error_lbl.setText("Incorrect Username or Password");
        }
        String textForLable = INFO_LBL_TITLE;
        for (int i = 0 ; i < listOfUsers.length; i++){
            textForLable += listOfUsers[i];
        }
        info_lbl.setText(textForLable);


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
        this.info_lbl.setText(INFO_LBL_TITLE);
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
            info_lbl.setText(INFO_LBL_TITLE + "Please Enter A Valid User Name");
            return;
        }
        CreateUser_view.setUserForUpdate(this.textField.getText());
        this.info_lbl.setText(INFO_LBL_TITLE);
        Stage updateStage = new Stage();
        updateStage.setTitle("Update user");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("createUser_view.fxml").openStream());
        Scene scene = new Scene(root,400,300);
        updateStage.setScene(scene);
        updateStage.show();
    }

    public void deleteUser(ActionEvent event) {
        String userId = textField.getText();
        String deleted = userManagement_controller.deleteUser(userId);
        info_lbl.setText(INFO_LBL_TITLE + deleted);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
