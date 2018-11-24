package Model.Vacation;

import Model.ACRUDModel;
import db.DBResult;
import db.Tables.UserTable;
import db.Tables.VacationTable;

import java.util.List;

public class VacationModel extends ACRUDModel<Vacation> {

    public VacationModel(){
        super.setTableManager(VacationTable.getInstance());}


    @Override
    public void updateTable(Vacation vacation) {
        DBResult result = DBResult.NONE;
        String [] whereFields = {VacationTable.COLUMN_VACATIONTABLE_KEY};
        String [] whereValues = {vacation.getVacationKey()};

        // Todo - implement set and values
        String [] set = {VacationTable.COLUMN_VACATIONTABLE_KEY,
                         VacationTable.COLUMN_VACATIONTABLE_SELLERKEY,
                         VacationTable.COLUMN_VACATIONTABLE_ORIGIN,
                         VacationTable.COLUMN_VACATIONTABLE_DESTINATION,
                         VacationTable.COLUMN_VACATIONTABLE_TIMESTAMP,
                         VacationTable.COLUMN_VACATIONTABLE_VISIBLE};
        String [] values = {vacation.getVacationKey(),
                            vacation.getSellerKey(),
                            vacation.getOrigin(),
                            vacation.getDestination(),
                            String.valueOf(vacation.getTimeStamp()),
                            String.valueOf(vacation.isVisible())};


        result = tableManager.updateData(set , values, whereFields,whereValues);
        setChanged();
        notifyObservers(result);
    }

    @Override
    public List<Vacation> readDataFromDB(String[][] listOfKeys) {
        return null;
    }

    /*@Override
    public void deleteDataFromDB(String[][] keys) {
        String where = keys[keys.length - 1][0] + " = " + "\"" + keys[keys.length - 1][1] + "\"";
        DBResult result = vacationTable.deleteFromTable(where);
        setChanged();
        notifyObservers(result);
    }

    public void deleteVacation(String key){
        String[][] keys = {{vacationTable.COLUMN_VACATIONTABLE_KEY,key}};
        deleteDataFromDB(keys);
    }*/
}
