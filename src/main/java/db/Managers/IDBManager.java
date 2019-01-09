package db.Managers;

import db.DBResult;

import java.sql.Connection;

public interface IDBManager {

    DBResult createTable(String sql);

    Connection connect();

    DBResult closeConnection(Connection connection);

}
