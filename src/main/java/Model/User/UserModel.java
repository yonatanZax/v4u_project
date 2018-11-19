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
        String [] where = {userTable.COLUMN_TABLE_KEY};
        String [] set = {userTable.COLUMN_TABLE_KEY, userTable.COLUMN_USERTABLE_PASS,
                userTable.COLUMN_USERTABLE_FIRST_NAME, userTable.COLUMN_USERTABLE_LAST_NAME,
                userTable.COLUMN_USERTABLE_CITY, userTable.COLUMN_USERTABLE_BIRTHDAY};
        String [] values = {user.getUserName(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getCity(), String.valueOf(user.getBirthDate()), user.getUserName()};
        result = userTable.updateData(set , values, where);
        setChanged();
        notifyObservers(result);
    }
}
