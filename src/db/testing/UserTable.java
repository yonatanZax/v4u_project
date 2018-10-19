package db.testing;

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


    public static UserTable getInstance() {
        if(ourInstance == null) {
            ourInstance = new UserTable();
        }
        return ourInstance;
    }

    private UserTable() {
        super(DBManager.getInstance(),"userInfo");
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
                        String[] dateArr = dateString.split("-");
                        java.sql.Date date = new java.sql.Date(Integer.valueOf(dateArr[0]),Integer.valueOf(dateArr[1]),Integer.valueOf(dateArr[2]));
                        user.setBirthDate(date);
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
                        //TODO - delete this, we don't want to return password never

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
    public boolean createTable() {
        String[] parameters = {COLUMN_USERTABLE_USER_NAME + " text PRIMARY KEY"
                , COLUMN_USERTABLE_PASS + " text NOT NULL"
                , COLUMN_USERTABLE_FIRST_NAME + " text NOT NULL"
                , COLUMN_USERTABLE_LAST_NAME + " text NOT NULL"
                , COLUMN_USERTABLE_CITY + " text NOT NULL"
                , COLUMN_USERTABLE_BIRTHDAY + " date NOT NULL" /*CHECK DATE PARAMETER*/};
        return super.createTable(parameters);
    }

    @Override
    public boolean DeleteFromTable(String id) {
        //TODO - implement
        return false;
    }

    @Override
    public int updateData(String where, String set) {
        //TODO - implement
        return 0;
    }
}
