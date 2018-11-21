package Model;

import Model.User.User;
import db.DBResult;
import db.Managers.ATableManager;
import db.Tables.UserTable;

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






    abstract public void updateTable(T t);
    abstract public List<T> readDataFromDB(String[] listOfKeys);
    abstract public void deleteDataFromDB(String keys[][]);





}
