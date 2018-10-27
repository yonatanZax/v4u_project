package MainPackage;

import db.Managers.DBResult;
import db.User;
import db.UserTable;

import java.io.IOException;
import java.util.List;
import java.util.Observable;

public class UserModel extends Observable{

    private static UserModel userModel;
    private UserTable userTable = UserTable.getInstance();
    //private ControllerCreateUser userController = new ControllerCreateUser();
    private User createdUser;



    public static UserModel getInstance(){
        if (userModel == null){
            return new UserModel();
        }
        return userModel;
    }


    public List<User> readUsers(String[] userNames){
        // Todo (DONE) - change to read multiple users
        List<User> userList = userTable.readUsers(userNames);
        return userList;
    }


    public void createUser(User newUser){
        DBResult result = DBResult.NONE;

        if (newUser != null) {
            result = userTable.InsertToTable(newUser);

            if (result == DBResult.ADDED) {
                createdUser = newUser;
                setChanged();
                notifyObservers("Added");
            } else if (result == DBResult.ALREADY_EXIST) {
                setChanged();
                notifyObservers("Exists");
            } else if (result == DBResult.ERROR) {
                setChanged();
                notifyObservers("Error");
            }
        }
    }

    public void deleteUser(){

    }

    public DBResult updateUser(User newUser){
        return userTable.updateUser(newUser);
    }

    public List<User> getUserList(String userName){
        return UserTable.getInstance().select(null, UserTable.COLUMN_USERTABLE_USER_NAME + " = \"" + userName + "\"", null);
    }



}
