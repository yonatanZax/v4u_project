package db.Tables;

import Model.User.User;
import db.DBResult;
import db.Managers.ATableManager;
import db.Managers.DBManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserTable extends ATableManager<User> {

    private static UserTable ourInstance;

    public static final String COLUMN_USERTABLE_KEY = "key";
    public static final String COLUMN_USERTABLE_PASS = "password";
    public static final String COLUMN_USERTABLE_FIRST_NAME = "firstName";
    public static final String COLUMN_USERTABLE_LAST_NAME = "lastName";
    public static final String COLUMN_USERTABLE_CITY = "city";
    public static final String COLUMN_USERTABLE_BIRTHDAY = "birthday";
    public static final String COLUMN_USERTABLE_CONTACT_INFO = "contactInfo";


    // Singleton
    public static UserTable getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserTable();
        }
        return ourInstance;
    }

    private UserTable() {
        super(DBManager.getInstance(), "userInfo");
        createTable();
    }

    @Override
    protected List<User> transformListMapToList(List<Map<String, String>> listMap) {

        List<User> list = new ArrayList<>(listMap.size());
        for (Map<String, String> map : listMap) {
            User user = new User();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                switch (key) {
                    case COLUMN_USERTABLE_BIRTHDAY:
                        String dateString = entry.getValue();
                        int dateAsInt = Integer.parseInt(dateString);
                        user.setBirthDate(dateAsInt);
                        break;
                    case COLUMN_USERTABLE_CITY:
                        user.setCity(entry.getValue());
                        break;

                    case COLUMN_USERTABLE_FIRST_NAME:
                        user.setFirstName(entry.getValue());
                        break;
                    case COLUMN_USERTABLE_LAST_NAME:
                        user.setLastName(entry.getValue());

                        break;
                    case COLUMN_USERTABLE_KEY:
                        user.setUserName(entry.getValue());
                        break;
                    case COLUMN_USERTABLE_PASS:
                        user.setPassword(entry.getValue());
                        break;
                    case COLUMN_USERTABLE_CONTACT_INFO:
                        user.setContactInfo(entry.getValue());
                        break;
                }

            }
            list.add(user);
        }
        return list;
    }


    @Override
    protected PreparedStatement getInsertPreparedStatement(User object, Connection connection) {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_USERTABLE_KEY + "," + COLUMN_USERTABLE_PASS + "," + COLUMN_USERTABLE_FIRST_NAME + "," + COLUMN_USERTABLE_LAST_NAME + "," + COLUMN_USERTABLE_CITY + "," + COLUMN_USERTABLE_CONTACT_INFO + "," +
                COLUMN_USERTABLE_BIRTHDAY + ") VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, object.getUserName());
                pstmt.setString(2, object.getPassword());
                pstmt.setString(3, object.getFirstName());
                pstmt.setString(4, object.getLastName());
                pstmt.setString(5, object.getCity());
                pstmt.setObject(6, object.getContactInfo());
                pstmt.setObject(7, object.getBirthDate());
                return pstmt;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return null;
    }


    @Override
    public DBResult createTable() {
        String[] primaryKeys = {COLUMN_USERTABLE_KEY};
        String[] foreignKeys = {};
        String[] stringFields = {COLUMN_USERTABLE_KEY, COLUMN_USERTABLE_PASS, COLUMN_USERTABLE_FIRST_NAME, COLUMN_USERTABLE_LAST_NAME, COLUMN_USERTABLE_CITY, COLUMN_USERTABLE_CONTACT_INFO};
        String[] intFields = {COLUMN_USERTABLE_BIRTHDAY};
        String[] doubleFields = {};
        return super.createTable(primaryKeys, foreignKeys, stringFields, intFields, doubleFields);
    }


}
