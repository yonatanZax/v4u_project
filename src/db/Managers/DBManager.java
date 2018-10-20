package db.Managers;
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

    /**
     * TODO - add if exists for the db
     * Connect to a MainPackage database
     * currently works only with 'DatabaseManager.DATABASE'
     */
    private boolean createDatabase() {

        Connection connection = connect();
        boolean worked = false;
        if(connection != null) {
            try {
                DatabaseMetaData meta = connection.getMetaData();
                worked =  true;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }finally {
                closeConnection(connection);
            }
        }
        return worked;
    }

    @Override
    public boolean createTable(String tableName, String[] tableParameters) {
        String sql = getCreateTableSQLSting(tableName,tableParameters);
        Connection conn = connect();
        boolean worked = false;
        if(conn != null && sql != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                // createUser a new table
                stmt.execute();
                stmt.close();
                worked = true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                closeConnection(conn);
            }
        }
        return worked;
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
    public boolean closeConnection(Connection connection) {
        boolean worked = false;
        try {
            connection.close();
            worked = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return worked;
    }
}
