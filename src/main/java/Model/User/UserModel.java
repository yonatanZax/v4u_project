package Model.User;

import Model.ACRUDModel;
import Model.Request.RequestModel;
import db.DBResult;
import db.Tables.UserTable;

import java.util.List;

public class UserModel extends ACRUDModel<User> {

    private static User loggedInUser = null;

    public UserModel() {
        super.setTableManager(UserTable.getInstance());
    }


    public boolean tryToLogin(String userName, String password){
        String where = UserTable.COLUMN_USERTABLE_KEY + " = " + userName;
        where += " AND ";
        where += UserTable.COLUMN_USERTABLE_PASS + " = " + password;
        List<User> list = UserTable.getInstance().select(null,where,null);
        if (list.size() > 0) {
            loggedInUser = list.get(0);
            return true;
        }

        return false;
    }

    public static boolean isLoggedIn(){
        return loggedInUser != null;
    }

    public static void logOff(){
        loggedInUser = null;
    }

    public static String getUserName(){
        return loggedInUser.getUserName();
    }

    public static void setLoggedInUserName(String userName){
        loggedInUser = new User();
        loggedInUser.setUserName(userName);
    }


    public User readUser(String primaryKey){
        String[][] listOfUserNames = {{UserTable.COLUMN_USERTABLE_KEY,primaryKey}};
        List<User> userList = readDataFromDB(listOfUserNames);
        if(userList != null && userList.size() > 0 ){
            return userList.get(0);
        }
        return null;
    }


    @Override
    public void updateTable(User user) {
        DBResult result = DBResult.NONE;
        String [] whereFields = {UserTable.COLUMN_USERTABLE_KEY};
        String [] whereValues = {user.getUserName()};
        String [] set = {UserTable.COLUMN_USERTABLE_KEY, UserTable.COLUMN_USERTABLE_PASS,
                UserTable.COLUMN_USERTABLE_FIRST_NAME, UserTable.COLUMN_USERTABLE_LAST_NAME,
                UserTable.COLUMN_USERTABLE_CITY, UserTable.COLUMN_USERTABLE_BIRTHDAY};
        String [] values = {user.getUserName(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getCity(), String.valueOf(user.getBirthDate()), user.getUserName()};
        result = tableManager.updateData(set , values, whereFields,whereValues);
        setChanged();
        notifyObservers(result);
    }


    public List<User> readDataFromDB(String[][] whereData){
        // Generates the selection part
        // Example:     SELECT * FROM <TableName> WHERE key IN ("key1","key2")
        String selection = UserTable.COLUMN_USERTABLE_KEY + " IN (";
        for (int i=0 ; i < whereData.length - 1; i++) {
            selection += "\"" + whereData[i][1] + "\",";
        }
        selection += "\"" + whereData[whereData.length-1][1] + "\")";

        // Get the list of users from the database
        List<User> dataList = tableManager.select(null,selection,null);
        return dataList;
    }


//    public void deleteDataFromDB(String keys[][]) {
//        String where = keys[keys.length - 1][0] + " = " + "\"" + keys[keys.length - 1][1] + "\"";
//        DBResult result = tableManager.deleteFromTable(where);
//        setChanged();
//        notifyObservers(result);
//    }

    public void deleteUser(String key){
        String[][] keys = {{UserTable.COLUMN_USERTABLE_KEY,key}};
        deleteDataFromDB(keys);
    }


}
