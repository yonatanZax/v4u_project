package Model;

import Model.Request.Request;
import db.DBResult;
import db.Managers.ATableManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Observable;

public abstract class ACRUDModel<T> extends Observable{

    protected ATableManager tableManager;


    public void setTableManager(ATableManager tableManager) {
        this.tableManager = tableManager;
    }


    public List<T> getAllData(){
        return tableManager.select(null,null,null);
    }

    public void createNewData(T t){
        DBResult result = DBResult.NONE;

        if (t != null) {
            result = tableManager.InsertToTable(t);
            setChanged();
            notifyObservers(result);
        }
    }


    public void deleteDataFromDB(String[][] keys) {
        String where = "";
        for (int i = 0; keys.length > 1 && i < keys.length - 1 ; i++)
            where += keys[i][0] + " = " + "\"" + keys[i][1] + "\"" + " AND \n";
        where += keys[keys.length - 1][0] + " = " + "\"" + keys[keys.length - 1][1] + "\"";
        tableManager.deleteFromTable(where);

    }



    abstract public void updateTable(T t);
    abstract public List<T> readDataFromDB(String[][] listOfKeys);

    //abstract public void deleteDataFromDB(String keys[][]);

    public static int getCurrentTimeStamp() {
        String month = String.valueOf(LocalDateTime.now().getMonthValue());
        String day = String.valueOf(LocalDateTime.now().getDayOfMonth());
        if (month.length() == 1) {
            month = "0" + month;
        }
        if (day.length() == 1) {
            day = "0" + day;
        }
        String date = LocalDateTime.now().getYear() + "" + month + "" + day;
        return Integer.parseInt(date);
    }



}
