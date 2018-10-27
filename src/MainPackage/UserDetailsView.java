package MainPackage;


import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserDetailsView extends java.util.Observable implements Initializable {


    public TextField userName_textInput;
    public TextField password_textInput;
    public TextField firstName_textInput;
    public TextField lastName_textInput;
    public TextField city_textInput;
    public DatePicker create_datePicker;

    public Label result_lbl;
    private static final String result_lblTitle = "Info from DB:\t";

    private String status;




    public UserDetailsView(String status) {
        this.status = status;
    }

    public void setUserName(String userName){
        userName_textInput.setText(userName);
    }

    public String getUserName(){
        return userName_textInput.getText();
    }

    public void setPassword(String password){
        password_textInput.setText(password);
    }

    public String getPassword(){
        return password_textInput.getText();
    }

    public void setFirstName(String firstName){
        firstName_textInput.setText(firstName);
    }

    public String getFirstName(){
        return firstName_textInput.getText();
    }

    public void setLastName(String lastNameName){
        lastName_textInput.setText(lastNameName);
    }

    public String getLastName(){
        return lastName_textInput.getText();
    }

    public void setCityName(String cityName){
        city_textInput.setText(cityName);
    }

    public String getCityName(){
        return city_textInput.getText();
    }

    public void setBirthdate(String birthdate){
        //create_datePicker.setValue();
        //TODO - make it work and insert date
    }

    public LocalDate getBirthday(){
        return create_datePicker.getValue();
    }

    public void setResult_lbl(String message){
        if(message != null)
            // Todo - need to fix, text does not change
            result_lbl.setText(result_lblTitle + message);
    }


    private int convertDateStringToInt(String str) {
        if(str != null && !str.equals("")){
            String[] tempArr = str.split("-");
            String temp = tempArr[0] + tempArr[1] + tempArr[2];
            return Integer.valueOf(temp);
        }
        return 0;
    }

    public void saveInfo(){
        System.out.println("UserDetailsView: saveInfo");
        setChanged();
        notifyObservers(status);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_datePicker.setDayCellFactory(create_datePicker-> new DateCell(){
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) > 0 );
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
