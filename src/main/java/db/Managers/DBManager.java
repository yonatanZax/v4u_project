package db.Managers;
import db.DBResult;

import java.sql.*;

public class DBManager implements IDBManager {

    private static DBManager ourInstance;

    public static DBManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new DBManager();
            ourInstance.createDatabase();
        }
        return ourInstance;
    }

    protected static final String DATABASE = "v4u.db";
    protected static final String PATH_DB = "jdbc:sqlite:" + System.getProperty("user.dir") + "/db/" + DATABASE;


    private DBResult createDatabase() {
        DBResult result = DBResult.NONE;

        Connection connection = connect();
        if(connection != null) {
            try {
                DatabaseMetaData meta = connection.getMetaData();
                result = DBResult.DATABASE_CREATED;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                result = DBResult.ERROR;
            }finally {
                if (closeConnection(connection) != DBResult.CONNECTION_CLOSED)
                    result = DBResult.ERROR;
            }
        }
        return result;
    }

    @Override
    public DBResult createTable(String tableName, String[] tableParameters) {
        DBResult result = DBResult.NONE;
        String sql = getCreateTableSQLSting(tableName,tableParameters);
        Connection conn = connect();
        if(conn != null && sql != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                // createUser a new table
                stmt.execute();
                stmt.close();
                result = DBResult.TABLE_CREATED;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                result = DBResult.ERROR;
            } finally {
                if (closeConnection(conn) != DBResult.CONNECTION_CLOSED)
                    result = DBResult.ERROR;
            }
        }
        return result;
    }

    private String getCreateTableSQLSting(String tableName, String[] tableParameters) {
        if(tableName != null && tableParameters != null && !tableName.equals("") && tableParameters.length > 1) {
            String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n";
            sql += tableParameters[0];
            for (int i = 1; i < tableParameters.length; i++) {
                sql += ",\n" + tableParameters[i];
            }
            sql += ");";
            return sql;
        }
        return null;
    }


    @Override
    public Connection connect() {
        String url = PATH_DB;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @Override
    public DBResult closeConnection(Connection connection) {
        DBResult result = DBResult.NONE;
        try {
            connection.close();
            result = DBResult.CONNECTION_CLOSED;
        } catch (SQLException e) {
            e.printStackTrace();
            result = DBResult.ERROR;
        }
        return result;
    }


    public static void main(String[] args) {

    }
}
