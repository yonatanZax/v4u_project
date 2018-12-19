package Controllers;

import Model.ACRUDModel;
import Model.User.UserModel;
import View.UserDetailsView;
import db.DBResult;
import Model.User.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;


public class ControllerCreateUser implements Observer{

    private UserDetailsView myView;
    private ACRUDModel myModel;
    private String status;
    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;

    private String updateUserName = null;

    public ControllerCreateUser(ACRUDModel myModel) {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/createUser_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root,400,400);
        stage.getIcons().add(new Image("/images/create.png"));
        stage.setScene(scene);
        myView = fxmlLoader.getController();

        this.myModel = myModel;
        myModel.addObserver(this);
    }

    public void openCreate() {
        status = "create";
        stage.setTitle("Create user");

        UserDetailsView userDetailsView = fxmlLoader.getController();
        userDetailsView.resetAll();
        userDetailsView.addObserver(this);

        myView.create_datePicker.setValue(LocalDate.now().minusYears(25));

        updateUserName = null;
        stage.show();
    }


    public void openUpdate(User user)  {
        myView.setResult_lbl("");
        updateUserName = user.getUserName();
        status = "update";
        stage.setTitle("Update user");
        myView = fxmlLoader.getController();

        myView.setUserName(user.getUserName());
        myView.setFirstName(user.getFirstName());
        myView.setLastName(user.getLastName());
        myView.setCityName(user.getCity());
        myView.setPassword(user.getPassword());
        myView.setContactInfo(user.getContactInfo());
        myView.setBirthdate(String.valueOf(user.getBirthDate()));
        myView.addObserver(this);

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

        newUser.setUserName(values[0].trim());
        newUser.setPassword(values[1]);
        newUser.setFirstName(values[2]);
        newUser.setLastName(values[3]);
        newUser.setCity(values[4]);
        newUser.setContactInfo(values[5]);
        newUser.setBirthDate(date);
        return newUser;
    }



    private User generateUserFromFields(){
        System.out.println("ControllerCreateUser: generateUser");
        myView.setResult_lbl("");
        String userName = myView.getUserName();

        String password = myView.getPassword();
        String firstName = myView.getFirstName();
        String lastName = myView.getLastName();
        String city = myView.getCityName();
        String contactInfo = myView.getContactInfo();
        String[] values = {userName,password,firstName,lastName,city,contactInfo};

        int date = 0;
        LocalDate birthDay = myView.getBirthday();
        if (birthDay != null){
            LocalDate eighteenYears = LocalDate.now().minusYears(18);
            if(birthDay.isBefore(eighteenYears)){
                date = this.convertDateStringToInt(birthDay.toString());
            }else{
                return null;
            }

        }
        return createUserIfValuesAreValid(values,date);
    }


    public void saveInfo() {
        System.out.println("ControllerCreateUse: saveInfo");

        User newUser = generateUserFromFields();
        if (newUser != null){
            myModel.createNewData(newUser);
        }else{
            myView.setResult_lbl("Please fill all the fields correctly..");
        }
    }


    private void updateInfo() {
        User newUser = generateUserFromFields();
        if (newUser != null) {
            if (!checkUpdateUserName(newUser.getUserName(), updateUserName)) {
                myView.setResult_lbl("User name already exist");
            }
            else{
                myModel.updateTable(newUser);
            }
        } else
            myView.setResult_lbl("Please fill all the fields..");

    }


    /**
     * checks if the new UserName is a valid UserName
     * @param newUserName
     * @param oldUserName
     * @return - true if its valid. meaning its the same as the old. if it isn't the same it checks that the new name doesn't already exist in the DB
     */
    private boolean checkUpdateUserName(String newUserName, String oldUserName){
        if(!newUserName.equals(oldUserName)){
            User user = ((UserModel)myModel).readUser(oldUserName);
            if(user != null){
                return false;
            }
        }
        return true;
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
        System.out.println("ControllerCreateUser: update by myView");
        if(arg.equals("saveInfo")){
            if(status.equals("create")) {
                saveInfo();
            }else if(status.equals("update")){
                updateInfo();
            }
        }else if (o == myModel){
            System.out.println("ControllerCreateUser: update by userModel");

            if(arg.equals(DBResult.ADDED)) {
                myView.setResult_lbl("");
                closeStage();
            }else if (arg.equals(DBResult.UPDATED)){
                myView.setResult_lbl("");
                closeStage();
            }else if (arg.equals(DBResult.ALREADY_EXIST)){
                myView.setResult_lbl("User already exists");
            }else if(arg.equals(DBResult.ERROR)){
                myView.setResult_lbl("Error while creating user");
            }
        }
    }

}
