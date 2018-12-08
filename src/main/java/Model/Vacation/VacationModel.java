package Model.Vacation;

import Model.ACRUDModel;
import Model.User.UserModel;
import db.DBResult;
import db.Tables.VacationTable;

import java.time.LocalDateTime;
import java.util.List;

public class VacationModel extends ACRUDModel<Vacation> {


    private int getCurrentTimeStamp() {
        String date = LocalDateTime.now().getYear() + "" + LocalDateTime.now().getMonthValue() + "" + LocalDateTime.now().getDayOfMonth();
        return Integer.parseInt(date);
    }

    public VacationModel() {
        super.setTableManager(VacationTable.getInstance());
    }


    public void insertVacationToTable(String destination, double price) {
        String userName = UserModel.getUserName();
        Vacation vacation = new Vacation(null, userName, "TLV", destination, true, getCurrentTimeStamp(), price);
        createNewData(vacation);
    }

    @Override
    public void updateTable(Vacation vacation) {
        DBResult result = DBResult.NONE;
        String [] whereFields = {VacationTable.COLUMN_VACATIONTABLE_KEY};
        String [] whereValues = {vacation.getVacationKey()};

        // Todo - implement set and values -> DONE
        String [] set = {VacationTable.COLUMN_VACATIONTABLE_KEY,
                VacationTable.COLUMN_VACATIONTABLE_SELLERKEY,
                VacationTable.COLUMN_VACATIONTABLE_PRICE,
                VacationTable.COLUMN_VACATIONTABLE_ORIGIN,
                VacationTable.COLUMN_VACATIONTABLE_DESTINATION,
                VacationTable.COLUMN_VACATIONTABLE_TIMESTAMP,
                VacationTable.COLUMN_VACATIONTABLE_VISIBLE};
        String [] values = {vacation.getVacationKey(),
                vacation.getSellerKey(),
                String.valueOf(vacation.getPrice()),
                vacation.getOrigin(),
                vacation.getDestination(),
                String.valueOf(vacation.getTimeStamp()),
                String.valueOf(vacation.isVisible())};


        result = tableManager.updateData(set , values, whereFields,whereValues);
        setChanged();
        notifyObservers(result);
    }

    public String getUserName() {
        return UserModel.getUserName();
    }

    @Override
    public List<Vacation> readDataFromDB(String[][] parameters) {
        //TODO - implement --> BETTER!
        String selection = parameters[0] + " IN (";
        for (int i=0 ; i < parameters.length - 1; i++) {
            selection += "\"" + parameters[i][1] + "\",";
        }
        selection += "\"" + parameters[parameters.length-1][1] + "\")";

        // Get the list of users from the database
        return tableManager.select(null,selection,null);
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
