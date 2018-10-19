package MainPackage;

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
        newUser.setBirthDate(20180505);

        UserTable userTable = UserTable.getInstance();
        Boolean added = userTable.InsertToTable(newUser);

        if (added){
            Stage stage = (Stage) save_btn.getScene().getWindow();
            stage.close();
        }else {
            System.out.println("User was not added");
        }

    }
}
