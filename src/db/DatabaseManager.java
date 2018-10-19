package db;

import db.testing.User;

import java.sql.*;
import java.util.*;

public class DatabaseManager {

    public static final String DATABASE = "v4u_db";
    public static final String PATH_DB = "jdbc:sqlite:" + System.getProperty("user.dir") + "/db/";
    public static final String TABLE_USER = "userInfo";

    public static final String COLUMN_USERTABLE_USER_NAME = "userName";
    public static final String COLUMN_USERTABLE_PASS = "password";
    public static final String COLUMN_USERTABLE_FIRST_NAME = "firstName";
    public static final String COLUMN_USERTABLE_LAST_NAME = "lastName";
    public static final String COLUMN_USERTABLE_CITY = "city";
    public static final String COLUMN_USERTABLE_BIRTHDAY = "birthday";

    /**
     * TODO - add if exists for the db
     * Connect to a sample database
     * currently works only with 'DatabaseManager.DATABASE'
     */
    public static void createNewDatabase() {

        String url = PATH_DB + DATABASE;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the test database
     * currently works only with 'DatabaseManager.TABLE_USER'
     */
    public static void createNewTable(String tableName) {
        // SQLite connection string
        String url = PATH_DB + tableName;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + COLUMN_USERTABLE_USER_NAME + " text PRIMARY KEY,\n"
                + COLUMN_USERTABLE_PASS + " text NOT NULL,\n"
                + COLUMN_USERTABLE_FIRST_NAME + " text NOT NULL,\n"
                + COLUMN_USERTABLE_LAST_NAME + " text NOT NULL,\n"
                + COLUMN_USERTABLE_CITY + " text NOT NULL,\n"
                + COLUMN_USERTABLE_BIRTHDAY + " date NOT NULL\n" /*CHECK DATE PARAMETER*/
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private static Connection connect() {
        // SQLite connection string
        String url = PATH_DB + TABLE_USER;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static Connection connect(String tableName) {
        // SQLite connection string
        String url = PATH_DB + TABLE_USER;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    /**
     * Insert a new row into the warehouses table
     *
     */
    public static void insert(User user) {
        String sql = "INSERT INTO " + TABLE_USER + "(" + COLUMN_USERTABLE_USER_NAME + "," + COLUMN_USERTABLE_PASS + "," + COLUMN_USERTABLE_FIRST_NAME + "," + COLUMN_USERTABLE_LAST_NAME + "," + COLUMN_USERTABLE_CITY + "," + COLUMN_USERTABLE_BIRTHDAY + ") VALUES(?,?,?,?,?,?)";
        Connection conn = connect();
        if (conn != null) {
            try {
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, user.getUserName());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getFirstName());
                pstmt.setString(4, user.getLastName());
                pstmt.setString(5, user.getCity());
                pstmt.setObject(6, user.getBirthDate());

                pstmt.executeUpdate();
                pstmt.closeOnCompletion();
                conn.commit();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            } finally {
                closeConnection(conn);
            }
        }

    }

    private static void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
     * select all rows in the warehouses table
     * TODO - fix this function
     */
    public void selectAll(String tableName){
        String sql = "SELECT * FROM " + tableName;
        Connection conn = null;
        Statement stmt  = null;
        ResultSet rs    = null;
        try {
            conn = this.connect();
            if(conn != null) {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                // loop through the result set
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + "\t" +
                            rs.getString("name") + "\t" +
                            rs.getDouble("capacity"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally{
            try {
                if (conn != null)
                    closeConnection(conn);
                if(stmt != null )
                    stmt.close();
                if(rs != null)
                    rs.close();
            }catch (SQLException ee){
                ee.printStackTrace();
            }
        }
    }

    /**
     * A Generic method to make any query on the 'userInfo' table.
     * any query of the type: 'SELECT ??? FROM userInfo WHERE ??? ORDERBY'
     * Use 'DatabaseManager.COLUMN_TABLEUSE_???' to insert arguments to projection or selection.
     * The index in 'selection' is refering the index in 'selectionArgs'
     * @param projection - What columns you want the query to return
     * @param selection
     * @param selectionArgs
     * @param orderBy
     * @return A list of Users
     */
    public static List<User> queryUserTable(String[] projection, String[] selection, String[] selectionArgs, String orderBy){
        String sqlQuery = createSQLQuery(TABLE_USER, projection, selection, selectionArgs, orderBy);
        Connection connection = connect(TABLE_USER);
        List<User> list = null;
        if(connection != null){
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Map<String,Object>> mapList = map(resultSet);
                list = getUserList(mapList);
            }catch ( SQLException e){
                e.printStackTrace();
            }
            finally {
                closeConnection(connection);
            }
        }
        return list;
    }

    private static List<Map<String, Object>> map(ResultSet rs) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        try {
            if (rs != null) {
                ResultSetMetaData meta = rs.getMetaData();
                int numColumns = meta.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<String, Object>();
                    for (int i = 1; i <= numColumns; ++i) {
                        String name = meta.getColumnName(i);
                        Object value = rs.getObject(i);
                        row.put(name, value);
                    }
                    results.add(row);
                }
            }
        } finally {
            rs.close();
        }
        return results;
    }




    /**
     * This method transfrom the list of maps to a list of users
     * @param mapList - each map represents a user, the keys in the map is the columns and the object is the data
     * @return
     */
    private static List<User> getUserList(List<Map<String,Object>> mapList) {
        List<User> list = new ArrayList<>(mapList.size());
        for(Map<String,Object> map : mapList){
            User user = new User();
            for(Map.Entry<String,Object> entry : map.entrySet()){
                String key = entry.getKey();
                switch (key){
                    case COLUMN_USERTABLE_BIRTHDAY:
                        java.sql.Date date = new java.sql.Date((Long)entry.getValue());
                        user.setBirthDate(date);
                        break;
                        case COLUMN_USERTABLE_CITY:
                            user.setCity((String) entry.getValue());
                            break;

                            case COLUMN_USERTABLE_FIRST_NAME:
                            user.setFirstName((String) entry.getValue());
                        break;
                        case COLUMN_USERTABLE_LAST_NAME:
                            user.setLastName((String) entry.getValue());

                        break;
                        case COLUMN_USERTABLE_USER_NAME:
                            user.setUserName((String) entry.getValue());
                        break;
                        case COLUMN_USERTABLE_PASS:
                            user.setPassword((String) entry.getValue());
                            //TODO - delete this, we don't want to return password never

                            break;
                }

            }
            list.add(user);
        }
        return list;
    }

    /**
     * This method is responsible to help 'getUserList' create the sql query from the arguments
     * @param tableName
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param orderBy
     * @return
     */
    private static String createSQLQuery(String tableName, String[] projection, String[] selection, String[] selectionArgs, String orderBy){
        String sqlQuery = "SELECT ";
        // set the projection, if null or empty will auto assign '*'
        if (projection != null && projection.length > 0){
            if(!projection[0].equals("")) {
                sqlQuery += projection[0] + " ";
            }
            for (int i = 1; i < projection.length; i++) {
                sqlQuery += ", " + projection[i];
            }
        }
        else{
            sqlQuery += "* ";
        }

        sqlQuery += " FROM " + tableName + " ";

        // set the selection. where selection[i] suppose to refer selectionArgs[i]
        if(selection != null && selectionArgs != null && selection.length > 0 && selection.length == selectionArgs.length){
            sqlQuery += "WHERE ";
            if(selection[0] != "" && selectionArgs[0] != "")
                sqlQuery += selection[0] + " " + selectionArgs[0];
            for(int i = 1; i < selection.length; i ++){
                sqlQuery += ", " + selection[i] + " " + selectionArgs[i];
            }
        }

        //set the Order By
        if(orderBy != null && orderBy != ""){
            sqlQuery += " ORDER BY " + orderBy;
        }
        return sqlQuery;
    }


    public static void main(String[] args) {
        java.util.Date today = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(today.getTime());
        DatabaseManager.insert(new User("2","2","3","4","5", sqlDate));
        String[] selection = new String[1];
        selection[0] = DatabaseManager.COLUMN_USERTABLE_USER_NAME;
        String[] selectionArgs = new String[1];
        selectionArgs[0] = "=2";
        String[] projection = new String[2];
        projection[0] = DatabaseManager.COLUMN_USERTABLE_USER_NAME;
        projection[1] = DatabaseManager.COLUMN_USERTABLE_BIRTHDAY;
        List<User> list = DatabaseManager.queryUserTable(projection, selection, selectionArgs, null);
        if(list != null){
            System.out.println("NOT NULL");
            for(User user: list){
                System.out.println(user);
            }
        }
        else{
            System.out.println("NULL");
        }
    }
}
