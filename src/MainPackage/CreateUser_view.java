package MainPackage;

import db.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/*
THIS CLASS NEED TO BE CALLED "ControllerUserForm"
and will do the update and the create
 */

public class CreateUser_view implements Initializable {

    public TextField userName_textInput;
    public TextField password_textInput;
    public TextField firstName_textInput;
    public TextField lastName_textInput;
    public TextField city_textInput;
    public DatePicker create_datePicker;

    public Label result_lbl;
    public static final String RESULT_LBLTITLE = "Info from DB:\t";
    public Button save_btn;
    private User updatedUser = null;
    private static String [] updateUserName = null;
    private CreateUser_controller createUser_controller;


    public static void setUserForUpdate(String userName) {
        updateUserName = new String[]{userName};
    }






    public void saveInfo(ActionEvent event) {
        this.result_lbl.setText(RESULT_LBLTITLE);



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

        String added = createUser_controller.addUserToDB(values,date);

        result_lbl.setText(RESULT_LBLTITLE + added);

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
        create_datePicker.setDayCellFactory(create_datePicker-> new DateCell(){
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) > 0 );


            }
        });
        if (updateUserName!=null){
            userName_textInput.setPromptText(updatedUser.getUserName());
            password_textInput.setPromptText(updatedUser.getPassword());
            firstName_textInput.setPromptText(updatedUser.getFirstName());
            lastName_textInput.setPromptText(updatedUser.getLastName());
            city_textInput.setPromptText(updatedUser.getCity());
            create_datePicker.setPromptText(""+updatedUser.getBirthDate());
        }
    }


}
