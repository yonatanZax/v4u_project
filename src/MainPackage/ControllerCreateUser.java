package MainPackage;

import db.Managers.DBResult;
import db.User;
import db.UserTable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerCreateUser {

    public TextField userName_textInput;
    public TextField password_textInput;
    public TextField firstName_textInput;
    public TextField lastName_textInput;
    public TextField city_textInput;
    public TextField year;
    public TextField month;
    public TextField day;

    public Button save_btn;


    public void saveInfo(ActionEvent event) {
        User newUser = new User();
        newUser.setUserName(this.userName_textInput.getText());
        newUser.setPassword(this.password_textInput.getText());
        newUser.setFirstName(this.firstName_textInput.getText());
        newUser.setLastName(this.lastName_textInput.getText());
        newUser.setCity(this.city_textInput.getText());
        //TODO - add to GUI drop down  tables of dates so the user could pick the date easily
        newUser.setBirthDate(20180505);

        UserTable userTable = UserTable.getInstance();
        DBResult result = userTable.InsertToTable(newUser);

        if (result == DBResult.ADDED){
            Stage stage = (Stage) save_btn.getScene().getWindow();
            System.out.println("User was added successfully");
            stage.close();
        }else if (result == DBResult.ALREADY_EXIST) {
            System.out.println("User already exists");
        }else if (result == DBResult.ERROR)
            System.out.println("Error while inserting new user");

    }
}
