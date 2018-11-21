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
    public DBResult createTable(String tableName, String[] primaryKeys, String[] foreignKeys, String[] stringFields,String[] intFields, String[] doubleFields) {
        DBResult result = DBResult.NONE;
        String sql = getCreateTableSQLString(tableName,primaryKeys, foreignKeys, stringFields, intFields, doubleFields);
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




//
//CREATE TABLE IF NOT EXISTS requestInfo (
//  vacationKey TEXT NOT NULL,
//  sellerKey TEXT NOT NULL,
//  buyerKey TEXT NOT NULL,
//  approved TEXT NOT NULL,
//  timestamp INTEGER NOT NULL,
//  primary key (vacationKey,sellerKey,buyerKey),
//  foreign key (vacationKey) references vacationInfo(key)
//  );




    private String getCreateTableSQLString(String tableName, String[] primaryKeys, String[] foreignKeys, String[] stringFields,String[] intFields, String[] doubleFields) {
        if(tableName != null && primaryKeys != null && !tableName.equals("")) {
            String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n";
            for (String key: primaryKeys)
                sql += key + " TEXT NOT NULL,\n";
            for (String str: stringFields)
                sql += str + " TEXT NOT NULL,\n";
            for (String i: intFields)
                sql += i + " INTEGER NOT NULL,\n";
            for (String d: doubleFields)
                sql += d + " REAL NOT NULL,\n";

            sql += "primary key (";
            for (String primaryKey: primaryKeys)
                sql += primaryKey + ",";

            sql = sql.substring(0, sql.length() - 1);
            sql += ')';
            if (foreignKeys.length > 0)
                sql += ',';

            // foreign key (house_id) references houses(id),
            for (String foreignKey: foreignKeys)
                sql += "foreign key " + foreignKey + "\n";

            sql += "\n);";
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
