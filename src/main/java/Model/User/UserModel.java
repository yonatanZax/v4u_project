package Model.User;

import Model.ACRUDModel;
import db.DBResult;
import db.Tables.UserTable;

import java.util.List;

public class UserModel extends ACRUDModel<User> {

    private UserTable userTable;

    public UserModel() {
        super.setTableManager(UserTable.getInstance());
        userTable = UserTable.getInstance();
    }


    public User readUser(String primaryKey){
        String[] listOfUserNames = {primaryKey};
        List<User> userList = readDataFromDB(listOfUserNames);
        if(userList != null && userList.size() > 0 ){
            return userList.get(0);
        }
        return null;
    }


    @Override
    public void updateTable(User user) {
        DBResult result = DBResult.NONE;
        String [] where = {userTable.COLUMN_USERTABLE_KEY};
        String [] set = {userTable.COLUMN_USERTABLE_KEY, userTable.COLUMN_USERTABLE_PASS,
                userTable.COLUMN_USERTABLE_FIRST_NAME, userTable.COLUMN_USERTABLE_LAST_NAME,
                userTable.COLUMN_USERTABLE_CITY, userTable.COLUMN_USERTABLE_BIRTHDAY};
        String [] values = {user.getUserName(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getCity(), String.valueOf(user.getBirthDate()), user.getUserName()};
        result = userTable.updateData(set , values, where);
        setChanged();
        notifyObservers(result);
    }


    public List<User> readDataFromDB(String[] listOfKeys){
        // Generates the selection part
        // Example:     SELECT * FROM <TableName> WHERE key IN ("key1","key2")
        String selection = UserTable.COLUMN_USERTABLE_KEY + " IN (";
        for (int i=0 ; i < listOfKeys.length - 1; i++) {
            selection += "\"" + listOfKeys[i] + "\",";
        }
        selection += "\"" + listOfKeys[listOfKeys.length-1] + "\")";

        // Get the list of users from the database
        List<User> dataList = userTable.select(null,selection,null);
        return dataList;
    }


    public void deleteDataFromDB(String keys[][]) {
        String where = keys[keys.length - 1][0] + " = " + "\"" + keys[keys.length - 1][1] + "\"";
        DBResult result = userTable.deleteFromTable(where);
        setChanged();
        notifyObservers(result);
    }

    public void deleteUser(String key){
        String[][] keys = {{UserTable.COLUMN_USERTABLE_KEY,key}};
        deleteDataFromDB(keys);
    }


}
