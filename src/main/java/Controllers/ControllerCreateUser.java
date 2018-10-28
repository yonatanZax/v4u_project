package Controllers;

import Model.UserModel;
import View.UserDetailsView;
import db.DBResult;
import Model.User;
import db.UserTable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


public class ControllerCreateUser implements Observer{

    private UserDetailsView myView;
    private UserModel myModel;
    private String status;
    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;


    public ControllerCreateUser() {
        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/createUser_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root,400,400);
        stage.setScene(scene);
        myView = fxmlLoader.getController();

        myModel = new UserModel();
        myModel.addObserver(this);
    }

    public void openCreate() throws IOException {
        status = "create";
        //stage = new Stage();
        stage.setTitle("Create user");
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/createUser_view.fxml"));

        //root = fxmlLoader.load();
        UserDetailsView userDetailsView = fxmlLoader.getController();
        userDetailsView.resetAll();
        userDetailsView.addObserver(this);

        //Scene scene = new Scene(root,400,400);
        //stage.setScene(scene);
        stage.show();
    }

    private String updateUserName = null;

    public void openUpdate(User user) throws IOException {
        updateUserName = user.getUserName();

        status = "update";
        //stage = new Stage();
        stage.setTitle("Update user");
        //root = fxmlLoader.load();
        myView = fxmlLoader.getController();

        myView.setUserName(user.getUserName());
        myView.setFirstName(user.getFirstName());
        myView.setLastName(user.getLastName());
        myView.setCityName(user.getCity());
        myView.setPassword(user.getPassword());
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

        newUser.setUserName(values[0]);
        newUser.setPassword(values[1]);
        newUser.setFirstName(values[2]);
        newUser.setLastName(values[3]);
        newUser.setCity(values[4]);
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
        String[] values = {userName,password,firstName,lastName,city};

        //int birthDay = 20180505;
        int date = 0;
        if (myView.getBirthday() != null){
            date = this.convertDateStringToInt(myView.getBirthday().toString());
        }
        // TODO (DONE) - get date as int
        return createUserIfValuesAreValid(values,date);
    }



    public void saveInfo() {
        System.out.println("ControllerCreateUse: saveInfo");

        User newUser = generateUserFromFields();
        if (newUser != null){
            myModel.createUser(newUser);
        }else{
            myView.setResult_lbl("Please fill all the fields..");
        }


    }

    //TODO - make sure we make updateUserName null where ever its needed
    private void updateInfo(){

        String userName = myView.getUserName();
        if(!userName.equals(updateUserName)){
            //TODO - if the new name already exists show an error
        }

        User newUser = generateUserFromFields();
        if (newUser != null){
            myModel.updateUser(newUser);
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
                myView.setResult_lbl("User was added successfully");
                closeStage();
            }else if (arg.equals(DBResult.UPDATED)){
                myView.setResult_lbl("User was Updated successfully");
                closeStage();
            }else if (arg.equals(DBResult.ALREADY_EXIST)){
                myView.setResult_lbl("User already exists");
            }else if(arg.equals(DBResult.ERROR)){
                myView.setResult_lbl("Error while creating user");
            }
        }
    }

}
