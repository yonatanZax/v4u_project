package Controllers;

import Model.ACRUDModel;
import Model.User.UserModel;
import View.CRUDViews.UserCRUDView;
import View.LoginView;
import db.DBResult;
import Model.User.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import MainPackage.Enum_CRUD;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


public class ControllerUserCRUD implements Observer {

    private ControllerCreateUser controllerCreateUser;
    private UserCRUDView myView;
    private LoginView loginView;
    private ACRUDModel myModel;
    private Stage stage;
    private Parent root;
    private FXMLLoader fxmlLoader;


    public ControllerUserCRUD() {
        myModel = new UserModel();
        myModel.addObserver(this);
        controllerCreateUser = new ControllerCreateUser(myModel);


        stage = new Stage();
        fxmlLoader = new FXMLLoader(getClass().getResource("/user_crud_view.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
    }
        Scene scene = new Scene(root,400,400);
        stage.setScene(scene);

        myView = fxmlLoader.getController();
        myView.addObserver(this);

    }


    public void readUser() {
        String textField = myView.userName.getText().trim();
        if (textField.equals("")){
            myView.status_lbl.setText("Please insert a valid userName..");
        }
        else{
            String userName = textField;
            User user = ((UserModel)myModel).readUser(userName);
            if (user == null) {
                // Empty list means that not even one of the list was in the db
                myView.info_lbl.setText(myView.info_lblTitle + "");
                myView.status_lbl.setText(textField + " is not stored in DB..");

            } else {
                // Generate the list of users as String to print
                myView.info_lbl.setText(myView.info_lblTitle + user.toString());
                myView.status_lbl.setText("");
            }
        }

    }



/*    private void errorWindow(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }*/



    public void createUser(){
        controllerCreateUser.openCreate();
    }


    public void updateUser(){
        String textField = myView.userName.getText().trim();
        if (textField.equals("")) {
            myView.status_lbl.setText("Please insert a valid userName..");
            return;
        }
        String userName = myView.userName.getText().trim();
        User user = ((UserModel)myModel).readUser(userName);
        if (user == null){
            myView.info_lbl.setText(myView.info_lblTitle + "");
            myView.status_lbl.setText(myView.info_lblTitle + userName + " Doesn't exist");
        }else{
            controllerCreateUser.openUpdate(user);
        }
    }

    public void deleteUser() {
        String id = myView.userName.getText();
        ((UserModel)myModel).deleteUser(id);
    }


    @Override
    public void update(Observable o, Object arg){
        System.out.println("ControllerUserCRUD: update by UserCRUDView");

        if (o.equals(myView)) {

            if (arg.equals(Enum_CRUD.READ)) {
                readUser();
            } else if (arg.equals(Enum_CRUD.CREATE)) {
                try {
                    createUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (arg.equals(Enum_CRUD.UPDATE)) {
                try {
                    updateUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (arg.equals(Enum_CRUD.DELETE)) {
                deleteUser();
            }
        }


        else if(o == myModel){
            myView.resetLabels();
            if(arg == DBResult.UPDATED){
                myView.status_lbl.setText("User was updated successfully");
            }
            else if ( arg == DBResult.DELETED){
                myView.status_lbl.setText("User was deleted successfully");
            }
            else if(arg == DBResult.NOTHING_TO_DELETE){
                myView.status_lbl.setText("User wasn't in DB");
            }
            else if(arg == DBResult.ADDED){
                myView.status_lbl.setText("User was created successfully");
            }

            // Todo - add not updated message
        }

    }

    public void showStage(){
        stage.show();
    }


}
