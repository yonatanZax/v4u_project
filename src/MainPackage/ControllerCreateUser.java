package MainPackage;

import db.User;
import db.UserTable;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerCreateUser implements Initializable {

    public TextField userName_textInput;
    public TextField password_textInput;
    public TextField firstName_textInput;
    public TextField lastName_textInput;
    public TextField city_textInput;
    public DatePicker create_datePicker;

    public Button save_btn;



    public void saveInfo(ActionEvent event) {
        User newUser = new User();
        newUser.setUserName(this.userName_textInput.getText());
        newUser.setPassword(this.password_textInput.getText());
        newUser.setFirstName(this.firstName_textInput.getText());
        newUser.setLastName(this.lastName_textInput.getText());
        newUser.setCity(this.city_textInput.getText());
        String str = create_datePicker.getValue().toString();
        int date = convertDateStringToInt(str);
        newUser.setBirthDate(date);
        //newUser.setBirthDate(20180505);

        UserTable userTable = UserTable.getInstance();
        boolean added = userTable.InsertToTable(newUser);

        if (added){
            Stage stage = (Stage) save_btn.getScene().getWindow();
            stage.close();
        }else {
            System.out.println("User was not added");
        }

    }

    private int convertDateStringToInt(String str) {
        if(str != null && !str.equals("")){
            String[] tempArr = str.split("-");
            String temp = tempArr[0] + tempArr[1] + tempArr[2];
            return Integer.valueOf(temp);
        }
        return 0;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        create_datePicker.setDayCellFactory(create_datePicker-> new DateCell(){
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) > 0 );
            }
        });

    }


}
