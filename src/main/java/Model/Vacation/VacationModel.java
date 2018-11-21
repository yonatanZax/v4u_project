package Model.Vacation;

import Model.ACRUDModel;
import db.DBResult;
import db.Tables.UserTable;
import db.Tables.VacationTable;

import java.util.List;

public class VacationModel extends ACRUDModel<Vacation> {

    private VacationTable vacationTable;

    public VacationModel(){ VacationTable.getInstance();}


    @Override
    public void updateTable(Vacation vacation) {
        DBResult result = DBResult.NONE;
        String [] where = {vacationTable.COLUMN_VACATIONTABLE_KEY};

        // Todo - implement set and values
        String [] set = {vacationTable.COLUMN_VACATIONTABLE_KEY};
        String [] values = {vacation.getVacationKey()};


        result = vacationTable.updateData(set , values, where);
        setChanged();
        notifyObservers(result);
    }

    @Override
    public List<Vacation> readDataFromDB(String[] listOfKeys) {
        return null;
    }

    @Override
    public void deleteDataFromDB(String[][] keys) {
        String where = keys[keys.length - 1][0] + " = " + "\"" + keys[keys.length - 1][1] + "\"";
        DBResult result = vacationTable.deleteFromTable(where);
        setChanged();
        notifyObservers(result);
    }

    public void deleteVacation(String key){
        String[][] keys = {{vacationTable.COLUMN_VACATIONTABLE_KEY,key}};
        deleteDataFromDB(keys);
    }
}
