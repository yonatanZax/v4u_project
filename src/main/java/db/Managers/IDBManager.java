package db.Managers;

import db.DBResult;

import java.sql.Connection;

public interface IDBManager {

    DBResult createTable(String tableName, String[] primaryKeys, String[] foreignKeys, String[] stringFields,String[] intFields, String[] doubleFields);

    Connection connect();

    DBResult closeConnection(Connection connection);

}
