package Model;

import db.UserTable;

import java.util.Observable;

public class UserModel extends Observable {

    private UserTable userTable;

    public UserModel() {
        userTable = UserTable.getInstance();
    }

    public void createUser(User user){

    }

    public User readUser(String id){
        return null;
    }

    public void updateUser(User user){

    }

    public void deleteUser(String id){

    }
}
