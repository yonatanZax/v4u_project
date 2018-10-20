package db.Managers;

import java.util.List;

public interface ITableManager<T> {

    DBResult createTable();

    DBResult InsertToTable(T object);

    DBResult DeleteFromTable(String id);

    List<T> select(String projection, String selection, String orderBy);

    DBResult updateData(String where, String set);

}
