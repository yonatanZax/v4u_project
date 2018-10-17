package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class ControllerLogin {
    public TextField pass;
    public TextField id;
    User user;

    //TODO - add function: after login.. all user's data saved in "user".

    public void login(ActionEvent actionEvent) {
        String password = pass.getText();
        String userName = id.getText();

    }

}
