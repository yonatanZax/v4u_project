package Model.Vacation;

import Model.ACRUDModel;
import Model.User.UserModel;
import db.DBResult;
import db.Tables.VacationTable;

import java.time.LocalDateTime;
import java.util.List;

public class VacationModel extends ACRUDModel<Vacation> {

    public VacationModel() {
        super.setTableManager(VacationTable.getInstance());
    }


    public void insertVacationToTable(String destination, double price, int departure, boolean isExchangeable) {
        String userName = UserModel.getUserName();
        Vacation vacation = new Vacation(null, userName, "TLV", destination, true, getCurrentTimeStamp(), price, departure, isExchangeable);
        createNewData(vacation);

    }

    @Override
    public void updateTable(Vacation vacation) {
        DBResult result = DBResult.NONE;
        String [] whereFields = {VacationTable.COLUMN_VACATIONTABLE_KEY};
        String [] whereValues = {vacation.getVacationKey()};

        String [] set = {VacationTable.COLUMN_VACATIONTABLE_KEY,
                VacationTable.COLUMN_VACATIONTABLE_SELLERKEY,
                VacationTable.COLUMN_VACATIONTABLE_PRICE,
                VacationTable.COLUMN_VACATIONTABLE_ORIGIN,
                VacationTable.COLUMN_VACATIONTABLE_DESTINATION,
                VacationTable.COLUMN_VACATIONTABLE_TIMESTAMP,
                VacationTable.COLUMN_VACATIONTABLE_VISIBLE,
                VacationTable.COLUMN_VACATIONTABLE_EXCHANGEABLE,
                VacationTable.COLUMN_VACATIONTABLE_DEPARTUREDATE};
        String [] values = {vacation.getVacationKey(),
                vacation.getSellerKey(),
                String.valueOf(vacation.getPrice()),
                vacation.getOrigin(),
                vacation.getDestination(),
                String.valueOf(vacation.getTimeStamp()),
                String.valueOf(vacation.isVisible()),
                String.valueOf(vacation.isExchangeable()),
                String.valueOf(vacation.getDepartureDate())};


        result = tableManager.updateData(set , values, whereFields,whereValues);
        setChanged();
        notifyObservers(result);
    }

    public String getUserName() {
        return UserModel.getUserName();
    }

    @Override
    public List<Vacation> readDataFromDB(String[][] parameters) {
        String selection = parameters[0][0] + " IN (";
        for (int i=0 ; i < parameters[1].length - 1; i++) {
            selection += "\"" + parameters[1][i] + "\",";
        }
        selection += "\"" + parameters[1][parameters[1].length-1] + "\")";

        // Get the list of users from the database
        return tableManager.select(null,selection,null);
    }
}
