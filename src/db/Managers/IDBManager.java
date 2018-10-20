package db.Managers;

import java.sql.Connection;

public interface IDBManager {

    DBResult createTable(String tableName, String[] tableParameters);

    Connection connect();

    DBResult closeConnection(Connection connection);

}
