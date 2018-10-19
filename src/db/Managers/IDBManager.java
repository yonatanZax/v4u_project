package db.Managers;

import java.sql.Connection;

public interface IDBManager {

    boolean createTable(String tableName, String[] tableParameters);

    Connection connect();

    boolean closeConnection(Connection connection);

}
