package MainPackage;

import db.Managers.DBResult;
import db.User;
import db.UserTable;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class ControllerCreateUser implements Observer{

/*    public TextField userName_textInput;
    public TextField password_textInput;
    public TextField firstName_textInput;
    public TextField lastName_textInput;
    public TextField city_textInput;
    public DatePicker create_datePicker;*/

    private UserDetailsView myView;
    private String status;
    private Stage stage;
/*    public Label result_lbl;
    public static final String result_lblTitle = "Info from DB:\t";*/

    /*public Button save_btn;*/

    public ControllerCreateUser() {
        this.myView = new UserDetailsView();
        myView.addObserver(this);
    }

    public void openCreate() throws IOException {
        status = "create";
        stage = new Stage();
        stage.setTitle("Create user");
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setController(myView);

        Parent root = fxmlLoader.load(getClass().getResource("createUser_view.fxml").openStream());
        myView.resetAll();
        Scene scene = new Scene(root,400,400);
        stage.setScene(scene);
        stage.show();
    }

    public void openUpdate(User user) throws IOException {
        //TODO - if user doesnt exist
        status = "update";
        stage = new Stage();
        stage.setTitle("Update user");
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setController(myView);

        //myView.setBirthdate(user.getBirthDate());
        Parent root = fxmlLoader.load(getClass().getResource("createUser_view.fxml").openStream());
        myView.setUserName(user.getUserName());
        myView.setFirstName(user.getFirstName());
        myView.setLastName(user.getLastName());
        myView.setCityName(user.getCity());
        myView.setPassword(user.getPassword());
        Scene scene = new Scene(root,400,400);
        stage.setScene(scene);
        stage.show();
    }

    private void closeStage(){
        stage.close();
    }

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

    public void saveInfo() {
        System.out.println("ControllerCreateUse: saveInfo");
        myView.setResult_lbl("");

        DBResult result = DBResult.NONE;

/*
        String userName = this.userName_textInput.getText();
        String password = this.password_textInput.getText();
        String firstName = this.firstName_textInput.getText();
        String lastName = this.lastName_textInput.getText();
        String city = this.city_textInput.getText();
        String[] values = {userName,password,firstName,lastName,city};

        //int birthDay = 20180505;
        int date = 0;
        if (create_datePicker.getValue() != null){
            date = this.convertDateStringToInt(create_datePicker.getValue().toString());
        }
        // TODO - get date as int
        User newUser = createUserIfValuesAreValid(values,date);
        if (newUser != null){

        UserTable userTable = UserTable.getInstance();
            result = userTable.InsertToTable(newUser);

            if (result == DBResult.ADDED){
                this.result_lbl.setText(result_lblTitle + "User was added successfully");
            Stage stage = (Stage) save_btn.getScene().getWindow();
            stage.close();
            }else if (result == DBResult.ALREADY_EXIST) {
                this.result_lbl.setText(result_lblTitle + "User already exists");
            }else if (result == DBResult.ERROR)
                this.result_lbl.setText(result_lblTitle + "Error while inserting new user");
        }else
            this.result_lbl.setText("Please fill all the fields..");
*/
        String userName = myView.getUserName();

        String password = myView.getPassword();
        String firstName = myView.getFirstName();
        String lastName = myView.getLastName();
        String city = myView.getCityName();
        String[] values = {userName,password,firstName,lastName,city};

        //int birthDay = 20180505;
        int date = 0;
        if (myView.getBirthday() != null){

            date = this.convertDateStringToInt(myView.getBirthday().toString());
        }
        // TODO - get date as int
        User newUser = createUserIfValuesAreValid(values,date);
        if (newUser != null){

            UserTable userTable = UserTable.getInstance();
            result = userTable.InsertToTable(newUser);

            if (result == DBResult.ADDED){
                myView.setResult_lbl("User was added successfully");
                closeStage();
            }else if (result == DBResult.ALREADY_EXIST) {
                myView.setResult_lbl( "User already exists");
            }else if (result == DBResult.ERROR)
                myView.setResult_lbl( "Error while inserting new user");
        }else
            myView.setResult_lbl("Please fill all the fields..");


    }

    private void updateInfo(){
        DBResult result = DBResult.NONE;
        String userName = myView.getUserName();

        String password = myView.getPassword();
        String firstName = myView.getFirstName();
        String lastName = myView.getLastName();
        String city = myView.getCityName();
        String[] values = {userName,password,firstName,lastName,city};

        //int birthDay = 20180505;
        int date = 0;
        if (myView.getBirthday() != null){

            date = this.convertDateStringToInt(myView.getBirthday().toString());
        }
        // TODO - get date as int
        User newUser = createUserIfValuesAreValid(values,date);
        if (newUser != null){

            UserTable userTable = UserTable.getInstance();
            result = userTable.updateUser(newUser);

            if (result == DBResult.UPDATED){
                myView.setResult_lbl("User was updated successfully");
                closeStage();
            }else if (result == DBResult.ALREADY_EXIST) {
                myView.setResult_lbl( "User already exists");
            }else if (result == DBResult.ERROR)
                myView.setResult_lbl( "Error while updating user");
        }else
            myView.setResult_lbl("Please fill all the fields..");

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
    public void update(Observable o, Object arg) {
        System.out.println("ControllerCreateUser: update");
        if(arg.equals("saveInfo")){
            if(status.equals("create")) {
                saveInfo();
            }else if(status.equals("update")){
                updateInfo();
            }
        }
    }

}
