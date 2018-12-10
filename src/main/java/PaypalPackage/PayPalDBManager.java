package PaypalPackage;

import db.DBResult;
import db.Managers.IDBManager;

import java.sql.*;

public class PayPalDBManager implements IDBManager{

    protected static final String DATABASE = "paypal.db";
    protected static final String PATH_DB = "jdbc:sqlite:" + System.getProperty("user.dir") + "/db/" + DATABASE;

    public PayPalDBManager(){}

    private DBResult createDatabase() {
        DBResult result = DBResult.NONE;

        Connection connection = connect();
        if (connection != null) {
            try {
                DatabaseMetaData meta = connection.getMetaData();
                result = DBResult.DATABASE_CREATED;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                result = DBResult.ERROR;
            } finally {
                if (closeConnection(connection) != DBResult.CONNECTION_CLOSED)
                    result = DBResult.ERROR;
            }
        }
        return result;
    }


    public DBResult createTable(String sql) {
        DBResult result = DBResult.NONE;
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

}
