package MainPackage;

import db.Managers.DBResult;
import db.User;
import db.UserTable;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
import java.util.concurrent.TimeUnit;

/*
THIS CLASS NEED TO BE CALLED "ControllerUserForm"
and will do the update and the create
 */

public class ControllerCreateUser implements Initializable {

    public TextField userName_textInput;
    public TextField password_textInput;
    public TextField firstName_textInput;
    public TextField lastName_textInput;
    public TextField city_textInput;
    public DatePicker create_datePicker;

    public Label result_lbl;
    public static final String result_lblTitle = "Info from DB:\t";

    public Button save_btn;
    private UserTable userTable;
    private User updatedUser = null;
    private static String [] updateUserName = null;

    public static void setUserForUpdate(String userName) {
        updateUserName = new String[]{userName};
    }

    public void setUpdatedUser(){
        updatedUser = userTable.readUsers(updateUserName).get(0);
    }


    /**
     * This method checks if the parameters are valid and creates a User
     * @param values: An array with parameters to create a new user
     * @param date: The user's birthday
     * @return
     */
    private User createUserIfValuesAreValid(String[] values, int date){
        User newUser = new User();
        for (String val: values) {
            if (val.equals(""))
                return null;
        }
        if (date <= 0)
            return null;

        newUser.setUserName(values[0]);
        newUser.setPassword(values[1]);
        newUser.setFirstName(values[2]);
        newUser.setLastName(values[3]);
        newUser.setCity(values[4]);
        newUser.setBirthDate(date);
        return newUser;
    }


    public void saveInfo(ActionEvent event) {
        this.result_lbl.setText(result_lblTitle);

        DBResult result = DBResult.NONE;

        String userName = getTextBoxValue(this.userName_textInput);
        String password = getTextBoxValue(this.password_textInput);
        String firstName = getTextBoxValue(this.firstName_textInput);
        String lastName = getTextBoxValue(this.lastName_textInput);
        String city = getTextBoxValue(this.city_textInput);
        String[] values = {userName,password,firstName,lastName,city};

        int date = 0;
        if (create_datePicker.getValue() != null){
            // TODO (DONE) - get date as int
            // Generates an int from the datePicker
            date = this.convertDateStringToInt(create_datePicker.getValue().toString());
        } else if ( create_datePicker.getPromptText() !=null){
            date = Integer.parseInt(create_datePicker.getPromptText());
        }
        // Creates a new user if the values a valid
        User newUser = createUserIfValuesAreValid(values,date);

        if (newUser != null){
            // Try to add the new user to the database
            result = updatedUser==null? userTable.InsertToTable(newUser): userTable.UpdateUser(newUser);

            if (result == DBResult.ADDED || result == DBResult.UPDATED){
                this.result_lbl.setText(result_lblTitle + "User was "+ (updatedUser==null? "added": "updated") + " successfully");
                Stage stage = (Stage) save_btn.getScene().getWindow();
                stage.close();
            }else if (result == DBResult.ALREADY_EXIST) {
                this.result_lbl.setText(result_lblTitle + "User already exists");
            }else if (result == DBResult.ERROR)
                this.result_lbl.setText(result_lblTitle + "Error while inserting new user");
        }else
            this.result_lbl.setText("Please fill all the fields..");

        updatedUser =null;
        updateUserName=null;
    }


    private String getTextBoxValue(TextField textField) {
        if (textField.getText().equals("") && !textField.getPromptText().equals(""))
                return textField.getPromptText();
        return textField.getText();
    }

    /**
     * Generates a dateAsInt from a string
     * @param str: 01-01-2018
     * @return
     */
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
        userTable = UserTable.getInstance();
        create_datePicker.setDayCellFactory(create_datePicker-> new DateCell(){
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) > 0 );


            }
        });
        if (updateUserName!=null){
            setUpdatedUser();
            userName_textInput.setPromptText(updatedUser.getUserName());
            password_textInput.setPromptText(updatedUser.getPassword());
            firstName_textInput.setPromptText(updatedUser.getFirstName());
            lastName_textInput.setPromptText(updatedUser.getLastName());
            city_textInput.setPromptText(updatedUser.getCity());
            create_datePicker.setPromptText(""+updatedUser.getBirthDate());
        }
    }


}
