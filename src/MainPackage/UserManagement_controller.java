package MainPackage;

import db.Managers.DBResult;
import db.User;
import db.UserTable_Model;

import java.util.List;

public class UserManagement_controller {

    private UserTable_Model userTable;

    public UserManagement_controller(){
        userTable = UserTable_Model.getInstance();
    }


    public String[] readUsers(String[] listOfUserName){
        // Users from the db, only if the user exists.
        List<User> usersFromDB = userTable.readUsers(listOfUserName);
        if (usersFromDB == null){
            return null;
        }else if(usersFromDB.size() == 0){
            String[] users = {listOfUserName.toString() + " is/are not stored in DB..\""};
            return users;
        }

        String[] users = new String[usersFromDB.size()];
        for (int i = 0; i < usersFromDB.size(); i++) {
            users[i] = usersFromDB.toString();
        }
        return users;

    }

    public String deleteUser(String userId){
        String deleted = "";
        DBResult result = UserTable_Model.getInstance().DeleteFromTable(userId);
        if(result == DBResult.NOTHING_TO_DELETE){
            deleted = userId + " is not in the DB";
        } else if(result == DBResult.DELETED){
            deleted = userId + " Deleted";
        }

        return deleted;
    }



}
