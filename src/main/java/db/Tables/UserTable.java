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
                    case COLUMN_TABLE_KEY:
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
        String sql = "INSERT INTO " + TABLE_NAME + "(" + COLUMN_TABLE_KEY + "," + COLUMN_USERTABLE_PASS + "," + COLUMN_USERTABLE_FIRST_NAME + "," + COLUMN_USERTABLE_LAST_NAME + "," + COLUMN_USERTABLE_CITY + "," + COLUMN_USERTABLE_BIRTHDAY + ") VALUES(?,?,?,?,?,?)";
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


//    @Override
//    protected PreparedStatement getUpdatePreparedStatement(String[] set, String[] values, String [] where, Connection connection) {
//        String sql = "UPDATE " + TABLE_NAME + " SET ";
//        sql += appendSql(set);
//        sql += "WHERE " + appendSql(where);
//        PreparedStatement pstmt = null;
//        if (connection != null) {
//            try {
//                pstmt = connection.prepareStatement(sql);
//                for (int i = 0; i < values.length; i++) {
//                    pstmt.setString(i+1,values[i]);
//                }
//                return pstmt;
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//                try {
//                    connection.rollback();
//                } catch (SQLException ex) {
//                    System.out.println(ex.getMessage());
//                }
//            }
//        }
//        return null;
//    }
//
//    private String appendSql(String[] strings) {
//        String s = "";
//        for (int i = 0; i < strings.length; i++) {
//            s+= strings[i] + " = ?";
//            if (i<strings.length-1)
//                s+=", ";
//        }
//        return s;
//    }

    @Override
    public DBResult createTable() {
        String[] parameters = {COLUMN_TABLE_KEY + " text PRIMARY KEY"
                , COLUMN_USERTABLE_PASS + " text NOT NULL"
                , COLUMN_USERTABLE_FIRST_NAME + " text NOT NULL"
                , COLUMN_USERTABLE_LAST_NAME + " text NOT NULL"
                , COLUMN_USERTABLE_CITY + " text NOT NULL"
                , COLUMN_USERTABLE_BIRTHDAY + " integer NOT NULL" /*CHECK DATE PARAMETER*/};
        return super.createTable(parameters);
    }

//    protected PreparedStatement getDeletePreparedStatement(String where, Connection connection){
//        String sql = "DELETE FROM "+ TABLE_NAME + " WHERE " +  where ;
//        PreparedStatement pstmt;
//        if (connection != null) {
//            try {
//                pstmt = connection.prepareStatement(sql);
//                return pstmt;
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//                try {
//                    connection.rollback();
//                } catch (SQLException ex) {
//                    System.out.println(ex.getMessage());
//                }
//            }
//        }
//        return null;
//    }


}
