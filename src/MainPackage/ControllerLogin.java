package MainPackage;

import db.User;
import db.UserTable;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;


public class ControllerLogin {
    public TextField pass;
    public TextField id;
    public Button login_bttn;
    public Label error_lbl;
    User user;

    private void errorWindow(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void login(ActionEvent actionEvent) {
        String password = pass.getText();
        String userName = id.getText();
        User user = new User();
        UserTable userTable = UserTable.getInstance();
        String selection = UserTable.COLUMN_USERTABLE_USER_NAME + " = \"" + userName + "\"";
        List<User> userList = userTable.select(null,selection,null);
        user = userList.get(0);
        if(user == null){
//            errorWindow("ERROR", "Invalid Connection", "Incorrect Username or Password...");
            error_lbl.setText("Incorrect Username or Password");
        } else {
            Stage stage = (Stage)login_bttn.getScene().getWindow();
            stage.close();
        }


        id.textProperty().addListener((observable, oldValue, newValue) -> {
            error_lbl.setText(" ");
        });
        pass.textProperty().addListener((observable, oldValue, newValue) -> {
            error_lbl.setText(" ");
        });

    }

}
