package Model;

import db.DBResult;
import db.UserTable;

import java.util.List;
import java.util.Observable;

public class UserModel extends Observable {

    private UserTable userTable;

    public UserModel() {
        userTable = UserTable.getInstance();
    }

    public void createUser(User user){
        DBResult result = DBResult.NONE;

        if (user != null) {
            result = userTable.InsertToTable(user);
            setChanged();
            notifyObservers(result);
        }
    }

    public User readUser(String primaryKey){
        String[] listOfUserNames = {primaryKey};
        List<User> userList = readUsers(listOfUserNames);
        if(userList != null && userList.size() > 0 ){
            return userList.get(0);
        }
        return null;
    }


    public List<User> readUsers(String[] listOfUsername) {
        // Generates the selection part
        // Example:     SELECT * FROM userInfo WHERE userName IN ("user1","user2")
        String selection = UserTable.COLUMN_USERTABLE_USER_NAME + " IN (";
        for (int i=0 ; i < listOfUsername.length - 1; i++) {
            selection += "\"" + listOfUsername[i] + "\",";
        }
        selection += "\"" + listOfUsername[listOfUsername.length-1] + "\")";

        // Get the list of users from the database
        List<User> userList = userTable.select(null,selection,null);
        return userList;
    }

    public void updateUser(User user){
        DBResult result = DBResult.NONE;
        String [] where = {userTable.COLUMN_USERTABLE_USER_NAME};
        String [] set = {userTable.COLUMN_USERTABLE_USER_NAME, userTable.COLUMN_USERTABLE_PASS,
                userTable.COLUMN_USERTABLE_FIRST_NAME, userTable.COLUMN_USERTABLE_LAST_NAME,
                userTable.COLUMN_USERTABLE_CITY, userTable.COLUMN_USERTABLE_BIRTHDAY};
        String [] values = {user.getUserName(), user.getPassword(), user.getFirstName(),
                            user.getLastName(), user.getCity(), String.valueOf(user.getBirthDate()), user.getUserName()};
        result = userTable.updateData(set , values, where);
        setChanged();
        notifyObservers(result);
    }

    public void deleteUser(String userName) {
        DBResult result = userTable.deleteFromTable(UserTable.COLUMN_USERTABLE_USER_NAME + " = " + "\"" + userName + "\"");
        setChanged();
        notifyObservers(result);
    }
}
