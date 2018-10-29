package db.Managers;

import db.DBResult;

import java.util.List;

public interface ITableManager<T> {

    DBResult createTable();

    DBResult InsertToTable(T object);

    DBResult deleteFromTable(String id);

    List<T> select(String projection, String selection, String orderBy);

    DBResult updateData(String [] set, String [] values, String [] where);

}
