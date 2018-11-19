package Model;

import db.DBResult;
import db.Managers.ATableManager;

import java.util.List;
import java.util.Observable;

public abstract class ACRUDModel<T> extends Observable{

    private ATableManager TableManager;


    public void setTableManager(ATableManager tableManager) {
        TableManager = tableManager;
    }

    public void createNewData(T t){
        DBResult result = DBResult.NONE;

        if (t != null) {
            result = TableManager.InsertToTable(t);
            setChanged();
            notifyObservers(result);
        }
    }


    public List<T> readDataFromDB(String[] listOfKeys){
        // Generates the selection part
        // Example:     SELECT * FROM <TableName> WHERE key IN ("key1","key2")
        String selection = TableManager.COLUMN_TABLE_KEY + " IN (";
        for (int i=0 ; i < listOfKeys.length - 1; i++) {
            selection += "\"" + listOfKeys[i] + "\",";
        }
        selection += "\"" + listOfKeys[listOfKeys.length-1] + "\")";

        // Get the list of users from the database
        List<T> dataList = TableManager.select(null,selection,null);
        return dataList;
    }



    abstract public void updateTable(T t);



    public void deleteDataFromDB(String key) {
        DBResult result = TableManager.deleteFromTable(TableManager.COLUMN_TABLE_KEY + " = " + "\"" + key + "\"");
        setChanged();
        notifyObservers(result);
    }


}
