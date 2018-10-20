package db.Managers;

import java.util.List;

public interface ITableManager<T> {

    boolean createTable();

    boolean InsertToTable(T object);

    boolean DeleteFromTable(String id);

    List<T> select(String projection, String selection, String orderBy);

    int updateData(String where, String set);

}
