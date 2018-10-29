package View;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Observable;

public class UserDetailsView extends Observable implements Initializable {


    public TextField userName_textInput;
    public TextField password_textInput;
    public TextField firstName_textInput;
    public TextField lastName_textInput;
    public TextField city_textInput;
    public DatePicker create_datePicker;
    public Button save_btn;

    public Label result_lbl;
    private static final String result_lblTitle = "Info from DB:\t";

    public void setUserName(String userName){
        userName_textInput.setText(userName);
    }

    public String getUserName(){
        return userName_textInput.getText().trim();
    }

    public void setPassword(String password){
        password_textInput.setText(password);
    }

    public String getPassword(){
        return password_textInput.getText().trim();
    }

    public void setFirstName(String firstName){
        firstName_textInput.setText(firstName);
    }

    public String getFirstName(){
        return firstName_textInput.getText().trim();
    }

    public void setLastName(String lastNameName){
        lastName_textInput.setText(lastNameName);
    }

    public String getLastName(){
        return lastName_textInput.getText().trim();
    }

    public void setCityName(String cityName){
        city_textInput.setText(cityName);
    }

    public String getCityName(){
        return city_textInput.getText().trim();
    }

    public void setBirthdate(String birthdate){
        int day = Integer.valueOf(birthdate.substring(birthdate.length() - 2, birthdate.length()));
        int month = Integer.valueOf(birthdate.substring(birthdate.length() - 4, birthdate.length() - 2));
        int year =  Integer.valueOf(birthdate.substring(0, birthdate.length() - 4));
        LocalDate localDate = LocalDate.of(year, month, day);
        create_datePicker.setValue(localDate);
    }

    public LocalDate getBirthday(){
        return create_datePicker.getValue();
    }

    public void setResult_lbl(String message){
        if(message != null)
            result_lbl.setText(result_lblTitle + message);
    }


    public void saveInfo(){
        System.out.println("UserDetailsView: saveInfo");
        setChanged();
        notifyObservers("saveInfo");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_datePicker.setDayCellFactory(create_datePicker-> new DateCell(){
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                LocalDate today18 = today.minusYears(18);

                setDisable(empty || date.compareTo(today18) > 0 );
            }
        });

        save_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveInfo();
            }
        });
    }

    public void resetAll(){
        userName_textInput.setText("");
        password_textInput.setText("");
        firstName_textInput.setText("");
        lastName_textInput.setText("");
        city_textInput.setText("");
        create_datePicker.setValue(null);
    }
}
