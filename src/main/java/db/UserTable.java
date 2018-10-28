package db;

import Model.User;
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

    public static final String COLUMN_USERTABLE_USER_NAME = "userName";
    public static final String COLUMN_USERTABLE_PASS = "password";
    public static final String COLUMN_USERTABLE_FIRST_NAME = "firstName";
    public static final String COLUMN_USERTABLE_LAST_NAME = "lastName";
    public static final String COLUMN_USERTABLE_CITY = "city";
    public static final String COLUMN_USERTABLE_BIRTHDAY = "birthday";


    // Singleton
    public static UserTable getInstance() {
        if(ourInstance == null) {
            ourInstance = new UserTable();
        }
        return ourInstance;
    }

    private UserTable() {
        super(DBManager.getInstance(),"userInfo");
        createTable();
    }

    @Override
    protected List<User> transformListMapToList(List<Map<String, String>> listMap) {

        List<User> list = new ArrayList<>(listMap.size());
        for(Map<String,String> map : listMap){
            User user = new User();
            for(Map.Entry<String,String> entry : map.entrySet()){
                String key = entry.getKey();
                switch (key){
                    case COLUMN_USERTABLE_BIRTHDAY:
                        String dateString = entry.getValue();
                        int dateAsInt = Integer.parseInt(dateString);
                        user.setBirthDate(dateAsInt);
                        break;
                    case COLUMN_USERTABLE_CITY:
                        user.setCity( entry.getValue());
                        break;

                    case COLUMN_USERTABLE_FIRST_NAME:
                        user.setFirstName( entry.getValue());
                        break;
                    case COLUMN_USERTABLE_LAST_NAME:
                        user.setLastName( entry.getValue());

                        break;
                    case COLUMN_USERTABLE_USER_NAME:
                        user.setUserName( entry.getValue());
                        break;
                    case COLUMN_USERTABLE_PASS:
                        user.setPassword( entry.getValue());
                        //TODO (Keep for Part1) - delete this, we don't want to return password never

                        break;
                }

            }
            list.add(user);
        }
        return list;
    }



    @Override
    protected PreparedStatement getInsertPreparedStatement(User object, Connection connection) {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_USERTABLE_USER_NAME + "," + COLUMN_USERTABLE_PASS + "," + COLUMN_USERTABLE_FIRST_NAME + "," + COLUMN_USERTABLE_LAST_NAME + "," + COLUMN_USERTABLE_CITY + "," + COLUMN_USERTABLE_BIRTHDAY + ") VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, object.getUserName());
                pstmt.setString(2, object.getPassword());
                pstmt.setString(3, object.getFirstName());
                pstmt.setString(4, object.getLastName());
                pstmt.setString(5, object.getCity());
                pstmt.setObject(6, object.getBirthDate());
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
    protected PreparedStatement getUpdatePreparedStatement(User user, Connection connection) {
        String sql = "UPDATE " + TABLE_NAME + " SET " + COLUMN_USERTABLE_USER_NAME + " = ? , "
                + COLUMN_USERTABLE_PASS + " = ? , "
                + COLUMN_USERTABLE_FIRST_NAME + " = ? , "
                + COLUMN_USERTABLE_LAST_NAME + " = ? , "
                + COLUMN_USERTABLE_CITY + " = ? , "
                + COLUMN_USERTABLE_BIRTHDAY + " = ?  " +
                "WHERE " + COLUMN_USERTABLE_USER_NAME + " = ?" ;
        PreparedStatement pstmt = null;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, user.getUserName());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getFirstName());
                pstmt.setString(4, user.getLastName());
                pstmt.setString(5, user.getCity());
                pstmt.setObject(6, user.getBirthDate());
                pstmt.setString(7, user.getUserName());
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
        String[] parameters = {COLUMN_USERTABLE_USER_NAME + " text PRIMARY KEY"
                , COLUMN_USERTABLE_PASS + " text NOT NULL"
                , COLUMN_USERTABLE_FIRST_NAME + " text NOT NULL"
                , COLUMN_USERTABLE_LAST_NAME + " text NOT NULL"
                , COLUMN_USERTABLE_CITY + " text NOT NULL"
                , COLUMN_USERTABLE_BIRTHDAY + " integer NOT NULL" /*CHECK DATE PARAMETER*/};
        return super.createTable(parameters);
    }

    protected PreparedStatement getDeletePreparedStatement(String id, Connection connection){
        String sql = "DELETE FROM "+ TABLE_NAME + " WHERE " +  COLUMN_USERTABLE_USER_NAME + " =  ?" ;
        PreparedStatement pstmt;
        if (connection != null) {
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, id);
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






    public static void main(String[] args) {
        UserTable userTable = getInstance();
        userTable.createTable();
        User user = new User();
        user.setUserName("USERNAME1");
        user.setPassword("PASSWORD");
        user.setFirstName("FNAME");
        user.setLastName("LNAME");
        user.setCity("CITY");
        java.util.Date today = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(today.getTime());
        user.setBirthDate(20101105);
        userTable.InsertToTable(user);
        String projection = null;
        String selection = null;
        String orderBy = null;

        List<User> userList = userTable.select(projection,selection,orderBy);

        System.out.println(userList);
    }

}
